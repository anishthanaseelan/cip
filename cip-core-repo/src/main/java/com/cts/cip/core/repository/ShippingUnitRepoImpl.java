/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 *   Contributors:
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *******************************************************************************/
package com.cts.cip.core.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.ShippingDocument;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.core.mapper.ShippingDocumentMapper;
import com.cts.cip.core.mapper.ShippingUnitMapper;
import com.cts.cip.core.repo.constants.CIPCoreConstants;
import com.cts.cip.core.repository.entities.Document;
import com.cts.cip.core.repository.entities.ShippingUnitEntity;
import com.cts.cip.core.repository.entities.TransactionLog;

/**
 * @author 
 *
 */
@Repository
public class ShippingUnitRepoImpl implements ShippingUnitRepo {

	Logger logger = LoggerFactory.getLogger(ShippingUnitRepoImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ShippingUnitMapper mapper;
	
	@Autowired
	private ShippingDocumentMapper shipDocMapper;

	@Override
	public ShippingUnit findById(Integer id) {
		ShippingUnit shippingUnit;
		ShippingUnitEntity shippingUnitEntity = em.find(ShippingUnitEntity.class, id);
		shippingUnit = mapper.mapToModel(shippingUnitEntity);
		return shippingUnit;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ShippingUnit updateStatus(ShippingUnit shippingUnit) throws SystemFailure {
		ShippingUnitEntity shippingUnitEntity = em.find(ShippingUnitEntity.class,
				shippingUnit.getShippingUnitBase().getId());
		if (shippingUnitEntity != null) {
			shippingUnitEntity.setStatus(shippingUnit.getShippingUnitBase().getState().toString());
			em.merge(shippingUnitEntity);
		}else {
			throw new SystemFailure("Shiping unit not found with ID " + shippingUnit.getShippingUnitBase().getId());
		}

		return shippingUnit;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)

	public ShippingUnit save(ShippingUnit model) {

		 ShippingUnitEntity entity = mapper.mapToEntity(model);
			entity = em.merge(entity);
			model.getShippingUnitBase().setId(entity.getId());
		return model;
	}

	@Override
	public List<ShippingUnit> findById(String id) throws ResourceNotExistException {
		List<ShippingUnit> shippingUnits = new ArrayList<>();
		TypedQuery<ShippingUnitEntity> query = em.createQuery(
				"SELECT su FROM ShippingUnitEntity su where su.referanceId = :referanceId", ShippingUnitEntity.class);
		query.setParameter("referanceId", id);
		List<ShippingUnitEntity> shippingUnitEntities = query.getResultList();

		if (shippingUnitEntities != null && !shippingUnitEntities.isEmpty()) {
			for (ShippingUnitEntity suEntity : shippingUnitEntities) {
				shippingUnits.add(mapper.mapToModel(suEntity));
			}
		} else {
			throw new ResourceNotExistException("Shiping unit with ID " + id + " not found");
		}
		return shippingUnits;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public ShippingUnit update(ShippingUnit shippingUnit) {

		ShippingUnitEntity shippingUnitEntity = em.find(ShippingUnitEntity.class,
				shippingUnit.getShippingUnitBase().getId());
		if (shippingUnitEntity != null) {
			shippingUnitEntity.setTrackingNumber(shippingUnit.getTrackingNumber());
			em.merge(shippingUnitEntity);
		}
		return shippingUnit;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShippingUnit> findByPage(Integer pageNumber, Integer pageSize) {

		List<ShippingUnit> shippingUnits = new ArrayList<>();
		List<ShippingUnitEntity> entities;
		logger.debug(" findByPage Parameters : " + pageNumber + " : " + pageSize);
		Query query = em.createQuery("From ShippingUnitEntity order by id desc");
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);

		entities = query.getResultList();

		for (ShippingUnitEntity entity : entities) {
			shippingUnits.add(mapper.mapToModel(entity));
		}

		return shippingUnits;

	}

	@Override
	public List<ShippingUnit> findAll() {
		TypedQuery<ShippingUnitEntity> query = em.createNamedQuery("ShippingUnitEntity.findAll",
				ShippingUnitEntity.class);
		List<ShippingUnit> shippingUnits = new ArrayList<>();
		query.setMaxResults(CIPCoreConstants.MAX_RECORD_COUNT);
		List<ShippingUnitEntity> entities = query.getResultList();
		for (ShippingUnitEntity entity : entities) {
			shippingUnits.add(mapper.mapToModel(entity));
		}
		return shippingUnits;
	}

	@Override
	public Integer findTotalCount() {
		Query queryTotal = em.createQuery("Select count(su.id) from ShippingUnitEntity su");
		long countResult = (long) queryTotal.getSingleResult();
		return (int) countResult;

	}

	@Override
	public List<ShippingUnit> findByTrackingNum(String trackingNumber) throws ResourceNotExistException{

		List<ShippingUnit> shippingUnits = new ArrayList<>();
		TypedQuery<ShippingUnitEntity> query = em.createQuery(
				"SELECT su FROM ShippingUnitEntity su where su.trackingNumber = :trackingNumber", ShippingUnitEntity.class);
		query.setParameter("trackingNumber", trackingNumber);
		List<ShippingUnitEntity> shippingUnitEntities = query.getResultList();

		if (shippingUnitEntities != null && !shippingUnitEntities.isEmpty()) {
			for (ShippingUnitEntity suEntity : shippingUnitEntities) {
				shippingUnits.add(mapper.mapToModel(suEntity));
			}
		} else {
			throw new ResourceNotExistException("Shiping unit with trackingNumber  " + trackingNumber + " not found");
		}
		return shippingUnits;
	
	}

	@Override
	public String getCarrierServiceNameByCode(String carrierServiceCode) {
		
		String carrierName = "";
		if(carrierServiceCode!=null && !carrierServiceCode.isEmpty()){
		Query q = em
				.createNativeQuery(
						"select cs.service_name from carrier_service cs where cs.code= :carrierServiceCode")
				.setParameter("carrierServiceCode", StringUtils.trim(carrierServiceCode));		
		List<String> carrierNameList = q.getResultList();		
			if (carrierNameList!=null && !carrierNameList.isEmpty()) {
				logger.debug("Carrier Service name is not empty: " + carrierNameList.size() );
				carrierName = carrierNameList.get(0);
			}
		}
		return carrierName;
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override	
	public void  saveTransaction(TransactionLog requestLog) {
		em.persist(requestLog);	
	}

	@Override	
	public List<TransactionLog> findTransactionsById(String id, String eventOrigin) {		
		TypedQuery<TransactionLog> query = em.createQuery(
				"SELECT tl FROM TransactionLog tl where tl.referenceId = :referenceId", TransactionLog.class);
		query.setParameter("referenceId", id );
		return query.getResultList();		
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.core.repository.dao.ShippingUnitRepo#getDocuments(java.lang.String)
	 */
	@Override
	public List<ShippingDocument> getDocuments(String shippingUnitId){
		List<ShippingDocument> shipDocs = null;
		TypedQuery<Document> query = em.createQuery(
				"SELECT doc FROM Document doc where doc.id.shippingUnitId = :shippingUnitId", Document.class);
		query.setParameter("shippingUnitId", shippingUnitId );
		List<Document> documentEntities = query.getResultList();
		if (documentEntities != null && !documentEntities.isEmpty()) { 
			shipDocs = new ArrayList<ShippingDocument>(0);
			for(Document document : documentEntities) {
				shipDocs.add(shipDocMapper.mapToModel(document));
			}
		}
		return shipDocs;
	}
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void persistDocuments(String shippingUnitId, List<ShippingDocument> docList) throws SystemFailure {
		try {
			for (ShippingDocument shippingDoc : docList) {
				logger.debug("ShippingUnitRepoImpl :: persistDocuments - "+shippingDoc.getUrl());
				shippingDoc.setDocId(shippingUnitId);
				em.merge(shipDocMapper.mapToEntity(shippingDoc));
			}
		}catch (Exception ex){
			logger.error("Exception occured while persisting the shipping document infomation:"+ ex);
			throw new SystemFailure(ex.getMessage());
		}
		return ;
	}


}

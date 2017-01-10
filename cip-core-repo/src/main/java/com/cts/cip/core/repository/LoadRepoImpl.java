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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.FilterCriteria;
import com.cts.cip.common.dto.LoadInfo;
import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.exceptions.MultipleResourceExistException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.core.mapper.CarrierMapper;
import com.cts.cip.core.mapper.LoadMapper;
import com.cts.cip.core.repository.entities.LoadEntity;
import com.cts.cip.core.repository.entities.LoadShippingUnit;
import com.cts.cip.core.repository.entities.LoadShippingUnitPK;
import com.cts.cip.rule.repository.entity.Carrier;
import com.cts.cip.rule.repository.entity.CarrierService;

@Repository
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class LoadRepoImpl implements LoadRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cts.cip.core.repository.dao.LoadRepo#findById(java.lang.Integer)
	 */

	Logger logger = LoggerFactory.getLogger(LoadRepoImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	ShippingUnitRepo repo;

	@Autowired
	LoadMapper mapper;

	@Autowired
	CarrierMapper carrierMapper;

	@Override
	public Load findById(Integer id) throws ResourceNotExistException {
		LoadEntity loadEntity = em.find(LoadEntity.class, id);
		if (loadEntity == null) {
			throw new ResourceNotExistException("The load " + id + " does not exist ");
		}
		return mapper.mapToModel(loadEntity);
	}

	@Override
	public List<Load> findByRefId(String loadReferenceId) throws ResourceNotExistException {
		List<Load> loads = new ArrayList<Load>();
		try {
			List<LoadEntity> entities = em
					.createQuery("SELECT ld FROM LoadEntity ld where ld.referenceId = :referenceId", LoadEntity.class)
					.setParameter("referenceId", loadReferenceId).getResultList();
			if (entities.isEmpty()) {
				throw new ResourceNotExistException("Load " + loadReferenceId + " does not exist");
			} else {
				for (LoadEntity entity : entities) {
					loads.add(mapper.mapToModel(entity));
				}
			}
		} catch (Exception e) {
			logger.error("Problem while getting load information based on loadref id:" + e);
		}
		return loads;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Load create(Load load) {
		LoadEntity entity = mapper.mapToEntity(load);
		em.persist(entity);

		return mapper.mapToModel(entity);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Load addSU(Load activeLoad, ShippingUnitBase shippingUnit) {
		LoadShippingUnit entity = new LoadShippingUnit();

		entity.setLoadId(activeLoad.getLoadDetails().getId());
		entity.setShippingUnitRefId(shippingUnit.getReferenceID());
		em.persist(entity);

		return mapper.mapToModel(em.find(LoadEntity.class, entity.getLoadId()));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Load removeSU(Load activeLoad, ShippingUnitBase shippingUnit) throws ResourceNotExistException {

		LoadShippingUnitPK id = new LoadShippingUnitPK();

		id.setLoadUnitId(activeLoad.getLoadDetails().getId());
		id.setShippingUnitRefId(shippingUnit.getReferenceID());

		LoadShippingUnit entity = em.find(LoadShippingUnit.class, id);

		if (entity == null)
			throw new ResourceNotExistException("The given shipping unit " + shippingUnit.getReferenceID()
					+ " is not associated to the " + activeLoad.getLoadDetails().getLoadReferenceId());

		em.remove(entity);

		return activeLoad;
	}

	@Override
	public Load removeSU(Load activeLoad, List<ShippingUnitBase> shippingUnits) throws ResourceNotExistException {
		for (ShippingUnitBase suBase : shippingUnits) {
			removeSU(activeLoad, suBase);
		}
		return findById(activeLoad.getLoadDetails().getId());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public Load updateStatus(Load activeLoad) {

		LoadEntity entity = em.find(LoadEntity.class, activeLoad.getLoadDetails().getId());

		entity.setStatus(activeLoad.getLoadDetails().getLoadState().value());
		entity.setManifestDate(new Date());

		entity = em.merge(entity);

		return mapper.mapToModel(entity);
	}

	@Override
	public Load findBySURefId(String suRefId) throws ResourceNotExistException, MultipleResourceExistException {
		List<LoadShippingUnit> entities = null;
		try {
			entities = em.createQuery("SELECT lsu FROM LoadShippingUnit lsu where lsu.shippingUnitRefId = :suRefId",
					LoadShippingUnit.class).setParameter("suRefId", suRefId).getResultList();
		} catch (Exception exception) {
			entities=null;
			logger.error("Exception while invoking a method findBySURefId(): ",exception);
		}
		
		if ( entities == null || entities.isEmpty()) {
			throw new ResourceNotExistException("No Load found for SU" + suRefId);
		} else if (entities.size() > 1) {
			throw new MultipleResourceExistException(
					"ShippingUnit " + suRefId + " is associated with " + entities.size() + "loads");
		} else if (entities.get(0).getLoad() == null) {
			throw new ResourceNotExistException(
					"This looks to be a bad data : Load association seems to be missing for " + suRefId);
		}

		return mapper.mapToModel(entities.get(0).getLoad());
	}

	@Override
	public List<Load> findbyPage(SearchCriteria searchCriteria) throws ResourceNotExistException {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<LoadEntity> criteriaQuery = criteriaBuilder.createQuery(LoadEntity.class);
		Root<LoadEntity> loadEntity = criteriaQuery.from(LoadEntity.class);

		if (searchCriteria == null) {
			throw new ResourceNotExistException("Invalid search Criteria");
		}
		// Apply all the filter criteria
		if (searchCriteria.getFilterCriteria() != null) {
			List<Predicate> predicates = new ArrayList<>();
			for (FilterCriteria filterCriteria : searchCriteria.getFilterCriteria()) {
				predicates.add(criteriaBuilder.like(
						loadEntity.<String> get(getFilterCriteriaMap().get(filterCriteria.getFieldName())),
						filterCriteria.getFieldValue()));
			}
			criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
		}

		// Apply the sorting logic
		if (searchCriteria.getSortDirection() != null && searchCriteria.getSortField() != null) {
			String sortField = getFilterCriteriaMap().get(searchCriteria.getSortField());
			if (searchCriteria.getSortDirection().equals("asc")) {
				criteriaQuery.orderBy(criteriaBuilder.asc(loadEntity.get(sortField)));
			} else if (searchCriteria.getSortDirection().equals("desc")) {
				criteriaQuery.orderBy(criteriaBuilder.desc(loadEntity.get(sortField)));
			}
		}

		Query query = em.createQuery(criteriaQuery);
		query.setFirstResult((searchCriteria.getPageNumber() - 1) * searchCriteria.getPageSize());
		query.setMaxResults(searchCriteria.getPageSize());
		List<LoadEntity> loadEntityList = query.getResultList();
		List<Load> loadList = new ArrayList<>();
		for (LoadEntity entity : loadEntityList) {
			Load load = mapper.mapToModel(entity);
			if (load.getShippingUnits() != null) {
				load.getLoadDetails().setTotalUnitCount(load.getShippingUnits().size());
			}
			loadList.add(load);
		}
		if(loadList.isEmpty()) {
			throw new ResourceNotExistException("No loads found for the given search criteria");
		}
		return loadList;

	}

	@Override
	public int findCount() {
		Query queryTotal = em.createQuery("Select count(l.id) from load l");
		long countResult = (long) queryTotal.getSingleResult();
		return (int) countResult;
	}

	@Override
	public Long findCountByCriteria(SearchCriteria searchCriteria) throws ResourceNotExistException {
		logger.debug("Search Criteria for load  listing: " + searchCriteria);
		Long count = 0L;
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaCountQuery = criteriaBuilder.createQuery(Long.class);
		Root<LoadEntity> loadEntity = criteriaCountQuery.from(LoadEntity.class);

		if (searchCriteria == null) {
			throw new ResourceNotExistException("Invalid search Criteria");
		}
		Predicate predicate = criteriaBuilder.conjunction();
		// Apply all the filter criteria
		if (searchCriteria.getFilterCriteria() != null) {
			for (FilterCriteria filterCriteria : searchCriteria.getFilterCriteria()) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.equal(
								loadEntity.<String> get(getFilterCriteriaMap().get(filterCriteria.getFieldName())),
								filterCriteria.getFieldValue()));
			}
		}
		criteriaCountQuery.select(criteriaBuilder.count(loadEntity)).where(predicate);
		// .where(predicate);
		count = em.createQuery(criteriaCountQuery).getSingleResult();
		logger.debug("Load Search Criteria Count: " + count);
		return count;

	}

	@Override
	public LoadInfo findLoadInfoById(int id) throws ResourceNotExistException {
		LoadEntity loadEntity = em.find(LoadEntity.class, id);
		if (loadEntity == null) {
			throw new ResourceNotExistException("The load " + id + " does not exist ");
		}
		LoadInfo loadInfo = mapper.mapToLoadInfo(loadEntity);

		return loadInfo;

	}

	@Override
	public LoadInfo findLoadInfoByName(String loadName) throws ResourceNotExistException {
		LoadInfo loadInfo;
		List<LoadEntity> entities = em
				.createQuery("SELECT ld FROM LoadEntity ld where ld.referenceId = :referenceId", LoadEntity.class)
				.setParameter("referenceId", loadName).getResultList();
		if (entities.isEmpty()) {
			throw new ResourceNotExistException("Load " + loadName + " does not exist");
		} else {
			loadInfo = mapper.mapToLoadInfo(entities.get(0));
		}
		return loadInfo;
	}

	private Map<String, String> getFilterCriteriaMap() {
		Map<String, String> filterCriteriaMap = new HashMap<String, String>();
		filterCriteriaMap.put("Load.loadReferenceId", "referenceId");
		filterCriteriaMap.put("Load.loadState", "status");
		filterCriteriaMap.put("Load.createDate", "createDate");
		filterCriteriaMap.put("Load.manifestDate", "manifestDate");
		filterCriteriaMap.put("Load.id", "id");
		return filterCriteriaMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cts.cip.core.repository.dao.LoadRepo#findCarrierByShipVia(int)
	 */
	@Override
	public CarrierInfo findCarrierByServiceTypeCd(String serviceTypeCd)
			throws SystemFailure, ResourceNotExistException {
		Carrier carrier = null;
		try {

			Query query = em.createQuery("Select cs From CarrierService cs where cs.code =:serviceTypeCd",
					CarrierService.class);
			query.setParameter("serviceTypeCd", serviceTypeCd);
			CarrierService carrierService = (CarrierService) query.getSingleResult();
			carrier = carrierService.getCarrier();
			if (carrier == null)
				throw new SystemFailure("No Carrier information.");

		} catch (NoResultException e) {
			logger.error("Empty Results:" + e);
			throw new ResourceNotExistException("No Carrier Service details found:" + e.getMessage());
		}
		return carrierMapper.mapToModel(carrier);
	}

	@Override
	public CarrierInfo findCarrierById(String carrierId) throws SystemFailure, ResourceNotExistException {
		Carrier carrier = null;
		try {
			Query query = em.createQuery("Select carrier From Carrier carrier where carrier.id =:carrierId",
					Carrier.class);
			query.setParameter("carrierId", Integer.parseInt(carrierId));
			carrier = (Carrier) query.getSingleResult();
			if (carrier == null)
				throw new SystemFailure("No Carrier information.");

		} catch (NoResultException e) {
			logger.error("Empty Results:" + e);
			throw new ResourceNotExistException("No Carrier Service details found:" + e.getMessage());
		}
		return carrierMapper.mapToModel(carrier);
	}
	
	public Map<String,String> getLoadCountAndWeightDetails(Integer loadReferenceId){

		Map<String,String> valueMap = new HashMap<>();
		Query query = em.createQuery("select sum(su.weight) , count(su.referanceId) from ShippingUnitEntity su where su.referanceId in "+
										"(SELECT lsu.shippingUnitRefId FROM LoadShippingUnit lsu where lsu.loadId =:loadId)");
		query.setParameter("loadId", loadReferenceId);
		Object[] results = (Object[])query.getSingleResult();
		if (null != results[0] ){
			BigDecimal bd = BigDecimal.valueOf((Double)results[0]);
			bd.setScale(4);
			valueMap.put("totalWeight", bd.toString());
		}else{
			valueMap.put("totalWeight", "0.0");
		}
			
		valueMap.put("unitCount", ((Long)results[1]).toString());
				
		return valueMap;
	}
	
	public Map<String,String> getManifestCountAndWeightDetails(Integer loadReferenceId){

		Map<String,String> valueMap = new HashMap<>();
		Query query = em.createQuery("select sum(su.weight) , count(su.referanceId) from ShippingUnitEntity su where su.status='CONFIRMED' and su.referanceId in "+
										"(SELECT lsu.shippingUnitRefId FROM LoadShippingUnit lsu where lsu.loadId =:loadId)");
		query.setParameter("loadId", loadReferenceId);
		Object[] results = (Object[])query.getSingleResult();
		BigDecimal bd = BigDecimal.valueOf((Double)results[0]);
		bd.setScale(4);
		valueMap.put("totalWeight", bd.toString());
		valueMap.put("unitCount", ((Long)results[1]).toString());
				
		return valueMap;
	}

}

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
package com.cts.cip.master.repository.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.CarrierService;

@Repository("carrierRepository")
public class CarrierRepositoryImpl implements CarrierRepository {

	Logger logger  =  LoggerFactory.getLogger(CarrierRepository.class);
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(Carrier carrier) {
		// TODO Auto-generated method stub

	}


	@Override
	public List<Carrier> findByPage(int pageNumber, int pageSize,String sortField,String sortDirection) {
		logger.debug("Parameters-1 : " + pageNumber + " : " + pageSize + " : " + sortDirection);
		Query query = null;
		if(!(sortDirection.equalsIgnoreCase("asc")||sortDirection.equalsIgnoreCase("desc"))) {			
			query = em.createQuery("From Carrier order by "+ sortField  + " " + "asc");
		}
		else{
			query = em.createQuery("From Carrier order by "+ sortField  + " " + sortDirection);
		}		
		query.setFirstResult((pageNumber - 1) * pageSize);
		query.setMaxResults(pageSize);		
		List<Carrier> carriersList = query.getResultList();
		return carriersList;

	}

	@Override
	public List<Carrier> findAll() {
		TypedQuery<Carrier> query = em.createNamedQuery("Carrier.findAll", Carrier.class);
		return query.getResultList();
	}

	@Override
	public int getCarriersCount() {
		Query queryTotal = em.createQuery("Select count(c.id) from Carrier c");
		long countResult = (long) queryTotal.getSingleResult();
		return (int) countResult;

	}

	@Override
	public Carrier findById(int id) {		
		return em.find(Carrier.class, id);
	}
	
	@Override
	public CarrierService findByCode(String code) {	
		Query getServiceQuery = em.createQuery("Select cs from CarrierService cs where code = :code",CarrierService.class);
		getServiceQuery.setParameter("code",code);
		return (CarrierService) getServiceQuery.getSingleResult();
	}
	
	@Override 
	public List<String> findActiveServiceCodes() {
		Query getServiceQuery = em.createQuery("Select cs.code from CarrierService cs where status = 1");
		List<String> activeServiceCodes = getServiceQuery.getResultList();
		return activeServiceCodes;
		
	}
	@Transactional
	@Override
	public void update(Carrier carrier)  {				
		em.merge(carrier);
	}
	
}

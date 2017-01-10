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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.core.repository.entities.ShipViaEntity;
import com.cts.cip.core.repository.entities.TransactionLog;


@Repository
public class CommonRepoImpl implements CommonRepo {
	

	Logger logger = LoggerFactory.getLogger(LoadRepoImpl.class);

	@PersistenceContext
	private EntityManager em;


	@Override
	@Transactional
	public void  saveTransaction(TransactionLog requestLog) {
		em.persist(requestLog);	
	}

	@Override	
	public List<TransactionLog> findTransactionsById(String id, String eventOrigin) {		
		TypedQuery<TransactionLog> query = em.createQuery(
				"SELECT tl FROM TransactionLog tl where tl.referenceId = :referenceId", TransactionLog.class);
		query.setParameter("referenceId", id );
		List<TransactionLog> transactionLogEntities = query.getResultList();
		return transactionLogEntities;
	}

	@Override
	public Map<String, String> getShipViaMap() {
		List<ShipViaEntity> result = em.createQuery("from ShipViaEntity", ShipViaEntity.class).getResultList();
		Map<String,String> shipViaMap=null;
		if(result!=null && result.size()>0) {
			shipViaMap = new HashMap<String,String>();
			for(ShipViaEntity shipViaEntity : result) {
				shipViaMap.put(shipViaEntity.getShipVia(), shipViaEntity.getCarrierServiecCode());
			}
		}
		return shipViaMap;
	}

}

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
package com.cts.cip.rule.repository.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cts.cip.rule.repository.entity.CarrierService;
import com.cts.cip.rule.repository.entity.Rule;

import com.cts.cip.rule.repository.entity.RuleType;

@Repository
public class RuleRepositoryImpl implements RuleRepository {

	Logger logger = LoggerFactory.getLogger(RuleRepositoryImpl.class);
	@PersistenceContext
	private EntityManager em;

	public void addRuleType(RuleType rule) {
		em.persist(rule);
	}

	public int findRuleTypeCount() {
		Query queryTotal = em.createQuery("Select count(rt.id) from RuleType rt");
		long countResult = (Long) queryTotal.getSingleResult();
		return (int) countResult;
	}

	public List<RuleType> findAllRuleType() {
		TypedQuery<RuleType> query = em.createNamedQuery("RuleType.findAll", RuleType.class);
		return query.getResultList();
	}
	
	@Override
	public List<Rule> findRulesByCarrierService(String code) {		
		Query getServiceQuery = em.createQuery("SELECT cs FROM CarrierService cs WHERE cs.code=:code",CarrierService.class);
		getServiceQuery.setParameter("code",code);
		CarrierService carrierService =  (CarrierService) getServiceQuery.getSingleResult();		
		
		List<Rule> rulesList = null;
		if (carrierService!=null) {		
			rulesList = new ArrayList<Rule>();
				rulesList = carrierService.getRulesList();
		}
		return rulesList;
	}
	
}

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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.cts.cip.master.repository.entities.Organization;

@Repository("organizationRepository")
public class OrganizationRepositoryImpl implements OrganizationRepository {

	@PersistenceContext
	private EntityManager em;
	
	public Organization getByName(String string) {
		return null;
	}	
	public void remove(Organization organization) {
		em.remove(organization);
	}
	public void update(Organization organization) {
		em.merge(organization);
	}
	public void save(Organization organization) {		
		em.persist(organization);
		
	}
	
	
	
	
	
}

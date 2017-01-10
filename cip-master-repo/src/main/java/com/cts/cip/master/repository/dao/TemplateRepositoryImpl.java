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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cts.cip.master.repository.entities.RuleType;
import com.cts.cip.master.repository.entities.Template;
import com.cts.cip.master.repository.entities.TemplateGroup;

@Repository("templateRepository")
public class TemplateRepositoryImpl implements TemplateRepository {

	Logger logger = LoggerFactory.getLogger(TemplateRepositoryImpl.class);
	@PersistenceContext
	private EntityManager em;

	public List<RuleType> findAllTemplates() {
		TypedQuery<RuleType> query = em.createNamedQuery("Template.findAll", RuleType.class);
		return query.getResultList();
	}

	@Override
	public List<Template> findTemplatesByCarrierService(int carrierServiceCode) {
		logger.debug("Method entry findTemplatesByCarrierService() ");
		Query getTemplatesGroupQuery = em
				.createQuery("Select tg from TemplateGroup tg where carrierServiceCode = " + carrierServiceCode);
		List<TemplateGroup> templateGroups = getTemplatesGroupQuery.getResultList();
		List<Template> templatesList = null;
		for (TemplateGroup templateGroup : templateGroups) {
			if (templateGroup != null) {
				if (templatesList == null) {
					templatesList = new ArrayList<>();
				}
				templatesList.addAll(templateGroup.getTemplateList());
			}
			logger.debug("Total templates retrieved from DB in findTemplatesByCarrierService() is : "
					+ ((templatesList == null) ? 0 : templatesList.size()));
		}

		// TODO Dirty Check.. remove this commented code after testing the above
		// code
		/*
		 * if (templatesList == null && templatesList.isEmpty()) { logger.
		 * debug(" Template list empty from above code due to hibernate entity mapping issue, need to debug later "
		 * );
		 * 
		 * for (TemplateGroup templateGroup : templateGroups) { if
		 * (templateGroup != null) { if (templatesList == null) { templatesList
		 * = new ArrayList<>(); }
		 * templatesList.addAll(getTemplatesbyTemplateId(templateGroup.getId()))
		 * ;
		 * 
		 * } }
		 * 
		 * }
		 */

		logger.debug("Method exit findTemplatesByCarrierService() ");
		return templatesList;
	}

	/*
	 * public List<Template> getTemplatesbyTemplateId(int templateId) {
	 * logger.debug("Method entry getTemplatesbyTemplateId() "); Query
	 * getTemplatesQuery =
	 * em.createQuery("Select t from Template t where id = "+templateId);
	 * List<Template> templates = getTemplatesQuery.getResultList();
	 * logger.debug("templates retrieved from getTemplatesbyTemplateId()... "+
	 * ((templates == null)?0:templates.size()));
	 * logger.debug("Method exit getTemplatesbyTemplateId() "); return
	 * templates; }
	 */

}

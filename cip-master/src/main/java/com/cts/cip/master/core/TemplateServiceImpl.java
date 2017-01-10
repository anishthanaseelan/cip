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
package com.cts.cip.master.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.model.DocumentTemplate;
import com.cts.cip.master.mapper.TemplateModelMapper;
import com.cts.cip.master.repository.dao.MasterDataService;
import com.cts.cip.master.repository.entities.Template;


public class TemplateServiceImpl implements TemplateService {
	
	TemplateModelMapper mapper = new TemplateModelMapper();
	
	Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);
	
	MasterDataService masterDataService;

	public List<DocumentTemplate> findTemplatesByCarrierService(int carrierServiceCode) {
		
		logger.debug("Method entry findTemplatesByCarrierService : " + carrierServiceCode );
		
		List<Template> templateList = masterDataService.findTemplatesByCarrierService(carrierServiceCode);
		DocumentTemplate templateInfo = null;
		List<DocumentTemplate> templateInfoList = new ArrayList<>();
		
		for (Template template : templateList) {
			templateInfo = mapper.toDocTemplate(template);
			templateInfoList.add(templateInfo);
		}
		
		logger.debug("Method exit findTemplatesByCarrierService : " + carrierServiceCode );
				
		return templateInfoList;		
	}

	public MasterDataService getMasterDataService() {
		return masterDataService;
	}

	public void setMasterDataService(MasterDataService masterDataService) {
		this.masterDataService = masterDataService;
	}


}

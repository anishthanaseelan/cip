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
package com.cts.cip.doc.services;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.model.DocumentTemplate;
import com.cts.cip.common.model.FileFormat;


public class MasterServiceImpl implements MasterService{
	
	Logger logger = LoggerFactory.getLogger(MasterService.class);	
	 
	String resource;

	public List<DocumentTemplate> getTempleteByCarrierService(String shipperServiceTypeCode,FileFormat requestedOutputFileFormat) {		
		logger.debug("Inside MasterServiceImpl  ::: method getTempleteByCarrierService "+shipperServiceTypeCode);
		Response response = null;
		List<DocumentTemplate> documentTemplates = null;		
		Client restClient = ClientBuilder.newClient();
		WebTarget childWebTarget = restClient.target(resource+shipperServiceTypeCode);
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();
		if (response.getStatus() == 200) {
			documentTemplates = response.readEntity(new GenericType<List<DocumentTemplate>>() {
			});
		}		
		List<DocumentTemplate> resultTemplates = new ArrayList<>();
		for (DocumentTemplate template : documentTemplates) {
			if (template.getOutputContentType().value().equals(requestedOutputFileFormat.value())) {
				resultTemplates.add(template);
			}
		}		
		return resultTemplates;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}

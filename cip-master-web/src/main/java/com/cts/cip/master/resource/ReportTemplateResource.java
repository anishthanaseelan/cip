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
package com.cts.cip.master.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.model.DocumentTemplate;
import com.cts.cip.master.core.TemplateService;

import io.swagger.annotations.Api;

@Path("/reportTemplates")
@Api(value = "/reportTemplates", description = "Report Templates service")
@Component
public class ReportTemplateResource {

	@Autowired
	TemplateService templateService;

	Logger logger = LoggerFactory.getLogger(ReportTemplateResource.class);

	@GET
	@Path("/test")
	public Response test() {
		String message = "You have reached the ReportTemplateResource end point and the resource is available";
		System.out.println(message);
		logger.debug(message);
		logger.info(message);
		logger.error(message);
		return Response.ok(message).build();
	}

	@GET
	@Produces({ "application/json","application/xml" })	
	@Transactional
	public Response getReportTemplates(@Context HttpHeaders headers, @QueryParam("carrierServiceCode") int carrierServiceCode
			) {

		logger.debug("Method entry getReportTemplates : " + carrierServiceCode );
		
		List<DocumentTemplate> templateList = templateService.findTemplatesByCarrierService(carrierServiceCode);
		
		GenericEntity<List<DocumentTemplate>> genericList = new GenericEntity<List<DocumentTemplate>>(templateList) {
		};
		
		Response response = Response.ok(genericList).header("totalRecords", 1).build();
		
		logger.debug("Method exit getReportTemplates : " + carrierServiceCode );
		return response;

	}
	
}

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
package com.cts.cip.doc.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.cip.common.model.GetDocumentsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.doc.services.DocumentEngineService;

@Path("/documents")
@Component
public class DocumentResource {

	Logger logger = LoggerFactory.getLogger(DocumentResource.class);

	@Autowired
	DocumentEngineService documentEngineService;

	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response createDocuments(@Context HttpHeaders headers, ShippingUnit request) {

		Response response = null;
		CIPResponse<GetDocumentsResponse> createResponse = null;

		if (request == null) {
			return Response.notAcceptable(null).build();
		}

		try {
			createResponse = documentEngineService.create(request);
		} catch (Exception e) {
			response = Response.serverError().build();
			logger.error(e.getMessage(), e);
			return response;
		}

		if (createResponse != null)
			response = Response.ok(createResponse, headers.getMediaType()).build();
		else
			response = Response.serverError().build();

		return response;
	}

}
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

import com.cts.cip.common.dto.CarrierDetail;
import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.common.resource.CIPResourceResponseBuilder;
import com.cts.cip.master.core.MasterCarrierService;

import io.swagger.annotations.Api;

@Path("/carrier")
@Api(value = "/carrier", description = "Carrier service")
@Component
public class CarrierResource {

	@Autowired
	MasterCarrierService masterService;

	Logger logger = LoggerFactory.getLogger(CarrierResource.class);

	@GET
	@Path("/test")
	public String test() {
		String message = "You have reached the carrier resource end point and the resource is available";
		return message;
	}

	@GET
	@Produces({ "application/json","application/xml" })	
	@Transactional
	public Response getCarriersByPage(@Context HttpHeaders headers, @QueryParam("offset") int offset,
			@QueryParam("limit") int limit, @QueryParam("sortField") String sortField,
			@QueryParam("sortDirection") String sortDirection) {

		logger.info(" Get Carrier By Page Info : " + offset + "-" + limit + "-" + sortField + "-" + sortDirection);

		List<CarrierInfo> carrierslist;
		int totalRecords = 0;

		/*
		 * By Default if user sends the offset or size as 0 or not pass the
		 * value all the carriers will be returned
		 */
		if (offset <= 0 || limit <= 0) {
			carrierslist = masterService.findAllCarriers();
			totalRecords = carrierslist.size();
		} else {
			logger.info("Entry for getCarriersByPage");
			carrierslist = masterService.findCarriersByPage(offset, limit,sortField,sortDirection);
			totalRecords = masterService.findCarriersCount();
		}

		GenericEntity<List<CarrierInfo>> genericList = new GenericEntity<List<CarrierInfo>>(carrierslist) {
		};
		Response response = Response.ok(genericList).header("totalRecords", totalRecords).build();
		return response;

	}

	@GET
	@Produces({ "application/json","application/xml" })
	@Path("/{id}")
	@Transactional
	public Response getCarriersById(@Context HttpHeaders headers, @PathParam("id") int id) {
		Response response = null;
		logger.info(" getCarriersById resource lookup by id : " + id);
		CarrierDetail carrierDetail = masterService.findCarrierById(id);
		if (carrierDetail==null) {
			response = Response.noContent().build();
		} else {
			response = Response.ok(carrierDetail).build();
		}
		return response;
	}

	@PUT
	@Produces({ "application/json","application/xml" })	
	@Path("/{id}")
	@Transactional
	public Response updateCarrier(@Context HttpHeaders headers, CarrierDetail carrierDetail) {	
		CIPResourceResponseBuilder responseBuilder = new CIPResourceResponseBuilder();
		StatusResponse statusReponse;
		Response response = null;
		try {
			statusReponse = masterService.updateCarrier(carrierDetail);			
			if(statusReponse.getCode().equalsIgnoreCase("002")) {
				response = Response.notModified(statusReponse.getErrorDetailList().toString()).build();
			} else {
				return responseBuilder.build(statusReponse, headers.getMediaType(),"");					
			}
			
		} catch (Exception e) {
			response = responseBuilder.build(e, headers.getMediaType(),"");
			e.printStackTrace();
		}		
		
		return response;
	}

}
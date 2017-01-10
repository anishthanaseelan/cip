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
package com.cts.cip.kpi.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.dto.ShipmentLoadInfo;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.CriteriaValidationException;
import com.cts.cip.common.model.kpi.ShippingUnitList;
import com.cts.cip.common.model.kpi.ShippingUnits;
import com.cts.cip.common.model.kpi.CipKpiSummaryResponse;
import com.cts.cip.common.model.kpi.GetKpiSummaryRequest;
import com.cts.cip.common.model.kpi.LabelsCreated;
import com.cts.cip.common.model.kpi.PendingConfirmation;
import com.cts.cip.common.model.kpi.PendingManifest;
import com.cts.cip.common.model.kpi.ReprintRequest;
import com.cts.cip.common.model.kpi.RequestsReceived;
import com.cts.cip.common.model.kpi.SUKPIFilterCriteria;
import com.cts.cip.common.model.kpi.ShipmentsPerOrder;
import com.cts.cip.common.model.kpi.UpdateSummaryRequest;
import com.cts.cip.kpi.bo.core.ShippingListService;
import com.cts.cip.kpi.bo.core.SummaryService;

import io.swagger.annotations.Api;

@Path("/kpi")
@Api(value = "/kpi", description = "Dashboard service")
@Component
public class ShippingUnitKPIResource {

	Logger logger = LoggerFactory.getLogger(ShippingUnitKPIResource.class);
	
	@Autowired
	ShippingListService shippingListService;
	@Autowired
	SummaryService summaryService;

		
	
	
	@POST
	@Path("/shippingUnits")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response getShipUnitList(@Context HttpHeaders headers,SearchCriteria searchCriteria) {
		logger.debug("getShipUnitList entry");
		Response response = null;
		List<ShipmentLoadInfo> shipmentLoadInfoList;
		Long totalRecords = 0l;
		try {
		 shipmentLoadInfoList = shippingListService.getShipUnitList(searchCriteria);		 
		 if (shipmentLoadInfoList!=null && ! shipmentLoadInfoList.isEmpty()) {
			 totalRecords = shippingListService.getShippingUnitsCriteriaCount(searchCriteria);
			 logger.debug("getShipUnitList total records : " + totalRecords );
			 GenericEntity<List<ShipmentLoadInfo>> genericList = new GenericEntity<List<ShipmentLoadInfo>>(shipmentLoadInfoList) {};
			 response = Response.ok(genericList, headers.getMediaType()).header("totalRecords", totalRecords).build();
		 } else {
			 response = Response.noContent().build();
		 }
		} catch (CriteriaValidationException e) {	
			response = Response.status(Status.EXPECTATION_FAILED).entity(e.getMessage()).build();
			logger.error(e.getMessage(), e);
		}
		return response;
	}	
	
	
	@POST
	@Path("/summary")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response getSummary(@Context HttpHeaders headers,GetKpiSummaryRequest request) {
		logger.debug("Received Request in rest layer: " + request);
		Response response = null;
		CipKpiSummaryResponse summaryResponse=null;
		try {
			summaryResponse = summaryService.getSummary(request);
		} catch (CriteriaValidationException e) {	
			response = Response.status(Status.EXPECTATION_FAILED).entity(e.getMessage()).build();
			logger.error(e.getMessage(), e);
			
		} catch (Exception e) {
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
			logger.error(e.getMessage(), e);
		}
		if (response == null && summaryResponse != null ) {		
			response = Response.ok(summaryResponse, headers.getMediaType()).build();
		} else {
			response = Response.noContent().build();
		}
		return response;
	}

}
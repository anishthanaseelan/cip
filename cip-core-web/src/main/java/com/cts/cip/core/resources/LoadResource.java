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
/**
 * 
 */
package com.cts.cip.core.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.cip.common.dto.LoadInfo;
import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.dto.ShipmentLoadInfo;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.LoadBase;
import com.cts.cip.common.model.LoadShippingUnitRequest;
import com.cts.cip.common.model.LoadShippingUnitResponse;
import com.cts.cip.common.model.ManifestShippingUnitsRequest;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.common.model.UnloadShippingUnitRequest;
import com.cts.cip.common.model.UnloadShippingUnitResponse;
import com.cts.cip.core.bo.LoadBO;
import com.cts.cip.core.bo.ShippingUnitBO;
import com.cts.cip.core.mapper.LoadMapper;
import com.cts.cip.core.repo.constants.CIPCoreConstants;
import com.cts.cip.util.NotificationManager;

import io.swagger.annotations.Api;

/**
 * @author 417765
 *
 */
@Path("/load")
@Api(value = "/load", description = "CIP Load service")
@Component
public class LoadResource {

	Logger logger = LoggerFactory.getLogger(LoadResource.class);

	@Autowired
	LoadBO loadBO;

	@Autowired
	ShippingUnitBO shippingUnitBO;

	@Autowired
	LoadMapper loadMapper;
	
	@Autowired
	NotificationManager notificationManager;

	static final String TRANS_ID = "transactionId";
	/** To Load Shipping Units
	 * @param headers
	 * @param loadShippingUnitRequest
	 * @return
	 */
	@POST
	@Path("/load")
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	public Response loadShippingUnit(@Context HttpHeaders headers, LoadShippingUnitRequest loadShippingUnitRequest) {
		logger.info("Processing Load Ship unit Request... ");
		CIPResourceResponseBuilder responseBuilder = new CIPResourceResponseBuilder();
		String transactionId = "";
		List<String> transactionHeader = headers.getRequestHeader(TRANS_ID);
		if (transactionHeader!=null && !transactionHeader.isEmpty()) {
			transactionId = transactionHeader.get(0);
		}
		LoadShippingUnitResponse loadSUResponse = null;
		Response response = null;
		try {
			loadBO.validateRequest(loadShippingUnitRequest);
			loadSUResponse = loadBO.loadSUs(loadShippingUnitRequest.getLoad(), loadShippingUnitRequest.getShippingBaseUnits());
		}	
		catch (Exception e) {			
			logger.error(e.getMessage(),e);
			response = responseBuilder.build(e, headers.getMediaType(),transactionId);
			notificationManager.sendNotification(e.getMessage(), null, CIPCoreConstants.LOAD_SHIPUNIT_NOTIF_SUB );
			logger.error("Response::" + response);
			return response;
		}
		logger.info("Completed processing the loadShippingUnit () ");
		if(loadSUResponse.getStatus().getResponseStatusCode().equalsIgnoreCase(CIPCoreConstants.RESPONSE_FAIL_DESC)){
			notificationManager.sendNotification(loadSUResponse.getStatus().getResponseStatusDescription(), null, CIPCoreConstants.LOAD_SHIPUNIT_NOTIF_SUB );
			response = responseBuilder.build(new BusinessException(loadSUResponse.getStatus().getResponseStatusDescription()),loadSUResponse, headers.getMediaType(),transactionId);
		}else 
			response = responseBuilder.build(loadSUResponse, headers.getMediaType(),transactionId);
		return response;
	}

	/** To Unload Shipping Units
	 * @param headers
	 * @param unloadShippingUnitRequest
	 * @return
	 */
	@PUT
	@Path("/unload")
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	public Response unloadShippingUnit(@Context HttpHeaders headers,
			UnloadShippingUnitRequest unloadShippingUnitRequest) {
		logger.info("Started processing the unloadShippingUnit () ");

		CIPResourceResponseBuilder responseBuilder = new CIPResourceResponseBuilder();
		String transactionId = "";
		List<String> transactionHeader = headers.getRequestHeader(TRANS_ID);
		if (transactionHeader!=null && !transactionHeader.isEmpty()) {
			transactionId = 	transactionHeader.get(0);
		}
		UnloadShippingUnitResponse unloadSUResponse = null;
		Response response = null;
		try {
			loadBO.validateRequest(unloadShippingUnitRequest);
			unloadSUResponse = loadBO.unloadShippingUnit(unloadShippingUnitRequest.getLoad(), 
					unloadShippingUnitRequest.getShippingBaseUnits());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			notificationManager.sendNotification(e.getMessage(), null, CIPCoreConstants.UNLOAD_SHIPUNIT_NOTIF_SUB );
			response = responseBuilder.build(e, headers.getMediaType(),transactionId);
			logger.error("Response::" + response);
			return response;
		}
		logger.info("Completed processing the unloadShippingUnit () ");
		return responseBuilder.build(unloadSUResponse, headers.getMediaType(),transactionId);
	}

	/** Fetch Manifest Shipping Units
	 * @param headers
	 * @param manifestShippingUnitsRequest
	 * @return
	 */
	@PUT
	@Path("/manifest")
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	public Response manifestShippingUnits(@Context HttpHeaders headers,
			ManifestShippingUnitsRequest manifestShippingUnitsRequest) {

		logger.info("Started processing the manifestShippingUnits () ");

		CIPResourceResponseBuilder responseBuilder = new CIPResourceResponseBuilder();
		String transactionId = "";
		List<String> transactionHeader = headers.getRequestHeader(TRANS_ID);
		if (transactionHeader!=null && !transactionHeader.isEmpty()) {
			transactionId = 	transactionHeader.get(0);
		}
		ManifestShippingUnitsResponse manifestSUResponse = null;
		try {
			loadBO.validateRequest(manifestShippingUnitsRequest);		
			manifestSUResponse = loadBO.manifestLoad(manifestShippingUnitsRequest.getLoad());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			notificationManager.sendNotification(e.getMessage(), null, CIPCoreConstants.MANIFEST_SHIPUNIT_NOTIF_SUB);
			logger.error(e.getMessage(), e);
			return responseBuilder.build(e, headers.getMediaType(),transactionId);
		}
		
		logger.info("Completed processing Manifest ");
		return responseBuilder.build(manifestSUResponse, headers.getMediaType(),transactionId);
	}

	/** To find load information by name
	 * @param headers
	 * @param loadName
	 * @return
	 */
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public Response findLoadInfoByName(@Context HttpHeaders headers, @PathParam("id") String loadName) {
		LoadInfo loadInfo;

		Response response;
		try {
			loadInfo = loadBO.findLoadInfoByName(loadName);
			// Update the detailed shipping unit information
			List<ShipmentLoadInfo> shipmentLoadInfos = new ArrayList<>();
			for (ShipmentLoadInfo shipmentLoadInfo : loadInfo.getShipmentLoadInfoList()) {
				ShippingUnit shippingUnit = shippingUnitBO.get(shipmentLoadInfo.getShipReferenceId());
				String carrierServiceName = shippingUnitBO
						.getCarrierServiceNameByCode(shippingUnit.getCarrierReference().getId());
				ShipmentLoadInfo updatedShipmentLoadInfo = loadMapper.mapToLoadShipmentInfo(shippingUnit,
						shipmentLoadInfo);
				updatedShipmentLoadInfo.setCarrierServiceType(carrierServiceName);				
				shipmentLoadInfos.add(updatedShipmentLoadInfo);
			}
			loadInfo.getShipmentLoadInfoList().clear();
			loadInfo.setShipmentLoadInfoList(shipmentLoadInfos);
			response = Response.ok(loadInfo).build();
		} catch (ResourceNotExistException e) {
			logger.error(e.getMessage(), e);
			response = Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();			
		} catch (SystemFailure e) {
			logger.error(e.getMessage(), e);
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();			
		}
		return response;
	}

	/** To find load units by page
	 * @param headers
	 * @param searchCriteria
	 * @return
	 */
	@POST
	@Path("/loadedShipUnits")
	@Produces({ "application/xml", "application/json" })
	@Consumes({ "application/xml", "application/json" })
	public Response findLoadUnitsByPage(@Context HttpHeaders headers, SearchCriteria searchCriteria) {
		List<Load> loadList;
		Response response;
		Long totalRecords = 0l;
		logger.debug("findLoadUnitsByPage resource method entry");
		try {
			totalRecords = loadBO.findCountByCriteria(searchCriteria);
			logger.debug(" Total count if records in findLoadUnitsByPage: " + totalRecords);
			loadList = loadBO.findByPage(searchCriteria);
			GenericEntity<List<Load>> genericList = new GenericEntity<List<Load>>(loadList) {};
			response = Response.ok(genericList).header("totalRecords", totalRecords).build();
		} catch (ResourceNotExistException e) {
			logger.error(e.getMessage(), e);
			response = Response.status(Response.Status.NO_CONTENT).entity(e.getMessage()).build();			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);			
			response = Response.status(Response.Status.EXPECTATION_FAILED).entity(e.getMessage()).build();
		}
		return response;
	}
}

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
package com.cts.cip.core.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.model.CreateShippingUnitRequest;
import com.cts.cip.common.model.CreateShippingUnitResponse;
import com.cts.cip.common.model.GetDocumentsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitList;
import com.cts.cip.common.model.ShippingUnits;
import com.cts.cip.core.bo.ShippingUnitBO;
import com.cts.cip.core.repo.constants.CIPCoreConstants;
import com.cts.cip.util.NotificationManager;

import io.swagger.annotations.Api;

/**
 * @author 
 *
 */
@Path("/shippingunit")
@Api(value = "/shippingunit", description = "Shipping units service")
@Component
public class ShippingUnitResource {

	Logger logger = LoggerFactory.getLogger(ShippingUnitResource.class);

	@Autowired
	ShippingUnitBO shippingUnitBO;
	
	@Autowired
	NotificationManager notificationManager;
	

	/** Method to get Shipping Unit information
	 * @param headers
	 * @param suRefId
	 * @return
	 */
	@GET
	@Path("/{suRefId}")
	@Produces({ "application/xml", "application/json" })
	public Response getShippingUnit(@Context HttpHeaders headers, @PathParam("suRefId") String suRefId) {

		ShippingUnit shippingUnit = null;
		CIPResourceResponseBuilder responseBuilder = new CIPResourceResponseBuilder();	

		try {
			shippingUnit = shippingUnitBO.get(suRefId);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			notificationManager.sendNotification(e.getMessage(), null, CIPCoreConstants.GET_SHIPUNIT_NOTIF_SUB );
			return responseBuilder.build(e, headers.getMediaType(),"");
		}

		logger.info("Completed processing the getShippingUnit () ");
		return responseBuilder.build(shippingUnit, headers.getMediaType(),"");
	}

	/** Get Documents
	 * @param headers
	 * @param referenceID
	 * @param trackingNumber
	 * @return
	 */
	@GET
	@Path("/getDocuments")
	@Produces({ "application/xml", "application/json" })
	public Response getDocuments(@Context HttpHeaders headers, @QueryParam("referenceID") String referenceID,
			@QueryParam("trackingNumber") String trackingNumber) {

		ShippingUnit shippingUnit = null;
		GetDocumentsResponse reprintDocumentResponse = null;
		CIPResourceResponseBuilder responseBuilder = new CIPResourceResponseBuilder();
		

		try {
			if (null == referenceID && trackingNumber != null) {
				shippingUnit = shippingUnitBO.getShipUnit(trackingNumber);
			} else {
				shippingUnit = shippingUnitBO.get(referenceID);
			}

			if (null != shippingUnit) {
				reprintDocumentResponse = shippingUnitBO.getSUDocuments(shippingUnit);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			notificationManager.sendNotification(e.getMessage(), null, CIPCoreConstants.GET_DOC_NOTIF_SUB );
			return responseBuilder.build(e, headers.getMediaType(),"");
		}

		logger.info("Completed processing the getDocuments () ");
		return responseBuilder.build(reprintDocumentResponse, headers.getMediaType(),"");
	}

	/** Get Offset
	 * @param headers
	 * @param offset
	 * @param limit
	 * @return
	 */
	@GET
	@Produces({ "application/json", "application/xml" })
	@Transactional
	public Response getInOffset(@Context HttpHeaders headers, @QueryParam("offset") int offset,
			@QueryParam("limit") int limit) {

		logger.info(" Get Shipping unit By Page : " + offset + "-" + limit);
		ShippingUnits responseObj = new ShippingUnits();
		responseObj.setShippingUnits(new ShippingUnitList());
		List<ShippingUnit> shippingUnits;
		int totalRecords;

		/*
		 * By Default if user sends the offset or size as 0 or not pass the
		 * value all the carriers will be returned
		 */
		if (offset <= 0 || limit <= 0) {
			shippingUnits = shippingUnitBO.findAll();
			totalRecords = shippingUnits.size();
		} else {
			logger.info("Getting Shipping units by page");
			shippingUnits = shippingUnitBO.getInOffset(offset, limit);
			totalRecords = shippingUnitBO.findTotalCount();
		}

		responseObj.getShippingUnits().getShippingUnit().addAll(shippingUnits);
		return Response.ok(responseObj, headers.getMediaType()).header("totalRecords", totalRecords).build();
	}

	/** Initiate Shipping Units
	 * @param headers
	 * @param request
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@POST
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response initiateShippingUnit(@Context HttpHeaders headers, CreateShippingUnitRequest request) {
		String transactionId = "";
		List<String> transactionHeader = headers.getRequestHeader("transactionId");
		if (transactionHeader!=null && !transactionHeader.isEmpty()) {
			transactionId = 	transactionHeader.get(0);
		}	
		
		Response response = null;
		CreateShippingUnitResponse createResponse = null;
		CIPResourceResponseBuilder responseBuilder = new CIPResourceResponseBuilder();
		
		try{			
			shippingUnitBO.validateRequest(request.getShippingUnit());			
			createResponse = shippingUnitBO.initate(request.getShippingUnit());	
			createResponse.setResponseTransactionID(transactionId);
		} catch (Exception e) {		
			logger.error(e.getMessage(), e);
			notificationManager.sendNotification(e.getMessage(), null, CIPCoreConstants.CREATE_SHIPUNIT_NOTIF_SUB );
			response = responseBuilder.build(e, headers.getMediaType(),transactionId); 
			logger.info("Response is--> "+ response);
			return response;
		}		
		logger.info("Completed processing the getDocuments () ");
		
		
		response = responseBuilder.build(createResponse, headers.getMediaType(),transactionId);	
		return response;
	}

	/** Cancel Shipping Unit
	 * @param headers
	 * @param suRefId
	 * @return
	 */
	@PUT
	@Path("/{suRefId}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response cancelShippingUnit(@Context HttpHeaders headers, @PathParam("suRefId") String suRefId) {
		ShippingUnit shippingUnit;
		CIPResourceResponseBuilder responseBuilder = new CIPResourceResponseBuilder();
		String transactionId = "";
		List<String> transactionHeader = headers.getRequestHeader("transactionId");
		if (transactionHeader!=null && !transactionHeader.isEmpty()) {
			transactionId = 	transactionHeader.get(0);
		}	
		
		try {
			shippingUnit = shippingUnitBO.cancel(suRefId);
		}  catch (Exception e) {
			logger.error(e.getMessage(), e);
			notificationManager.sendNotification(e.getMessage(), null, CIPCoreConstants.CANCEL_SHIPUNIT_NOTIF_SUB );
			return responseBuilder.build(e, headers.getMediaType(),transactionId);
		}

		logger.info("Completed processing the cancel shipping unit () ");
		return responseBuilder.build(shippingUnit.getShippingUnitBase(), headers.getMediaType(),transactionId);
	}

}
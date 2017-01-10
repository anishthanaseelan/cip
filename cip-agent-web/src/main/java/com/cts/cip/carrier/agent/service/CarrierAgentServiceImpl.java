/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (CWW)
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
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (CWW)
 *******************************************************************************/
/**
 * 
 */
package com.cts.cip.carrier.agent.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cts.cip.agent.model.CancelRequest;
import com.cts.cip.agent.model.CancelResponse;
import com.cts.cip.agent.model.CreateRequest;
import com.cts.cip.agent.model.CreateResponse;
import com.cts.cip.agent.model.LoadRequest;
import com.cts.cip.agent.model.ManifestRequest;
import com.cts.cip.agent.model.ManifestResponse;
import com.cts.cip.agent.model.TrackRequest;
import com.cts.cip.agent.model.UnLoadRequest;
import com.cts.cip.carrier.CarrierClient;
import com.cts.cip.carrier.exception.CarrierClientException;
import com.cts.cip.common.tracker.model.ShipmentTrackRequest;

import io.swagger.annotations.Api;

/**
 * @author 
 *
 */


@Path("/Agent")
@Api(value = "/Agent", description = "CIP Agent service")
@Component
public class CarrierAgentServiceImpl implements CarrierAgentService {
	
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	CarrierClient commonCarrierClient;
	
	protected static final Logger logger = LoggerFactory.getLogger(CarrierAgentServiceImpl.class);


	/* (non-Javadoc)
	 * @see com.cts.cip.carrier.agent.service.CarrierAgentService#createShipment(javax.ws.rs.core.HttpHeaders, com.cts.cip.carrier.model.CreateRequest)
	 */
	
	@POST
	@Path("/createShipment")
	@Produces({"application/xml","application/json"})
	public Response createShipment(@Context HttpHeaders headers, CreateRequest createShipmentRequest) {
		logger.debug("In createShipment service..");
		CreateResponse  createResponse = commonCarrierClient.createShipment(createShipmentRequest);
		MediaType mediaType = getMediaType(headers);
		return Response.ok(createResponse, mediaType).build();		
	}

	/**
	 * Returns Default MediaType if not available in header
	 * @param headers
	 * @return
	 */
	private MediaType getMediaType(HttpHeaders headers) {
		MediaType mediaType = headers.getMediaType();
		if(null == mediaType){
			mediaType = MediaType.APPLICATION_JSON_TYPE;
		}
		return mediaType;
		
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.carrier.agent.service.CarrierAgentService#cancelShipment(javax.ws.rs.core.HttpHeaders, com.cts.cip.carrier.model.CancelRequest)
	 */
	@POST
	@Path("/cancelShipment")
	@Produces({"application/xml","application/json"})
	public Response cancelShipment(@Context HttpHeaders headers, CancelRequest cancelShipmentRequest) {
		logger.debug("In cancelShipment service..");
		CancelResponse  cancelResponse = commonCarrierClient.cancelShipment(cancelShipmentRequest);
		MediaType mediaType = getMediaType(headers);
		return Response.ok(cancelResponse, mediaType).build();		
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.carrier.agent.service.CarrierAgentService#loadShipment(javax.ws.rs.core.HttpHeaders, com.cts.cip.carrier.model.LoadRequest)
	 */
	
	public Response loadShipment(@Context HttpHeaders headers, LoadRequest confirmShipmentRequest) {	
		return  Response.ok().build();
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.carrier.agent.service.CarrierAgentService#mainfestTrailer(com.cts.cip.carrier.model.ManifestRequest)
	 */
	@POST
	@Path("/manifestTrailer")
	@Produces({"application/xml","application/json"})
	public Response mainfestTrailer(@Context HttpHeaders headers, ManifestRequest manifestTrailerRequest) throws CarrierClientException {
		logger.debug("In mainfestTrailer service..");
		ManifestResponse  manifestResponse = commonCarrierClient.mainfestTrailer(manifestTrailerRequest);
		MediaType mediaType = getMediaType(headers);
		return Response.ok(manifestResponse, mediaType).build();		
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.carrier.agent.service.CarrierAgentService#unLoadShipment(com.cts.cip.carrier.model.UnLoadRequest)
	 */
	
	public Response unLoadShipment(@Context HttpHeaders headers, UnLoadRequest unloadRequest) throws CarrierClientException {
		return  Response.ok().build();
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.carrier.agent.service.CarrierAgentService#trackShipment(com.cts.cip.carrier.model.TrackRequest)
	 */
	
	public Response trackShipment(@Context HttpHeaders headers,ShipmentTrackRequest trackShipmentRequest) throws CarrierClientException {
		return  Response.ok().build();
	}

	public CarrierClient getCommonCarrierClient() {
		return commonCarrierClient;
	}

	public void setCommonCarrierClient(CarrierClient commonCarrierClient) {
		this.commonCarrierClient = commonCarrierClient;
	}




}

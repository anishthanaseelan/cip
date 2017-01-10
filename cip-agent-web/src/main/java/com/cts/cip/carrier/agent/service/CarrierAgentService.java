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
package com.cts.cip.carrier.agent.service;
 
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.cts.cip.agent.model.CancelRequest;
import com.cts.cip.agent.model.CancelResponse;
import com.cts.cip.agent.model.CreateRequest;
import com.cts.cip.agent.model.CreateResponse;
import com.cts.cip.agent.model.LoadRequest;
import com.cts.cip.agent.model.LoadResponse;
import com.cts.cip.agent.model.ManifestRequest;
import com.cts.cip.agent.model.TrackRequest;
import com.cts.cip.agent.model.UnLoadRequest;
import com.cts.cip.carrier.exception.CarrierClientException;
import com.cts.cip.common.tracker.model.ShipmentTrackRequest;


/**
 * Interface to expose the Carrier Agent as a REST Service
 * Functionality supported
 *  -Create Shipment
 *  -Cancel Shipment
 *  -Manifest Shipment
 *  -Load Shipment
 *  -Unload Shipment
 * @author 
 *
 */
public interface CarrierAgentService {
	
	/**
	 * Generate tracking Number and create Label/ Label components
	 * @param headers
	 * @param createRequest
	 * @return
	 */
	public Response createShipment(@Context HttpHeaders headers,CreateRequest createShipmentRequest);


	/**
	 * Method to Cancel a shipment
	 * @param headers
	 * @param cancelShipmentRequest
	 * @return
	 */
	public Response cancelShipment(@Context HttpHeaders headers,CancelRequest cancelShipmentRequest);

	
	/**
	 * Update Load id for the shipment
	 * @param headers
	 * @param confirmShipmentRequest
	 * @return
	 */
	public Response loadShipment(@Context HttpHeaders headers,LoadRequest confirmShipmentRequest);

	/**
	 * Trigger Manifest creation 
	 * @param manifestTrailerRequest
	 * @return
	 * @throws CarrierClientException
	 */
	public Response mainfestTrailer(@Context HttpHeaders headers, ManifestRequest manifestTrailerRequest) throws CarrierClientException;

	/**
	 * Unload/Disassociate Load id
	 * @param unloadRequest
	 * @return
	 * @throws CarrierClientException
	 */
	public Response unLoadShipment(@Context HttpHeaders headers, UnLoadRequest unloadRequest) throws CarrierClientException;
	
	/**
	 * Track a shipment
	 * @param trackShipmentRequest
	 * @return
	 * @throws CarrierClientException
	 */
	public Response trackShipment(@Context HttpHeaders headers, ShipmentTrackRequest trackShipmentRequest) throws CarrierClientException;
	
	/**
	 * Handle/Group similar errors and notification
	 * @param exception
	 * @return TODO
	 */
}

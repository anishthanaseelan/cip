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
package com.cts.cip.carrier;

import com.cts.cip.agent.model.CancelRequest;
import com.cts.cip.agent.model.CancelResponse;
import com.cts.cip.agent.model.CreateRequest;
import com.cts.cip.agent.model.CreateResponse;
import com.cts.cip.agent.model.LoadRequest;
import com.cts.cip.agent.model.LoadResponse;
import com.cts.cip.agent.model.ManifestRequest;
import com.cts.cip.agent.model.ManifestResponse;
import com.cts.cip.agent.model.UnLoadRequest;
import com.cts.cip.agent.model.UnLoadResponse;
import com.cts.cip.common.tracker.model.ShipmentTrackRequest;
import com.cts.cip.common.tracker.model.ShipmentTrackResponse;

/**
 * Carrier Client Inteface with methods to create, cancel ,load and manifest shipments
 * 
 * @author 256132
 *
 */
public interface CarrierClient  {
	
	/**
	 * Create/Register shipment with the carrier and generate label/label associated details
	 * @param createShipmentRequest
	 * @return
	 */
	public CreateResponse createShipment(CreateRequest createShipmentRequest);

	/**
	 * Method to Cancel a shipment
	 * @param cancelShipmentRequest
	 * @return
	 */
	public CancelResponse cancelShipment(CancelRequest cancelShipmentRequest);

	/**
	 * Update Load id for the shipment
	 * @param confirmShipmentRequest
	 * @return
	 */
	public LoadResponse loadShipment(LoadRequest confirmShipmentRequest);

	/**
	 * Trigger Manifest creation 
	 * @param manifestTrailerRequest
	 * @return
	 */
	public ManifestResponse mainfestTrailer(ManifestRequest manifestTrailerRequest);

	/**
	 * Unload/Disassociate Load id
	 * @param unloadRequest
	 * @return
	 */
	public UnLoadResponse unLoadShipment(UnLoadRequest unloadRequest);
	
	/**
	 * Track a shipment
	 * @param trackShipmentRequest
	 * @return
	 */
	public ShipmentTrackResponse trackShipment(ShipmentTrackRequest trackShipmentRequest);
	
	/**
	 * Handle/Group similar errors and notification
	 * @param exception
	 * @return TODO
	 */
	public Object handleErrors(Object exception); 

	
}

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
package com.cts.cip.carrier;

import com.cts.cip.agent.model.LoadRequest;
import com.cts.cip.agent.model.LoadResponse;
import com.cts.cip.agent.model.UnLoadRequest;
import com.cts.cip.agent.model.UnLoadResponse;
import com.cts.cip.carrier.exception.CarrierClientException;

/**
 * @author 
 *
 */
public interface CarrierLoad {
	
	/**
	 * Method to perform load Shipment and associating it with a load id
	 * @param loadRequest
	 * @return
	 * @throws CarrierClientException
	 */
	public LoadResponse loadCarrierShipment(LoadRequest loadRequest) throws CarrierClientException;

	/**
	 * Method to disassociate a shipment from a load
	 * @param unloadResquest
	 * @return
	 * @throws CarrierClientException
	 */
	public UnLoadResponse unLoadCarrierShipment(UnLoadRequest unloadResquest) throws CarrierClientException;
}

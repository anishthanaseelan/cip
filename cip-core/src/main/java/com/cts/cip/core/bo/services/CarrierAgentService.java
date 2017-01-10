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
package com.cts.cip.core.bo.services;

import com.cts.cip.agent.model.ManifestRequest;
import com.cts.cip.agent.model.ManifestResponse;
import com.cts.cip.common.exceptions.CarrierAgentException;
import com.cts.cip.common.exceptions.CarrierAgentFailure;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.tracker.model.ShipmentTrackRequest;
import com.cts.cip.common.tracker.model.TrackResponseContainer;

public interface CarrierAgentService {
	
	ManifestShippingUnitsResponse manifestLoad(Load activeLoad) throws CarrierAgentFailure, CarrierAgentException;
	
	void cancelShippingUnit(ShippingUnit shippingUnit) throws CarrierAgentFailure, CarrierAgentException;
	
	ManifestResponse contactAgentForManifest(ManifestRequest manifestRequest) throws CarrierAgentFailure;
	
	ShippingUnit generateTrackingNumber(ShippingUnit shippingUnitReq) throws CarrierAgentException, CarrierAgentFailure;
	
	TrackResponseContainer getTrackingDetails(ShipmentTrackRequest trackRequest) throws CarrierAgentException, CarrierAgentFailure;

}

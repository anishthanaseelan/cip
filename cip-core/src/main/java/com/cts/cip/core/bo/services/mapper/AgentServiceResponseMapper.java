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
package com.cts.cip.core.bo.services.mapper;

import com.cts.cip.agent.model.CancelResponse;
import com.cts.cip.agent.model.CreateResponse;
import com.cts.cip.agent.model.LoadResponse;
import com.cts.cip.agent.model.ManifestResponse;
import com.cts.cip.agent.model.UnLoadResponse;
import com.cts.cip.common.model.CancelShippingUnitResponse;
import com.cts.cip.common.model.CreateShippingUnitResponse;
import com.cts.cip.common.model.LoadShippingUnitResponse;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.UnloadShippingUnitResponse;

/**
 * Mappper to map the response from Agent Service to COM Response
 * 
 * @author
 *
 */
public interface AgentServiceResponseMapper {

	/**
	 * Map CreateResponse to COM Response
	 * 
	 * @param createResponse
	 * @return
	 */
	public CreateShippingUnitResponse mapFromCreateResponse(CreateResponse createResponse, ShippingUnit shippingUnitReq);

	/**
	 * Map CancelResponse to COM Response
	 * 
	 * @param cancelResponse
	 * @return
	 */
	public CancelShippingUnitResponse mapFromCancelResponse(CancelResponse cancelResponse);

	/**
	 * Map ManifestResponse to COM Response
	 * 
	 * @param manifestResponse
	 * @return
	 */
	public ManifestShippingUnitsResponse mapFromManifestResponse(ManifestResponse manifestResponse);

	/**
	 * Map LoadResponse to COM Response
	 * 
	 * @param loadResponse
	 * @return
	 */
	public LoadShippingUnitResponse mapFromLoadResponse(LoadResponse loadResponse);

	/**
	 * Map UnLoadResponse to COM Response
	 * 
	 * @param unLoadResponse
	 * @return
	 */
	public UnloadShippingUnitResponse mapFromUnLoadResponse(UnLoadResponse unLoadResponse);
}

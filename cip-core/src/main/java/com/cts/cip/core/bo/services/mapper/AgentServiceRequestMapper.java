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

import com.cts.cip.agent.model.CancelRequest;
import com.cts.cip.agent.model.CreateRequest;
import com.cts.cip.agent.model.LoadRequest;
import com.cts.cip.agent.model.ManifestRequest;
import com.cts.cip.agent.model.UnLoadRequest;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.ShippingUnit;

/**
 * Mapper with methods to map COM to Agent Request Types
 * 
 * @author
 *
 */
public interface AgentServiceRequestMapper {
	/**
	 * Map COM to Create Request
	 * 
	 * @param shippingUnit
	 * @return
	 */
	public CreateRequest mapToCreateRequest(ShippingUnit shippingUnit);

	/**
	 * Map COM to Cancel Request
	 * 
	 * @param shippingUnit
	 *            TODO
	 * @return
	 */
	public CancelRequest mapToCancelRequest(ShippingUnit shippingUnit);

	/**
	 * Map COM to Manifest Request
	 * 
	 * @param shippingUnitList
	 *            TODO
	 * @param carrierName
	 *            TODO
	 * @return
	 */
	//public ManifestRequest mapToManifestRequest(List<ShippingUnitBase> shippingUnitList, String carrierName);
	/**
	 * 
	 * @param activeLoad
	 * @return
	 */
	public ManifestRequest mapToManifestRequest(Load activeLoad);

	/**
	 * Map COM to Load Request
	 * 
	 * @param shippingUnit
	 *            TODO
	 * @return
	 */
	public LoadRequest mapToLoadRequest(ShippingUnit shippingUnit);

	/**
	 * Map COM to UnLoad Request
	 * 
	 * @param shippingUnit
	 *            TODO
	 * @return
	 */
	public UnLoadRequest mapToUnLoadRequest(ShippingUnit shippingUnit);
}

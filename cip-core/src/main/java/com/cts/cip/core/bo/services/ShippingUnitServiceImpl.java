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
package com.cts.cip.core.bo.services;

import java.util.List;

import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.core.bo.ShippingUnitBO;

public class ShippingUnitServiceImpl implements ShippingUnitService {
	
	ShippingUnitBO shippingUnitBO;

	public boolean isSUActive(String referenceID) throws SystemFailure {
		return shippingUnitBO.isActiveShippingUnitExist(referenceID);
	}

	public void confirmShipment(List<ShippingUnitBase> shippingUnits)
			throws ResourceNotExistException, SystemFailure {
		for (ShippingUnitBase suBase : shippingUnits) {
			shippingUnitBO.confirm(suBase);
		}
		return;
	}

	public ShippingUnitBO getShippingUnitBO() {
		return shippingUnitBO;
	}

	public void setShippingUnitBO(ShippingUnitBO shippingUnitBO) {
		this.shippingUnitBO = shippingUnitBO;
	}
}

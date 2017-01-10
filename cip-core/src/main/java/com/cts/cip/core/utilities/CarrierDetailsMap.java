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
package com.cts.cip.core.utilities;

import java.util.HashMap;

import com.cts.cip.common.model.CarrierDetails;

public class CarrierDetailsMap implements CarrierDetail {
	
	private HashMap<String,CarrierDetails> carrierDetailsMap = new HashMap<>();
	private HashMap<String,CarrierDetails> serviceDetailsMap = new HashMap<>();

	public HashMap<String, CarrierDetails> getCarrierDetailsMap() {
		return carrierDetailsMap;
	}

	public void setCarrierDetailsMap(HashMap<String, CarrierDetails> carrierDetailsMap) {
		this.carrierDetailsMap = carrierDetailsMap;
	}

	@Override
	public String toString() {
		return "CarrierDetailsMap [carrierDetailsMap=" + carrierDetailsMap + "]";
	}

	public HashMap<String, CarrierDetails> getServiceDetailsMap() {
		return serviceDetailsMap;
	}

	public void setServiceDetailsMap(HashMap<String, CarrierDetails> serviceDetailsMap) {
		this.serviceDetailsMap = serviceDetailsMap;
	}
}

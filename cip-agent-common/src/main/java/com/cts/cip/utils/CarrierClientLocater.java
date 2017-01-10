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
package com.cts.cip.utils;

import java.util.Map;

/**
 * @author 
 *
 */
public class CarrierClientLocater {	

	private Map<String,Object> carrierClientMap;
	
	public Map<String, Object> getCarrierClientMap() {
		return carrierClientMap;
	}

	public void setCarrierClientMap(Map<String, Object> carrierClientMap) {
		this.carrierClientMap = carrierClientMap;
	}

}

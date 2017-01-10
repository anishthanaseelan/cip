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
package com.cts.cip.agent.model;

public class UPSCreateResponse {
	
	@Override
	public String toString() {
		return "UPSCreateResponse [routingCode=" + routingCode + ", maxiCode=" + maxiCode + ", postalBarCode="
				+ postalBarCode + ", weight=" + weight + ", weightUOM=" + weightUOM + "]";
	}
	public RoutingCode getRoutingCode() {
		return routingCode;
	}
	public void setRoutingCode(RoutingCode routingCode) {
		this.routingCode = routingCode;
	}
	public MaxiCode getMaxiCode() {
		return maxiCode;
	}
	public void setMaxiCode(MaxiCode maxiCode) {
		this.maxiCode = maxiCode;
	}
	public String getPostalBarCode() {
		return postalBarCode;
	}
	public void setPostalBarCode(String postalBarCode) {
		this.postalBarCode = postalBarCode;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getWeightUOM() {
		return weightUOM;
	}
	public void setWeightUOM(String weightUOM) {
		this.weightUOM = weightUOM;
	}
	private RoutingCode routingCode;
	private MaxiCode maxiCode;
	private String postalBarCode;
	private String weight;
	private String weightUOM;
	

}

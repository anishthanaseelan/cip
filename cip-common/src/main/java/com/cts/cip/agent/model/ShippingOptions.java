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


public class ShippingOptions {

    public SpecialService getSpecialServiceType() {
		return specialServiceType;
	}
	public void setSpecialServiceType(SpecialService specialServiceType) {
		this.specialServiceType = specialServiceType;
	}
	public String getSpecialInstructions() {
		return specialInstructions;
	}
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}
	public HazardousMaterial getHazardousMaterial() {
		return hazardousMaterial;
	}
	public void setHazardousMaterial(HazardousMaterial hazardousMaterial) {
		this.hazardousMaterial = hazardousMaterial;
	}
	private SpecialService specialServiceType;
    private String specialInstructions;
    private HazardousMaterial hazardousMaterial;
	
}

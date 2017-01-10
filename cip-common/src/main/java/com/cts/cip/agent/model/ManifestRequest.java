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

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Request Object for Manifest Request
 * @author 
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ManifestRequest {
	
	private String requestTransactionID;
	private List<PackageInfo> packageList;
	private ShipperService shipperServiceType;
	private String billingAccountID;
	
	public String getBillingAccountID() {
		return billingAccountID;
	}
	public void setBillingAccountID(String billingAccountID) {
		this.billingAccountID = billingAccountID;
	}
	public String getRequestTransactionID() {
		return requestTransactionID;
	}
	public void setRequestTransactionID(String requestTransactionID) {
		this.requestTransactionID = requestTransactionID;
	}
	public List<PackageInfo> getPackageList() {
		return packageList;
	}
	public void setPackageList(List<PackageInfo> packageList) {
		this.packageList = packageList;
	}
	
	public ShipperService getShipperServiceType() {
		return shipperServiceType;
	}
	public void setShipperServiceType(ShipperService shipperServiceType) {
		this.shipperServiceType = shipperServiceType;
	}
}

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
 * Request Object for CancelRequest
 * @author 
 *
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CancelRequest {
	
	private List<String> trackingNumberList;
	private List<PackageInfo> packageList;
	private String requestTransactionID;
	private ShipperService shipperServiceType;
	
	public List<PackageInfo> getPackageList() {
		return packageList;
	}
	public void setPackageList(List<PackageInfo> packageList) {
		this.packageList = packageList;
	}

	
	public List<String> getTrackingNumberList() {
		return trackingNumberList;
	}
	public void setTrackingNumberList(List<String> trackingNumberList) {
		this.trackingNumberList = trackingNumberList;
	}
	public String getRequestTransactionID() {
		return requestTransactionID;
	}
	public void setRequestTransactionID(String requestTransactionID) {
		this.requestTransactionID = requestTransactionID;
	}

	public ShipperService getShipperServiceType() {
		return shipperServiceType;
	}
	public void setShipperServiceType(ShipperService shipperServiceType) {
		this.shipperServiceType = shipperServiceType;
	}

}

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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CreateResponse {

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public PackageInfo getPackageInfo() {
		return packageInfo;
	}
	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public List<Document> getDocuments() {
		if(null == documents){
			documents = new ArrayList<Document>();
		}
		return documents;
	}
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getResponseTransactionID() {
		return responseTransactionID;
	}
	public void setResponseTransactionID(String responseTransactionID) {
		this.responseTransactionID = responseTransactionID;
	}
	/*public RoutingCode getRoutingCode() {
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
	}*/

	public UPSCreateResponse getUpsCreateResponse() {
		return upsCreateResponse;
	}
	public void setUpsCreateResponse(UPSCreateResponse upsCreateResponse) {
		this.upsCreateResponse = upsCreateResponse;
	}
	public ParameterMap getParameterMap() {
		return parameterMap;
	}
	public void setParameterMap(ParameterMap parameterMap) {
		this.parameterMap = parameterMap;
	}

	private Status status;
	private PackageInfo packageInfo;
	private String serviceType;
	private String trackingNumber;
	
	private List<Document> documents;
	private double totalAmount;
	private String responseTransactionID;
	private ParameterMap parameterMap;
	private UPSCreateResponse upsCreateResponse;

	
	


}

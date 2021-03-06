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
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CancelResponse {
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getResponseTransactionID() {
		return responseTransactionID;
	}
	public void setResponseTransactionID(String responseTransactionID) {
		this.responseTransactionID = responseTransactionID;
	}
	public List<TrackingNumInfo> getTrackingNumInfoList() {
		return trackingNumInfoList;
	}
	public void setTrackingNumInfoList(List<TrackingNumInfo> trackingNumInfoList) {
		this.trackingNumInfoList = trackingNumInfoList;
	}
	private Status status;
	private String responseTransactionID;
	private List<TrackingNumInfo> trackingNumInfoList;
	
	




}

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
package com.cts.cip.common.model.kpi;

import java.util.List;



public class UpdateSummaryRequest {
	
	
	private int id;	
	private String nodeId;
	private String carrierService;
	private ShipmentEventType eventType;
	private int count;
	private List<String> referenceIdList;
	
	public List<String> getReferenceIdList() {
		return referenceIdList;
	}
	public void setReferenceIdList(List<String> referenceIdList) {
		this.referenceIdList = referenceIdList;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getCarrierService() {
		return carrierService;
	}
	public void setCarrierService(String carrierService) {
		this.carrierService = carrierService;
	}
	public ShipmentEventType getEventType() {
		return eventType;
	}
	public void setEventType(ShipmentEventType eventType) {
		this.eventType = eventType;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}

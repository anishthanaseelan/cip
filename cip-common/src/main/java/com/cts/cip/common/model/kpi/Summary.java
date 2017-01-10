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

public class Summary {
	
	private ShipmentSummary overallSummary;	
	private ShipmentSummary pendingSummary;
	private List<ShipmentSummary> pendingSummaryByTime;
	private List<ShipmentSummary> summaryByCarrier;
	private List<ShipmentSummary> summaryByCarrierService;
	private List<ShipmentSummary> summaryByNode;
	
	
	public ShipmentSummary getOverallSummary() {
		return overallSummary;
	}
	public void setOverallSummary(ShipmentSummary overallSummary) {
		this.overallSummary = overallSummary;
	}
	public List<ShipmentSummary> getPendingSummaryByTime() {
		return pendingSummaryByTime;
	}
	public void setPendingSummaryByTime(List<ShipmentSummary> pendingSummaryByTime) {
		this.pendingSummaryByTime = pendingSummaryByTime;
	}
	public List<ShipmentSummary> getSummaryByCarrier() {
		return summaryByCarrier;
	}
	public void setSummaryByCarrier(List<ShipmentSummary> summaryByCarrier) {
		this.summaryByCarrier = summaryByCarrier;
	}
	public List<ShipmentSummary> getSummaryByCarrierService() {
		return summaryByCarrierService;
	}
	public void setSummaryByCarrierService(List<ShipmentSummary> summaryByCarrierService) {
		this.summaryByCarrierService = summaryByCarrierService;
	}
	public List<ShipmentSummary> getSummaryByNode() {
		return summaryByNode;
	}
	public void setSummaryByNode(List<ShipmentSummary> summaryByNode) {
		this.summaryByNode = summaryByNode;
	}
	public ShipmentSummary getPendingShipmentSummary() {
		return pendingSummary;
	}
	public void setPendingShipmentSummary(ShipmentSummary pendingShipmentSummary) {
		this.pendingSummary = pendingShipmentSummary;
	}
	
	
}

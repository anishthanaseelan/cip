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
package com.cts.cip.kpi.model;

import java.util.List;

public class Summary {
	
	private ShipmentSummary overallSummary;	
	private List<ShipmentSummary> summaryListByTime;
	private List<ShipmentSummary> summaryListByCarrier;
	
	public ShipmentSummary getOverallSummary() {
		return overallSummary;
	}
	public void setOverallSummary(ShipmentSummary overallSummary) {
		this.overallSummary = overallSummary;
	}
	public List<ShipmentSummary> getSummaryListByTime() {
		return summaryListByTime;
	}
	public void setSummaryListByTime(List<ShipmentSummary> summaryListByTime) {
		this.summaryListByTime = summaryListByTime;
	}
	public List<ShipmentSummary> getSummaryListByCarrier() {
		return summaryListByCarrier;
	}
	public void setSummaryListByCarrier(List<ShipmentSummary> summaryListByCarrier) {
		this.summaryListByCarrier = summaryListByCarrier;
	}

}

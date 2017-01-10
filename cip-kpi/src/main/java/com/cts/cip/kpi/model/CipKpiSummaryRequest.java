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

public class CipKpiSummaryRequest {
	
	private String fromDate;
	private String toDate;
	private String businessUnitName;
	private String nodeName;
	private String carrierName;
	private String carrierServiceName;
	private int splitSize;
	private boolean overallSummary;
	private boolean summaryListByTime;
	private boolean summaryListByCarrier;
	private boolean summaryListByCarrierService;
	
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getCarrierServiceName() {
		return carrierServiceName;
	}
	public void setCarrierServiceName(String carrierServiceName) {
		this.carrierServiceName = carrierServiceName;
	}
	public int getSplitSize() {
		return splitSize;
	}
	public void setSplitSize(int splitSize) {
		this.splitSize = splitSize;
	}
	public boolean isOverallSummary() {
		return overallSummary;
	}
	public void setOverallSummary(boolean overallSummary) {
		this.overallSummary = overallSummary;
	}
	public boolean isSummaryListByTime() {
		return summaryListByTime;
	}
	public void setSummaryListByTime(boolean summaryListByTime) {
		this.summaryListByTime = summaryListByTime;
	}
	public boolean isSummaryListByCarrier() {
		return summaryListByCarrier;
	}
	public void setSummaryListByCarrier(boolean summaryListByCarrier) {
		this.summaryListByCarrier = summaryListByCarrier;
	}
	public boolean isSummaryListByCarrierService() {
		return summaryListByCarrierService;
	}
	public void setSummaryListByCarrierService(boolean summaryListByCarrierService) {
		this.summaryListByCarrierService = summaryListByCarrierService;
	}
	public String getBusinessUnitName() {
		return businessUnitName;
	}
	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}	
	
	
}

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

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetKpiSummaryRequest {
	
		
	private String fromDate;	
	private String toDate;
	private List<String> businessUnitList;
	private List<String> nodeList;
	private List<String> carrierList;
	private List<String> carrierServiceList;	
	private int splitSize;	
	private boolean overallSummary;
	private boolean pendingSummary;
	private boolean pendingSummaryByTime;
	private boolean summaryByTime;
	private boolean summaryByNode;
	private boolean summaryByCarrier;	
	private boolean summaryByCarrierService;
	
	
	
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
	public List<String> getBusinessUnitList() {
		return businessUnitList;
	}
	public void setBusinessUnitList(List<String> businessUnitList) {
		this.businessUnitList = businessUnitList;
	}
	public List<String> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<String> nodeList) {
		this.nodeList = nodeList;
	}
	public List<String> getCarrierList() {
		return carrierList;
	}
	public void setCarrierList(List<String> carrierList) {
		this.carrierList = carrierList;
	}
	public List<String> getCarrierServiceList() {
		return carrierServiceList;
	}
	public void setCarrierServiceList(List<String> carrierServiceList) {
		this.carrierServiceList = carrierServiceList;
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
	public boolean isSummaryByTime() {
		return summaryByTime;
	}
	public void setSummaryByTime(boolean summaryByTime) {
		this.summaryByTime = summaryByTime;
	}
	public boolean isSummaryByCarrier() {
		return summaryByCarrier;
	}
	public void setSummaryByCarrier(boolean summaryByCarrier) {
		this.summaryByCarrier = summaryByCarrier;
	}
	public boolean isSummaryByCarrierService() {
		return summaryByCarrierService;
	}
	public void setSummaryByCarrierService(boolean summaryByCarrierService) {
		this.summaryByCarrierService = summaryByCarrierService;
	}
	public boolean isSummaryByNode() {
		return summaryByNode;
	}
	public void setSummaryByNode(boolean summaryByNode) {
		this.summaryByNode = summaryByNode;
	}
	
	
	
	
	public boolean isPendingSummary() {
		return pendingSummary;
	}
	public void setPendingSummary(boolean pendingSummary) {
		this.pendingSummary = pendingSummary;
	}
	public boolean isPendingSummaryByTime() {
		return pendingSummaryByTime;
	}
	public void setPendingSummaryByTime(boolean pendingSummaryByTime) {
		this.pendingSummaryByTime = pendingSummaryByTime;
	}	
}

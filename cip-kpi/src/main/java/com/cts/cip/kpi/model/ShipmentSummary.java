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

public class ShipmentSummary {
	
	private int id;
	private String indicator;
	private String value;
	private Long requestCount;
	private Long labelCount;
	private Long loadCount;
	private Long manifestCount;
	private Long errorCount;
	private Long pendingLoadCount;
	private Long pendingManifestCount;	
	private String startTime;
	private String createTime;
	private String updateTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(Long requestCount) {
		this.requestCount = requestCount;
	}
	public Long getLabelCount() {
		return labelCount;
	}
	public void setLabelCount(Long labelCount) {
		this.labelCount = labelCount;
	}
	public Long getLoadCount() {
		return loadCount;
	}
	public void setLoadCount(Long loadCount) {
		this.loadCount = loadCount;
	}
	public Long getManifestCount() {
		return manifestCount;
	}
	public void setManifestCount(Long manifestCount) {
		this.manifestCount = manifestCount;
	}
	public Long getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Long errorCount) {
		this.errorCount = errorCount;
	}
	public Long getPendingLoadCount() {
		return pendingLoadCount;
	}
	public void setPendingLoadCount(Long pendingLoadCount) {
		this.pendingLoadCount = pendingLoadCount;
	}
	public Long getPendingManifestCount() {
		return pendingManifestCount;
	}
	public void setPendingManifestCount(Long pendingManifestCount) {
		this.pendingManifestCount = pendingManifestCount;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}

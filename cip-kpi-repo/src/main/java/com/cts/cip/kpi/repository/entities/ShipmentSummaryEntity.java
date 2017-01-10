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
package com.cts.cip.kpi.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shipment_summary")
public class ShipmentSummaryEntity {	

	@Override
	public String toString() {
		return "ShipmentSummaryEntity [id=" + id + ", node=" + node + ", carrier=" + carrier + ", carrierService="
				+ carrierService + ", requestCount=" + requestCount + ", labelCount=" + labelCount + ", loadCount="
				+ loadCount + ", manifestCount=" + manifestCount + ", errorCount=" + errorCount + ", pendingLoadCount="
				+ pendingLoadCount + ", pendingManifestCount=" + pendingManifestCount + ", createTime=" + createTime;
			
	}
	@Id
	@Column(name="id")
	private int id;
	@Column(name="summary_unit_id")
	private int summaryGroupId;	
	@Column(name="node")
	private String node;
	@Column(name="carrier")
	private String carrier;
	@Column(name="carrier_svc")	
	private String carrierService;
	@Column(name="rqst_rcvd")	
	private Long requestCount;
	@Column(name="lbl_rqst")	
	private Long labelCount;
	@Column(name="cancel_rqst")
	private Long cancelCount;
	@Column(name="load_rqst")	
	private Long loadCount;
	@Column(name="unload_rqst")	
	private Long unloadCount;
	@Column(name="mfst_rqst")	
	private Long manifestCount;
	@Column(name="err_rqst")	
	private Long errorCount;
	@Column(name="pnd_load")	
	private Long pendingLoadCount;
	@Column(name="pnd_mfst")	
	private Long pendingManifestCount;	
	@Column(name="create_date")	
	private String createTime;
	
	
	public Long getCancelCount() {
		return cancelCount;
	}
	public void setCancelCount(Long cancel_Count) {
		this.cancelCount = cancel_Count;
	}
	public Long getUnloadCount() {
		return unloadCount;
	}
	public void setUnloadCount(Long unloadCount) {
		this.unloadCount = unloadCount;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getCarrierService() {
		return carrierService;
	}
	public void setCarrierService(String carrierService) {
		this.carrierService = carrierService;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getSummaryGroupId() {
		return summaryGroupId;
	}
	public void setSummaryGroupId(int summaryGroupId) {
		this.summaryGroupId = summaryGroupId;
	}
		
}

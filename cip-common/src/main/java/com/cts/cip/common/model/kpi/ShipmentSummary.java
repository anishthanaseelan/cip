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

public class ShipmentSummary {
	

	private int id;
	private String label="";
	private String labelAlias="";
	private int labelIndex=0;
	private Long requestCount=0l;
	private Long labelCount=0l;
	private Long cancelCount=0l;
	private Long unloadCount=0l;
	private int summaryGroupId=0;
	private Long loadCount=0l;	
	private Long manifestCount=0l;
	private Long errorCount=0l;
	private Long pendingLoadCount=0l;
	private Long pendingManifestCount=0l;
	private Long pendingLabelCount=0l;
	private Long reprintCount=0l;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
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
	
	public Long getCancelCount() {
		return cancelCount;
	}
	public void setCancelCount(Long cancelCount) {
		this.cancelCount = cancelCount;
	}
	public Long getUnloadCount() {
		return unloadCount;
	}
	public void setUnloadCount(Long unloadCount) {
		this.unloadCount = unloadCount;
	}
	public int getSummaryGroupId() {
		return summaryGroupId;
	}
	public void setSummaryGroupId(int summaryGroupId) {
		this.summaryGroupId = summaryGroupId;
	}
	
	public String getLabelAlias() {
		return labelAlias;
	}
	public void setLabelAlias(String labelAlias) {
		this.labelAlias = labelAlias;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShipmentSummary other = (ShipmentSummary) obj;
		if (errorCount == null) {
			if (other.errorCount != null)
				return false;
		} else if (!errorCount.equals(other.errorCount))
			return false;
		if (id != other.id)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (labelCount == null) {
			if (other.labelCount != null)
				return false;
		} else if (!labelCount.equals(other.labelCount))
			return false;
		if (loadCount == null) {
			if (other.loadCount != null)
				return false;
		} else if (!loadCount.equals(other.loadCount))
			return false;
		if (manifestCount == null) {
			if (other.manifestCount != null)
				return false;
		} else if (!manifestCount.equals(other.manifestCount))
			return false;
		if (pendingLoadCount == null) {
			if (other.pendingLoadCount != null)
				return false;
		} else if (!pendingLoadCount.equals(other.pendingLoadCount))
			return false;
		if (pendingManifestCount == null) {
			if (other.pendingManifestCount != null)
				return false;
		} else if (!pendingManifestCount.equals(other.pendingManifestCount))
			return false;
		if (requestCount == null) {
			if (other.requestCount != null)
				return false;
		} else if (!requestCount.equals(other.requestCount))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "ShipmentSummary [id=" + id + ", label=" + label + ", labelAlias=" + labelAlias + ", labelIndex="
				+ labelIndex + ", requestCount=" + requestCount + ", labelCount=" + labelCount + ", cancelCount="
				+ cancelCount + ", unloadCount=" + unloadCount + ", summaryGroupId=" + summaryGroupId + ", loadCount="
				+ loadCount + ", manifestCount=" + manifestCount + ", errorCount=" + errorCount + ", pendingLoadCount="
				+ pendingLoadCount + ", pendingManifestCount=" + pendingManifestCount + ", pendingLabelCount="
				+ pendingLabelCount + ", reprintCount=" + reprintCount + "]";
	}
	public Long getReprintCount() {
		return reprintCount;
	}
	public void setReprintCount(Long reprintCount) {
		this.reprintCount = reprintCount;
	}
	public Long getPendingLabelCount() {
		return pendingLabelCount;
	}
	public void setPendingLabelCount(Long pendingLabelCount) {
		this.pendingLabelCount = pendingLabelCount;
	}
	public int getLabelIndex() {
		return labelIndex;
	}
	public void setLabelIndex(int labelIndex) {
		this.labelIndex = labelIndex;
	}
		
}

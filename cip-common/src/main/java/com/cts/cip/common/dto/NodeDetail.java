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
package com.cts.cip.common.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class NodeDetail {
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;		
		result = prime * result + ((globalLocationNumber == null) ? 0 : globalLocationNumber.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((locationAddressLine1 == null) ? 0 : locationAddressLine1.hashCode());
		result = prime * result + ((locationAddressLine2 == null) ? 0 : locationAddressLine2.hashCode());
		result = prime * result + ((locationCity == null) ? 0 : locationCity.hashCode());
		result = prime * result + ((locationCountryCode == null) ? 0 : locationCountryCode.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((locationPrimaryContactEmail == null) ? 0 : locationPrimaryContactEmail.hashCode());
		result = prime * result + ((locationPrimaryContactName == null) ? 0 : locationPrimaryContactName.hashCode());
		result = prime * result
				+ ((locationPrimaryContactNumber == null) ? 0 : locationPrimaryContactNumber.hashCode());
		result = prime * result + ((locationStateCode == null) ? 0 : locationStateCode.hashCode());
		result = prime * result + ((locationZipcode == null) ? 0 : locationZipcode.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nodeCarrierInfoList == null) ? 0 : nodeCarrierInfoList.hashCode());
		result = prime * result + (nodeStatusActive ? 1231 : 1237);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeDetail other = (NodeDetail) obj;
		
		if (globalLocationNumber == null) {
			if (other.globalLocationNumber != null)
				return false;
		} else if (!globalLocationNumber.equals(other.globalLocationNumber))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locationAddressLine1 == null) {
			if (other.locationAddressLine1 != null)
				return false;
		} else if (!locationAddressLine1.equals(other.locationAddressLine1))
			return false;
		if (locationAddressLine2 == null) {
			if (other.locationAddressLine2 != null)
				return false;
		} else if (!locationAddressLine2.equals(other.locationAddressLine2))
			return false;
		if (locationCity == null) {
			if (other.locationCity != null)
				return false;
		} else if (!locationCity.equals(other.locationCity))
			return false;
		if (locationCountryCode == null) {
			if (other.locationCountryCode != null)
				return false;
		} else if (!locationCountryCode.equals(other.locationCountryCode))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (locationPrimaryContactEmail == null) {
			if (other.locationPrimaryContactEmail != null)
				return false;
		} else if (!locationPrimaryContactEmail.equals(other.locationPrimaryContactEmail))
			return false;
		if (locationPrimaryContactName == null) {
			if (other.locationPrimaryContactName != null)
				return false;
		} else if (!locationPrimaryContactName.equals(other.locationPrimaryContactName))
			return false;
		if (locationPrimaryContactNumber == null) {
			if (other.locationPrimaryContactNumber != null)
				return false;
		} else if (!locationPrimaryContactNumber.equals(other.locationPrimaryContactNumber))
			return false;
		if (locationStateCode == null) {
			if (other.locationStateCode != null)
				return false;
		} else if (!locationStateCode.equals(other.locationStateCode))
			return false;
		if (locationZipcode == null) {
			if (other.locationZipcode != null)
				return false;
		} else if (!locationZipcode.equals(other.locationZipcode))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nodeCarrierInfoList == null) {
			if (other.nodeCarrierInfoList != null)
				return false;
		} else if (!nodeCarrierInfoList.equals(other.nodeCarrierInfoList))
			return false;
		if (nodeStatusActive != other.nodeStatusActive)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NodeDetail [id=" + id + ", name=" + name + ", globalLocationNumber=" + globalLocationNumber
				+ ", locationId=" + locationId + ", locationAddressLine1="
				+ locationAddressLine1 + ", locationAddressLine2=" + locationAddressLine2 + ", locationCity="
				+ locationCity + ", locationZipcode=" + locationZipcode + ", locationStateCode=" + locationStateCode
				+ ", locationCountryCode=" + locationCountryCode + ", locationPrimaryContactName="
				+ locationPrimaryContactName + ", locationPrimaryContactNumber=" + locationPrimaryContactNumber
				+ ", locationPrimaryContactEmail=" + locationPrimaryContactEmail + ", nodeStatusActive="
				+ nodeStatusActive + ", nodeCarrierInfoList=" + nodeCarrierInfoList + "]";
	}
	private String id;
	private String name;
	private String globalLocationNumber;
	private String description;		
	private String locationId;
	private String locationAddressLine1;
	private String locationAddressLine2;
	private String locationCity;
	private String locationZipcode;
	private String locationStateCode;
	private String locationCountryCode;	
	private String locationPrimaryContactName;
	private String locationPrimaryContactNumber;
	private String locationPrimaryContactEmail;
	private boolean nodeStatusActive;
	public List<NodeCarrierInfo> nodeCarrierInfoList;
	
	public String getLocationCountryCode() {
		return locationCountryCode;
	}
	public void setLocationCountryCode(String locationCountryCode) {
		this.locationCountryCode = locationCountryCode;
	}	
	
	public boolean isNodeStatusActive() {
		return nodeStatusActive;
	}
	public void setNodeStatusActive(boolean nodeStatusActive) {
		this.nodeStatusActive = nodeStatusActive;
	}
	public String getLocationPrimaryContactName() {
		return locationPrimaryContactName;
	}
	public void setLocationPrimaryContactName(String locationPrimaryContactName) {
		this.locationPrimaryContactName = locationPrimaryContactName;
	}
	public String getLocationPrimaryContactNumber() {
		return locationPrimaryContactNumber;
	}
	public void setLocationPrimaryContactNumber(String locationPrimaryContactNumber) {
		this.locationPrimaryContactNumber = locationPrimaryContactNumber;
	}
	public String getLocationPrimaryContactEmail() {
		return locationPrimaryContactEmail;
	}
	public void setLocationPrimaryContactEmail(String locationPrimaryContactEmail) {
		this.locationPrimaryContactEmail = locationPrimaryContactEmail;
	}
	
	public List<NodeCarrierInfo> getNodeCarrierInfoList() {
		return nodeCarrierInfoList;
	}
	public void setNodeCarrierInfoList(List<NodeCarrierInfo> nodeCarrierInfoList) {
		this.nodeCarrierInfoList = nodeCarrierInfoList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGlobalLocationNumber() {
		return globalLocationNumber;
	}
	public void setGlobalLocationNumber(String globalLocationNumber) {
		this.globalLocationNumber = globalLocationNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocationAddressLine1() {
		return locationAddressLine1;
	}
	public void setLocationAddressLine1(String locationAddressLine1) {
		this.locationAddressLine1 = locationAddressLine1;
	}
	public String getLocationAddressLine2() {
		return locationAddressLine2;
	}
	public void setLocationAddressLine2(String locationAddressLine2) {
		this.locationAddressLine2 = locationAddressLine2;
	}
	public String getLocationCity() {
		return locationCity;
	}
	public void setLocationCity(String locationCity) {
		this.locationCity = locationCity;
	}
	public String getLocationZipcode() {
		return locationZipcode;
	}
	public void setLocationZipcode(String locationZipcode) {
		this.locationZipcode = locationZipcode;
	}
	public String getLocationStateCode() {
		return locationStateCode;
	}
	public void setLocationStateCode(String locationStateCode) {
		this.locationStateCode = locationStateCode;
	}
	
	
	
	
}

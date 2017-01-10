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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class CarrierDetail {
	
	
	private int id;	
	@NotNull
	@Pattern(regexp = "^[A-Za-z]+$",message="Provide the alphanumeric value for the carrier name")
	private String name;
	private String description;
	@NotNull (message="Please provide the value for scac")
	private String scac;
	@NotNull
	private boolean isActive;	
    
	@NotNull(message="Please provide the value for address")
	@Valid
	private LocationInfo locationInfo;
	private String iconUrl;
	@Valid
	public List<CarrierServiceInfo> carrierServiceInfoList;
	private int totalServices;
	private int totalActiveServices;
	private int totalInactiveServices;
	
	
	
	
	public int getTotalServices() {
		return totalServices;
	}
	public void setTotalServices(int totalServices) {
		this.totalServices = totalServices;
	}
	public int getTotalActiveServices() {
		return totalActiveServices;
	}
	public void setTotalActiveServices(int totalActiveServices) {
		this.totalActiveServices = totalActiveServices;
	}
	public int getTotalInactiveServices() {
		return totalInactiveServices;
	}
	public void setTotalInactiveServices(int totalInactiveServices) {
		this.totalInactiveServices = totalInactiveServices;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScac() {
		return scac;
	}
	public void setScac(String scac) {
		this.scac = scac;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public LocationInfo getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(LocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}	
	
	public List<CarrierServiceInfo> getCarrierServiceInfoList() {
		return carrierServiceInfoList;
	}
	public void setCarrierServiceInfoList(List<CarrierServiceInfo> carrierServiceInfoList) {
		this.carrierServiceInfoList = carrierServiceInfoList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;		
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((iconUrl == null) ? 0 : iconUrl.hashCode());
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);		
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((scac == null) ? 0 : scac.hashCode());		
		return result;
	}
	@Override
	public String toString() {
		return "CarrierDetail [id=" + id + ", name=" + name + ", description=" + description + ", scac=" + scac
				+ ", isActive=" + isActive + ", locationInfo=" + locationInfo + ", iconUrl=" + iconUrl
				+ ", carrierServiceInfoList=" + carrierServiceInfoList + ", totalServices=" + totalServices
				+ ", totalActiveServices=" + totalActiveServices + ", totalInactiveServices=" + totalInactiveServices
				+ "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarrierDetail other = (CarrierDetail) obj;
		if (carrierServiceInfoList == null) {
			if (other.carrierServiceInfoList != null)
				return false;
		}
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (iconUrl == null) {
			if (other.iconUrl != null)
				return false;
		} else if (!iconUrl.equals(other.iconUrl))
			return false;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (locationInfo == null) {
			if (other.locationInfo != null)
				return false;
		} else if (!locationInfo.equals(other.locationInfo))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (scac == null) {
			if (other.scac != null)
				return false;
		} else if (!scac.equals(other.scac))
			return false;
		if (totalActiveServices != other.totalActiveServices)
			return false;
		if (totalInactiveServices != other.totalInactiveServices)
			return false;
		if (totalServices != other.totalServices)
			return false;
		return true;
	}
}

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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CarrierServiceInfo {
	
	
	private int Id;
	@NotNull
	private String name;
	private boolean isActive;
	private String serviceType;
	private String serviceLevel;
	private String scac;
	
	@NotNull
    @Pattern(regexp = "^[A-Za-z0-9\\.\\-\\_*]+$", 
    	message="{cip_common.shippingunit.shipperdetails.shipperservicetype.shipperservicetypecode.pattern}")
    @Size(min = 0, max = 10,  message = "{cip_common.shippingunit.shipperdetails.shipperservicetype.shipperservicetypecode.size}")
	private String code;
	private String locationPrimaryContactName;
	private String locationPrimaryContactNumber;
	private String locationPrimaryContactEmail;	
	private List<RuleInfo> ruleInfos;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Id;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((locationPrimaryContactEmail == null) ? 0 : locationPrimaryContactEmail.hashCode());
		result = prime * result + ((locationPrimaryContactName == null) ? 0 : locationPrimaryContactName.hashCode());
		result = prime * result
				+ ((locationPrimaryContactNumber == null) ? 0 : locationPrimaryContactNumber.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ruleInfos == null) ? 0 : ruleInfos.hashCode());
		result = prime * result + ((scac == null) ? 0 : scac.hashCode());
		result = prime * result + ((serviceLevel == null) ? 0 : serviceLevel.hashCode());
		result = prime * result + ((serviceType == null) ? 0 : serviceType.hashCode());
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
		CarrierServiceInfo other = (CarrierServiceInfo) obj;
		if (Id != other.Id)
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (isActive != other.isActive)
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ruleInfos == null) {
			if (other.ruleInfos != null)
				return false;
		} else if (!ruleInfos.equals(other.ruleInfos))
			return false;
		if (scac == null) {
			if (other.scac != null)
				return false;
		} else if (!scac.equals(other.scac))
			return false;
		if (serviceLevel == null) {
			if (other.serviceLevel != null)
				return false;
		} else if (!serviceLevel.equals(other.serviceLevel))
			return false;
		if (serviceType == null) {
			if (other.serviceType != null)
				return false;
		} else if (!serviceType.equals(other.serviceType))
			return false;
		return true;
	}	
	
	@Override
	public String toString() {
		return "CarrierServiceInfo [Id=" + Id + ", name=" + name + ", isActive=" + isActive + ", serviceType="
				+ serviceType + ", serviceLevel=" + serviceLevel + ", scac=" + scac + "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getScac() {
		return scac;
	}

	public void setScac(String scac) {
		this.scac = scac;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	

	public List<RuleInfo> getRuleInfos() {
		return ruleInfos;
	}

	public void setRuleInfos(List<RuleInfo> ruleInfos) {
		this.ruleInfos = ruleInfos;
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


}

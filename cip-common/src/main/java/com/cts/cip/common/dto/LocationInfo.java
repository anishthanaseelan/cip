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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class LocationInfo {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressLine1 == null) ? 0 : addressLine1.hashCode());
		result = prime * result + ((addressLine2 == null) ? 0 : addressLine2.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((primaryContactEmail == null) ? 0 : primaryContactEmail.hashCode());
		result = prime * result + ((primaryContactName == null) ? 0 : primaryContactName.hashCode());
		result = prime * result + ((primaryContactNumber == null) ? 0 : primaryContactNumber.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
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
		LocationInfo other = (LocationInfo) obj;
		if (addressLine1 == null) {
			if (other.addressLine1 != null)
				return false;
		} else if (!addressLine1.equals(other.addressLine1))
			return false;
		if (addressLine2 == null) {
			if (other.addressLine2 != null)
				return false;
		} else if (!addressLine2.equals(other.addressLine2))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (primaryContactEmail == null) {
			if (other.primaryContactEmail != null)
				return false;
		} else if (!primaryContactEmail.equals(other.primaryContactEmail))
			return false;
		if (primaryContactName == null) {
			if (other.primaryContactName != null)
				return false;
		} else if (!primaryContactName.equals(other.primaryContactName))
			return false;
		if (primaryContactNumber == null) {
			if (other.primaryContactNumber != null)
				return false;
		} else if (!primaryContactNumber.equals(other.primaryContactNumber))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}

	@NotNull	
	private int id;
	
	@NotNull    
    @NotBlank(message = "{cip_common.shippingunit.address.city.empty}")
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$", 
		message="{cip_common.shippingunit.address.city.pattern}")
    @Size(min = 0, max = 35,  message = "{cip_common.shippingunit.address.city.size}")
	private String city;

	@NotNull
	@NotBlank(message = "{cip_common.shippingunit.address.country.empty}")
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-\\_\\.\\#\\*]+$", 
   		message="{cip_common.shippingunit.address.country.pattern}")
    @Size(min = 0, max = 20,  message = "{cip_common.shippingunit.address.country.size}")	
	private String countryCode;

	private String name;
	
	@NotNull
	@NotBlank(message = "{cip_common.shippingunit.address.individualname.empty}")
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-\\.\\_*]+$", 
	message="{cip_common.shippingunit.address.individualname.pattern}")
    @Size(min = 0, max = 35,  message = "{cip_common.shippingunit.address.individualname.size}")
	private String primaryContactName;
	
	@NotNull
	@NotBlank(message = "{cip_common.shippingunit.address.phonenumber.empty}")
    @Pattern(regexp = "^[0-9]+$", 
	message="{cip_common.shippingunit.address.phonenumber.pattern}")
    @Size(min = 0, max = 10,  message = "{cip_common.shippingunit.address.phonenumber.size}")
	private String primaryContactNumber;
	
	@Email(message = "{cip_common.shippingunit.address.emailaddress.invalid}")
    @Size(min = 0, max = 50,  message = "{cip_common.shippingunit.address.emailaddress.size}")	
	private String primaryContactEmail;
	
	@NotNull
	@NotBlank(message = "{cip_common.shippingunit.address.state.empty}")
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-\\_\\.\\#\\*]+$", 
	message="{cip_common.shippingunit.address.state.pattern}")
    @Size(min = 0, max = 35,  message = "{cip_common.shippingunit.address.state.size}")
	private String stateCode;

	@NotNull
	@NotBlank(message = "{cip_common.shippingunit.address.zipcode.empty}")
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-\\_\\.\\#\\*]+$", 
	message="{cip_common.shippingunit.address.zipcode.pattern}")
    @Size(min = 0, max = 10,  message = "{cip_common.shippingunit.address.zipcode.size}")	
	private String zipCode;
	
	@NotNull
	@NotBlank(message = "{cip_common.shippingunit.address.addressline1.empty}")
    @Pattern(regexp = "^[A-Za-z0-9\\s\\-\\_\\.\\#\\*]+$", 
	message="{cip_common.shippingunit.address.addressline1.pattern}")
    @Size(min = 0, max = 35,  message = "{cip_common.shippingunit.address.addressline1.size}")
	private String addressLine1;
	
	@Pattern(regexp = "^[A-Za-z0-9\\s\\-\\_\\.\\#\\*]+$", 
			message="{cip_common.shippingunit.address.addressline2.pattern}")
		    @Size(min = 0, max = 35,  message = "{cip_common.shippingunit.address.addressline2.size}")
	private String addressLine2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimaryContactName() {
		return primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}

	public String getPrimaryContactNumber() {
		return primaryContactNumber;
	}

	public void setPrimaryContactNumber(String primaryContactNumber) {
		this.primaryContactNumber = primaryContactNumber;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getPrimaryContactEmail() {
		return primaryContactEmail;
	}

	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	

}

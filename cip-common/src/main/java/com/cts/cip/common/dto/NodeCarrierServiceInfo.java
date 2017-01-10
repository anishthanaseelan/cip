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

public class NodeCarrierServiceInfo {
	
	private String carrierServiceId;
	private String name;
	private boolean isActive;
	private int id;
	private String meterNumber;
	
	private String accountNumber;
	private String locationId;
	private String locationAddressLine1;
	private String locationAddressLine2;
	private String locationCity;
	private String locationZipcode;
	private String locationStateCode;
	private String locationPrimaryContactName;
	private String locationPrimaryContactNumber;
	private String locationPrimaryContactEmail;
	private String locationCountryCode;
	
	public String getLocationCountryCode() {
		return locationCountryCode;
	}
	public void setLocationCountryCode(String locationCountryCode) {
		this.locationCountryCode = locationCountryCode;
	}
	public String getMeterNumber() {
		return meterNumber;
	}
	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	@Override
	public String toString() {
		return "CarrierServiceInfo [id=" + carrierServiceId + ", serviceName=" + name + ", isActive=" + isActive+ "]";
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setCarrierServiceId(String carrierServiceId) {
		this.carrierServiceId = carrierServiceId;
		
	}
	
	public String getCarrierServiceId() {
		return carrierServiceId;
		
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

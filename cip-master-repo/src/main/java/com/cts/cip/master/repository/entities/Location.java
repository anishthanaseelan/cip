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
package com.cts.cip.master.repository.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the location database table.
 * 
 */
@Entity
@Table(name = "location")
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l")
public class Location implements Serializable {
	@Override
	public String toString() {
		return "Location [id=" + id + ", city=" + city + ", countryCode=" + countryCode + ", fax=" + fax + ", name="
				+ name + ", primaryContactName=" + primaryContactName + ", primaryContactNumber=" + primaryContactNumber
				+ ", primaryContactEmail=" + primaryContactEmail + ", secondaryContactEmail=" + secondaryContactEmail
				+ ", secondaryContactName=" + secondaryContactName + ", secondaryContactNumber="
				+ secondaryContactNumber + ", stateCode=" + stateCode + ", supportHelpLine=" + supportHelpLine
				+ ", zipCode=" + zipCode + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + "]";
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String city;

	@Column(name="country_code")
	private String countryCode;

	private String fax;

	private String name;

	@Column(name="primary_contact_name")
	private String primaryContactName;

	@Column(name="primary_contact_number")
	private String primaryContactNumber;
	
	@Column(name="primary_contact_email")
	private String primaryContactEmail;

	@Column(name="secondary_contact_email")
	private String secondaryContactEmail;

	@Column(name="secondary_contact_name")
	private String secondaryContactName;

	@Column(name="secondary_contact_number")
	private String secondaryContactNumber;

	@Column(name="state_code")
	private String stateCode;

	

	@Column(name="support_help_line")
	private String supportHelpLine;

	@Column(name="zip_code")
	private String zipCode;

	@Column(name="address_line_1")
	private String addressLine1;
	
	@Column(name="address_line_2")
	private String addressLine2;
	

	

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

	public Location() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrimaryContactName() {
		return this.primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}

	public String getPrimaryContactNumber() {
		return this.primaryContactNumber;
	}

	public void setPrimaryContactNumber(String primaryContactNumber) {
		this.primaryContactNumber = primaryContactNumber;
	}

	public String getSecondaryContactEmail() {
		return this.secondaryContactEmail;
	}

	public void setSecondaryContactEmail(String secondaryContactEmail) {
		this.secondaryContactEmail = secondaryContactEmail;
	}

	public String getSecondaryContactName() {
		return this.secondaryContactName;
	}

	public void setSecondaryContactName(String secondaryContactName) {
		this.secondaryContactName = secondaryContactName;
	}

	public String getSecondaryContactNumber() {
		return this.secondaryContactNumber;
	}

	public void setSecondaryContactNumber(String secondaryContactNumber) {
		this.secondaryContactNumber = secondaryContactNumber;
	}

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	
	public String getPrimaryContactEmail() {
		return primaryContactEmail;
	}

	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}

	public String getSupportHelpLine() {
		return this.supportHelpLine;
	}

	public void setSupportHelpLine(String supportHelpLine) {
		this.supportHelpLine = supportHelpLine;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
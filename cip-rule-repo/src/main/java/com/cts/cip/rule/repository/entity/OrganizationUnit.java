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
package com.cts.cip.rule.repository.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the organization_unit database table.
 * 
 */
@Entity
@Table(name="organization_unit")
@NamedQuery(name="OrganizationUnit.findAll", query="SELECT o FROM OrganizationUnit o")
public class OrganizationUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="global_location_number")
	private String globalLocationNumber;

	@Column(name="location_id")
	private int locationId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	/*//bi-directional many-to-many association to CarrierService
	@OneToMany(mappedBy="organizationUnits")
	private List<CarrierService> carrierServices;*/

	//bi-directional many-to-one association to Organization
	@ManyToOne
	private Organization organization;

	public OrganizationUnit() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGlobalLocationNumber() {
		return this.globalLocationNumber;
	}

	public void setGlobalLocationNumber(String globalLocationNumber) {
		this.globalLocationNumber = globalLocationNumber;
	}

	public int getLocationId() {
		return this.locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/*public List<CarrierService> getCarrierServices() {
		return this.carrierServices;
	}

	public void setCarrierServices(List<CarrierService> carrierServices) {
		this.carrierServices = carrierServices;
	}*/

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

}
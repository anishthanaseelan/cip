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

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the organization database table.
 * 
 */
@Entity
@Table(name = "organization")
@NamedQuery(name="Organization.findAll", query="SELECT o FROM Organization o")
public class Organization implements Serializable {
	@Override
	public String toString() {
		return "Organization [id=" + id + ", createTime=" + createTime + ", description=" + description + ", iconImg="
				+ Arrays.toString(iconImg) + ", name=" + name + ", updateTime=" + updateTime + ", location=" + location
				+ ", organizationUnits=" + organizationUnits + "]";
	}

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	private String description;

	@Lob
	@Column(name="icon_img")
	private byte[] iconImg;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	//bi-directional many-to-one association to Location
	@OneToOne(cascade = CascadeType.PERSIST)
	private Location location;

	//bi-directional many-to-one association to OrganizationUnit
	@OneToMany(mappedBy="organization")
	private List<OrganizationUnit> organizationUnits;

	public Organization() {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getIconImg() {
		return this.iconImg;
	}

	public void setIconImg(byte[] iconImg) {
		this.iconImg = iconImg;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<OrganizationUnit> getOrganizationUnits() {
		return this.organizationUnits;
	}

	public void setOrganizationUnits(List<OrganizationUnit> organizationUnits) {
		this.organizationUnits = organizationUnits;
	}

	public OrganizationUnit addOrganizationUnit(OrganizationUnit organizationUnit) {
		getOrganizationUnits().add(organizationUnit);
		organizationUnit.setOrganization(this);

		return organizationUnit;
	}

	public OrganizationUnit removeOrganizationUnit(OrganizationUnit organizationUnit) {
		getOrganizationUnits().remove(organizationUnit);
		organizationUnit.setOrganization(null);

		return organizationUnit;
	}

}
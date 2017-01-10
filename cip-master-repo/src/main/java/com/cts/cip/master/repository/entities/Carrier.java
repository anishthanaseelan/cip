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
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the carrier database table.
 * 
 */
@Entity
@Table(name = "carrier")
@NamedQuery(name="Carrier.findAll", query="SELECT c FROM Carrier c")
public class Carrier implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;	
	
	private String name;
	
	private String scac;
	
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;	
	
	@Column(name="icon_url")	
	private String iconUrl;		

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="location_id")
	private Location location;
	
	@Column(name="status")
	private boolean isActive;	
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="carrier_id")
	private List<CarrierService> carrierServiceList;	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	

	public Carrier() {
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

	
	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconId( String iconUrl) {
		this.iconUrl = iconUrl;
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

	public String getScac() {
		return scac;
	}

	public void setScac(String scac) {
		this.scac = scac;
	}

	
	public List<CarrierService> getCarrierServiceList() {
		return carrierServiceList;
	}

	public void setCarrierServiceList(List<CarrierService> carrierServiceList) {
		this.carrierServiceList = carrierServiceList;
	}


}
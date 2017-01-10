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
import java.util.Date;


/**
 * The persistent class for the node_carrier_service database table.
 * 
 */
@Entity
@Table(name="node_carrier_service")
@NamedQuery(name="NodeCarrierService.findAll", query="SELECT n FROM NodeCarrierService n")
public class NodeCarrierService implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NodeCarrierServiceId id;	
	
	@Column(name="status")
	private boolean isActive;	
	
	@Column(name="account_number")
	private String accountNumber;	
	
	@Column(name="meter_number")
	private String meterNumber;		

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="carrier_service_location_id",insertable = true, updatable = true)
	private Location location;
	
	
	@ManyToOne
	@JoinColumn(name="node_id",insertable = false, updatable = false)
	private Node node;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name="carrier_service_code",insertable = false, updatable = false)
	private CarrierService carrierService;
	
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getMeterNumber() {
		return meterNumber;
	}

	public void setMeterNumber(String meterNumber) {
		this.meterNumber = meterNumber;
	}
	
	public boolean isActive() {
		return isActive;
	}	

	public CarrierService getCarrierService() {
		return carrierService;
	}

	public void setCarrierService(CarrierService carrierService) {
		this.carrierService = carrierService;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public NodeCarrierService() {
	}

	public NodeCarrierServiceId getId() {
		return this.id;
	}

	public void setId(NodeCarrierServiceId id) {
		this.id = id;
	}

	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}	

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
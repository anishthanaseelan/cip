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

	@Column(name="carrier_service_location_id")
	private int carrierServiceLocationId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	
	@Column(name="status")
	private boolean isActive;

	public boolean isActive() {
		return isActive;
	}

	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@ManyToOne
	@JoinColumn(name="node_id",insertable = false, updatable = false)
	private Node node;
	
	@ManyToOne
	@JoinColumn(name="carrier_service_id",insertable = false, updatable = false)
	private CarrierService carrierService;
	

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

	public int getCarrierServiceLocationId() {
		return this.carrierServiceLocationId;
	}

	public void setCarrierServiceLocationId(int carrierServiceLocationId) {
		this.carrierServiceLocationId = carrierServiceLocationId;
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
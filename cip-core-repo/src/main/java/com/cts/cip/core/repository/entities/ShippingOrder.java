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
package com.cts.cip.core.repository.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the order database table.
 * 
 */
@Entity
@Table(name = "shipping_order")
@NamedQuery(name = "ShippingOrder.findAll", query = "SELECT o FROM ShippingOrder o")
public class ShippingOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "reference_id")
	private String referenceId;

	@Column(name = "parent_reference_id")
	private String parentReferenceId;

	private String status;

	private String tcn;

	@Column(name = "wave_number")
	private String waveNumber;

	// bi-directional many-to-one association to Address
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "billing_address_id")
	private AddressEntity billingAddress;

	// bi-directional many-to-one association to Address
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "return_address_id")
	private AddressEntity returnAddress;

	// bi-directional many-to-one association to Address
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "shipper_address_id")
	private AddressEntity shipperAddress;

	// bi-directional many-to-one association to Address
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "delivery_address_id")
	private AddressEntity deliveryAddress;

	@Column(name = "node_id")
	private Integer nodeId;

	@Column(name = "business_unit_id")
	private Integer businessUnitId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	public ShippingOrder() {
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

	public String getParentReferenceId() {
		return this.parentReferenceId;
	}

	public void setParentReferenceId(String parentReferenceId) {
		this.parentReferenceId = parentReferenceId;
	}

	public String getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTcn() {
		return this.tcn;
	}

	public void setTcn(String tcn) {
		this.tcn = tcn;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getWaveNumber() {
		return this.waveNumber;
	}

	public void setWaveNumber(String waveNumber) {
		this.waveNumber = waveNumber;
	}

	public AddressEntity getBillingAddress() {
		return this.billingAddress;
	}

	public void setBillingAddress(AddressEntity billingAddress) {
		this.billingAddress = billingAddress;
	}

	public AddressEntity getReturnAddress() {
		return this.returnAddress;
	}

	public void setReturnAddress(AddressEntity returnAddress) {
		this.returnAddress = returnAddress;
	}

	public AddressEntity getShipperAddress() {
		return this.shipperAddress;
	}

	public void setShipperAddress(AddressEntity shipperAddress) {
		this.shipperAddress = shipperAddress;
	}

	public AddressEntity getDeliveryAddress() {
		return this.deliveryAddress;
	}

	public void setDeliveryAddress(AddressEntity deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getBusinessUnitId() {
		return businessUnitId;
	}

	public void setBusinessUnitId(Integer businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

}
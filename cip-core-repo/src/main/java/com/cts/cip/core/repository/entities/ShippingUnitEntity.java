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
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the shipping_unit database table.
 * 
 */
@Entity
@Table(name = "shipping_unit")
@NamedQuery(name = "ShippingUnitEntity.findAll", query = "SELECT s FROM ShippingUnitEntity s")
public class ShippingUnitEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "referance_id")
	private String referanceId;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "invoice_id")
	private Invoice invoice;

	private Float height;

	private Float length;

	private Float width;

	private Float weight;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "order_id")
	private ShippingOrder shippingOrder;

	@Column(name = "carrier_service_id")
	private Integer carrierServiceId;

	@Column(name = "status")
	private String status;

	@Column(name = "tracking_nbr")
	private String trackingNumber;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_ts")
	private Date lastUpdatedTimeStamp;

	public Date getLastUpdatedTimeStamp() {		
		return lastUpdatedTimeStamp;
	}

	public void setLastUpdatedTimeStamp(Date lastUpdatedTimeStamp) {
		this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
	}

	public ShippingUnitEntity() {
	}

	public Integer getId() {
		return this.id;
	}

	public String getReferanceId() {
		return referanceId;
	}

	public void setReferanceId(String referanceId) {
		this.referanceId = referanceId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getHeight() {
		return this.height;
	}

	public void setHeight(Float height) {
		this.height = height;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Float getLength() {
		return this.length;
	}

	public void setLength(Float length) {
		this.length = length;
	}

	public ShippingOrder getOrder() {
		return this.shippingOrder;
	}

	public void setOrder(ShippingOrder shippingOrder) {
		this.shippingOrder = shippingOrder;
	}

	public Integer getCarrierServiceId() {
		return this.carrierServiceId;
	}

	public void setCarrierServiceId(Integer carrierServiceId) {
		this.carrierServiceId = carrierServiceId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Float getWeight() {
		return this.weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Float getWidth() {
		return this.width;
	}

	public void setWidth(Float width) {
		this.width = width;
	}

}
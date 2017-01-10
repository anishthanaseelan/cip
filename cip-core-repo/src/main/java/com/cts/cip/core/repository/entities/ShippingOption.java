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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the shipping_option database table.
 * 
 */
@Entity
@Table(name = "shipping_option")
@NamedQuery(name = "ShippingOption.findAll", query = "SELECT s FROM ShippingOption s")
public class ShippingOption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "shipping_unit_id")
	private String shippingUnitId;

	@Column(name = "shipping_option_name")
	private String shippingOptionName;

	@Column(name = "shipping_option_value")
	private String shippingOptionValue;

	private byte sequence;

	public ShippingOption() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCartonId() {
		return this.shippingUnitId;
	}

	public void setCartonId(String cartonId) {
		this.shippingUnitId = cartonId;
	}

	public String getShippingOptionName() {
		return this.shippingOptionName;
	}

	public void setShippingOptionName(String shippingOptionName) {
		this.shippingOptionName = shippingOptionName;
	}

	public String getShippingOptionValue() {
		return this.shippingOptionValue;
	}

	public void setShippingOptionValue(String shippingOptionValue) {
		this.shippingOptionValue = shippingOptionValue;
	}

	public byte getSequence() {
		return this.sequence;
	}

	public void setSequence(byte sequence) {
		this.sequence = sequence;
	}

}
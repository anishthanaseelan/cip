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
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the load_shipping_unit database table.
 * 
 */
@Entity
@IdClass(LoadShippingUnitPK.class)
@Table(name = "load_shipping_unit")
@NamedQuery(name = "LoadShippingUnit.findAll", query = "SELECT s FROM LoadShippingUnit s")
public class LoadShippingUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "shipping_unit_ref_id")
	private String shippingUnitRefId;

	@Id
	@Column(name = "load_id")
	private Integer loadId;

	@ManyToOne
	@JoinColumn(name = "load_id", nullable = false, insertable = false, updatable = false)
	private LoadEntity load;

	public String getShippingUnitRefId() {
		return shippingUnitRefId;
	}

	public void setShippingUnitRefId(String shippingUnitRefId) {
		this.shippingUnitRefId = shippingUnitRefId;
	}

	public Integer getLoadId() {
		return loadId;
	}

	public void setLoadId(Integer loadUnitId) {
		this.loadId = loadUnitId;
	}

	public LoadEntity getLoad() {
		return load;
	}

	public void setLoad(LoadEntity load) {
		this.load = load;
	}

}

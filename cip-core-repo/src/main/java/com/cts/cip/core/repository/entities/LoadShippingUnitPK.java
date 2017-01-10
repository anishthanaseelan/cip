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
import javax.persistence.Embeddable;

/**
 * The primary key class for the load_shipping_unit database table.
 * 
 */
@Embeddable
public class LoadShippingUnitPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "shipping_unit_ref_id")
	private String shippingUnitRefId;

	@Column(name = "load_id")
	private Integer loadId;

	/**
	 * @return the shippingUnitId
	 */
	public String getShippingUnitRefId() {
		return shippingUnitRefId;
	}

	/**
	 * @param shippingUnitId
	 *            the shippingUnitId to set
	 */
	public void setShippingUnitRefId(String shippingUnitId) {
		this.shippingUnitRefId = shippingUnitId;
	}

	/**
	 * @return the loadUnitId
	 */
	public Integer getLoadUnitId() {
		return loadId;
	}

	/**
	 * @param loadUnitId
	 *            the loadUnitId to set
	 */
	public void setLoadUnitId(Integer loadId) {
		this.loadId = loadId;
	}

}

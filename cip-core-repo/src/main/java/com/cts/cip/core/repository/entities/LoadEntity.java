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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the load database table.
 * 
 */
@Entity
@Table(name = "load_unit")
@NamedQuery(name = "LoadEntity.findAll", query = "SELECT l FROM LoadEntity l")
public class LoadEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "reference_id")
	private String referenceId;

	private String status;

	@OneToMany(mappedBy = "loadId", fetch = FetchType.EAGER)
	private List<LoadShippingUnit> loadShippingUnits;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_ts")
	private Date createTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "manifest_ts")
	private Date manifestTs;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_ts")
	private Date updateTs;
	
	@Column(name = "carrier_id")
	private Integer carrier_id;
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return this.createTs;
	}

	public void setCreateDate(Date createDate) {
		this.createTs = createDate;
	}

	public Date getManifestDate() {
		return this.manifestTs;
	}

	public void setManifestDate(Date manifestDate) {
		this.manifestTs = manifestDate;
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

	public List<LoadShippingUnit> getLoadShippingUnits() {
		return loadShippingUnits;
	}

	public void setLoadShippingUnits(List<LoadShippingUnit> loadShippingUnits) {
		this.loadShippingUnits = loadShippingUnits;
	}

	public Date getUpdateTime() {
		return this.updateTs;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTs = updateTime;
	}

	/**
	 * @return the carrier_id
	 */
	public Integer getCarrier_id() {
		return carrier_id;
	}

	/**
	 * @param carrier_id the carrier_id to set
	 */
	public void setCarrier_id(Integer carrier_id) {
		this.carrier_id = carrier_id;
	}

}
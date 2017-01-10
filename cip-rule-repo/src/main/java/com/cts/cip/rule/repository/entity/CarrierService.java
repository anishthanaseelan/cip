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
import java.util.List;

/**
 * The persistent class for the carrier_service database table.
 * 
 */
@Entity
@Table(name = "carrier_service")
@NamedQuery(name = "CarrierService.findAll", query = "SELECT c FROM CarrierService c")
public class CarrierService implements Serializable {
	private static final long serialVersionUID = 1L;

	
	
	
	@Id
	private String code;

	@Column(name = "carrier_service_type")
	private String carrierServiceType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "icon_url")
	private String iconUrl;

	private String scac;

	@Column(name = "service_level")
	private String serviceLevel;

	@Column(name = "service_name")
	private String serviceName;

	@Column(name = "status")
	private boolean isActive;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	private Date updateTime;

	// bi-directional many-to-one association to Carrier
	@ManyToOne
	private Carrier carrier;

	@ManyToMany
	@JoinTable(
			name = "rule_service_map", 
			joinColumns = @JoinColumn(name = "carrier_service_code", referencedColumnName = "code"),
			inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"))
	private List<Rule> rulesList;

	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	
	public List<Rule> getRulesList() {
		return rulesList;
	}

	public void setRulesList(List<Rule> rulesList) {
		this.rulesList = rulesList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public CarrierService() {
	}

	

	public String getCarrierServiceType() {
		return this.carrierServiceType;
	}

	public void setCarrierServiceType(String carrierServiceType) {
		this.carrierServiceType = carrierServiceType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIconUrl() {
		return this.iconUrl;
	}

	public void setIconImg(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getScac() {
		return this.scac;
	}

	public void setScac(String scac) {
		this.scac = scac;
	}

	public String getServiceLevel() {
		return this.serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getServiceName() {
		return this.serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Carrier getCarrier() {
		return this.carrier;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

}
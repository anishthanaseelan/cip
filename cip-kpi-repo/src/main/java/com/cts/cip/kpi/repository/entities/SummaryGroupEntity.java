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
package com.cts.cip.kpi.repository.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="summary_group")
public class SummaryGroupEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")	
	private String id;
	@Column(name="org_name")
	private String organizationName;
	@Column(name="bu_name")
	private String businessUnitName;
	@Column(name="node_name")
	private String nodeName;
	@Column(name="carrier_name")
	private String carrierName;
	@Column(name="carrier_svc_name")
	private String carrierServiceName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getBusinessUnitName() {
		return businessUnitName;
	}
	public void setBusinessUnitName(String businessUnitName) {
		this.businessUnitName = businessUnitName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getCarrierServiceName() {
		return carrierServiceName;
	}
	public void setCarrierServiceName(String carrierServiceName) {
		this.carrierServiceName = carrierServiceName;
	}
	
	
	
	

}

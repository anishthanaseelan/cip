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
import java.util.List;


/**
 * The persistent class for the node database table.
 * 
 */
@Entity
@Table(name = "node")
@NamedQuery(name="Node.findAll", query="SELECT n FROM Node n")
public class Node implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	

	@Column(name="business_unit_id")
	private int businessUnitId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="global_location_number")
	private String globalLocationNumber;	
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name="location_id")
	private Location location;
	
	@OneToMany(mappedBy = "node",cascade=CascadeType.ALL)	
	private List<NodeCarrierService> nodeCarrierServiceList;	
	

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;
	
	@Column(name="status")
	private boolean isActive;
	
	

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Node() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBusinessUnitId() {
		return this.businessUnitId;
	}

	public void setBusinessUnitId(int businessUnitId) {
		this.businessUnitId = businessUnitId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGlobalLocationNumber() {
		return this.globalLocationNumber;
	}

	public void setGlobalLocationNumber(String globalLocationNumber) {
		this.globalLocationNumber = globalLocationNumber;
	}

	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	public List<NodeCarrierService> getNodeCarrierServiceList() {
		return nodeCarrierServiceList;
	}

	public void setNodeCarrierServiceList(List<NodeCarrierService> nodeCarrierServiceList) {
		this.nodeCarrierServiceList = nodeCarrierServiceList;
	}


	

}
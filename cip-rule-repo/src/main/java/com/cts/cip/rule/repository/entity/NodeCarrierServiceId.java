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

/**
 * The primary key class for the node_carrier_service database table.
 * 
 */
@Embeddable
public class NodeCarrierServiceId implements Serializable {
	
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	
	@Column(name="node_id", insertable=false, updatable=false)
	private int nodeId;

	@Column(name="carrier_service_id", insertable=false, updatable=false)
	private int carrierServiceId;

	public NodeCarrierServiceId() {
	}
	public int getNodeId() {
		return this.nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getCarrierServiceId() {
		return this.carrierServiceId;
	}
	public void setCarrierServiceId(int carrierServiceId) {
		this.carrierServiceId = carrierServiceId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NodeCarrierServiceId)) {
			return false;
		}
		NodeCarrierServiceId castOther = (NodeCarrierServiceId)other;
		return 
			(this.nodeId == castOther.nodeId)
			&& (this.carrierServiceId == castOther.carrierServiceId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.nodeId;
		hash = hash * prime + this.carrierServiceId;
		
		return hash;
	}
}
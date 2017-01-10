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
package com.cts.cip.common.dto;

import java.util.ArrayList;
import java.util.List;

public class NodeCarrierInfo {
	
	
	
	@Override
	public String toString() {
		return "NodeCarrierInfo [carrierName=" + carrierName + ", isActive=" + isActive + ", iconUrl=" + iconUrl
				+ ", id=" + id + ", carrierServiceInfoList=" + carrierServiceInfoList + "]";
	}
	
	public String carrierName;
	public boolean isActive;	
	public String iconUrl;
	public int  id;
	
	public List<NodeCarrierServiceInfo> carrierServiceInfoList;
	
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public List<NodeCarrierServiceInfo> getCarrierServiceInfoList() {
		if (carrierServiceInfoList==null) {
			carrierServiceInfoList = new ArrayList<NodeCarrierServiceInfo>();
		}
		return carrierServiceInfoList;
	}
	public void setCarrierServiceInfoList(List<NodeCarrierServiceInfo> carrierServiceInfoList) {
		this.carrierServiceInfoList = carrierServiceInfoList;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl =  iconUrl;
		
	}	
	public String getIconUrl() {
		return iconUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}

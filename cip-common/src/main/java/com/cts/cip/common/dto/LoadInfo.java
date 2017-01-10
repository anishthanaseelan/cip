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

import javax.xml.bind.annotation.XmlRootElement;

import com.cts.cip.common.model.LoadBase;

@XmlRootElement
public class LoadInfo {
	
	private LoadBase loadBase;	
	private List<ShipmentLoadInfo> shipmentLoadInfoList;
	
	public LoadBase getLoadBase() {
		return loadBase;
	}
	public void setLoadBase(LoadBase loadBase) {
		this.loadBase = loadBase;
	}
	public List<ShipmentLoadInfo> getShipmentLoadInfoList() {
		if (shipmentLoadInfoList==null) {
			shipmentLoadInfoList =  new ArrayList<>();
		}
		return shipmentLoadInfoList;
	}
	public void setShipmentLoadInfoList(List<ShipmentLoadInfo> shiipmentLoadInfoList) {
		this.shipmentLoadInfoList = shiipmentLoadInfoList;
	}
	

}

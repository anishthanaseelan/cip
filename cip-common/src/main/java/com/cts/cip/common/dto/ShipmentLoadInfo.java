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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ShipmentLoadInfo {

	@Override
	public String toString() {
		return "ShipmentLoadInfo [shipReferanceId=" + shipReferenceId + ", loadReferanceId=" + loadRefernceId
				+ ", shipperServiceId=" + shipperServiceId + ", carrierServiceType=" + carrierServiceType
				+ ", shipmentStatus=" + shipmentStatus + ", loadStatus=" + loadStatus + ", lastUpdatedTmstmp="
				+ lastUpdatedTmstmp + ", trackingNumber=" + trackingNumber + "]";
	}
	private String shipReferenceId;
	private String loadRefernceId;

	private String shipperServiceId;
	private String carrierServiceType;

	private String shipmentStatus;
	private String loadStatus;

	private String lastUpdatedTmstmp;
	private String trackingNumber;
	
	public String getShipReferenceId() {
		return shipReferenceId;
	}
	public void setShipReferenceId(String shipReferanceId) {
		this.shipReferenceId = shipReferanceId;
	}
	public String getLoadReferenceId() {
		return loadRefernceId;
	}
	public void setLoadReferenceId(String loadReferanceId) {
		this.loadRefernceId = loadReferanceId;
	}
	public String getShipperServiceId() {
		return shipperServiceId;
	}
	public void setShipperServiceId(String shipperServiceId) {
		this.shipperServiceId = shipperServiceId;
	}
	public String getShipmentStatus() {
		return shipmentStatus;
	}
	public void setShipmentStatus(String shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
	public String getLoadStatus() {
		return loadStatus;
	}
	public void setLoadStatus(String loadStatus) {
		this.loadStatus = loadStatus;
	}
	public String getLastUpdatedTmstmp() {
		return lastUpdatedTmstmp;
	}
	public void setLastUpdatedTmstmp(String lastUpdatedTmstmp) {
		this.lastUpdatedTmstmp = lastUpdatedTmstmp;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	/**
	 * @return the carrierServiceType
	 */
	public String getCarrierServiceType() {
		return carrierServiceType;
	}
	/**
	 * @param carrierServiceType the carrierServiceType to set
	 */
	public void setCarrierServiceType(String carrierServiceType) {
		this.carrierServiceType = carrierServiceType;
	}
	

}

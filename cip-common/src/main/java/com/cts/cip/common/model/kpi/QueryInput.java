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
/**
 * 
 */
package com.cts.cip.common.model.kpi;

import java.util.List;

/**
 * @author 417765
 *
 */
public class QueryInput {

	/**
	 * 
	 */
	public QueryInput() {
		// TODO Auto-generated constructor stub
	}
	List<Integer> nodeIdList;
	List<Integer> businessUnitIdList;
	List<Integer> carrierIdList;
	List<Integer> carrierServiceIdList;
	String fromDate;
	String toDate;
	List<String> shipUnitStatusList;
	List<String> loadUnitStatusList;
	/**
	 * @return the nodeIdList
	 */
	public List<Integer> getNodeIdList() {
		return nodeIdList;
	}
	/**
	 * @param nodeIdList the nodeIdList to set
	 */
	public void setNodeIdList(List<Integer> nodeIdList) {
		this.nodeIdList = nodeIdList;
	}
	/**
	 * @return the businessUnitIdList
	 */
	public List<Integer> getBusinessUnitIdList() {
		return businessUnitIdList;
	}
	/**
	 * @param businessUnitIdList the businessUnitIdList to set
	 */
	public void setBusinessUnitIdList(List<Integer> businessUnitIdList) {
		this.businessUnitIdList = businessUnitIdList;
	}
	/**
	 * @return the carrierIdList
	 */
	public List<Integer> getCarrierIdList() {
		return carrierIdList;
	}
	/**
	 * @param carrierIdList the carrierIdList to set
	 */
	public void setCarrierIdList(List<Integer> carrierIdList) {
		this.carrierIdList = carrierIdList;
	}
	/**
	 * @return the carrierServiceIdList
	 */
	public List<Integer> getCarrierServiceIdList() {
		return carrierServiceIdList;
	}
	/**
	 * @param carrierServiceIdList the carrierServiceIdList to set
	 */
	public void setCarrierServiceIdList(List<Integer> carrierServiceIdList) {
		this.carrierServiceIdList = carrierServiceIdList;
	}
	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the shipUnitStatusList
	 */
	public List<String> getShipUnitStatusList() {
		return shipUnitStatusList;
	}
	/**
	 * @param shipUnitStatusList the shipUnitStatusList to set
	 */
	public void setShipUnitStatusList(List<String> shipUnitStatusList) {
		this.shipUnitStatusList = shipUnitStatusList;
	}
	/**
	 * @return the loadUnitStatusList
	 */
	public List<String> getLoadUnitStatusList() {
		return loadUnitStatusList;
	}
	/**
	 * @param loadUnitStatusList the loadUnitStatusList to set
	 */
	public void setLoadUnitStatusList(List<String> loadUnitStatusList) {
		this.loadUnitStatusList = loadUnitStatusList;
	}
	

}

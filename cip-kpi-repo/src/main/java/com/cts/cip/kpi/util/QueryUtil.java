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
package com.cts.cip.kpi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cts.cip.common.model.kpi.GetKpiSummaryRequest;
import com.cts.cip.common.model.kpi.ShipmentSummary;
import com.cts.cip.util.CommonUtil;


;


public class QueryUtil {

	public static QueryFilter getQueryFilter(GetKpiSummaryRequest cipKpiSummaryRequest) {
		
		QueryFilter queryFilter =  new QueryFilter();
		
		List<String> carrierList = cipKpiSummaryRequest.getCarrierList();
		if(carrierList!=null && carrierList.size()>0) {
			if (!carrierList.get(0).equalsIgnoreCase("ALL")) {
				queryFilter.setCarrierFilter(true);
				queryFilter.setCarrierSvcFilter(false);
			}
		}
		
		List<String> carrierSvcList = cipKpiSummaryRequest.getCarrierServiceList();
		if(carrierSvcList!=null && carrierSvcList.size()>0) {
			if (!carrierSvcList.get(0).equalsIgnoreCase("ALL")) {
				queryFilter.setCarrierSvcFilter(true);
			} else {
				queryFilter.setCarrierSvcFilter(false);
			}
		}
		
		List<String> nodeList = cipKpiSummaryRequest.getNodeList();
		if(nodeList!=null && nodeList.size()>0) {
			if (!nodeList.get(0).equalsIgnoreCase("ALL")) {
				queryFilter.setNodeFilter(true);
			} else {
				queryFilter.setNodeFilter(false);
			}
		}
		
		List<String> buList = cipKpiSummaryRequest.getBusinessUnitList();
		if(buList!=null && buList.size()>0) {
			if (!buList.get(0).equalsIgnoreCase("ALL")) {
				queryFilter.setNodeFilter(true);
			}
		}		
		return queryFilter;
	}
	
	public static String getFilters(GetKpiSummaryRequest request) {
		
		String filters = "";
		QueryFilter queryFilter =  getQueryFilter(request);
		
		if(queryFilter.hasBuFilter()) {
			filters =  filters + KPIQueryConstants.INCLUDE_BU_FILTER;
		}
		
		if(queryFilter.hasNodeFilter()) {
			filters =  filters + KPIQueryConstants.INCLUDE_NODE_FILTER;
		}
		
		if(queryFilter.hasCarrierFilter()) {
			filters =  filters + KPIQueryConstants.INCLUDE_CARRIER_FILTER;
		}
		
		if(queryFilter.hasCarrierSvcFilter()) {
			filters =  filters + KPIQueryConstants.INCLUDE_CARRIER_SERVICE_FILTER;
		}
		
		return filters;
		
	}

	public static Map<String, Object> getParamMap(GetKpiSummaryRequest request) {
		
		Map<String,Object> paramMap =  new HashMap<>();
		
		if (request!=null) {
			
			if(request.getFromDate()!=null && ! request.getFromDate().trim().equalsIgnoreCase("") ) {				
				
				paramMap.put("fromDate", request.getFromDate());
			}
			if(request.getToDate()!=null && ! request.getToDate().trim().equalsIgnoreCase("") ) {				
				paramMap.put("toDate", request.getToDate());				
			}			
			if(request.getNodeList()!=null && !request.getNodeList().isEmpty()) {
				if (request.getNodeList().get(0).equalsIgnoreCase("ALL")) {
					paramMap.put("nodeList","ALL");
				} else {
					paramMap.put("nodeList", request.getNodeList());
				}
			}
			if(request.getCarrierList()!=null && !request.getCarrierList().isEmpty()) {
				if (request.getCarrierList().get(0).equalsIgnoreCase("ALL")) {					
					paramMap.put("carrierServiceList", "ALL");
				} 			
			}
			if(request.getCarrierServiceList()!=null && !request.getCarrierServiceList().isEmpty()) {
				if (request.getCarrierServiceList().get(0).equalsIgnoreCase("ALL")) {
					paramMap.put("carrierServiceList", "ALL");
				} else {
					paramMap.put("carrierServiceList", request.getCarrierServiceList());
				}
			}	
			
		}
		
		for (String key : paramMap.keySet()) {
			System.out.println("Key : " + key + "- Value:" + paramMap.get(key));
		}
		return paramMap;
	}
	
	
	public static String findReportType(String fromDate,String toDate) {

		String reportType="";
		
		long reportInterval = CommonUtil.getDaysBetween(fromDate,toDate);
		
		if (reportInterval<=2) {
			
			reportType ="SUMMARY_BY_HOUR_INTERVAL";
			
		} else {
			
			reportType = "SUMMARY_BY_DAY_INTERVAL";
		}
		
		return reportType;
		
	}
	
	public static List<ShipmentSummary> buildPendingSummaryRequest(String dateTime) {
		List<ShipmentSummary> shipmentSummaryList = new ArrayList<>();
		
		return shipmentSummaryList;
	}
}

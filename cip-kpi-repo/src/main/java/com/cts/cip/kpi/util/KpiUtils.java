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
package com.cts.cip.kpi.util;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.model.kpi.ShipmentSummary;
import com.cts.cip.kpi.repository.dao.Metric;

/**
 * @author 417765
 *
 */
public class KpiUtils {
	
	static Logger logger = LoggerFactory.getLogger(KpiUtils.class);	
	static DateTimeFormatter dateTimeHourFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
	static DateTimeFormatter readableDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	/**
	 * 
	 */
	public KpiUtils() {
		// TODO Auto-generated constructor stub
	}
	
	

	public static void incrementSummaryTimeByHour(List<ShipmentSummary> summaryList, int unit) {
		
		for (ShipmentSummary shipmentSummary :  summaryList) {
			String oldTime = shipmentSummary.getLabelAlias();
			LocalDateTime date = LocalDateTime.parse(oldTime,dateTimeHourFormatter);
			date= date.plusHours(unit);
			shipmentSummary.setLabelAlias(date.format(dateTimeHourFormatter));
			shipmentSummary.setLabel(date.format(readableDateTimeFormat));			
		}
		
	}

	
	
	public static  ShipmentSummary getShipmentSummaryCopy(ShipmentSummary shipmentSummary) {
		ShipmentSummary newShipmentSummary = new ShipmentSummary();
		newShipmentSummary.setPendingLabelCount(shipmentSummary.getPendingLabelCount());
		newShipmentSummary.setPendingLoadCount(shipmentSummary.getPendingLoadCount());
		newShipmentSummary.setPendingManifestCount(shipmentSummary.getPendingManifestCount());
		newShipmentSummary.setLabel(shipmentSummary.getLabel());
		newShipmentSummary.setLabelAlias(shipmentSummary.getLabelAlias());
		return newShipmentSummary;
	}
	public static List<ShipmentSummary> getSummaryByTimeEntries(String now) {
		List<ShipmentSummary> shipmentSummaryList = new ArrayList<>();
		LocalDateTime  date = LocalDateTime.now();
		date = date.truncatedTo(ChronoUnit.HOURS);
		for(int i=0;i<12;i++) {
			ShipmentSummary shipmentSummary = new ShipmentSummary();
			shipmentSummary.setLabel(date.format(readableDateTimeFormat));
			shipmentSummary.setLabelAlias(date.format(dateTimeHourFormatter));
			shipmentSummary.setPendingLabelCount(0l);
			shipmentSummary.setPendingLoadCount(0l);
			shipmentSummary.setPendingManifestCount(0l);
			date = date.minusHours(1);
			shipmentSummaryList.add(shipmentSummary);
		}		
		return shipmentSummaryList; 
	}
	

	public static  Map<String,Object>  updateTimeToParam(Map<String,Object> paramMap) {
		Map<String,Object> updatedParamMap = paramMap;
		String fromDate = (String) paramMap.get("fromDate");
		String toDate = (String) paramMap.get("toDate");
		fromDate = fromDate + " 00:00:00";
		toDate = toDate + " 23:59:59";
		updatedParamMap.put("fromDate", fromDate);
		updatedParamMap.put("toDate",toDate);
		return updatedParamMap;
	}
	
	
	public static void synchonizeMetrics(List<Metric> metrics, List<ShipmentSummary> pendingSummaryList,String metricsType) {
		for(ShipmentSummary shipmentSummary : pendingSummaryList) {
			for(Metric metric: metrics) {
				if(metric.getIdentifier().equalsIgnoreCase(shipmentSummary.getLabelAlias())) {
					if (metricsType.equalsIgnoreCase("PENDING_LABEL")) {				
						shipmentSummary.setPendingLabelCount(metric.getCount());
					} else if (metricsType.equalsIgnoreCase("PENDING_LOAD")) {						
						shipmentSummary.setPendingLoadCount(metric.getCount());
					} else if (metricsType.equalsIgnoreCase("PENDING_MANIFEST")) {
						shipmentSummary.setPendingManifestCount(metric.getCount());
					}
				}
			}
		}	
		
	}
	
}

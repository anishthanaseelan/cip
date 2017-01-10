/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (â€œCWWâ€�)
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
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (â€œCWWâ€�)
 *******************************************************************************/
package com.cts.cip.util;

import java.awt.Event;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cts.cip.common.model.ReportType;
import com.cts.cip.common.model.kpi.ShipmentEventType;
import com.cts.cip.common.model.kpi.ShipmentSummary;
import com.cts.cip.common.model.kpi.UpdateSummaryRequest;




public class CommonUtil {
	
	static DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
	static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	
	public static int toIdFromDateTime(LocalDateTime date) {
		String id = date.format(dateTimeformatter);
		if (id!=null && !id.equalsIgnoreCase("")) {
			return Integer.parseInt(id);
		} else {
			return 0;
		}
	}
	
	public static ReportType getReportType(String fromDate,String toDate) {
		
		long days = getDaysBetween(fromDate, toDate);
		if (days>2) {
			return ReportType.BY_DAY;
		} else {
			return ReportType.BY_HOUR;
		}
	}
	
	public static long getDaysBetween(String fromDate, String toDate) {
		
		final LocalDate firstDate = LocalDate.parse(fromDate, dateFormatter);
	    final LocalDate secondDate = LocalDate.parse(toDate, dateFormatter);	        
		return ChronoUnit.DAYS.between(firstDate, secondDate);
	}
	
	
	

	public static UpdateSummaryRequest createUpdateSummaryRequest(ShipmentEventType eventType,int count) {
		LocalDateTime now =  LocalDateTime.now();
		int id = toIdFromDateTime(now);
		UpdateSummaryRequest request =  new UpdateSummaryRequest();
		request.setId(id);
		request.setEventType(eventType);
		request.setCarrierService("");
		request.setCount(count);		
		return request;
		
	}
	
	
	
	public static void main (String[] args) {
		
	}
	

	
	
}

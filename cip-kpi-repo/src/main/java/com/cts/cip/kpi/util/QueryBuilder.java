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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.model.ReportType;
import com.cts.cip.common.model.kpi.GetKpiSummaryRequest;




public class QueryBuilder {	
	
	public static Logger logger = LoggerFactory.getLogger(QueryBuilder.class);
	
	public static String createSummaryQuery(GetKpiSummaryRequest request, ReportType reportType) {
		
		String selectQueryPrefix = getSelectPrefixQueryString(reportType);		
		
		String query = selectQueryPrefix +  KPIQueryConstants.GET_OVERALL_SUMMARY_QUERY;
		
		//String filters = QueryUtil.getFilters(request);	
		
		//query =  query + filters;	
		
		String conditionalQuery = getGroupingQueryString(reportType);
		
		query = query + conditionalQuery; 
		
		logger.debug("Generated Summary Query: " + query);
		
		return query;
	}

	
	
	public static String createPendingSummaryQueryByHour(GetKpiSummaryRequest request, ReportType reportType) {
		
		String query  = KPIQueryConstants.GET_OVEALL_PENDING_SUMMARY_BY_HOUR_QUERY;
		
		String filters = QueryUtil.getFilters(request);	
		
		query =  query + filters;	
		
		String conditionalQuery = getGroupingQueryString(reportType);
		
		query = query + conditionalQuery; 
		
		logger.debug("Generated Summary Query: " + query);
		
		return query;	
	}
	
	public static String getGroupingQueryString (ReportType reportType) {
		
		String conditionalQuery ="";
		
		switch (reportType) {
		
			case BY_DAY: {
				conditionalQuery = "group by create_date ";
				break;
			}
			case BY_HOUR: {
				conditionalQuery = "group by id ";
				break;
			}
			case BY_NODE: {
				conditionalQuery = "group by node ";
				break;
			}
			case BY_CARRIER: {
				conditionalQuery = "group by carrier ";
				break;
			}
			case BY_CARRIER_SERVICE: {
				conditionalQuery = "group by carrier_svc";
				break;
			}
			case OVERALL: {
				conditionalQuery = "group by label";
				break;
			}
			default : {
				conditionalQuery = "";
				break;
			}
		}
		return conditionalQuery;
	}

	public static String getSelectPrefixQueryString (ReportType reportType) {
		
		String selectPrefixQuery ="";
		
		switch (reportType) {
		
			case BY_DAY: {
				selectPrefixQuery = "SELECT create_date as label, ";
				break;
			}
			case BY_HOUR: {
				selectPrefixQuery = "SELECT id as label, ";
				break;
			}
			case BY_NODE: {
				selectPrefixQuery = "SELECT node as label, ";
				break;
			}
			case BY_CARRIER: {
				selectPrefixQuery = "SELECT carrier as label, ";
				break;
			}
			case BY_CARRIER_SERVICE: {
				selectPrefixQuery = "SELECT carrier_svc as label, ";
				break;
			}
			case OVERALL: {
				selectPrefixQuery = "";
				break;
			}
			default : {
				selectPrefixQuery = "";
				break;
			}
		}
		return selectPrefixQuery;
	}

	

}

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
package com.cts.cip.kpi.repository.dao;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.model.ReportType;
import com.cts.cip.common.model.kpi.GetKpiSummaryRequest;
import com.cts.cip.common.model.kpi.ShipmentSummary;
import com.cts.cip.kpi.repository.mapper.SummaryListRowMapper;
import com.cts.cip.kpi.repository.mapper.SummaryRowMapper;
import com.cts.cip.kpi.util.KPIQueryConstants;
import com.cts.cip.kpi.util.KpiUtils;
import com.cts.cip.kpi.util.QueryBuilder;
import com.cts.cip.kpi.util.QueryUtil;



@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class SummaryDaoImpl implements SummaryDao {
	
	Logger logger = LoggerFactory.getLogger(SummaryDaoImpl.class);
	
	
	
	@PersistenceContext
	private EntityManager em;	
	

	@Autowired
	@Qualifier("namedParameterJdbcTemplate")
	private NamedParameterJdbcTemplate dataTemplate;
	
	@Autowired
	@Qualifier("jdbcTemplate")
	JdbcTemplate template;
	

	@Value( "${jpa.database}" )
	String databaseName="";
	
	static DateTimeFormatter dateTimeHourFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
	static DateTimeFormatter readableDateTimeFormat = DateTimeFormatter.ofPattern("MMM-dd HH:mm");
	
	
	@Override
	public ShipmentSummary getSummary(GetKpiSummaryRequest request,ReportType reportType) {		
		ShipmentSummary shipmentSummary = getOverallSummary(request, reportType);		
		return shipmentSummary;
		
	}
	
	@Override
	public ShipmentSummary getOverallSummary(GetKpiSummaryRequest request,ReportType reportType) {		
		 
		String getOverallSummaryQuery = QueryBuilder.createSummaryQuery(request, reportType);		
		Map<String,Object> paramMap = QueryUtil.getParamMap(request);	
		
		
		if(request.getNodeList()!=null && !request.getNodeList().isEmpty()) {
			if (request.getNodeList().get(0).equalsIgnoreCase("ALL")) {
				List<String> nodeList = (List<String>)template.queryForList(KPIQueryConstants.GET_ALL_NODE_ID_QUERY, String.class);
				paramMap.put("nodeList",nodeList);
			}
		}
		if(request.getCarrierList()!=null && !request.getCarrierList().isEmpty()) {
			
		
			if (request.getCarrierList().get(0).equalsIgnoreCase("ALL")) {
				List<String> carrierServiceList = (List<String>)template.queryForList(KPIQueryConstants.GET_ALL_CARRIER_SERVICE_CODES_QUERY, String.class);
				paramMap.put("carrierServiceList",carrierServiceList);
				
			} else if(request.getCarrierServiceList()!=null && !request.getCarrierServiceList().isEmpty()) {
				
			
				if (request.getCarrierServiceList().get(0).equalsIgnoreCase("ALL")) {				
					
					Map<String,Object> codeParamMap = new HashMap<String,Object>();
					codeParamMap.put("carrierCode", request.getCarrierList().get(0));
					List<String> carrierServiceList = dataTemplate.queryForList(KPIQueryConstants.GET_ALL_CARRIER_SERVICE_CODES_BY_CARRIER_QUERY,codeParamMap, String.class);					
					paramMap.put("carrierServiceList",carrierServiceList);			
				} 
			}	
		
		}
		List<Metric> metrics = new ArrayList<Metric>();
		paramMap = KpiUtils.updateTimeToParam(paramMap);
		logger.debug("getOverallSummaryQuery : " + getOverallSummaryQuery);
		metrics = dataTemplate.query(getOverallSummaryQuery, paramMap, new SummaryRowMapper());		
		// Get the reprint request from transaction log		
		long reprintLabelCount =  dataTemplate.queryForObject(KPIQueryConstants.GET_OVERALL_REPRINT_QUERY, paramMap, Long.class);		
		ShipmentSummary shipmentSummary =  populateShipmeneShummary(metrics);
		shipmentSummary.setReprintCount(reprintLabelCount);
		return shipmentSummary;
		
	}
	
	@Override
	public ShipmentSummary getPendingSummary(String fromDate,String toDate, ReportType reportType) {
		
		ShipmentSummary pendingShipmentSummary = new ShipmentSummary();
		long pendingLabelCount=0l;
		long pendingLoadCount=0l;
		long pendingManifestCount=0l;
		
		String pendingLabelCountQuery = "";
		String pendingLoadCountQuery = "";
		String pendingManifestCountQuery  = "";
		
		if (reportType.equals(ReportType.PENDING_OVERALL_BEFORE_12_HOUR)) {
			if(databaseName.equalsIgnoreCase("INFORMIX")) {
				pendingLabelCountQuery = KPIQueryConstants.INFORMIX_GET_PENDING_LABEL_BEFORE_12_HOUR_QUERY;
				pendingLoadCountQuery = KPIQueryConstants.INFORMIX_GET_PENDING_LOAD_BEFORE_12_HOUR_QUERY;
				pendingManifestCountQuery  = KPIQueryConstants.INFORMIX_GET_PENDING_MANIFEST_BEFORE_12_HOUR_QUERY;
			} else {
				pendingLabelCountQuery = KPIQueryConstants.GET_PENDING_LABEL_BEFORE_12_HOUR_QUERY;
				pendingLoadCountQuery = KPIQueryConstants.GET_PENDING_LOAD_BEFORE_12_HOUR_QUERY;
				pendingManifestCountQuery  = KPIQueryConstants.GET_PENDING_MANIFEST_BEFORE_12_HOUR_QUERY;
			}			
		} else {
			pendingLabelCountQuery = KPIQueryConstants.GET_PENDING_LABEL_QUERY;
			pendingLoadCountQuery = KPIQueryConstants.GET_PENDING_LOAD_QUERY;
			pendingManifestCountQuery  = KPIQueryConstants.GET_PENDING_MANIFEST_QUERY;
		}
		
		pendingLabelCount = template.queryForObject(pendingLabelCountQuery, Long.class);		
		pendingLoadCount = template.queryForObject(pendingLoadCountQuery, Long.class);
		pendingManifestCount = template.queryForObject(pendingManifestCountQuery, Long.class);
		
		
		pendingShipmentSummary.setPendingLoadCount(pendingLoadCount);
		pendingShipmentSummary.setPendingManifestCount(pendingManifestCount);
		pendingShipmentSummary.setPendingLabelCount(pendingLabelCount);		
		return pendingShipmentSummary;
	}
	
	

	@Override
	public List<ShipmentSummary> getPendingSummaryList(GetKpiSummaryRequest request, ReportType reportType) {
		
		String pendingLabelCountByHourQuery = "";
		String pendingLoadCountByHourQuery = "";
		String pendingManifestCountByHourQuery  = "";
		
		
		if(databaseName.equalsIgnoreCase("INFORMIX")) {			
			pendingLabelCountByHourQuery = KPIQueryConstants.INFORMIX_GET_PENDING_LABEL_BY_HOUR_QUERY;
			pendingLoadCountByHourQuery = KPIQueryConstants.INFORMIX_GET_PENDING_LOAD_BY_HOUR_QUERY;
			pendingManifestCountByHourQuery  = KPIQueryConstants.INFORMIX_GET_PENDING_MANIFEST_BY_HOUR_QUERY;
		} else {
			pendingLabelCountByHourQuery = KPIQueryConstants.GET_PENDING_LABEL_BY_HOUR_QUERY;
			pendingLoadCountByHourQuery = KPIQueryConstants.GET_PENDING_LOAD_BY_HOUR_QUERY;
			pendingManifestCountByHourQuery  = KPIQueryConstants.GET_PENDING_MANIFEST_BY_HOUR_QUERY;
		}	
		
		
		
		ShipmentSummary overallPendingSummary = getPendingSummary(null, null, ReportType.PENDING_OVERALL_BEFORE_12_HOUR);		
		List<ShipmentSummary> pendingSummaryList = KpiUtils.getSummaryByTimeEntries(null);
		
		List<ShipmentSummary> newSummaryList =  new ArrayList<>();
		for(ShipmentSummary shipmentSummary : pendingSummaryList) {
			newSummaryList.add(KpiUtils.getShipmentSummaryCopy(shipmentSummary));
			
		}
		
		List<Metric> pendingLabelMetrics = new ArrayList<Metric>();
		pendingLabelMetrics = dataTemplate.query(pendingLabelCountByHourQuery,new SummaryRowMapper());		
		updateMetrics(overallPendingSummary.getPendingLabelCount(), pendingLabelMetrics,pendingSummaryList,newSummaryList,"PENDING_LABEL");
		
		List<Metric> pendingLoadMetrics = new ArrayList<Metric>();
		pendingLoadMetrics = dataTemplate.query(pendingLoadCountByHourQuery,new SummaryRowMapper());		
		updateMetrics(overallPendingSummary.getPendingLoadCount(), pendingLoadMetrics,pendingSummaryList,newSummaryList,"PENDING_LOAD");
		
		List<Metric> pendingManifestMetrics = new ArrayList<Metric>();
		pendingManifestMetrics = dataTemplate.query(pendingManifestCountByHourQuery,new SummaryRowMapper());
		updateMetrics(overallPendingSummary.getPendingManifestCount(), pendingManifestMetrics,pendingSummaryList,newSummaryList,"PENDING_MANIFEST");
		
		//KpiUtils.incrementSummaryTimeByHour(newSummaryList,1);
		
		
		return newSummaryList;
	}
	
	
	
	
	private List<ShipmentSummary> updateMetrics(long totalCount, List<Metric> metrics, List<ShipmentSummary> summaryList,List<ShipmentSummary> newSummaryList,String metricsType) {
		
		
		KpiUtils.synchonizeMetrics(metrics, summaryList, metricsType);
		
		
		long remainingCount = 0l;
		int summaryIndex=0;		
		for (int i = 11; i >= 0; i--) {			
			remainingCount = 0;			
			for (int j = i; j <=11; j++) {				
				if (metricsType.equalsIgnoreCase("PENDING_LABEL")) {	
					remainingCount = remainingCount + summaryList.get(j).getPendingLabelCount();					
				} else if (metricsType.equalsIgnoreCase("PENDING_LOAD")) {					
					remainingCount = remainingCount + summaryList.get(j).getPendingLoadCount();					
				} else if (metricsType.equalsIgnoreCase("PENDING_MANIFEST")) {
					remainingCount = remainingCount + summaryList.get(j).getPendingManifestCount();
				}					
			}
			
			if (metricsType.equalsIgnoreCase("PENDING_LABEL")) {				
				newSummaryList.get(i).setPendingLabelCount(remainingCount);
			} else if (metricsType.equalsIgnoreCase("PENDING_LOAD")) {				
				newSummaryList.get(i).setPendingLoadCount(remainingCount);
			} else if (metricsType.equalsIgnoreCase("PENDING_MANIFEST")) {
				newSummaryList.get(i).setPendingManifestCount(remainingCount);
			}			
			newSummaryList.get(summaryIndex).setLabelIndex(i+1);
			summaryIndex = summaryIndex+1;
			
		}
		
		updateTotal(totalCount,metrics, newSummaryList, metricsType);
		
		return newSummaryList;

	}
	
	private void updateTotal(long totalCount,List<Metric> metrics, List<ShipmentSummary> newSummaryList, String metricsType) {
		for(int i = 0;i<12;i++) {
			if (metricsType.equalsIgnoreCase("PENDING_LABEL")) {				
				newSummaryList.get(i).setPendingLabelCount(totalCount + newSummaryList.get(i).getPendingLabelCount());
			} else if (metricsType.equalsIgnoreCase("PENDING_LOAD")) {				
				newSummaryList.get(i).setPendingLoadCount(totalCount + newSummaryList.get(i).getPendingLoadCount());
			} else if (metricsType.equalsIgnoreCase("PENDING_MANIFEST")) {
				newSummaryList.get(i).setPendingManifestCount(totalCount + newSummaryList.get(i).getPendingManifestCount());
			}
			
		}		
		
		
	}

	

	@Override
	public List<ShipmentSummary> getSummaryList(GetKpiSummaryRequest request,ReportType reportType) {
		
		List<ShipmentSummary> completedShipmentSummaryList = getCompletedSummaryList(request, reportType);
		List<ShipmentSummary> pendingShipmentSummaryList =getPendingSummaryList(request, reportType);		
		Iterator<ShipmentSummary> completedListIterator = 	completedShipmentSummaryList.iterator();
		Iterator<ShipmentSummary> pendingListIterator = 	pendingShipmentSummaryList.iterator();
		while (completedListIterator.hasNext() && pendingListIterator.hasNext()) {
			ShipmentSummary shipmentSummary = completedListIterator.next();
			ShipmentSummary pendingShipmentSummary = pendingListIterator.next();
			shipmentSummary.setPendingLoadCount(pendingShipmentSummary.getPendingLoadCount());
			shipmentSummary.setPendingManifestCount(pendingShipmentSummary.getPendingManifestCount());
		}
		return completedShipmentSummaryList;
		
	}
	
	
	
	@Override
	public List<ShipmentSummary> getCompletedSummaryList(GetKpiSummaryRequest request, ReportType reportType) {
		
		List<ShipmentSummary> shipmentSummaryList = new ArrayList<>();
		
		String getOverallSummaryQuery = QueryBuilder.createSummaryQuery(request, reportType);	
		Map<String,Object> paramMap = QueryUtil.getParamMap(request);		
		shipmentSummaryList = dataTemplate.query(getOverallSummaryQuery, paramMap, new SummaryListRowMapper());
		return shipmentSummaryList;
		
	}
	

	private ShipmentSummary populateShipmeneShummary(List<Metric> metrics ) {
		ShipmentSummary  shipmentSummary = new ShipmentSummary();
		long failedCount =0l;
		for (Metric metric : metrics) {
			switch(metric.getIdentifier()) {
				case "LABEL_CREATED": {
					shipmentSummary.setLabelCount((long)metric.getCount());				
					break;
				}
				case "CONFIRMED": {
					shipmentSummary.setManifestCount((long)metric.getCount());				
					break;
				}
				case "EXCEPTION": {
					failedCount = failedCount + metric.getCount();
					break;	
				}
				case "FAILED" : {
					failedCount = failedCount + metric.getCount();
					break;
				}
				case "CANCELLED" : {
					shipmentSummary.setCancelCount((long)metric.getCount());
				}
			}
		}
		shipmentSummary.setErrorCount(failedCount);
		return shipmentSummary;
	}	
	
}

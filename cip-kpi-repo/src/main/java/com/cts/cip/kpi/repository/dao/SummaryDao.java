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
package com.cts.cip.kpi.repository.dao;

import java.util.List;

import com.cts.cip.common.model.ReportType;
import com.cts.cip.common.model.kpi.GetKpiSummaryRequest;
import com.cts.cip.common.model.kpi.ShipmentSummary;




public interface SummaryDao {
	
	
	public ShipmentSummary getSummary(GetKpiSummaryRequest cipKpiSummaryRequest,ReportType reportType);
	
	public ShipmentSummary getPendingSummary(String fromDate,String toDate,ReportType reportType);
	
	public List<ShipmentSummary> getPendingSummaryList(GetKpiSummaryRequest request, ReportType reportType);
	
	public List<ShipmentSummary> getSummaryList(GetKpiSummaryRequest request, ReportType reportType);
	
	public List<ShipmentSummary> getCompletedSummaryList(GetKpiSummaryRequest request, ReportType reportType);	
	
	public ShipmentSummary getOverallSummary(GetKpiSummaryRequest request, ReportType reportType);
	
}

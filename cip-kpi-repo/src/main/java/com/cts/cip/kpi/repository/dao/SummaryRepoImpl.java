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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.cip.common.model.ReportType;
import com.cts.cip.common.model.kpi.SummaryGroup;
import com.cts.cip.common.model.kpi.GetKpiSummaryRequest;
import com.cts.cip.common.model.kpi.ShipmentSummary;
import com.cts.cip.kpi.repository.entities.ShipmentSummaryEntity;
import com.cts.cip.kpi.repository.entities.SummaryGroupEntity;



@Service("summaryRepo")
public class SummaryRepoImpl  implements SummaryRepo {
	
	@Autowired
	SummaryDao summaryDao;

	

	@Override
	public ShipmentSummary getSummary(GetKpiSummaryRequest cipKpiSummaryRequest, ReportType reportType) {		
		return summaryDao.getSummary(cipKpiSummaryRequest, reportType);
	}

	@Override
	public ShipmentSummary getPendingSummary(String fromDate,String toDate, ReportType reportType) {		
		return summaryDao.getPendingSummary(fromDate,toDate,reportType);
	}

	@Override
	public ShipmentSummary getCompletedSummary(GetKpiSummaryRequest request, ReportType reportType) {
		return summaryDao.getOverallSummary(request,reportType);		
	}

	@Override
	public List<ShipmentSummary> getSummaryList(GetKpiSummaryRequest request, ReportType reportType) {		
		return summaryDao.getSummaryList(request, reportType);
	}


	@Override
	public List<ShipmentSummary> getPendingSummaryList(GetKpiSummaryRequest request, ReportType reportType) {		
		return summaryDao.getPendingSummaryList(request, reportType);
	}	

}

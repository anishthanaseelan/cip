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
package com.cts.cip.kpi.bo.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.exceptions.CriteriaValidationException;
import com.cts.cip.common.model.ReportType;
import com.cts.cip.common.model.kpi.CipKpiSummaryResponse;
import com.cts.cip.common.model.kpi.GetKpiSummaryRequest;
import com.cts.cip.common.model.kpi.Summary;
import com.cts.cip.kpi.repository.dao.SummaryRepo;
import com.cts.cip.util.CommonUtil;

public class SummaryServiceImpl implements SummaryService {

	public static Logger logger = LoggerFactory.getLogger(SummaryServiceImpl.class);

	SummaryRepo summaryRepo;


	@Override
	public CipKpiSummaryResponse getSummary(GetKpiSummaryRequest request) throws CriteriaValidationException {

		validateRequest(request);
		Summary summary = new Summary();
		if (request.isOverallSummary()) {
			summary.setOverallSummary(summaryRepo.getSummary(request, ReportType.OVERALL));
		}
		if (request.isPendingSummary()) {
			summary.setPendingShipmentSummary(summaryRepo.getPendingSummary(null, null, ReportType.PENDING_OVERALL));
		}
		if (request.isPendingSummaryByTime()) {
			summary.setPendingSummaryByTime(summaryRepo.getPendingSummaryList(request, ReportType.PENDING_BY_HOUR));
		}

		CipKpiSummaryResponse response = new CipKpiSummaryResponse();
		response.setSummary(summary);
		return response;
	}

	private void validateRequest(GetKpiSummaryRequest request) throws CriteriaValidationException {

		if (request == null) {
			throw new CriteriaValidationException("The request criteria  cannot be empty");
		}

		if (request.getFromDate() == null || request.getFromDate().trim().equalsIgnoreCase("")) {
			throw new CriteriaValidationException("From date value cannot be empty");
		}

		if (request.getToDate() == null || request.getToDate().trim().equalsIgnoreCase("")) {
			throw new CriteriaValidationException("To date value cannot be empty");
		}

		long days = CommonUtil.getDaysBetween(request.getFromDate(), request.getToDate());
		if (days > 100) {
			throw new CriteriaValidationException(
					"The service does not currenlty support the report poeriod interval more than 100 days");
		}
		if (!request.isPendingSummaryByTime() && !request.isPendingSummary() && !request.isOverallSummary()
				&& !request.isSummaryByCarrier() && !request.isSummaryByCarrierService() && !request.isSummaryByNode()
				&& !request.isSummaryByTime()) {
			logger.debug("Validate Request : all flags are false");
			throw new CriteriaValidationException(
					"All the reporting flags are set to false.Atleast on of the report flags should be true");

		}
	}

	public SummaryRepo getSummaryRepo() {
		return summaryRepo;
	}

	public void setSummaryRepo(SummaryRepo summaryRepo) {
		this.summaryRepo = summaryRepo;
	}	
}

package com.cts.cip.kpi.bo.core;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cts.cip.common.exceptions.CriteriaValidationException;
import com.cts.cip.common.model.kpi.GetKpiSummaryRequest;
import com.cts.cip.common.model.ReportType;
import com.cts.cip.common.model.kpi.CipKpiSummaryResponse;
import com.cts.cip.kpi.repository.dao.SummaryRepo;
import com.cts.cip.kpi.util.TestDataProvider;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class SummaryServiceTest {
	
	Logger logger = LoggerFactory.getLogger(SummaryServiceTest.class);

	@Mock
	SummaryRepo summaryRepo;

	@InjectMocks
	private SummaryService summaryService = new SummaryServiceImpl();
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testGetSummary_1 () throws CriteriaValidationException  { 
		thrown.expect(CriteriaValidationException.class);
		thrown.expectMessage("The request criteria  cannot be empty");
		GetKpiSummaryRequest request = null;
		summaryService.getSummary(request);		
	}
	
	@Test
	public void testGetSummary_2 () throws CriteriaValidationException {
		thrown.expect(CriteriaValidationException.class);
		thrown.expectMessage("From date value cannot be empty");
		GetKpiSummaryRequest request = new GetKpiSummaryRequest();
		summaryService.getSummary(request);
	}
	
	@Test
	public void testGetSummary_3 () throws CriteriaValidationException {
		thrown.expect(CriteriaValidationException.class);
		thrown.expectMessage("To date value cannot be empty");
		GetKpiSummaryRequest request = new GetKpiSummaryRequest();
		request.setFromDate("12/12/2016");
		summaryService.getSummary(request);
	}
	
	@Test
	public void testGetSummary_4 () throws CriteriaValidationException {
		thrown.expect(CriteriaValidationException.class);
		thrown.expectMessage("The service does not currenlty support the report poeriod interval more than 100 days");
		GetKpiSummaryRequest request = new GetKpiSummaryRequest();
		request.setFromDate("2016-03-28");
		request.setToDate("2016-12-28");
		summaryService.getSummary(request);
	}
	
	@Test
	public void testGetSummary_5 () throws CriteriaValidationException {
		thrown.expect(CriteriaValidationException.class);
		thrown.expectMessage("All the reporting flags are set to false.Atleast on of the report flags should be true");
		GetKpiSummaryRequest request = new GetKpiSummaryRequest();
		request.setFromDate("2016-11-10");
		request.setToDate("2016-11-11");
		summaryService.getSummary(request);
	}
	
	@Test
	public void testGetSummary_6 () throws CriteriaValidationException {
		GetKpiSummaryRequest request = new GetKpiSummaryRequest();
		request.setFromDate("2016-11-10");
		request.setToDate("2016-11-11");
		request.setOverallSummary(true);
		CipKpiSummaryResponse expectedResponse = TestDataProvider.getSimpleResponse();
		Mockito.when(summaryRepo.getSummary(request,ReportType.OVERALL)).thenReturn(expectedResponse.getSummary().getOverallSummary());
		CipKpiSummaryResponse actualResponse = summaryService.getSummary(request);		
		assertThat(actualResponse.getSummary().getOverallSummary().getLabelCount()).isEqualTo(expectedResponse.getSummary().getOverallSummary().getLabelCount());
		assertThat(actualResponse.getSummary().getOverallSummary().getLoadCount()).isEqualTo(expectedResponse.getSummary().getOverallSummary().getLoadCount());
	
	}
	
	@Test
	public void testGetSummary_7 () throws CriteriaValidationException {
		GetKpiSummaryRequest request = new GetKpiSummaryRequest();
		request.setFromDate("2016-11-10");
		request.setToDate("2016-11-11");
		request.setPendingSummary(true);		
		CipKpiSummaryResponse expectedResponse = TestDataProvider.getSimpleResponse();
		Mockito.when(summaryRepo.getPendingSummary(null, null,ReportType.PENDING_OVERALL)).thenReturn(expectedResponse.getSummary().getPendingShipmentSummary());
		CipKpiSummaryResponse actualResponse = summaryService.getSummary(request);		
		assertThat(actualResponse.getSummary().getPendingShipmentSummary().getLabelCount()).isEqualTo(expectedResponse.getSummary().getPendingShipmentSummary().getLabelCount());
		assertThat(actualResponse.getSummary().getPendingShipmentSummary().getLoadCount()).isEqualTo(expectedResponse.getSummary().getPendingShipmentSummary().getLoadCount());	
	}
	
	@Test
	public void testGetSummary_8 () throws CriteriaValidationException {
		GetKpiSummaryRequest request = new GetKpiSummaryRequest();
		request.setFromDate("2016-11-10");
		request.setToDate("2016-11-11");
		request.setPendingSummaryByTime(true);
		CipKpiSummaryResponse expectedResponse = TestDataProvider.getSimpleResponse();
		Mockito.when(summaryRepo.getPendingSummaryList(request,ReportType.PENDING_BY_HOUR)).thenReturn(expectedResponse.getSummary().getPendingSummaryByTime());
		CipKpiSummaryResponse actualResponse = summaryService.getSummary(request);		
		assertThat(actualResponse.getSummary().getPendingSummaryByTime().size()).isEqualTo(expectedResponse.getSummary().getPendingSummaryByTime().size());		
	
	}
}

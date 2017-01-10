package com.cts.cip.kpi.util;

import java.io.File;
import java.io.IOException;

import com.cts.cip.common.model.kpi.CipKpiSummaryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDataProvider {
	
	private static CipKpiSummaryResponse simpleResponse;
	
	
	static {		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String simpleResponseDataFile = "C:/Users/234174/git/CarrierIntegrationPlatform_cip/cip-kpi/src/test/resources/data/CommonSummaryData.json";			
			simpleResponse = mapper.readValue(new File(simpleResponseDataFile),CipKpiSummaryResponse.class);
			
		} catch (IOException e) {			
			e.printStackTrace();
		} 
	}


	public static CipKpiSummaryResponse getSimpleResponse() {
		return simpleResponse;
	}


	public static void setSimpleResponse(CipKpiSummaryResponse simpleResponse) {
		TestDataProvider.simpleResponse = simpleResponse;
	}
	
}

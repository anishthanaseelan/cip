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
package com.cts.cip.master.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
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
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cts.cip.common.dto.CarrierDetail;
import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.RuleInfo;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.repository.dao.MasterDataService;
import com.cts.cip.master.util.TestDataProvider;



@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class MasterCarrierSvcTest {
	
	Logger logger = LoggerFactory.getLogger(MasterCarrierSvcTest.class);

	@Mock
	MasterDataService masterDataSvc;

	@InjectMocks
	private MasterCarrierService masterCarrierSvc = new MasterCarrierServiceImpl();
	
	 @Rule
     public ExpectedException thrown = ExpectedException.none();


	@Before
	public void setUp() {

	}

	/*
	 * This method is used to test the sample getAllCarrier flow with out any exception. 
	 */
	@Test	
	public void testGetAllCarrier() {

		Mockito.when(masterDataSvc.findAllCarriers()).thenReturn(TestDataProvider.getCarrierList("Simple"));		
		List<CarrierInfo> carrierDtoList = masterCarrierSvc.findAllCarriers();
		assertThat(carrierDtoList).isEqualTo(TestDataProvider.getCarrierInfoList("Simple"));
	}
	
	/*
	 * This method is used to test the getAfindCarriersByPage flow with out any exception. 
	 */
	@Test
	public void testFindCarriersByPage() {
		Mockito.when(masterDataSvc.findCarriersByPage(0, 0, null, null)).thenReturn(TestDataProvider.getCarrierList("Simple"));
		List<CarrierInfo> carrierDtoList = masterCarrierSvc.findCarriersByPage(0, 0, null, null);
		assertThat(carrierDtoList).isEqualTo(TestDataProvider.getCarrierInfoList("Simple"));
	}
	
	
	/*
	 * This method is used to test the findCarriersCount flow with out any exception. 
	 */
	@Test
	public void testFindCarriersCount() {
		Mockito.when(masterDataSvc.findCarriersCount()).thenReturn(5);
		int carriersCount = masterCarrierSvc.findCarriersCount();
		assertThat(carriersCount).isEqualTo(5);
	}
	
	/*
	 * This method is used to test the findCarriersById flow with out any exception. 
	 */	
	@Test
	public void testFindCarrierById() {		
		Mockito.when(masterDataSvc.findCarrierById(1)).thenReturn(TestDataProvider.getCarrier("Simple"));
		CarrierDetail carrierDetail = masterCarrierSvc.findCarrierById(1);	
		if(carrierDetail.getCarrierServiceInfoList().isEmpty()) {
			carrierDetail.setCarrierServiceInfoList(null);
		}
		assertThat(carrierDetail).isEqualTo(TestDataProvider.getCarrierDetail("Simple"));	
	}
	
	/*
	 * This method is used to test the UpdateCarrier positive flow with out any exception. 
	 */
	@Test
	public void testUpdateCarrier_1() throws SystemFailure, BusinessException {
		Mockito.when(masterDataSvc.findCarrierById(1)).thenReturn(TestDataProvider.getCarrier("Simple"));
		StatusResponse statusResponse =  masterCarrierSvc.updateCarrier(TestDataProvider.getCarrierDetail("Simple"));
		assertThat(statusResponse).isEqualTo(TestDataProvider.getStatusReponse("Simple"));
	}
	
	/*
	 * This method is used to test the UpdateCarrier negative flow with a id which does not have valid data 
	 */
	@Test
	public void testUpdateCarrier_2() throws SystemFailure, BusinessException {
		Mockito.when(masterDataSvc.findCarrierById(1)).thenReturn(null);
		StatusResponse statusResponse =  masterCarrierSvc.updateCarrier(TestDataProvider.getCarrierDetail("Simple"));
		assertThat(statusResponse.getCode()).isEqualTo(TestDataProvider.getStatusReponse("EmptyResponse").getCode());
	}
	
	
	/*
	 * This method is used to test the UpdateCarrier negative flow which throws the SystemFailure exception 
	 */
	@Test
	public void testUpdateCarrier_3() throws SystemFailure, BusinessException {		
		thrown.expect(SystemFailure.class);
		thrown.expectMessage("Test Message");
		Mockito.when(masterDataSvc.findCarrierById(1)).thenThrow(new DataRetrievalFailureException("Test Message"));		
		masterCarrierSvc.updateCarrier(TestDataProvider.getCarrierDetail("Simple"));
		
	}
	
	/*
	 * This method is used to test the findRulesByService positive flow 
	 */	
	@Test
	public void testFindRulesByService() {
		Mockito.when(masterDataSvc.findRulesByService("2")).thenReturn(TestDataProvider.getRuleList("Simple"));
		List<RuleInfo> ruleInfoList = masterCarrierSvc.findRulesByService("2");
		assertThat(ruleInfoList.get(0).getName()).isEqualTo(TestDataProvider.getRuleInfoList("Simple").get(0).getName());
	}
	
}

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
package com.cts.cip.master.Mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cts.cip.common.dto.CarrierDetail;
import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.LocationInfo;
import com.cts.cip.common.dto.RuleInfo;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.master.mapper.CarrierModelMapper;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.Location;
import com.cts.cip.master.util.TestDataProvider;
import static org.assertj.core.api.Assertions.assertThat;



@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class CarrierModelMapperTest {
	
	CarrierModelMapper carrierModelMapper = new CarrierModelMapper();
	
	Logger logger = LoggerFactory.getLogger(CarrierModelMapperTest.class);
	
	@Test
	public void testToCarrierInfo() {
		CarrierInfo expectedCarrierInfo = TestDataProvider.getCarrierInfo("Simple");
		CarrierInfo actualCarrierInfo = carrierModelMapper.toCarrierInfo(TestDataProvider.getCarrier("Simple"));
		assertThat(actualCarrierInfo).isEqualTo(expectedCarrierInfo);
		
	}
	
	@Test
	public void testToCarrierEntity_1() {
		Carrier expectedCarrier = TestDataProvider.getCarrier("Simple");
		Carrier actualCarrier = carrierModelMapper.toCarrierEntity(TestDataProvider.getCarrierInfo("Simple"));
		assertThat(actualCarrier.getName()).isEqualTo(expectedCarrier.getName());
		assertThat(actualCarrier.getIconUrl()).isEqualTo(expectedCarrier.getIconUrl());
	}
	
	
	@Test
	public void testToCarrierEntity_2() throws BusinessException {
		CarrierDetail carrierDetail = TestDataProvider.getCarrierDetail("Simple");
		Carrier expectedCarrier = TestDataProvider.getCarrier("Simple");
		Carrier actualCarrier = carrierModelMapper.toCarrierEntity(carrierDetail,TestDataProvider.getCarrier("Simple"));
		assertThat(actualCarrier.getName()).isEqualTo(expectedCarrier.getName());
		assertThat(actualCarrier.getIconUrl()).isEqualTo(expectedCarrier.getIconUrl());
		
	}
	
	@Test
	public void testToCarrierDetail() {
		CarrierDetail actualCarrierDetail = carrierModelMapper.toCarrierDetail(TestDataProvider.getCarrier("Simple"));
		CarrierDetail expectedCarrierDetail = TestDataProvider.getCarrierDetail("Simple");
		assertThat(actualCarrierDetail.getName()).isEqualTo(expectedCarrierDetail.getName());
		assertThat(actualCarrierDetail.getIconUrl()).isEqualTo(expectedCarrierDetail.getIconUrl());
	}
	
	@Test
	public void testToRuleInfo() {
		
		RuleInfo actualRuleInfo = carrierModelMapper.toRuleInfo(TestDataProvider.getRuleList("Simple").get(0));
		RuleInfo expctedRuleInfo = TestDataProvider.getRuleInfoList("Simple").get(0);
		assertThat(actualRuleInfo.getDescription()).isEqualTo(expctedRuleInfo.getDescription());
		assertThat(actualRuleInfo.getName()).isEqualTo(expctedRuleInfo.getName());
		assertThat(actualRuleInfo.getId()).isEqualTo(expctedRuleInfo.getId());
		
	}
	
	
	@Test
	public void testToLocationEntity() {
		Location expectedLocation = new Location();
		Location actualLocation = carrierModelMapper.toLocationEntity(new LocationInfo(), new Location());
		assertThat(actualLocation.getId()).isEqualTo(expectedLocation.getId());
		assertThat(actualLocation.getCity()).isEqualTo(expectedLocation.getCity());
		assertThat(actualLocation.getZipCode()).isEqualTo(expectedLocation.getZipCode());
	}

	

}

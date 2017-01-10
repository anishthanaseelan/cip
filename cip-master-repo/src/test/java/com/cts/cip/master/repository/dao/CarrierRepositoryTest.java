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
package com.cts.cip.master.repository.dao;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.CarrierService;
import com.cts.cip.master.repository.entities.Rule;
import com.cts.cip.master.repository.entities.RuleTypeDefinition;






@ContextConfiguration(locations = {"classpath:spring/core-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class CarrierRepositoryTest {
	
	Logger logger  =  LoggerFactory.getLogger(CarrierRepository.class);
	
	@Autowired
	MasterDataService masterDataService;
	
	

	@Test
	@Transactional
	public void testFindAll() {		
	
		List<Carrier> carrierList = masterDataService.findAllCarriers();		
		 assertThat(carrierList.size()).isEqualTo(4);
				
		
	}
	
	@Test
	@Transactional
	public void testfindByPage() {	
	
		List<Carrier> carrierList = masterDataService.findCarriersByPage(1,10,"id","desc");
		
		assertThat(carrierList.size()).isEqualTo(4);
		assertThat(carrierList.get(0).getId()).isEqualTo(14);
		
		carrierList = masterDataService.findCarriersByPage(1,10,"id","asc");
		assertThat(carrierList.get(0).getId()).isEqualTo(11);
		
		carrierList = masterDataService.findCarriersByPage(2,10,"id","desc");
		assertThat(carrierList.size()).isEqualTo(0);
		
		
	}
	
	
	@Test
	@Transactional
	public void testfindById() {		
	
		Carrier carrier = masterDataService.findCarrierById(12);		
		assertThat(carrier.getName()).isEqualTo("UPS");
		assertThat(carrier.getScac()).isEqualTo("46379");
		logger.debug("Carrier-Details:",carrier.toString());		
	}
	
	@Test
	@Transactional
	public void testfindByCode() {		
	
		CarrierService carrierService = masterDataService.findCarrierByCode("20");		
		assertThat(carrierService.getScac()).isEqualTo("25454");			
	}
	@Test
	@Transactional	
	public void testUpdateCarrier() {	
		Carrier carrier = masterDataService.findCarrierById(12);	
		carrier.setName("UPSMOD");
		masterDataService.updateCarrier(carrier);
		Carrier updatedCarrier = masterDataService.findCarrierById(12);
		assertThat(updatedCarrier.getName()).isEqualTo("UPSMOD");			
		logger.debug(carrier.toString());
		
	}
	
	@Test
	@Transactional
	public void testFindRulesByService() {
		List<Rule> ruleList = masterDataService.findRulesByService("2");
		Rule rule = ruleList.get(0);
		List<RuleTypeDefinition> ruleTypeDefinitions  =  rule.getRuleType().getRuleTypeDefinitions();
		assertThat(ruleTypeDefinitions.get(0).getFieldName()).isEqualTo("MAX_WEIGHT");
		
		assertThat(ruleList.size()).isEqualTo(1);
		
	}
	
	@Test
	@Transactional
	public void testFindActiveServiceCodes() {
		List<String> activeServiceCodes = masterDataService.findActiveServiceCodes();	
		assertThat(activeServiceCodes.size()).isGreaterThan(1);
	}
}

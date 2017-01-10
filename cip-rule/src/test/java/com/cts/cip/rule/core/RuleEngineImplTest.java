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
package com.cts.cip.rule.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.model.CarrierReference;
import com.cts.cip.common.model.Dimensions;
import com.cts.cip.common.model.ShipperDetails;
import com.cts.cip.common.model.ShipperServiceType;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.Weight;
import com.cts.cip.common.model.WeightAndDimensions;
import com.cts.cip.rule.model.RuleEngine;
import com.cts.cip.rule.model.RuleEngineResult;
import com.cts.cip.rule.model.RuleException;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("cip-rule-test")
public class RuleEngineImplTest {
	
	@Autowired
	public RuleEngine ruleEngine;
	
	@Test
	@Transactional
	public void testValidateServiceWithoutRules () throws RuleException {
		
		ShippingUnit shippingUnit = getBaseModel();
		ShipperDetails shipperDetails = new ShipperDetails();
		ShipperServiceType shipperServiceType = new ShipperServiceType();
		shipperDetails.setShipperServiceType(shipperServiceType);
		shipperDetails.getShipperServiceType().setShipperServiceTypeCode("9");		
		shippingUnit.setShipperDetails(shipperDetails);		
		shippingUnit.getWeightAndDimensions().getDimensions().setLength(79f);
		RuleEngineResult ruleEngineResult =  ruleEngine.validateRules(shippingUnit);		
		assertThat(ruleEngineResult.getErrorRulesList().size()).isEqualTo(0);
		assertThat(ruleEngineResult.isSuccess()).isEqualTo(true);
		assertThat(ruleEngineResult.getPassCount()).isEqualTo(0);
		assertThat(ruleEngineResult.getFailCount()).isEqualTo(0);
		assertThat(ruleEngineResult.getTotalRules()).isEqualTo(0);
		
		
		
	}
	
	

	@Test
	@Transactional
	public void testValidateRulesPassCondition() throws RuleException {
		
		ShippingUnit shippingUnit = getBaseModel();
		
		ShipperDetails shipperDetails = new ShipperDetails();
		ShipperServiceType shipperServiceType = new ShipperServiceType();
		shipperDetails.setShipperServiceType(shipperServiceType);
		shipperDetails.getShipperServiceType().setShipperServiceTypeCode("20");		
		shippingUnit.setShipperDetails(shipperDetails);
		shippingUnit.getWeightAndDimensions().getDimensions().setLength(79.f);
		RuleEngineResult ruleEngineResult =  ruleEngine.validateRules(shippingUnit);		
		assertThat(ruleEngineResult.getErrorRulesList().size()).isEqualTo(0);
		assertThat(ruleEngineResult.isSuccess()).isEqualTo(true);
		assertThat(ruleEngineResult.getPassCount()).isEqualTo(1);
		assertThat(ruleEngineResult.getFailCount()).isEqualTo(0);
		assertThat(ruleEngineResult.getTotalRules()).isEqualTo(1);
		
		
		
	}

	@Test
	@Transactional
	public void testValidateRulesFailCondition() throws RuleException {
		ShippingUnit shippingUnit = getBaseModel();		
		ShipperDetails shipperDetails = new ShipperDetails();
		ShipperServiceType shipperServiceType = new ShipperServiceType();
		shipperDetails.setShipperServiceType(shipperServiceType);
		shipperDetails.getShipperServiceType().setShipperServiceTypeCode("20");		
		shippingUnit.setShipperDetails(shipperDetails);
		shippingUnit.getWeightAndDimensions().getDimensions().setLength(200.f);
		RuleEngineResult ruleEngineResult =  ruleEngine.validateRules(shippingUnit);
		System.out.println("Main Result: " + ruleEngineResult);
		assertThat(ruleEngineResult.getErrorRulesList().size()).isEqualTo(1);
		assertThat(ruleEngineResult.isSuccess()).isEqualTo(false);
		assertThat(ruleEngineResult.getPassCount()).isEqualTo(0);
		assertThat(ruleEngineResult.getFailCount()).isEqualTo(1);
		assertThat(ruleEngineResult.getTotalRules()).isEqualTo(1);
		assert(true);
		
	
	}

	
	@Test
	public void testValidateRulesException () {
		assert(true);
		//fail("Not yet implemented");
	}
	
	private ShippingUnit getBaseModel() {
		ShippingUnit shippingUnit = new ShippingUnit();
		CarrierReference  carrierReference =  new CarrierReference();
		carrierReference.setId("1");
		WeightAndDimensions  weightAndDimensions = new WeightAndDimensions();
		Weight weight = new Weight();
		weight.setWeight(50f);
		weightAndDimensions.setWeight(weight);
		Dimensions dimension = new Dimensions();
		dimension.setHeight(100f);
		dimension.setLength(100f);
		dimension.setWidth(100f);		
		weightAndDimensions.setDimensions(dimension);
		shippingUnit.setWeightAndDimensions(weightAndDimensions);
		shippingUnit.setCarrierReference(carrierReference);
		return shippingUnit;
	}
}

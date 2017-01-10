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
package com.cts.cip.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.Weight;
import com.cts.cip.common.model.WeightAndDimensions;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ResponseStatus;

public class ShipUnitRequestValidator {
	ShippingUnit shippingUnit = new ShippingUnit();

	@Before
    public void setUp() {
		
		
    }
	
	@Test
	public void tesInvalidWeight() {
		WeightAndDimensions weightAndDimensions = new WeightAndDimensions();
		Weight weight = new Weight();
		weight.setWeight(0.0f);
		weightAndDimensions.setWeight(weight);
		shippingUnit.setWeightAndDimensions(weightAndDimensions);
		CIPResponse<String> validationRestult = CipValidator.validateRequest(shippingUnit);		
		assertThat(validationRestult.getStatus().equals(ResponseStatus.FAILURE));
		System.out.println(validationRestult);
		shippingUnit.getWeightAndDimensions().getWeight().setWeight(111000f);
		validationRestult = CipValidator.validateRequest(shippingUnit);	
		assertThat(validationRestult.getStatus().equals(ResponseStatus.FAILURE));				
		System.out.println(validationRestult);
		
		
	}

}

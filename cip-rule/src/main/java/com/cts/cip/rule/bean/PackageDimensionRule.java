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
package com.cts.cip.rule.bean;

import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.rule.model.RuleResult;

public class PackageDimensionRule implements RuleBean {		

	public RuleResult validateRule(ShippingUnit shippingUnit, RuleData ruleData) {
		
		String details = "";
		boolean hasError = false;
		
		
		float maxWeight = Float.valueOf(ruleData.getRuleFieldMap().get("MAX_WEIGHT"));
		float maxLength = Float.valueOf(ruleData.getRuleFieldMap().get("MAX_LENGTH"));
		float maxWidth = Float.valueOf(ruleData.getRuleFieldMap().get("MAX_WIDTH"));
		float maxHeight = Float.valueOf(ruleData.getRuleFieldMap().get("MAX_HEIGHT"));
		
	
		
		// Main Validation Logic
		if(shippingUnit.getWeightAndDimensions().getWeight().getWeight()>maxWeight)  {
			hasError=true;
			details = details + "\n RuleCondition-1: Max wieight allowed for this package is " + maxWeight
					+ " lbs .But the actual weight is " + shippingUnit.getWeightAndDimensions().getWeight().getWeight()
					+ " lbs";
		}
		
		if(shippingUnit.getWeightAndDimensions().getDimensions().getWidth()>maxWidth)  {
			hasError=true;
			details = details + "\n RuleCondition-2: Max Width allowed for this package is " + maxWidth
					+ ".But the actual Width is " + shippingUnit.getWeightAndDimensions().getDimensions().getWidth();
		}
		
		if(shippingUnit.getWeightAndDimensions().getDimensions().getLength()>maxLength)  {
			hasError=true;
			details = details + "\n RuleCondition-3: Max Length allowed for this package is " + maxLength
					+ ".But the actual length is " + shippingUnit.getWeightAndDimensions().getDimensions().getLength();
		}
		
		if(shippingUnit.getWeightAndDimensions().getDimensions().getHeight()>maxHeight)  {
			hasError=true;
			details = details + "\n RuleCondition-4: Max Height allowed for this package is " + maxHeight
					+ ".But the actual height is " + shippingUnit.getWeightAndDimensions().getDimensions().getHeight();
		}
		
		RuleResult ruleResult = new RuleResult();
		
		if(hasError) {
			ruleResult = buildErrorResult(details,ruleData);
		} else {
			ruleResult = buildSuccessResult();
		}
		
		ruleResult.setRuleName(ruleData.getRuleName());
		ruleResult.setSequence(ruleData.getSequence());
		ruleResult.setRuleType(ruleData.getRuleType());
		ruleResult.setCodeDescription(ruleData.getRuleDescription());	
		return ruleResult;

			
	}

	
	private RuleResult buildSuccessResult() {
		RuleResult ruleResult = new RuleResult();
		ruleResult.setCode("0");	
		ruleResult.setCodeDescription("SUCCESS");			
		return ruleResult;
		

	}
	
	private RuleResult buildErrorResult(String details,RuleData ruleData) {
		RuleResult ruleResult = new RuleResult();		
		ruleResult.setCode("1");	
		ruleResult.setCodeDescription("Failure");
		ruleResult.setDetails(details);		
		return ruleResult;
	}

	

	
}

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
import com.cts.cip.common.model.WeightAndDimensions;
import com.cts.cip.rule.model.RuleResult;

public class PackageSizeRestrictionRule implements RuleBean {

	@Override
	public RuleResult validateRule(ShippingUnit shippingUnit, RuleData ruleData) {

		String details = "";
		boolean hasError = false;

		float maxWeight = Float.valueOf(ruleData.getRuleFieldMap().get("MAX_WEIGHT"));
		float maxLength = Float.valueOf(ruleData.getRuleFieldMap().get("MAX_LENGTH"));
		float maxSize = Float.valueOf(ruleData.getRuleFieldMap().get("MAX_SIZE"));
		
		WeightAndDimensions dimension = shippingUnit.getWeightAndDimensions();

		// Validating the weight
		if (dimension.getWeight().getWeight() > maxWeight) {
			hasError = true;
			details = details + "\n RuleCondition-1: Max weight allowed for this package is " + maxWeight
					+ " lbs .But the actual weight is " + dimension.getWeight().getWeight()
					+ " lbs";
		}

		// Validating the Length
		if (dimension.getDimensions().getLength() > maxLength) {
			hasError = true;
			details = details + "\n RuleCondition-2: Max Length allowed for this package is " + maxLength
					+ ".But the actual length is " + dimension.getDimensions().getLength();
		}
		
		// Validating Package Size
		//package size = length + (2xWidth ) + (2xHeight)
		float actualSize = dimension.getDimensions().getLength() + (2 * dimension.getDimensions().getWidth()) + (2 * dimension.getDimensions().getHeight()); 
		if (actualSize>maxSize) {
			hasError =  true;
			details = details + "\n RuleCondition-3: Max Size allowed for this package is " +  maxSize
					+ ".But the actual size is " + actualSize + ". The size is computed based on the formula Length + 2xWidth +2xHeight";
		}

		RuleResult ruleResult = new RuleResult();

		if (hasError) {
			ruleResult = buildErrorResult(details, ruleData);
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

	private RuleResult buildErrorResult(String details, RuleData ruleData) {
		RuleResult ruleResult = new RuleResult();
		ruleResult.setCode("1");
		ruleResult.setCodeDescription("Failure");
		ruleResult.setDetails(details);
		return ruleResult;
	}

}

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

import java.util.Arrays;
import java.util.List;

import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.rule.model.RuleResult;

public class ZoneRestrictionRule implements RuleBean {

	@Override
	public RuleResult validateRule(ShippingUnit shippingUnit, RuleData ruleData) {

		String details = "";
		boolean hasError = false;
		String actualZipCode = shippingUnit.getOrder().getDeliveryToAddress().getZipCode();
		String actualState = shippingUnit.getOrder().getDeliveryToAddress().getState();

		String excludedStates   = ruleData.getRuleFieldMap().get("EXCLUDED_STATES");
		String restrictedStates = ruleData.getRuleFieldMap().get("RESTRICTED_STATES");
		String restrictedZipCodes = ruleData.getRuleFieldMap().get("RESTRICTED_ZIP_CODES");
		
		
		List<String> excludedStateList = Arrays.asList(excludedStates.split(","));
		List<String> restrictedStateList = Arrays.asList(restrictedStates.split(","));
		List<String> restrictedZipCodeList = Arrays.asList(restrictedZipCodes.split(","));
		
		if (excludedStateList.contains(actualState)) {
			hasError = true;
			details = details + "\n RuleCondition-1: For the UPS NextDay Air Saver the " +  excludedStates + "states are not allowed.";			
		} else if(restrictedStateList.contains(actualState)) {
			if(!restrictedZipCodeList.contains(actualZipCode)) {
				hasError = true;
				details = details + "\n For the UPS NextDay Air Saver the " +  actualState + " comes under one of the restricted states. The following zipcodes are only allowed for the restricted states. " + restrictedZipCodes; 
			}
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

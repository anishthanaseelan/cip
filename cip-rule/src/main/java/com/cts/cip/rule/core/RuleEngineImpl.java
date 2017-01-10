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



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.rule.model.RuleEngine;
import com.cts.cip.rule.model.RuleEngineResult;
import com.cts.cip.rule.model.RuleException;
import com.cts.cip.rule.model.RuleResult;

@Service
public class RuleEngineImpl  implements RuleEngine {	
	
	Logger logger  =  LoggerFactory.getLogger(RuleEngineImpl.class);
	
	@Autowired
	RuleExecutor ruleExecutor;
	
	public RuleEngineResult validateRules(ShippingUnit shippingUnit) throws RuleException {
		RuleEngineResult ruleEngineResult; 			
		List<RuleResult> ruleResutlList =  ruleExecutor.executeRules(shippingUnit);
		ruleEngineResult = populateResult(ruleResutlList);
		logger.debug("Rule Engine Result: "+ruleEngineResult.toString());
		return ruleEngineResult;
	}

	private RuleEngineResult populateResult(List<RuleResult> ruleResutlList) {
		RuleEngineResult ruleEngineResult = new RuleEngineResult();
		List<RuleResult> errorRuleResultList = new ArrayList<RuleResult>();
		ruleEngineResult.setTotalRules(ruleResutlList.size());
		
		int passCount=0;
		int errorCount=0;
		for(RuleResult ruleResult : ruleResutlList) {
			if(ruleResult.getCode().equals("0")) {
				passCount = passCount + 1;
			} else {
				errorCount = errorCount + 1;
				errorRuleResultList.add(ruleResult);
			}
		}
		
		if (errorCount==0) {
			ruleEngineResult.setSuccess(true);
		} else {
			ruleEngineResult.setSuccess(false);
		}
		ruleEngineResult.setFailCount(errorCount);
		ruleEngineResult.setPassCount(passCount);
		ruleEngineResult.setErrorRulesList(errorRuleResultList);
		return ruleEngineResult;
	}

}

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
package com.cts.cip.core.bo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.cip.common.exceptions.RuleEngineException;
import com.cts.cip.common.exceptions.RuleValidationException;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.rule.model.RuleEngine;
import com.cts.cip.rule.model.RuleEngineResult;
import com.cts.cip.rule.model.RuleException;

public class RuleEngineServiceImpl implements RuleEngineService {

	@Autowired
	RuleEngine ruleEngine;

	Logger logger = LoggerFactory.getLogger(RuleEngineServiceImpl.class);
	
	public void validateRules(ShippingUnit shippingUnitReq) throws RuleEngineException, RuleValidationException {
		RuleEngineResult result = null;

		
		try {
			result = ruleEngine.validateRules(shippingUnitReq);
		} catch (RuleException e) {
			logger.error(e.getMessage(),e);
			throw new RuleEngineException(
					"Rule engine couldn't perform the validations and met with following exception " + e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new RuleEngineException(
					"Rule engine couldn't perform the validations and met with following exception " + e.getMessage());
		}

		if (!result.isSuccess()) {
			throw new RuleValidationException("The Shipping Unit failed validation(s)" + result.toString());
		}
		return;

	}

}
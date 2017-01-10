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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.rule.bean.RuleBean;
import com.cts.cip.rule.bean.RuleData;
import com.cts.cip.rule.model.RuleResult;
import com.cts.cip.rule.repository.dao.RuleRepository;
import com.cts.cip.rule.repository.entity.Rule;
import com.cts.cip.rule.repository.entity.RuleTypeDefinition;



@Service
public class RuleExecutor {
	
	Logger logger = LoggerFactory.getLogger(RuleExecutor.class);

	RuleRepository ruleRepository;
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	public void setRuleRepository(RuleRepository ruleRepository) {
		this.ruleRepository =  ruleRepository;
	}
	
	public List<RuleResult> executeRules(ShippingUnit shippingUnit) {
		
				
		 List<RuleResult> ruleResultList = new ArrayList<RuleResult>();
		
		 List<Rule> ruleList =  ruleRepository.findRulesByCarrierService(shippingUnit.getShipperDetails().getShipperServiceType().getShipperServiceTypeCode());
		 
		
		 for (Rule rule  : ruleList ) {
			 logger.debug("Executing the rule: " + rule.getName() + "-" + rule.getRuleType().getRuleBeanName());
			 RuleData ruleData  = mapRuleData(rule);
			 RuleBean ruleBean = (RuleBean) applicationContext.getBean(rule.getRuleType().getRuleBeanName());
			 RuleResult ruleResult = ruleBean.validateRule(shippingUnit,ruleData);
			 ruleResultList.add(ruleResult);
		 }		
		
		return ruleResultList;
		
		
	}

	private RuleData mapRuleData(Rule rule) {
		RuleData ruleData = new RuleData();
		Map<String,String> ruleFieldMap = new HashMap<String,String>();
		int index=0;
		for (RuleTypeDefinition ruleTypeDefinition :  rule.getRuleType().getRuleTypeDefinitions()) {			
			ruleFieldMap.put(ruleTypeDefinition.getFieldName(), rule.getRuleDefinitionList().get(index).getValue());
			index=index+1;
		}
		ruleData.setRuleFieldMap(ruleFieldMap);
		ruleData.setRuleDescription(rule.getDescription());
		ruleData.setRuleName(rule.getName());
		ruleData.setRuleType(rule.getRuleType().getName());
		
		return ruleData;
	}
	
	
}

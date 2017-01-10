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
package com.cts.cip.rule.repository.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.cts.cip.rule.repository.dao.RuleRepository;
import com.cts.cip.rule.repository.entity.Rule;
import com.cts.cip.rule.repository.entity.RuleType;

@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("cip-rule-test")
public class RuleRepositoryTest {

	Logger logger = LoggerFactory.getLogger(RuleRepositoryTest.class);

	@Autowired
	RuleRepository ruleRepository;

	@Test
	@Transactional
	public void testAddRuleType() {
		int oldSize = ruleRepository.findRuleTypeCount();
		RuleType ruleType = new RuleType();
		ruleType.setName("Test Rule Type");
		ruleType.setDescription("Test Rule Type description");
		ruleRepository.addRuleType(ruleType);
		int newSize = ruleRepository.findRuleTypeCount();
		assertThat(newSize).isEqualTo(oldSize + 1);

	}
	
	
	

	@Test
	public void testFindAllRuleType() {
		List<RuleType> ruleTypeList = ruleRepository.findAllRuleType();
		assertThat(ruleTypeList.size()).isEqualTo(2);
	}
	
	
	@Test
	@Transactional
	public void testFindRulesbyService() {
		List<Rule> ruleList =  ruleRepository.findRulesByCarrierService("20");
		for(Rule rule: ruleList) {
			logger.debug(rule.getName());
			System.out.println("Rule Name:" +  rule.getName());
		}
		assertThat(ruleList.size()).isEqualTo(1);
	}

}

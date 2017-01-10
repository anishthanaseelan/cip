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

import java.util.Map;

public class RuleData {

	private String ruleName;
	private String ruleDescription;
	private String ruleType;
	private int sequence;
	private Map<String, String> ruleFieldMap;

	

	@Override
	public String toString() {
		return "RuleData [ruleName=" + ruleName + ", ruleDescription=" + ruleDescription + ", ruleType=" + ruleType
				+ ", sequence=" + sequence + ", ruleFieldMap=" + ruleFieldMap + "]";
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getRuleDescription() {
		return ruleDescription;
	}

	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Map<String, String> getRuleFieldMap() {
		return ruleFieldMap;
	}

	public void setRuleFieldMap(Map<String, String> ruleFieldMap) {
		this.ruleFieldMap = ruleFieldMap;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

}

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
package com.cts.cip.rule.model;

public class RuleResult {
	private String ruleName;
	private String ruleType;	
	private String ruleDescription;
	private int sequence;
	private String code;
	private String codeDescription;
	private String details;
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	private String ruleTrace;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeDescription() {
		return codeDescription;
	}
	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}
	public String getRuleTrace() {
		return ruleTrace;
	}
	public void setRuleTrace(String ruleTrace) {
		this.ruleTrace = ruleTrace;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	

}

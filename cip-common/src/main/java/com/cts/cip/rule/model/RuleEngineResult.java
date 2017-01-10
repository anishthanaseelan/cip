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

import java.util.List;

public class RuleEngineResult {
	
	private int totalRules;
	private int passCount;
	private int failCount;	
	private boolean success;
	private List<RuleResult> errorRulesList;
	
	public int getTotalRules() {
		return totalRules;
	}
	public void setTotalRules(int totalRules) {
		this.totalRules = totalRules;
	}
	public int getPassCount() {
		return passCount;
	}
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<RuleResult> getErrorRulesList() {
		return errorRulesList;
	}
	public void setErrorRulesList(List<RuleResult> errorRulesList) {
		this.errorRulesList = errorRulesList;
	}
	
	@Override
	public String toString(){
		StringBuilder  collaredErrMsg = new StringBuilder(); 
		if ( errorRulesList != null){
			for ( RuleResult ruleError : errorRulesList){
				collaredErrMsg.append(ruleError.getDetails());
			}
		}
		return collaredErrMsg.toString();
	}
	
}

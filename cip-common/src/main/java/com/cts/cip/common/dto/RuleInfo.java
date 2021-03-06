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
package com.cts.cip.common.dto;

import java.util.List;

public class RuleInfo {
	
	private int id;	
	private String ruleTypeName;
	private String name;
	private String description;
	private List<RuleDefinitionInfo> ruleDefintionInfos;
	
	
	public String getName() {
		return name;
	}
	public void setName(String ruleName) {
		this.name = ruleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String ruleDescription) {
		this.description = ruleDescription;
	}
	public List<RuleDefinitionInfo> getRuleDefintionInfos() {
		return ruleDefintionInfos;
	}
	public void setRuleDefintionInfos(List<RuleDefinitionInfo> ruleDefintionInfos) {
		this.ruleDefintionInfos = ruleDefintionInfos;
	}
	public String getRuleTypeName() {
		return ruleTypeName;
	}
	public void setRuleTypeName(String ruleTypeName) {
		this.ruleTypeName = ruleTypeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}

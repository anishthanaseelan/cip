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
package com.cts.cip.rule.repository.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the rule_type database table.
 * 
 */
@Entity
@Table(name = "rule_type")
@NamedQuery(name = "RuleType.findAll", query = "SELECT r FROM RuleType r")
public class RuleType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "create_time")
	private Timestamp createTime;

	private String description;

	private String name;

	@Column(name = "update_time")
	private Timestamp updateTime;

	
	@OneToMany
	@JoinColumn(name="rule_type_id")
	private List<RuleTypeDefinition> ruleTypeDefinitions;
	
	@Column(name="rule_reference")
	private String ruleBeanName;
	

	public String getRuleBeanName() {
		return ruleBeanName;
	}

	public void setRuleBeanName(String ruleBeanName) {
		this.ruleBeanName = ruleBeanName;
	}

	public RuleType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public List<RuleTypeDefinition> getRuleTypeDefinitions() {
		return this.ruleTypeDefinitions;
	}

	public void setRuleTypeDefinitions(List<RuleTypeDefinition> ruleTypeDefinitions) {
		this.ruleTypeDefinitions = ruleTypeDefinitions;
	}

}
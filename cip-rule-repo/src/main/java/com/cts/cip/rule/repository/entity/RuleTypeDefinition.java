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

/**
 * The persistent class for the rule_type_definition database table.
 * 
 */
@Entity
@Table(name = "rule_type_definition")
@NamedQuery(name = "RuleTypeDefinition.findAll", query = "SELECT r FROM RuleTypeDefinition r")
public class RuleTypeDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RuleTypeDefinitionPK id;

	@Column(name = "create_time")
	private Timestamp createTime;

	@Column(name = "field_name")
	private String fieldName;

	@Column(name = "field_type")
	private String fieldType;

	@Column(name = "update_time")
	private Timestamp updateTime;

	/*// bi-directional many-to-one association to Organization
	@ManyToOne
	@Column(name = "rule_type_id", insertable = false, updatable = false)
	private RuleType ruleType;

	public RuleType getRuleType() {
		return ruleType;
	}

	public void setRuleType(RuleType ruleType) {
		this.ruleType = ruleType;
	}*/

	public RuleTypeDefinition() {
	}

	public RuleTypeDefinitionPK getId() {
		return this.id;
	}

	public void setId(RuleTypeDefinitionPK id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
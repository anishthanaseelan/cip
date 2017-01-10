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
package com.cts.cip.master.repository.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the rule_definition database table.
 * 
 */
@Entity
@Table(name="rule_definition")
@NamedQuery(name="RuleDefinition.findAll", query="SELECT r FROM RuleDefinition r")
public class RuleDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RuleDefinitionPK id;

	@Column(name="create_time")
	private Timestamp createTime;

	@Column(name="update_time")
	private Timestamp updateTime;
	@Column(name="vallue")
	private String value;

	//bi-directional one-to-one association to Rule
	
	/*@Column(name="rule_id", insertable=false, updatable=false)
	private Rule rule;*/

	public RuleDefinition() {
	}

	public RuleDefinitionPK getId() {
		return this.id;
	}

	public void setId(RuleDefinitionPK id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String vallue) {
		this.value = vallue;
	}

	/*public Rule getRule() {
		return this.rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}*/

}
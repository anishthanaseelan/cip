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

/**
 * The primary key class for the rule_definition database table.
 * 
 */
@Embeddable
public class RuleDefinitionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="rule_id", insertable=false, updatable=false)
	private int ruleId;

	@Column(name="field_id")
	private short fieldId;

	public RuleDefinitionPK() {
	}
	public int getRuleId() {
		return this.ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public short getFieldId() {
		return this.fieldId;
	}
	public void setFieldId(short fieldId) {
		this.fieldId = fieldId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RuleDefinitionPK)) {
			return false;
		}
		RuleDefinitionPK castOther = (RuleDefinitionPK)other;
		return 
			(this.ruleId == castOther.ruleId)
			&& (this.fieldId == castOther.fieldId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.ruleId;
		hash = hash * prime + ((int) this.fieldId);
		
		return hash;
	}
}
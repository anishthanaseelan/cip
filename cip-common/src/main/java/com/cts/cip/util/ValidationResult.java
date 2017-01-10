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
package com.cts.cip.util;

import java.util.List;

public class ValidationResult {
	
	private boolean status;
	private List<FieldStatus> fieldStatusList;
	private String code;
	private String description;
	
	@Override
	public String toString() {
		return "ValidationResult [succesful=" + status + ", fieldStatusList=" + fieldStatusList + "]";
	}
	
	
	public boolean isSuccesful() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean getStatus() {
		return status;
	}


	public List<FieldStatus> getFieldStatusList() {
		return fieldStatusList;
	}
	public void setFieldStatusList(List<FieldStatus> fieldStatusList) {
		this.fieldStatusList = fieldStatusList;
	}

}

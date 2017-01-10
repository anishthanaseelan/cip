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
package com.cts.cip.common.exceptions;

import java.util.List;

import com.cts.cip.util.FieldStatus;

public class RequestValidationException extends BusinessException  {

	@Override
	public String toString() {
		return "RequestValidationException [fieldStatusList=" + fieldStatusList + "]";
	}

	private static final long serialVersionUID = 1L;
	private List<FieldStatus> fieldStatusList;

	public List<FieldStatus> getFieldStatusList() {
		return fieldStatusList;
	}

	public void setFieldStatusList(List<FieldStatus> fieldStatusList) {
		this.fieldStatusList = fieldStatusList;
	}

	public RequestValidationException(String msg) {
		super(msg);
	}
	
	public RequestValidationException(String msg,List<FieldStatus> fieldStatusList) {
		super(msg);
		this.fieldStatusList = fieldStatusList;		
	}


}

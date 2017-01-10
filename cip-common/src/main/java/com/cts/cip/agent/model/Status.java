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
package com.cts.cip.agent.model;

import java.util.ArrayList;
import java.util.List;

public class Status {

	public String getResponseStatusCode() {
		return responseStatusCode;
	}

	public void setResponseStatusCode(String responseStatusCode) {
		this.responseStatusCode = responseStatusCode;
	}

	public String getResponseStatusDescription() {
		return responseStatusDescription;
	}

	public void setResponseStatusDescription(String responseStatusDescription) {
		this.responseStatusDescription = responseStatusDescription;
	}

	public List<ErrorDetails> getErrors() {
		if(null == errors){
			errors = new ArrayList<ErrorDetails>();
		}
		return errors;
	}

	public void setErrors(List<ErrorDetails> errors) {
		this.errors = errors;
	}

	private String responseStatusCode;

	private String responseStatusDescription;

	private List<ErrorDetails> errors;
}

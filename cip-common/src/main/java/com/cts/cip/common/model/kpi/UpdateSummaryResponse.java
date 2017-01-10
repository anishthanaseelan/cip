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
package com.cts.cip.common.model.kpi;

import java.util.List;

import com.cts.cip.common.response.model.ErrorCode;
import com.cts.cip.common.response.model.ErrorDetail;
import com.cts.cip.common.response.model.ErrorType;
import com.cts.cip.common.response.model.ResponseStatus;

public class UpdateSummaryResponse {
	

	ResponseStatus status;
	
	ErrorType errorType;
	
	ErrorCode errorCode; 
	
	String errorDesc;
	
	List<ErrorDetail> errorList;

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public List<ErrorDetail> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorDetail> errorList) {
		this.errorList = errorList;
	} 		
	

	
}

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
package com.cts.cip.carrier.exception;

public class ResourceException extends Exception{
	private static final long serialVersionUID = 1L;
	private String errorCd;
	private String errorDesc;
		
	public ResourceException(String errCode, String message, Throwable th){
		super(message,th);
		this.errorCd = errCode;
		
	}
	
	public  ResourceException(String errCode, String message){
		super(message);
		this.errorCd = errCode;
	}

	public String getErrorCd() {
		return errorCd;
	}

	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}


}

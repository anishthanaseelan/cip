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
/**
 * 
 */
package com.cts.cip.common.response.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 417765
 *
 */
@XmlRootElement
public class ErrorDetail {

	/**
	 * 
	 */
	public ErrorDetail() {
		// TODO Auto-generated constructor stub
	}
	
	private String fieldName;
	private String fieldValue;
	private String errorMessage;
	private String errorCode;
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * @return the fieldValue
	 */
	public String getFieldValue() {
		return fieldValue;
	}
	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public String toString() {
		return "ErrorDetail [fieldName=" + fieldName + ", fieldValue=" + fieldValue + ", errorMessage=" + errorMessage
				+ ", errorCode=" + errorCode + "]";
	}
	
	
	
}

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
package com.cts.cip.common.response.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class CIPResponse<T> {

	public CIPResponse() {
		// TODO Auto-generated constructor stub
	}
	
	ResponseStatus status;
	
	ErrorType errorType;
	
	ErrorCode errorCode; 
	
	String errorDesc;
	
	List<ErrorDetail> errorList; //  fieldName,fieldValue, errorMessage, code;
	@XmlTransient
	T entity; // entity can be any specific application response.
	
	Object childEntity;

	public Object getChildEntity() {
		return childEntity;
	}

	public void setChildEntity(Object childEntity) {
		this.childEntity = childEntity;
	}

	/**
	 * @return the status
	 */
	public ResponseStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	/**
	 * @return the errorType
	 */
	public ErrorType getErrorType() {
		return errorType;
	}

	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	/**
	 * @return the errorCode
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the errorList
	 */
	public List<ErrorDetail> getErrorList() {
		if ( errorList == null)
			this.errorList = new ArrayList<ErrorDetail>();
		
		return errorList;
	}

	/**
	 * @param errorList the errorList to set
	 */
	public void setErrorList(List<ErrorDetail> errorList) {
		this.errorList = errorList;
	}

	/**
	 * @return the entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(T entity) {
		this.entity = entity;
		this.childEntity = entity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CIPResponse [status=" + status + ", errorType=" + errorType + ", errorCode=" + errorCode
				+ ", errorDesc=" + errorDesc + ", errorList=" + errorList + ", entity=" + entity + "]";
	}
	
}

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
package com.cts.cip.common.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.cts.cip.common.response.model.ErrorDetail;
import com.cts.cip.util.FieldStatus;

@XmlRootElement
public class StatusResponse {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((codeDescription == null) ? 0 : codeDescription.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((errorDetailList == null) ? 0 : errorDetailList.hashCode());
		result = prime * result + ((fieldStatusList == null) ? 0 : fieldStatusList.hashCode());
		result = prime * result + ((returnValue == null) ? 0 : returnValue.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatusResponse other = (StatusResponse) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (codeDescription == null) {
			if (other.codeDescription != null)
				return false;
		} else if (!codeDescription.equals(other.codeDescription))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (errorDetailList == null) {
			if (other.errorDetailList != null)
				return false;
		} else if (!errorDetailList.equals(other.errorDetailList))
			return false;
		if (fieldStatusList == null) {
			if (other.fieldStatusList != null)
				return false;
		} else if (!fieldStatusList.equals(other.fieldStatusList))
			return false;
		if (returnValue == null) {
			if (other.returnValue != null)
				return false;
		} else if (!returnValue.equals(other.returnValue))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "StatusResponse [code=" + code + ", codeDescription=" + codeDescription + ", description=" + description
				+ ", detail=" + detail + ", returnValue=" + returnValue + ", fieldStatusList=" + fieldStatusList
				+ ", errorDetailList=" + errorDetailList + "]";
	}
	private String code;
	private String codeDescription;
	private String description;
	private String detail;
	private String returnValue;
	private List<FieldStatus> fieldStatusList;
	private List<ErrorDetail> errorDetailList;
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
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
	public String getDetails() {
		return detail;
	}
	public void setDetails(String details) {
		this.detail = details;
	}
	public String getCodeDescription() {
		return codeDescription;
	}
	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}
	public List<FieldStatus> getFieldStatusList() {
		return fieldStatusList;
	}
	public void setFieldStatusList(List<FieldStatus> fieldStatusList) {
		this.fieldStatusList = fieldStatusList;
	}
	public List<ErrorDetail> getErrorDetailList() {
		return errorDetailList;
	}
	public void setErrorDetailList(List<ErrorDetail> errorDetailList) {
		this.errorDetailList = errorDetailList;
	}

}

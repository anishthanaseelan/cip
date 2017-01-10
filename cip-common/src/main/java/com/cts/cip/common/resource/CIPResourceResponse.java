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
package com.cts.cip.common.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.cts.cip.common.model.CancelShippingUnitResponse;
import com.cts.cip.common.model.CreateShippingUnitResponse;
import com.cts.cip.common.model.GetDocumentsResponse;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.LoadShippingUnitResponse;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.UnloadShippingUnitResponse;
import com.cts.cip.common.response.model.ErrorCode;
import com.cts.cip.common.response.model.ErrorDetail;
import com.cts.cip.common.response.model.ErrorType;
import com.cts.cip.common.response.model.ResponseStatus;
/**
 * @author 
 *
 */
@XmlRootElement
public class CIPResourceResponse {
	
	
	ResponseStatus status;
	
	ErrorType errorType;
	
	ErrorCode errorCode; 
	
	String errorDesc;
	
	List<ErrorDetail> errorList; 	
	
	private CreateShippingUnitResponse createShippingUnitResponse;
	
	private CancelShippingUnitResponse cancelShippingUnitResponse;
	
	private LoadShippingUnitResponse loadShippingUnitResponse;
	
	private ManifestShippingUnitsResponse manifestShippingUnitsResponse;
	
	private UnloadShippingUnitResponse unloadShippingUnitResponse;	
	
	private GetDocumentsResponse getDocumentsResponse;
	
	private ShippingUnit shippingUnit;
	
	private Load load;
	
	public Load getLoad() {
		return load;
	}

	public void setLoad(Load load) {
		this.load = load;
	}

	public GetDocumentsResponse getGetDocumentsResponse() {
		return getDocumentsResponse;
	}

	public void setGetDocumentsResponse(GetDocumentsResponse getDocumentsResponse) {
		this.getDocumentsResponse = getDocumentsResponse;
	}

	public ShippingUnit getShippingUnit() {
		return shippingUnit;
	}

	public void setShippingUnit(ShippingUnit shippingUnit) {
		this.shippingUnit = shippingUnit;
	}

	public CreateShippingUnitResponse getCreateShippingUnitResponse() {
		return createShippingUnitResponse;
	}

	public void setCreateShippingUnitResponse(CreateShippingUnitResponse createShippingUnitResponse) {
		this.createShippingUnitResponse = createShippingUnitResponse;
	}

	public CancelShippingUnitResponse getCancelShippingUnitResponse() {
		return cancelShippingUnitResponse;
	}

	public void setCancelShippingUnitResponse(CancelShippingUnitResponse cancelShippingUnitResponse) {
		this.cancelShippingUnitResponse = cancelShippingUnitResponse;
	}

	public LoadShippingUnitResponse getLoadShippingUnitResponse() {
		return loadShippingUnitResponse;
	}

	public void setLoadShippingUnitResponse(LoadShippingUnitResponse loadShippingUnitResponse) {
		this.loadShippingUnitResponse = loadShippingUnitResponse;
	}

	public ManifestShippingUnitsResponse getManifestShippingUnitsResponse() {
		return manifestShippingUnitsResponse;
	}

	public void setManifestShippingUnitsResponse(ManifestShippingUnitsResponse manifestShippingUnitsResponse) {
		this.manifestShippingUnitsResponse = manifestShippingUnitsResponse;
	}

	public UnloadShippingUnitResponse getUnloadShippingUnitResponse() {
		return unloadShippingUnitResponse;
	}

	public void setUnloadShippingUnitResponse(UnloadShippingUnitResponse unloadShippingUnitResponse) {
		this.unloadShippingUnitResponse = unloadShippingUnitResponse;
	}

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
		if(errorList==null) {
			errorList = new ArrayList<ErrorDetail>();
		}
		return errorList;
	}

	public void setErrorList(List<ErrorDetail> errorList) {
		this.errorList = errorList;
	}

	

}

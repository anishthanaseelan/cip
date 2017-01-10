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
package com.cts.cip.core.bo.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.GetDocumentsResponse;
import com.cts.cip.common.model.ShippingDocument;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ErrorType;
import com.cts.cip.common.response.model.ResponseStatus;
import com.cts.cip.doc.services.DocumentEngineService;

public class DocumentServiceImpl implements DocumentService{

	@Autowired
	DocumentEngineService docEngine;
	
	Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

	
	public List<ShippingDocument> generateDocuments(ShippingUnit shippingUnitReq)
			throws SystemFailure, BusinessException {
		CIPResponse<GetDocumentsResponse>  response ;
		List<ShippingDocument> docList = null;
		
		try{

		response = docEngine.create(shippingUnitReq);
		
		if ( response.getStatus().equals(ResponseStatus.SUCCESS)){
			docList =  response.getEntity().getDocuments();
		} else {
			generateException(response);
		}
		return docList;
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);			
			throw new SystemFailure(e.getMessage());
		}
		
		

	}
	
	private void generateException(CIPResponse<GetDocumentsResponse> response) throws BusinessException, SystemFailure {
		if ( response.getErrorType().equals(ErrorType.BUSINESS_ERROR)){
			throw new BusinessException ( "Business Exception in Document engine : " + response.getErrorDesc() ,response.getErrorList() );
		} else {
			throw new SystemFailure ( "System Failure in Document engine : " + response.getErrorDesc());
		}
	
}

}

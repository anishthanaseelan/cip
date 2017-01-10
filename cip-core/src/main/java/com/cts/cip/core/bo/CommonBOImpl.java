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
package com.cts.cip.core.bo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.RequestValidationException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.TransactionLogInfo;
import com.cts.cip.core.bo.services.CommonService;

public class CommonBOImpl implements CommonBO {
	
	CommonService commonService;
	

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}


	Logger logger = LoggerFactory.getLogger(CommonBOImpl.class);

	@Override
	public void saveTransaction(TransactionLogInfo transactionLogInfo) throws BusinessException, SystemFailure, RequestValidationException {
		commonService.saveTransaction(transactionLogInfo);
		
	}

	@Override
	public List<TransactionLogInfo> findTransactionsById(String id) throws BusinessException, SystemFailure, RequestValidationException {		
		try {
			return commonService.findTransactionsById(id);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new BusinessException("Not able to locate the transaction log files path");
			
		}
	}
	
	
	@Override
	public Map<String,String> getShipViaMap() {
		return commonService.getShipViaMap();
	}

}

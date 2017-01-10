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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.model.TransactionLogInfo;
import com.cts.cip.core.mapper.CommonMapper;
import com.cts.cip.core.repository.CommonRepo;
import com.cts.cip.core.repository.entities.TransactionLog;
import com.cts.cip.core.utilities.CipCoreApplicationProperties;
import com.cts.cip.core.utilities.Util;

public class CommonServiceImpl implements CommonService {
	
	@Autowired
	CommonRepo repository;
	
	@Autowired 
	CommonMapper mapper;

	@Override
	@Transactional
	public void saveTransaction(TransactionLogInfo transactionLogInfo) {
		TransactionLog transactionLog = mapper.mapToEntity(transactionLogInfo);
		repository.saveTransaction(transactionLog);
	}

	@Override
	@Transactional
	public List<TransactionLogInfo> findTransactionsById(String id) throws IOException {
		List<TransactionLog> transLogList = repository.findTransactionsById(id, "NA");
		List<TransactionLogInfo> transLogInfoList = new ArrayList<TransactionLogInfo>();
		Properties properties = new CipCoreApplicationProperties().getProperties();
		for(TransactionLog transactionLog : transLogList) {
			TransactionLogInfo transactionLogInfo =  mapper.mapToModel(transactionLog);
			String baseUrl = properties.getProperty("document.transaction.url");
			String folderName = Util.getDateStringFromUuid(transactionLog.getTransactionId());		
			String fileName = baseUrl + "/" + folderName + "/" + transactionLog.getTransactionId();
			transactionLogInfo.setRequestFileUrl(fileName + "_request." + transactionLog.getFileType());
			transactionLogInfo.setResponseFileUrl(fileName + "_response." + transactionLog.getFileType());
			transLogInfoList.add(transactionLogInfo);
		}
		return transLogInfoList;
	}

	@Override
	public Map<String, String> getShipViaMap() {
		return repository.getShipViaMap();
	}

}

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
package com.cts.cip.core.mapper;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.cts.cip.common.model.TransactionLogInfo;
import com.cts.cip.core.repository.entities.TransactionLog;

@Service
public class CommonMapperImpl implements CommonMapper {

	@Override
	public TransactionLogInfo mapToModel(TransactionLog transactionLog) throws IOException {
		
		TransactionLogInfo transactionLogInfo = new TransactionLogInfo();
		transactionLogInfo.setTransactionId(transactionLog.getTransactionId());
		transactionLogInfo.setTimeStamp(transactionLog.getTimeStamp().toString());
		transactionLogInfo.setEventOrigin(transactionLog.getEventOrigin());
		transactionLogInfo.setEventType(transactionLog.getEventType());
		return transactionLogInfo;
		
	}
	
	public TransactionLog mapToEntity(TransactionLogInfo transactionLogInfo) {
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setEventOrigin(transactionLogInfo.getEventOrigin());
		transactionLog.setEventType(transactionLogInfo.getEventType());
		transactionLog.setReferenceId(transactionLogInfo.getReferenceId());
		transactionLog.setTransactionId(transactionLogInfo.getTransactionId());
		transactionLog.setFileType(transactionLogInfo.getFileType());
		return transactionLog;
	}

}

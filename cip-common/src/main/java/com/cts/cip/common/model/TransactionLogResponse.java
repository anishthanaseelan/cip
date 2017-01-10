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
@XmlRootElement
public class TransactionLogResponse {
	
	private int totalTransactions;
	private List<TransactionLogInfo> transactionLogList;
	private String status;
	private String errorDesc;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public int getTotalTransactions() {
		return totalTransactions;
	}
	public void setTotalTransactions(int totalTransactions) {
		this.totalTransactions = totalTransactions;
	}
	public List<TransactionLogInfo> getTransactionLogList() {
		return transactionLogList;
	}
	public void setTransactionLogList(List<TransactionLogInfo> transLogList) {
		this.transactionLogList = transLogList;
	}
	
	
}

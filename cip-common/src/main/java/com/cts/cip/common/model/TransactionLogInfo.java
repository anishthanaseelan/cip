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

public class TransactionLogInfo {
	
	private String transactionId;
	private String eventOrigin;
	private String eventType;
	private String referenceId;
	private String timeStamp;
	private String requestFileUrl;
	private String responseFileUrl;
	private String fileType;
	
	
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getEventOrigin() {
		return eventOrigin;
	}
	public void setEventOrigin(String eventOrigin) {
		this.eventOrigin = eventOrigin;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getRequestFileUrl() {
		return requestFileUrl;
	}
	public void setRequestFileUrl(String requestFileUrl) {
		this.requestFileUrl = requestFileUrl;
	}
	public String getResponseFileUrl() {
		return responseFileUrl;
	}
	public void setResponseFileUrl(String responseFileUrl) {
		this.responseFileUrl = responseFileUrl;
	}

}

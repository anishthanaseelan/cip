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
package com.cts.cip.carrier;

public class CarrierConstants {
	
	private CarrierConstants(){
		
	}
	
	public static final String CARRIER_SHIP_TYPE_EMPTY="Carrier or Ship Type needs to specified";
	public static final String CARRIER_SHIP_TYPE_INVALID="Carrier or  Ship Type is Invalid / Not Supported";
	
	public static final String CARRIER_MODULE_INFRA_ERR_CD = "CIPE-0001";
	public static final String CARRIER_MODULE_CLIENT_INFRA_ERR_CD = "CIPE-0002";
	public static final String CARRIER_MODULE_DATA_ERR_CD = "CIPE-0003";
	
	public static final String RESPONSE_SUCCESS_CD = "1";
	public static final String RESPONSE_SUCCESS_DESC = "SUCCESS";
	public static final String RESPONSE_FAIL_CD = "0";
	public static final String RESPONSE_FAIL_DESC = "FAILURE";

	public static final String JSON_MAP_FILE_READ_ERR_DESC = "Unable to read JSON Mapping File ";
	public static final String TEMPLATE_FILE_READ_ERR_DESC = "Unable to read Template File ";
	
	public static final int PACKAGE_COUNT=1;
	
	
	public static final String SMTP_HOST = "mail.smtp.host";
	public static final String SMTP_PORT = "mail.smtp.port";
	public static final String MAIL_TO_ADDRESS_LIST = "notify.recipient.list";
	public static final String MAIL_FROM_ADDRESS = "notify.sender";	
	
	public static final String SMTP_AUTHENTICATE = "smtp.authenticate";
	public static final String SMTP_USERNAME = "smtp.username";
	public static final String SMTP_PD = "smtp.password";
	public static final String SMTP_PROTOCOL = "smtp.protocol";
	
	public static final String NOTIFICATION_PROPS_FILE="notification.properties";
	

}

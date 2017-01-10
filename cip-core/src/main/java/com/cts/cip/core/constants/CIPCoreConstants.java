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
/**
 * 
 */
package com.cts.cip.core.constants;

/**
 * @author 417765
 *
 */
public class CIPCoreConstants {

	// Ship unit states
	public static final String SHIP_UNIT_STATE_LABEL_CREATED = "LABEL_CREATED";
	public static final String SHIP_UNIT_STATE_LABEL_EXCEPTIOM = "EXCEPTION";

	// LOAD States
	public static final String LOAD_STATE_LOADING = "LOADING";
	public static final String LOAD_STATE_MANIFESTED = "MANIFESTED";
	public static final String LOAD_STATE_CANCELLED = "CANCELLED";

	//
	public static final String LOAD_ALREADY_EXISTS = "Already Load Exists!!";

	public static final String CREATE = "CREATE";
	public static final String UPDATE = "UPDATE";

	// AGENT URLS
	// public static final String AGENT_MANIFEST_URL =
	// "http://40.117.237.90:9080/cip-agent/Agent/manifestTrailer";
	// public static final String AGENT_CREATE_URL =
	// "http://40.117.237.90:9080/cip-agent/Agent/createShipment";
	// public static final String AGENT_CANCEL_URL =
	// "http://40.117.237.90:9080/cip-agent/Agent/cancelShipment";

	// DEV_CONFIG
	// public static final String MASTER_SERVICE_URL =
	// "http://52.165.41.151:8500/cip-master/service/node/";
	// TEST_CONFIG
	// public static final String MASTER_SERVICE_URL =
	// "http://localhost:8500/cip-master/service/node/";
	// INT_CONFIG
	// public static final String MASTER_SERVICE_URL =
	// "http://localhost:8800/cip-master/service/node/";

	public static final String CIP_CORE_PROPERTY_FILE_PATH = "/properties/cip-core.properties";
	public static final String AGENT_MANIFEST_URL = "agent.manifest.url";
	public static final String AGENT_CREATE_URL = "agent.create.url";
	public static final String AGENT_CANCEL_URL = "agent.cancel.url";
	public static final String MASTER_SERVICE_URL = "master.service.url";

	public static final String RESPONSE_SUCCESS_CD = "1";
	public static final String RESPONSE_SUCCESS_DESC = "SUCCESS";
	public static final String RESPONSE_FAIL_CD = "0";
	public static final String RESPONSE_FAIL_DESC = "FAILURE";

	public static final Integer MAX_RECORD_COUNT = 100;

	public static final String GET_SHIPUNIT_NOTIF_SUB="CIP-CORE : PROBLEM RETRIEVING SHIPPING UNIT";
	public static final String GET_DOC_NOTIF_SUB="CIP-CORE : PROBLEM RETRIEVING DOCUMENTS";
	public static final String CREATE_SHIPUNIT_NOTIF_SUB="CIP-CORE : PROBLEM CREATING SHIPPING LABEL";	
	public static final String CANCEL_SHIPUNIT_NOTIF_SUB="CIP-CORE : PROBLEM IN SHIPMENT LABEL CANCELLATION";	
	public static final String LOAD_SHIPUNIT_NOTIF_SUB="CIP-CORE : PROBLEM IN LOADING SHIPMENT UNIT";	
	public static final String UNLOAD_SHIPUNIT_NOTIF_SUB="CIP-CORE : PROBLEM IN UNLOADING SHIPMENT UNIT";	
	public static final String MANIFEST_SHIPUNIT_NOTIF_SUB="CIP-CORE : PROBLEM IN MANIFESTING SHIPMENT UNITS";	
}

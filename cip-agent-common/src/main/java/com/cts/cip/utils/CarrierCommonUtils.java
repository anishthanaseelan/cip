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
package com.cts.cip.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.agent.model.ErrorDetails;
import com.cts.cip.agent.model.Status;
import com.cts.cip.carrier.CarrierConstants;
import com.cts.cip.carrier.exception.CarrierClientException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CarrierCommonUtils {
	private static Logger logger = LoggerFactory.getLogger(CarrierCommonUtils.class);

	/** To get error status
	 * @param cce
	 * @return
	 */
	public Status getStatus(CarrierClientException cce) {
		Status status = new Status();
		status.setResponseStatusCode(CarrierConstants.RESPONSE_FAIL_CD);
		status.setResponseStatusDescription(CarrierConstants.RESPONSE_FAIL_DESC);

		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorCode(cce.getErrorCd());
		errorDetails.setErrorDescription(cce.getErrorDesc());

		status.getErrors().add(errorDetails);

		return status;
	}

	/** Load JSON data to Map
	 * @param fileName
	 * @return
	 * @throws CarrierClientException
	 */
	public Map<String, Object> getMapFromJSONFile(String fileName) throws CarrierClientException {

		HashMap<String, Object> jsonMap = null;
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
			if (inputStream == null)
				throw new IOException(fileName + "- properties file not found.");
			jsonMap = new ObjectMapper().readValue(inputStream, HashMap.class);

		} catch (IOException ioexcep) {
			logger.error("Exception " + ioexcep);
			logger.error("Unable to Read JSON File : " + fileName);
			throw new CarrierClientException(CarrierConstants.CARRIER_MODULE_INFRA_ERR_CD,
					CarrierConstants.JSON_MAP_FILE_READ_ERR_DESC + fileName, ioexcep);
		}

		return jsonMap;

	}
	
	/** Load data from property file
	 * @param fileName
	 * @return
	 * @throws CarrierClientException
	 */
	public Map<String,String> getMapFromPropertyFile(String fileName) throws CarrierClientException {
		
		HashMap<String, String> propMap = new HashMap<>();
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
		    while ((line = reader.readLine()) != null) {
		    	String[] keyVal = line.split("=",2);
		    	if (keyVal.length==2) {
		    		propMap.put(keyVal[0], keyVal[1]);
		    	}
		     }		     
		     reader.close();
			if (inputStream == null)
				throw new IOException(fileName + "- properties file not found.");
			
		} catch (IOException ioexcep) {
			logger.error("Exception " + ioexcep);
			logger.error("Unable to Read property File : " + fileName);
			throw new CarrierClientException(CarrierConstants.CARRIER_MODULE_INFRA_ERR_CD,
					CarrierConstants.JSON_MAP_FILE_READ_ERR_DESC + fileName, ioexcep);
		}
		return propMap;

	}

	public Status setStatus(CarrierClientException cce) {
		Status status = new Status();
		status.setResponseStatusCode(CarrierConstants.RESPONSE_FAIL_CD);
		status.setResponseStatusDescription(CarrierConstants.RESPONSE_FAIL_DESC);

		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorCode(cce.getErrorCd());
		errorDetails.setErrorDescription(cce.getErrorDesc());

		status.getErrors().add(errorDetails);

		return status;

	}

	/** Get response status
	 * @param comResponseFailCd
	 * @param comResponseFailDesc
	 * @param responseMap
	 * @param errKey
	 * @param errDescKey
	 * @return
	 */
	public Status getStatus(String comResponseFailCd, String comResponseFailDesc, Map<String, String> responseMap,
			String errKey, String errDescKey) {
		Status status = new Status();

		status.setResponseStatusDescription(comResponseFailDesc);
		status.setResponseStatusCode(comResponseFailCd);
		if (null != responseMap)
			status.getErrors().add(getErrorInfo(responseMap, errKey, errDescKey));

		return status;

	}

	private ErrorDetails getErrorInfo(Map<String, String> responseMap, String errKey, String errDescKey) {

		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setErrorCode(responseMap.get(errKey));
		errorDetails.setErrorDescription(responseMap.get(errDescKey));

		return errorDetails;

	}

	/**
	 * @param templateString
	 * @param valueMap
	 * @return
	 */
	public String injectPlaceholderValues(String templateString, Map<String, Object> valueMap) {
		StrSubstitutor strSub = new StrSubstitutor(valueMap);
		strSub.setValueDelimiter(":-");
		return strSub.replace(templateString);

	}

	/**
	 * Read the template and return as String
	 * 
	 * @param fileName
	 * @return
	 * @throws ShipSolException
	 */
	public String getTemplateString(String fileName) throws CarrierClientException {

		StringBuilder strBuilder = new StringBuilder();
		String line = null;
		BufferedReader br = null;
		try {
			InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
			br = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = br.readLine()) != null) {
				strBuilder.append(line);

			}
			br.close();

		} catch (IOException ioExcep) {
			logger.error(Arrays.toString(ioExcep.getStackTrace()));
			throw new CarrierClientException(CarrierConstants.CARRIER_MODULE_INFRA_ERR_CD,
					CarrierConstants.TEMPLATE_FILE_READ_ERR_DESC, ioExcep);
		}

		return strBuilder.toString();
	}

	/**
	 * @param templateStr
	 * @param valueMap
	 * @return
	 */
	public String injectPlaceholderValuesRec(String templateStr, Map<String, Object> valueMap) {
		String templateString = templateStr;

		Set<String> keySet = valueMap.keySet();
		boolean levelReplaced = false;
		for (String key : keySet) {
			Object val = valueMap.get(key);

			if (val instanceof HashMap) {
				templateString = injectPlaceholderValuesRec(templateString, (HashMap<String, Object>) val);

			} else if (val instanceof ArrayList) {
				logger.info("List instance ");
				List itemsList = (ArrayList) val;
				int size = itemsList.size();
				int counter = 0;
				while (counter < size) {

					Map<String, Object> map = (HashMap<String, Object>) itemsList.get(counter);
					templateString = injectPlaceholderValuesRec(templateString, (HashMap<String, Object>) map);
					counter++;
				}

			} else {
				if (!levelReplaced) {
					StrSubstitutor strSub = new StrSubstitutor(valueMap);
					templateString = strSub.replace(templateString);
					levelReplaced = true;

				}

			}
		}

		return templateString;

	}
	
	
	/**
	 * @param request
	 * @return
	 * @throws CarrierClientException
	 */
	public <T> Map<String,Object> getAsMap(T request) throws CarrierClientException{
		HashMap<String,Object> requestMap = null;
		String objectToJSONString = null;
		try {
			
			objectToJSONString = new ObjectMapper().writeValueAsString(request);
			requestMap = new ObjectMapper().readValue(objectToJSONString, HashMap.class);
			
		} catch (IOException jsonExcep) {
			logger.error("Exception converting Request to Map " + jsonExcep);
			logger.error(Arrays.toString(jsonExcep.getStackTrace()));
			throw new CarrierClientException(CarrierConstants.CARRIER_MODULE_DATA_ERR_CD,jsonExcep.getMessage() , jsonExcep);
		}
		
		return requestMap;
	}

	public static void main(String[] args)
			throws CarrierClientException, JsonParseException, JsonMappingException, IOException {
		// injectPlaceholderValuesRec(String templateStr, Map<String,Object>
		// valueMap)
		CarrierCommonUtils utils = new CarrierCommonUtils();
		String file = "C:/Muthu/Repo_27Jun16/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/cip-agent/WEB-INF/classes/properties/fsmsParams.json";
		HashMap<String, Object> jsonMap = new ObjectMapper().readValue(new File(file), HashMap.class);
		;

		utils.injectPlaceholderValuesRec(null, jsonMap);

		System.out.println("Map : " + jsonMap);

		System.out.println("Is zip 4 :" + utils.isZip4Code("72712-1234"));

	}

	/**
	 * @param zipCode
	 * @return
	 */
	public boolean isZip4Code(String zipCode) {
		boolean isZip4 = false;
		if (null != zipCode && zipCode.length() > 5) {
			isZip4 = (zipCode.substring(6).length()) == 4 ? true : false;
		}
		return isZip4;
	}

	public Status getSuccessStatus(){
		Status status = new Status();
		status.setResponseStatusCode(CarrierConstants.RESPONSE_SUCCESS_CD);
		status.setResponseStatusDescription(CarrierConstants.RESPONSE_SUCCESS_DESC);
		return status;
		
	}

	public Status getFailureStatus(){
		Status status = new Status();
		status.setResponseStatusCode(CarrierConstants.RESPONSE_FAIL_CD);
		status.setResponseStatusDescription(CarrierConstants.RESPONSE_FAIL_DESC);
		return status;
		
	}

	public com.cts.cip.common.model.Status getComStatus(CarrierClientException cce) {
		com.cts.cip.common.model.Status status = new com.cts.cip.common.model.Status();
		status.setResponseStatusCode(CarrierConstants.RESPONSE_FAIL_CD);
		status.setResponseStatusDescription(CarrierConstants.RESPONSE_FAIL_DESC);

		com.cts.cip.common.model.Error errorDetails = new com.cts.cip.common.model.Error();
		errorDetails.setErrorCode(cce.getErrorCd());
		errorDetails.setErrorDescription(cce.getErrorDesc());

		status.getErrors().add(errorDetails);

		return status;
	}
	
	
}

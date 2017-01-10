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
package com.cts.cip.core.utilities;

import java.util.Date;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Util {
	
	static Logger logger = LoggerFactory.getLogger(Util.class);
	
	static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 0x01b21dd213814000L;
	
	static final String SHIPMENT = "Shipment";
	
	static final String LOAD = "Load";
	
	static final String XML = "xml";
	
	static final String PUT = "PUT";
	
	static final String GET ="GET";
	
	/**
	 * @param uuidString
	 * @return
	 */
	public static String getDateStringFromUuid(String uuidString) {	
		
		UUID uuid = UUID.fromString(uuidString);
		long timeStamp = (uuid.timestamp() - NUM_100NS_INTERVALS_SINCE_UUID_EPOCH) / 10000;
		Date date =  new Date(timeStamp);
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM-dd-yyyy");
		return dateFormatter.format(date);	
		
	}

	/**
	 * @return
	 */
	public static String generateSessionId() {
		return IdGenerator.generateId();		
	}
	
	/**
	 * @param id
	 * @param fileType
	 * @param transactionType
	 * @return
	 */
	public static String getSessionFileName (String id,String fileType,String transactionType) {
		String dateFolder = Util.getDateStringFromUuid(id);		
		String fileName = id;
		String filePrefix="_";	
		fileName = dateFolder+"/"+fileName + filePrefix + transactionType + "." + fileType;
		return  fileName;		
	}	
	
	/**
	 * @param filePath
	 * @param fileContent
	 * @throws IOException
	 */
	public static void writeToFile(String filePath, String fileContent) throws IOException {
		if (fileContent != null && fileContent.trim() != "") {
			File file = new File(filePath);
			if (file.getParentFile() != null) {
				file.getParentFile().mkdirs();
			}

			FileUtils.writeStringToFile(file, fileContent);
		}
	}

	/**
	 * @param methodType
	 * @param resourcePath
	 * @return
	 */
	public static String parseEvent(String methodType,String resourcePath) {
		logger.debug("Parse Event:" + methodType + "-" + resourcePath );
		String eventOrigin = SHIPMENT;
		String eventType= "Create";
		if (resourcePath.contains("/load")) {
			eventOrigin = LOAD;
			eventType = LOAD;
		} else if (resourcePath.contains("/unload")) {
			eventOrigin = LOAD;
			eventType = "UnLoad";
		} else if (resourcePath.contains("/Manifest")) {
			eventOrigin = LOAD;
			eventType = "Manifest";
		} else if (methodType.equalsIgnoreCase(PUT) && resourcePath.contains("/Manifest")) {
			eventType = "Update";
		} else if (methodType.equalsIgnoreCase(PUT) && resourcePath.contains("shippingunit")) {
			eventType = "Cancel";
		} else if(methodType.equalsIgnoreCase(GET) && resourcePath.contains("getDocuments")) {
			eventType ="Reprint";					
		}		
		
		return eventOrigin+"-"+eventType;
	}
	

	/**
	 * @param payLoad
	 * @param contentType
	 * @param eventOrigin
	 * @return
	 */
	public static String getReferenceId(String payLoad,String contentType,String eventOrigin) {
		logger.debug("--" + contentType +  " --" + eventOrigin);		
		if (contentType.equalsIgnoreCase(XML)) {
			return getRefIdFromXMl(payLoad,eventOrigin);
		} else {
			return getRefIdFromJson(payLoad,eventOrigin);
		}		
	}
	
	private static String getRefIdFromJson(String payLoad,String eventOrigin)  {
		String id="";
		try {
		Object document = Configuration.defaultConfiguration().jsonProvider().parse(payLoad);
		String expression ="";
		if (eventOrigin.equalsIgnoreCase(LOAD)) {
			expression = "$.load.loadReferenceId";
		} else if (eventOrigin.equalsIgnoreCase(SHIPMENT)) {
			expression = "$.shippingUnit.shippingUnitBase.referenceID";
		}		
			id = JsonPath.read(document, expression);		
		} catch( Exception e) {
			logger.error("" + e.getMessage(), e);
			
			id="NA";
		}
		return id;
	
	}

	private static String getRefIdFromXMl(String payLoad,String eventOrigin) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		String result="";
		try {
			builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(new ByteArrayInputStream(payLoad.getBytes()));
			XPath xPath = XPathFactory.newInstance().newXPath();
			String expression ="";
			if (eventOrigin.equalsIgnoreCase(LOAD)) {
				expression = "/LoadShippingUnitRequest/load/loadReferenceId";
			} else if (eventOrigin.equalsIgnoreCase(SHIPMENT)) {
				expression = "/createShippingUnitRequest/shippingUnit/shippingUnitBase/referenceID";
			}
			logger.debug("Ex" + expression);
			result = xPath.compile(expression).evaluate(xmlDocument);
		} catch (ParserConfigurationException | XPathExpressionException | SAXException | IOException e) {			
			logger.error(e.getMessage(), e);
		}		
		return result;
		
	}

}

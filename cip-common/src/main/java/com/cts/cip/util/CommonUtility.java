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
package com.cts.cip.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common Utility
 * @author 417765
 *
 */

public final class CommonUtility {

	static Logger logger = LoggerFactory.getLogger(CommonUtility.class);
	
	
	/** To load properties
	 * @param propertiesPath
	 * @return
	 * @throws IOException
	 */
	public static Properties loadProperties (String propertiesPath) throws IOException 
	{
		Properties properties = new Properties();
		InputStream input = new FileInputStream(propertiesPath);
		properties.load(input);
		input.close();
		return properties;
	}
	
	/** To get base 64 encoding string
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String getBase64EncodedString(String path) throws IOException {
		String encodedImg;
		FileInputStream imageInFile = new FileInputStream(path);
        byte[] imageData = IOUtils.toByteArray(imageInFile);
        encodedImg = Base64.getEncoder().encodeToString(imageData);
		return encodedImg;
	}
	
	/** To get file name
	 * @param url
	 * @return
	 */
	public static String getFileNameFromUrl(String url) {	
		String fileName="";	
		String[] tokens = url.split("/");
		if (tokens!=null) {
			fileName = tokens[tokens.length-1];
		}	 
		return fileName;
	}

	/** To get URL from file name
	 * @param fileName
	 * @param propertyFileName
	 * @return
	 */
	public static String getUrlFromFileName(String fileName,String propertyFileName) {	
		logger.debug("getUrlFromFileName:" + fileName + "-" + propertyFileName);
		Properties properties = new Properties();
		String iconUrl="";
		try {
			InputStream inputStream = CommonUtility.class.getResourceAsStream(propertyFileName);
			if (inputStream == null)
				throw new IOException(
						propertyFileName + "- properties file path is not found.");
			properties.load(inputStream);
			inputStream.close();
			logger.debug("cip master properties has been loaded." + properties);
			iconUrl = properties.getProperty("images.server.url"); 
			iconUrl = iconUrl+fileName;
			logger.debug("iconUrl:" + iconUrl);
			
		} catch (IOException e) {
			logger.error("Exception occured while reading the Properties file in " + CommonUtility.class.getName() + "::" + e);			
		}
				
		return iconUrl;
	}
	
	public static String getPropertyValue(String key,String propertyFileName) {
		Properties properties = new Properties();
		String value ="";
		try {
			
			InputStream inputStream = CommonUtility.class.getResourceAsStream(propertyFileName);
			if (inputStream == null)
				throw new IOException(
						propertyFileName + "- properties file path is not found.");
			properties.load(inputStream);
			inputStream.close();
			value = properties.getProperty(key);
			
		} catch (IOException e) {
			logger.error("Exception occured while reading the Properties file in " + CommonUtility.class.getName() + "::" + e);			
		}
			
		return value; 
	}
	
	
}	

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
package com.cts.cip.doc.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * @author 417765
 *
 */
public class CipDocUtils {
	
	public static Properties loadAndGetProperties(String path) throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = CipDocUtils.class.getResourceAsStream(path);
		if(inputStream == null)
			throw new IOException(path+"- properties file path is not found.");
		properties.load(inputStream);		
		return properties;
	}
	/**
	 * This method gives the resource absolute path.
	 * @param path
	 * @return String 
	 * @throws URISyntaxException 
	 */
	public static String getResourceAbsolutePath (String path) throws URISyntaxException {
		String absPath = null;
		if(!new File(path).isAbsolute()) {
			URL url  = CipDocUtils.class.getResource(path);
			absPath = url.toURI().getPath();			
		} else {
			absPath = path;			
		}
		return absPath;
	}
}

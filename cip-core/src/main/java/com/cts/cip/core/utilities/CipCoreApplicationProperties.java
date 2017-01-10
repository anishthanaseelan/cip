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
package com.cts.cip.core.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.core.bo.services.CarrierAgentService;
import com.cts.cip.core.repo.constants.CIPCoreConstants;

/**
 * @author 417765
 *
 */
public class CipCoreApplicationProperties {

	Logger logger = LoggerFactory.getLogger(CarrierAgentService.class);

	public Properties getProperties() throws IOException {
		Properties agentProperties = new Properties();
		try {
			InputStream inputStream = this.getClass().getResourceAsStream(CIPCoreConstants.CIP_CORE_PROPERTY_FILE_PATH);
			if (inputStream == null)
				throw new IOException(
						CIPCoreConstants.CIP_CORE_PROPERTY_FILE_PATH + "- properties file path is not found.");
			agentProperties.load(inputStream);
			logger.info("cip core properties has been loaded." + agentProperties);
		} catch (IOException e) {
			logger.error(
					"Exception occured while reading the Properties file in " + this.getClass().getName() + "::" + e);
			throw e;
		}
		return agentProperties;
	}

}

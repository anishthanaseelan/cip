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
package com.cts.cip.manager.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.Api;

@Path("/cipManager")
@Api(value = "/cipManager", description = "CIP manager service")
public class CIPManagerResource {
	
	Logger logger = LoggerFactory.getLogger(CIPManagerResource.class);
	
	@GET
	@Path("/heartbeat")
	public Response heartbeat() {
		return Response.ok().build();	
	}

	@GET
	@Path("/getSwaggerDefinitions")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getSwaggerDefnFromProperties() {
		Properties properties = null;
		
		try {
			properties = getProperties();
		} catch (IOException e) {
			logger.info("cip manager properties not loaded."+e.getMessage());
		}
		
		List<String> swaggerDefnsList = new ArrayList<String>();
		
		if (properties != null) {
			Set<Object> keysSet = properties.keySet();
			for (Object keyObj : keysSet) {
				if (keyObj != null) {
					String key = (String ) keyObj;
					if (key.contains("swagger")) {
						logger.info(" swagger defns -- >"+properties.getProperty(key));
						swaggerDefnsList.add(properties.getProperty(key));
					}
				}
			}
		}
		
		GenericEntity<List<String>> genericList = new GenericEntity<List<String>>(swaggerDefnsList) {
		};
		
		return Response.ok(genericList).build();	
	}
	
	
	private Properties getProperties() throws IOException {
		Properties agentProperties = new Properties();
		try {
			InputStream inputStream = this.getClass().getResourceAsStream("/properties/cip-manager.properties");
			if (inputStream == null)
				throw new IOException(
						"/properties/cip-manager.properties" + "- properties file path is not found.");
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

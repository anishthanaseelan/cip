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
package com.cts.cip.core.resources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.cip.common.model.TransactionLogInfo;
import com.cts.cip.core.bo.services.CommonService;
import com.cts.cip.core.utilities.CipCoreApplicationProperties;
import com.cts.cip.core.utilities.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * @author 
 *
 */
public class CustomLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
	@Autowired
	CommonService commonService;

	Logger logger = LoggerFactory.getLogger(CustomLoggingFilter.class);
	
	static final String PUT = "PUT";
	static final String POST = "POST";

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		String methodType = requestContext.getMethod();
		String contentType = requestContext.getHeaderString("content-type");
		String resourcePath = requestContext.getUriInfo().getPath();
		logger.debug("Capturing the incoming requeste for transaction log : " + methodType +"-" + contentType+ "-" + resourcePath );
		
		if ((methodType.equalsIgnoreCase(PUT) || methodType.equalsIgnoreCase(POST)) || resourcePath.contains("getDocuments") && !resourcePath.contains("loadedShipUnits")) {
		
				String[] eventInfo = Util.parseEvent(methodType, resourcePath).split("-");
				Properties properties;
				properties = new CipCoreApplicationProperties().getProperties();
				String baseFileName = properties.getProperty("document.transaction.path");
				String transactionId = Util.generateSessionId();				

				StringBuilder responseBuilder = new StringBuilder();
				responseBuilder.append(getEntityBody(requestContext));

				String fileType = "json";
				if (contentType==null) {
					contentType = "json";
				}
				if (contentType.contains("xml")) {
					fileType = "xml";
				}
				
				requestContext.getHeaders().add("transactionId", transactionId);
				String referenceId = "";
				if(!methodType.equalsIgnoreCase(PUT)) {					
					 referenceId = Util.getReferenceId(responseBuilder.toString(), fileType, eventInfo[0]);
					if (referenceId.length()>40) {
					referenceId = referenceId.substring(0,40);	
					}
				} else {
					if (StringUtils.isNotBlank(resourcePath)) {
						String [] tokens = resourcePath.split("/");
						if (tokens!=null && tokens.length>1){
							referenceId = tokens[tokens.length-1];
						}
					} else {
						logger.debug("No valid reference id found in the request url");
						return;
					}
				}
				
				String fileName = Util.getSessionFileName(transactionId, fileType, "request");
				fileName = baseFileName + '/' + fileName;
				Util.writeToFile(fileName, responseBuilder.toString());

				TransactionLogInfo transactionLogInfo = new TransactionLogInfo();
				transactionLogInfo.setFileType(fileType);
				transactionLogInfo.setEventOrigin(eventInfo[0]);
				transactionLogInfo.setEventType(eventInfo[1]);
				transactionLogInfo.setTransactionId(transactionId);
				transactionLogInfo.setReferenceId(referenceId);
				commonService.saveTransaction(transactionLogInfo);
			
		}

	}

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerResponseFilter#filter(javax.ws.rs.container.ContainerRequestContext, javax.ws.rs.container.ContainerResponseContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		
		String methodType = requestContext.getMethod();
		String contentType = requestContext.getHeaderString("content-type");
		String resourcePath = requestContext.getUriInfo().getPath();
		logger.debug("Capturing the outgoing resposne for transaction log : " + methodType +"-" + contentType+ "-" + resourcePath );
		if ((methodType.equalsIgnoreCase(PUT) || methodType.equalsIgnoreCase(POST)) && !resourcePath.contains("loadedShipUnits")) {
			
				String response = "";
				Object entity = responseContext.getEntity();

				String transactionId = "";
				List<Object> headerInfo = responseContext.getHeaders().get("transactionId");
				if (headerInfo != null && !headerInfo.isEmpty()) {
					transactionId = headerInfo.get(0).toString();
				}
				logger.debug("Transaction id: " + transactionId);
				Properties properties;
 				properties = new CipCoreApplicationProperties().getProperties();
				String baseFileName = properties.getProperty("document.transaction.path");
				if (transactionId == null || transactionId.isEmpty()) {
					transactionId = Util.generateSessionId();
				}
				String fileType = "json";

				if (contentType != null && contentType != "") {						
					if (contentType.contains("xml")) {
						fileType = "xml";
						ObjectMapper xmlMapper = new XmlMapper();
						response = xmlMapper.writeValueAsString(entity);
					} else {
						ObjectMapper mapper = new ObjectMapper();
						mapper.enable(SerializationFeature.INDENT_OUTPUT);
						response = mapper.writeValueAsString(entity);
					}
					
					String fileName = Util.getSessionFileName(transactionId, fileType, "response");
					fileName = baseFileName + '/' + fileName;
					Util.writeToFile(fileName, response);
				}
				responseContext.setEntity(entity);
				logger.debug("HTTP RESPONSE : " + response);
			}		
	}

	private String getEntityBody(ContainerRequestContext requestContext) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = requestContext.getEntityStream();

		final StringBuilder b = new StringBuilder();

		ReaderWriter.writeTo(in, out);

		byte[] requestEntity = out.toByteArray();
		if (requestEntity.length == 0) {
			b.append("").append("\n");
		} else {
			b.append(new String(requestEntity)).append("\n");
		}
		requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));

		return b.toString();
	}

	
}
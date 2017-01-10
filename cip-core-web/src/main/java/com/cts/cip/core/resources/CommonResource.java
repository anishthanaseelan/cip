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

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.TransactionLogInfo;
import com.cts.cip.common.model.TransactionLogResponse;
import com.cts.cip.common.response.CIPResponseBuilder;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ResponseStatus;
import com.cts.cip.core.bo.CommonBO;
import com.cts.cip.core.bo.services.CommonService;

import io.swagger.annotations.Api;

/**
 * @author 
 *
 */
@Path("/common")
@Api(value = "/common", description = "CIP common service")
@Component
public class CommonResource {
	
	@Autowired
	CommonBO commonBO;
	
	Logger logger = LoggerFactory.getLogger(CommonResource.class);

	
	/**
	 * @param headers
	 * @param referenceId
	 * @return
	 */
	@GET
	@Path("/{referenceId}")
	@Produces({ "application/xml", "application/json" })
	public Response getShippingUnit(@Context HttpHeaders headers, @PathParam("referenceId") String referenceId) {
		Response response = null;
		TransactionLogResponse transactionLogResponse = new TransactionLogResponse();		
		List<TransactionLogInfo> transLogInfoList = null;
		try {
			transLogInfoList = commonBO.findTransactionsById(referenceId);
			if (transLogInfoList==null ||transLogInfoList.isEmpty()) {
				response = Response.noContent().build();
			} else {
				transactionLogResponse.setTransactionLogList(transLogInfoList);			
				transactionLogResponse.setTotalTransactions(transLogInfoList.size());	
				response = Response.ok(transactionLogResponse).build();
			}
			logger.info("Completed processing the getShippingUnit () ");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			transactionLogResponse.setErrorDesc(e.getMessage());
			transactionLogResponse.setStatus("FAILURE");
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
			logger.error(e.getMessage(), e);
		}		
		
		return response;
	}
}
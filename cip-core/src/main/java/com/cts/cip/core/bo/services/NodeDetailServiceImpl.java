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
package com.cts.cip.core.bo.services;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.dto.NodeCarrierInfo;
import com.cts.cip.common.dto.NodeCarrierServiceInfo;
import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.exceptions.NodeServiceFailed;
import com.cts.cip.common.model.CarrierDetails;
import com.cts.cip.core.repo.constants.CIPCoreConstants;
import com.cts.cip.core.utilities.CipCoreApplicationProperties;
import com.cts.cip.util.ValidationResult;

public class NodeDetailServiceImpl implements NodeDetailService {
	
	com.cts.cip.core.utilities.CarrierDetail CarrierDetail;
	
	public com.cts.cip.core.utilities.CarrierDetail getCarrierDetail() {
		return CarrierDetail;
	}

	public void setCarrierDetail(com.cts.cip.core.utilities.CarrierDetail carrierDetail) {
		CarrierDetail = carrierDetail;
	}

	Logger logger = LoggerFactory.getLogger(NodeDetailServiceImpl.class);

	private ValidationResult isActiveService(String carrierServiceCode, NodeDetail nodeDetail) {

		ValidationResult validationResult = new ValidationResult();
		List<NodeCarrierInfo> nodeCarrierInfos = nodeDetail.getNodeCarrierInfoList();

		searchCarrierService: for (NodeCarrierInfo nodeCarrierInfo : nodeCarrierInfos) {
			for (NodeCarrierServiceInfo nodeCarrierServiceInfo : nodeCarrierInfo.getCarrierServiceInfoList()) {
				if (nodeCarrierServiceInfo.getCarrierServiceId().equals(carrierServiceCode)) {
					if (nodeCarrierServiceInfo.isActive()) {
						//if(!carrierDetails.getCarrierDetailsMap().containsKey(nodeCarrierInfo.getCarrierName())){
							if(!CarrierDetail.getServiceDetailsMap().containsKey(nodeCarrierServiceInfo.getCarrierServiceId())){					
							CarrierDetails cd = new CarrierDetails();
							cd.setNodeId(nodeCarrierInfo.getId());
							cd.setCarrierName(nodeCarrierInfo.getCarrierName());
							cd.setAccountID(nodeCarrierServiceInfo.getAccountNumber());
							cd.setServiceID(carrierServiceCode);
							CarrierDetail.getServiceDetailsMap().put(carrierServiceCode, cd);
							
							if(!CarrierDetail.getCarrierDetailsMap().containsKey(nodeCarrierInfo.getCarrierName()))
								CarrierDetail.getCarrierDetailsMap().put(nodeCarrierInfo.getCarrierName(), cd);
							
							logger.debug("NodeDetailService CarrierDetailsMap : "+cd.getAccountID()  );
						}
						
						validationResult.setStatus(true);
						validationResult.setCode("00");
						validationResult.setDescription("The carrier service " + carrierServiceCode
								+ "is not activated for the node " + nodeDetail.getId());
						break searchCarrierService;
					} else {
						validationResult.setStatus(false);
						validationResult.setCode("03");
						validationResult.setDescription("The carrier Service " + carrierServiceCode
								+ " is not activated for the node " + nodeDetail.getId());
						break searchCarrierService;
					}
				}
			}
		}

		/*
		 * If the service is found and active the code will be 00. If inactive
		 * the code will be 03. If the code is nether 00 or 03 then the carrier
		 * service is not found
		 */
		if (validationResult.getCode() == null || validationResult.getCode().equalsIgnoreCase("")) {
			validationResult.setStatus(false);
			validationResult.setCode("04");
			validationResult.setDescription(
					"The carrier Service " + carrierServiceCode + "  is  not found for the node " + nodeDetail.getId());
		}
		return validationResult;

	}

	public ValidationResult isActiveNodeService(int nodeId, String serviceCode) throws NodeServiceFailed {
		NodeDetail nodeDetail = null;
		ValidationResult validationResult = new ValidationResult();
		Properties properties = null;
		String resource;
		Response response;

		try {
			properties = new CipCoreApplicationProperties().getProperties();
		} catch (IOException e) {
			logger.error("CipCoreApplicationProperties : Throws an inhandled exception" , e);
			throw new NodeServiceFailed ( "CipCoreApplicationProperties : Throws an inhandled exception " + e.getMessage());
		}

		resource = properties.getProperty(CIPCoreConstants.MASTER_SERVICE_URL) + nodeId;
		
		logger.debug("master service url is:" + resource);
		
		Client restClient = ClientBuilder.newClient();
		WebTarget childWebTarget = restClient.target(resource);
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_JSON);
		response = invocationBuilder.get();

		if (response.getStatus() == 200) {
			nodeDetail = response.readEntity(NodeDetail.class);
		}

		if (nodeDetail != null) {
			if (nodeDetail.isNodeStatusActive()) {
				validationResult = isActiveService(serviceCode, nodeDetail);
			} else {
				validationResult.setStatus(false);
				validationResult.setCode("02");
				validationResult.setDescription("Node found but not active");
			}
		} else {
			validationResult.setStatus(false);
			validationResult.setCode("01");
			validationResult.setDescription("Node is not found");

		}
		response.close();

		return validationResult;
	}
}
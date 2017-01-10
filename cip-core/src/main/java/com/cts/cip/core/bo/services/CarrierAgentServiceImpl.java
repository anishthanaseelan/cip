/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (CWW)
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
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (CWW)
 *******************************************************************************/
package com.cts.cip.core.bo.services;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.agent.model.CancelRequest;
import com.cts.cip.agent.model.CancelResponse;
import com.cts.cip.agent.model.CreateRequest;
import com.cts.cip.agent.model.CreateResponse;
import com.cts.cip.agent.model.ManifestRequest;
import com.cts.cip.agent.model.ManifestResponse;
import com.cts.cip.common.exceptions.CarrierAgentException;
import com.cts.cip.common.exceptions.CarrierAgentFailure;
import com.cts.cip.common.model.CancelShippingUnitResponse;
import com.cts.cip.common.model.CreateShippingUnitResponse;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.tracker.model.ShipmentTrackRequest;
import com.cts.cip.common.tracker.model.TrackResponseContainer;
import com.cts.cip.core.bo.services.mapper.AgentServiceRequestMapper;
import com.cts.cip.core.bo.services.mapper.AgentServiceResponseMapper;
import com.cts.cip.core.repo.constants.CIPCoreConstants;
import com.cts.cip.core.utilities.CipCoreApplicationProperties;

public class CarrierAgentServiceImpl implements CarrierAgentService {
	Logger logger = LoggerFactory.getLogger(CarrierAgentServiceImpl.class);

	Properties agentProperties;
	
	AgentServiceResponseMapper agentServiceResponseMapper;

	AgentServiceRequestMapper agentServiceRequestMapper;
	
	public AgentServiceResponseMapper getAgentServiceResponseMapper() {
		return agentServiceResponseMapper;
	}

	public void setAgentServiceResponseMapper(AgentServiceResponseMapper agentServiceResponseMapper) {
		this.agentServiceResponseMapper = agentServiceResponseMapper;
	}

	public AgentServiceRequestMapper getAgentServiceRequestMapper() {
		return agentServiceRequestMapper;
	}

	public void setAgentServiceRequestMapper(AgentServiceRequestMapper agentServiceRequestMapper) {
		this.agentServiceRequestMapper = agentServiceRequestMapper;
	}

	public CarrierAgentServiceImpl() throws CarrierAgentFailure {
		super();
		try {
			agentProperties = new CipCoreApplicationProperties().getProperties();
		} catch (IOException e) {
			logger.error(" Exception occured while loading cip-core properties..",e);
			throw new CarrierAgentFailure(" Agent service got Processing Exception " + e.getMessage());
		}
	}

	public ShippingUnit generateTrackingNumber(ShippingUnit shippingUnitReq)
			throws CarrierAgentException, CarrierAgentFailure {
		//TOBE-RE
		CreateRequest request = agentServiceRequestMapper.mapToCreateRequest(shippingUnitReq);
		CreateResponse response = contactAgentForCreate(request);
		CreateShippingUnitResponse shippingUnitRes = agentServiceResponseMapper.mapFromCreateResponse(response, shippingUnitReq);
		if ("0".equals(response.getStatus().getResponseStatusCode())) {
			logger.error(" Carrier Agent failed to Create Tracking number with exception "
					+ shippingUnitRes.getStatus().getErrors().get(0).getErrorDescription());
			throw new CarrierAgentException(shippingUnitRes.getStatus().getErrors().get(0).getErrorDescription());
		}
		if (shippingUnitRes.getDocuments() == null) {
			throw new CarrierAgentFailure(" Didn't get any response back from Agent ");
		}
		//shippingUnitReq.setBase64LabelContent(shippingUnitRes.getDocuments().get(0).getBase64Content().getBytes());
		
		
		
		shippingUnitReq.setTrackingNumber(shippingUnitRes.getTrackingNumber());
		
		shippingUnitReq.setPostalBarCode(shippingUnitRes.getShippingUnit().getPostalBarCode());
		shippingUnitReq.setMaxiCode(shippingUnitRes.getShippingUnit().getMaxiCode());
		shippingUnitReq.setRoutingCode(shippingUnitRes.getShippingUnit().getRoutingCode());
		shippingUnitReq.setVersion(shippingUnitRes.getShippingUnit().getVersion());
		shippingUnitReq.setVersionDate(shippingUnitRes.getShippingUnit().getVersionDate());
		shippingUnitReq.setTrackingNumber(shippingUnitRes.getTrackingNumber());
		
		shippingUnitReq.setPackageWeight(shippingUnitRes.getShippingUnit().getPackageWeight());
		shippingUnitReq.setPackageWeightUOM(shippingUnitRes.getShippingUnit().getPackageWeightUOM());
		shippingUnitReq.setIsMaxiCodeReqd(shippingUnitRes.getShippingUnit().isMaxiCodeReqd());
		shippingUnitReq.setLabelParameters(shippingUnitRes.getShippingUnit().getLabelParameters());
		shippingUnitReq.setServiceName(shippingUnitRes.getShippingUnit().getServiceName());
		shippingUnitReq.setServiceIcon_p1(shippingUnitRes.getShippingUnit().getServiceIcon_p1());
		shippingUnitReq.setServiceIcon_p2(shippingUnitRes.getShippingUnit().getServiceIcon_p2());
		shippingUnitReq.setServiceIcon_p3(shippingUnitRes.getShippingUnit().getServiceIcon_p3());
		
		shippingUnitReq.setPropertyOne(shippingUnitRes.getShippingUnit().getPropertyOne());
		shippingUnitReq.setPropertyTwo(shippingUnitRes.getShippingUnit().getPropertyTwo());
		shippingUnitReq.setPropertyThree(shippingUnitRes.getShippingUnit().getPropertyThree());
		shippingUnitReq.setPropertyFour(shippingUnitRes.getShippingUnit().getPropertyFour());
		shippingUnitReq.setPropertyFive(shippingUnitRes.getShippingUnit().getPropertyFive());
		shippingUnitReq.setPropertySix(shippingUnitRes.getShippingUnit().getPropertySix());
		shippingUnitReq.setPropertySeven(shippingUnitRes.getShippingUnit().getPropertySeven());
		shippingUnitReq.setPropertyEight(shippingUnitRes.getShippingUnit().getPropertyEight());
		shippingUnitReq.setPropertyNine(shippingUnitRes.getShippingUnit().getPropertyNine());
		shippingUnitReq.setPropertyTen(shippingUnitRes.getShippingUnit().getPropertyTen());
		
		
		
		//shippingUnitReq.getLabelParamMap().putAll(shippingUnitRes.getShippingUnit().getLabelParamMap());
		
		
		return shippingUnitReq;

	}

	private CreateResponse contactAgentForCreate(CreateRequest request) throws CarrierAgentFailure {

		Response response = null;
		CreateResponse createResponse;
		WebTarget childWebTarget = null;
		try {
			Client restClient = ClientBuilder.newClient();
			String agentCreateUrl = agentProperties.getProperty(CIPCoreConstants.AGENT_CREATE_URL);
			logger.debug("Agent Create URL" + agentCreateUrl);
			childWebTarget = restClient.target(agentCreateUrl);
			Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_XML);
			response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_XML));
		} catch (ProcessingException e) {
			logger.error( e.getMessage() , e );
			throw new CarrierAgentFailure(" Agent service got Processing Exception " + e.getMessage());
		}

		if (response.getStatus() == 200) {
			createResponse = response.readEntity(CreateResponse.class);
		} else {
			response.close();
			
			throw new CarrierAgentFailure(" Agent service failed ");
		}

		return createResponse;

	}

	private CancelResponse contactAgentForCancel(CancelRequest request) throws CarrierAgentFailure {
		CancelResponse cancelResponse;
		Client restClient = ClientBuilder.newClient();
		String agentCancelUrl = agentProperties.getProperty(CIPCoreConstants.AGENT_CANCEL_URL);
		logger.debug("Agent Cancel URL" + agentCancelUrl);

		WebTarget childWebTarget = restClient.target(agentCancelUrl);
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_XML));

		if (response.getStatus() == 200) {
			cancelResponse = response.readEntity(CancelResponse.class);
		} else {
			response.close();
			throw new CarrierAgentFailure(" Agent service failed ");
		}
		response.close();

		return cancelResponse;

	}

	public ManifestResponse contactAgentForManifest(ManifestRequest manifestRequest) throws CarrierAgentFailure {
		
		//TOBE UNCOMMENTED
		ManifestResponse manifestResponse;
		Client restClient = ClientBuilder.newClient();
		String agentManifestUrl = agentProperties.getProperty(CIPCoreConstants.AGENT_MANIFEST_URL);
		logger.debug("Agent Manifest URL" + agentManifestUrl);

		WebTarget childWebTarget = restClient.target(agentManifestUrl);
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.post(Entity.entity(manifestRequest, MediaType.APPLICATION_XML));

		if (response.getStatus() == 200) {
			manifestResponse = response.readEntity(ManifestResponse.class);
		} else {
			response.close();
			throw new CarrierAgentFailure(" Agent service failed ");
		}
		response.close();
		//TOBE REMOVED LATER
		/*PackageStatus packageStatus1 = new PackageStatus();
		PackageInfo packInfo1 = new PackageInfo(); 
		packInfo1.setPackageID("100000002072");
		packageStatus1.setPackageInfo(packInfo1);
		
		PackageStatus packageStatus2 = new PackageStatus();
		PackageInfo packInfo2 = new PackageInfo(); 
		packInfo2.setPackageID("100000002073");
		packageStatus2.setPackageInfo(packInfo2);
		
		PackageStatus packageStatus3 = new PackageStatus();
		PackageInfo packInfo3 = new PackageInfo(); 
		packInfo3.setPackageID("100000002065");
		packageStatus3.setPackageInfo(packInfo3);
		
		
		Status packStat1 = new Status();
		packStat1.setResponseStatusCode("0");
		ErrorDetails errorDetail1 = new ErrorDetails();
		errorDetail1.setErrorCode("001");
		errorDetail1.setErrorDescription("Failed");
		errorDetail1.setErrorSeverity("");
		List<ErrorDetails> errorDetails1 = new ArrayList<ErrorDetails>();
		errorDetails1.add(errorDetail1);
		packStat1.setErrors(errorDetails1);
		packageStatus1.setStatus(packStat1);
		
		
		Status packStat2 = new Status();
		packStat2.setResponseStatusCode("0");
		ErrorDetails errorDetail2 = new ErrorDetails();
		errorDetail2.setErrorCode("001");
		errorDetail2.setErrorDescription("Failed");
		errorDetail2.setErrorSeverity("");
		List<ErrorDetails> errorDetails2 = new ArrayList<ErrorDetails>();
		errorDetails2.add(errorDetail1);
		packStat2.setErrors(errorDetails2);
		packageStatus2.setStatus(packStat2);
		
		Status packStat3 = new Status();
		packStat3.setResponseStatusCode("0");
		ErrorDetails errorDetail3 = new ErrorDetails();
		errorDetail3.setErrorCode("001");
		errorDetail3.setErrorDescription("Failed");
		errorDetail3.setErrorSeverity("");
		List<ErrorDetails> errorDetails3 = new ArrayList<ErrorDetails>();
		errorDetails3.add(errorDetail3);
		packStat3.setErrors(errorDetails3);
		packageStatus3.setStatus(packStat3);
		
		List<PackageStatus> packageStatusList = new ArrayList<PackageStatus>(0);
		packageStatusList.add(packageStatus1);
		packageStatusList.add(packageStatus2);
		packageStatusList.add(packageStatus3);
		
		ManifestResponse manifestResponse = new ManifestResponse();
		manifestResponse.setPackageStatusList(packageStatusList);
		
		Status topLevelRes = new Status();
		
		topLevelRes.setResponseStatusCode("1");
		topLevelRes.setResponseStatusDescription("SUCCESS.");
		
		topLevelRes.setResponseStatusCode("0");
		topLevelRes.setResponseStatusDescription("FAILURE");
		manifestResponse.setStatus(topLevelRes);
		manifestResponse.setResponseTransactionID("123456");*/
		
		return manifestResponse;

	}

	public void cancelShippingUnit(ShippingUnit shippingUnit) throws CarrierAgentFailure, CarrierAgentException {
		CancelRequest request = agentServiceRequestMapper.mapToCancelRequest(shippingUnit);
		CancelResponse response = contactAgentForCancel(request);
		CancelShippingUnitResponse cancelRes = agentServiceResponseMapper.mapFromCancelResponse(response);
		if ("0".equals(response.getStatus().getResponseStatusCode())) {
			throw new CarrierAgentException(" Carrier Agent failed to cancel Tracking number with exception "
					+ cancelRes.getStatus().getErrors());
		}
		return;
	}
	/**
	 * 
	 * @param activeLoad
	 * @return
	 * @throws CarrierAgentFailure
	 * @throws CarrierAgentException
	 */
	public ManifestShippingUnitsResponse manifestLoad(Load activeLoad) throws CarrierAgentFailure, CarrierAgentException {
		
		ManifestRequest request = agentServiceRequestMapper.mapToManifestRequest(activeLoad);
		ManifestResponse response = contactAgentForManifest(request);
		
		return agentServiceResponseMapper.mapFromManifestResponse(response); 
	}

	@Override
	public TrackResponseContainer getTrackingDetails(ShipmentTrackRequest trackRequest)
			throws CarrierAgentException, CarrierAgentFailure {
		
		trackRequest.setCarrier(trackRequest.getCarrier()+"Tracking");
		
		Response response = null;
		TrackResponseContainer trackResponse = null;
		WebTarget childWebTarget = null;
		try {
			Client restClient = ClientBuilder.newClient();
			String agentTrackUrl = agentProperties.getProperty(CIPCoreConstants.AGENT_TRACK_URL);
			logger.debug("Agent Track URL" + agentTrackUrl);
			childWebTarget = restClient.target(agentTrackUrl);
			Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_XML);
			response = invocationBuilder.post(Entity.entity(trackRequest, MediaType.APPLICATION_XML));
		} catch (ProcessingException e) {
			logger.error( e.getMessage() , e );
			throw new CarrierAgentFailure(" Agent service got Processing Exception " + e.getMessage());
		}

		if (response.getStatus() == 200) {
			trackResponse = response.readEntity(TrackResponseContainer.class);
		} else {
			response.close();
			throw new CarrierAgentFailure(" Agent service failed ");
		}

		return trackResponse;
		
		
		
	}
}

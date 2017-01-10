/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) 
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
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (â€œCWWâ€�)
 *******************************************************************************/
package com.cts.prototype.carrier.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.agent.model.CancelRequest;
import com.cts.cip.agent.model.CancelResponse;
import com.cts.cip.agent.model.CreateRequest;
import com.cts.cip.agent.model.CreateResponse;
import com.cts.cip.agent.model.LoadRequest;
import com.cts.cip.agent.model.LoadResponse;
import com.cts.cip.agent.model.ManifestRequest;
import com.cts.cip.agent.model.ManifestResponse;
import com.cts.cip.agent.model.TrackRequest;
import com.cts.cip.agent.model.TrackResponse;
import com.cts.cip.agent.model.TrackingNumInfo;
import com.cts.cip.agent.model.UnLoadRequest;
import com.cts.cip.agent.model.UnLoadResponse;
import com.cts.cip.carrier.CarrierClient;
import com.cts.cip.common.tracker.model.ShipmentTrackRequest;
import com.cts.cip.common.tracker.model.ShipmentTrackResponse;
import com.cts.cip.utils.CarrierCommonUtils;
import com.cts.cip.agent.model.Status;

public class ModelCarrierAgentServiceImpl implements CarrierClient {
	static Logger logger = LoggerFactory.getLogger(ModelCarrierAgentServiceImpl.class);
	private CarrierCommonUtils commonUtils;
	


	public static final String SAMPLE_TRACKING_NUMBER="1234567890";

	public CreateResponse createShipment(CreateRequest createShipmentRequest) {
		CreateResponse createResponse = new CreateResponse();
		Status status =  commonUtils.getSuccessStatus();
		createResponse.setPackageInfo(createShipmentRequest.getOrderDetails().getPackageInfo());
		createResponse.setServiceType(createShipmentRequest.getShipperDetails().getShipperServiceType().getServiceTypeCode());
		
		//Generate tracking number for the carrier
		logger.debug("Tracking Number : " +SAMPLE_TRACKING_NUMBER );
		createResponse.setTrackingNumber(SAMPLE_TRACKING_NUMBER);
		createResponse.setStatus(status);
		return createResponse;
	}

	public CancelResponse cancelShipment(CancelRequest cancelShipmentRequest) {
		CancelResponse cancelResponse = new CancelResponse();
		List<TrackingNumInfo> trackInfoList = new ArrayList<TrackingNumInfo>();
		TrackingNumInfo trackNumDetails = new TrackingNumInfo();
		trackNumDetails.setTrackingNumber(cancelShipmentRequest.getTrackingNumberList().get(0));
		trackNumDetails.setStatus( commonUtils.getSuccessStatus());
		trackInfoList.add(trackNumDetails);
		cancelResponse.setTrackingNumInfoList(trackInfoList);
		Status status =  commonUtils.getSuccessStatus();
		cancelResponse.setStatus(status);
		return cancelResponse;
	}

	public LoadResponse loadShipment(LoadRequest confirmShipmentRequest) {
		LoadResponse loadResponse = new LoadResponse();
		Status status =  commonUtils.getSuccessStatus();
		loadResponse.setStatus(status);
		return loadResponse;
	}

	public ManifestResponse mainfestTrailer(ManifestRequest manifestTrailerRequest) {
		logger.debug("In Manifest Trailer");
		ManifestResponse manifestResponse = new ManifestResponse();
		Status status =  commonUtils.getSuccessStatus();
		manifestResponse.setStatus(status);
		logger.debug("Agent Manifest Status :" + status.getResponseStatusDescription());
		return manifestResponse;
	}

	public UnLoadResponse unLoadShipment(UnLoadRequest unloadRequest) {
		UnLoadResponse unloadResponse = new UnLoadResponse();
		Status status =  commonUtils.getSuccessStatus();
		unloadResponse.setStatus(status);
		return unloadResponse;
	}

	public TrackResponse trackShipment(TrackRequest trackShipmentRequest) {
		TrackResponse trackResponse = new TrackResponse();
		Status status =  commonUtils.getSuccessStatus();
		trackResponse.setStatus(status);
		return trackResponse;
	}

	public Object handleErrors(Object exception) {
		return null;
	}
	
	public CarrierCommonUtils getCommonUtils() {
		return commonUtils;
	}

	public void setCommonUtils(CarrierCommonUtils commonUtils) {
		this.commonUtils = commonUtils;
	}

	public ShipmentTrackResponse trackShipment(ShipmentTrackRequest trackShipmentRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}

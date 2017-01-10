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
package com.cts.cip.carrier;

import java.util.Arrays;
import java.util.Map;

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
import com.cts.cip.agent.model.UnLoadRequest;
import com.cts.cip.agent.model.UnLoadResponse;
import com.cts.cip.carrier.exception.CarrierClientException;
import com.cts.cip.common.tracker.model.ShipmentTrackRequest;
import com.cts.cip.common.tracker.model.ShipmentTrackResponse;
import com.cts.cip.common.tracker.model.TrackResponseContainer;
import com.cts.cip.utils.CarrierClientLocater;
import com.cts.cip.utils.CarrierCommonUtils;


/**
 * Base Carrier Client Implementation to perform common functions
 * @author FedExAdmin
 *
 */
public class CarrierClientImpl implements CarrierClient{
	
	private static Logger logger  =  LoggerFactory.getLogger(CarrierClientImpl.class);
	private CarrierClientLocater carrierClientLocator;
	private CarrierClient carrierClient;
	private Map<String,Object> carrierClientMap ;
	private CarrierCommonUtils commonUtils;
	
	public CarrierClientLocater getCarrierClientLocator() {
		return carrierClientLocator;
	}

	public void setCarrierClientLocator(CarrierClientLocater carrierClientLocator) {
		this.carrierClientLocator = carrierClientLocator;
	}

	public CarrierClient getCarrierClient() {
		return carrierClient;
	}

	public void setCarrierClient(CarrierClient carrierClient) {
		this.carrierClient = carrierClient;
	}

	public CarrierCommonUtils getCommonUtils() {
		return commonUtils;
	}

	public void setCommonUtils(CarrierCommonUtils commonUtils) {
		this.commonUtils = commonUtils;
	}


	
	public CreateResponse createShipment(CreateRequest createShipmentRequest){

		CreateResponse createShipmentResponse = null;
		
		try{
			String shipType = createShipmentRequest.getShipperDetails().getShipperServiceType().getServiceTypeCode();
			setCarrierClient(shipType);	
			createShipmentResponse =  carrierClient.createShipment(createShipmentRequest);
		}catch(CarrierClientException carrierClientException ){
			logger.error("Exception in creating shipment : " + Arrays.toString(carrierClientException.getStackTrace()));
			createShipmentResponse = new CreateResponse();
			createShipmentResponse.setStatus(commonUtils.getStatus(carrierClientException));
			createShipmentResponse.setPackageInfo(createShipmentRequest.getOrderDetails().getPackageInfo());
		}
		
		return createShipmentResponse;
	}

	/**
	 * @param identifier
	 * @throws CarrierClientException
	 */
	public void setCarrierClient(String identifier) throws CarrierClientException {
		if (null == identifier)
			throw new CarrierClientException(CarrierConstants.CARRIER_MODULE_DATA_ERR_CD,CarrierConstants.CARRIER_SHIP_TYPE_EMPTY);
		
		carrierClient = (CarrierClient)carrierClientMap.get(identifier);
		
		if (null == carrierClient){
			logger.error(CarrierConstants.CARRIER_SHIP_TYPE_INVALID  + " : " + identifier );
			throw new CarrierClientException(CarrierConstants.CARRIER_MODULE_DATA_ERR_CD,CarrierConstants.CARRIER_SHIP_TYPE_INVALID);
		}
			
	}

	public CancelResponse cancelShipment(CancelRequest cancelShipmentRequest)
	{
		logger.info("In Cancel Shipment ...");
		CancelResponse cancelResponse = null;
		
		try{
			String shipType = cancelShipmentRequest.getShipperServiceType().getServiceTypeCode().toLowerCase();
			setCarrierClient(shipType);
			logger.debug("CarrierClient " + carrierClient.getClass() + " for shipType " + shipType );
			cancelResponse = carrierClient.cancelShipment(cancelShipmentRequest);
		}catch(CarrierClientException carrierClientException){
			cancelResponse = new CancelResponse();
			cancelResponse.setStatus(commonUtils.getStatus(carrierClientException));

		}
	
		return cancelResponse;
	}

	public ManifestResponse mainfestTrailer(ManifestRequest manifestTrailerRequest)
	{
		logger.info("In Manifest Trailer Shipment ...");
		ManifestResponse manifestResponse = null;
		try{
			String carrierName = manifestTrailerRequest.getShipperServiceType().getCarrierName().toLowerCase();
			setCarrierClient(carrierName);
			manifestResponse = carrierClient.mainfestTrailer(manifestTrailerRequest);
		}catch(CarrierClientException carrierClientException){
			manifestResponse = new ManifestResponse();
			manifestResponse.setStatus(commonUtils.getStatus(carrierClientException));
		}
		
		return manifestResponse;
	}


	public ShipmentTrackResponse trackShipment(ShipmentTrackRequest trackShipmentRequest)
	{
		logger.info("In Track Shipment Service...");
		ShipmentTrackResponse trackResponse = null;
		try{
			String carrierName = trackShipmentRequest.getCarrier().toLowerCase();
			setCarrierClient(carrierName);
			trackResponse = carrierClient.trackShipment(trackShipmentRequest);
		}catch(CarrierClientException carrierClientException){
			trackResponse = new ShipmentTrackResponse();
			trackResponse.setStatus(commonUtils.getComStatus(carrierClientException));
		}
		
		return trackResponse;
	}

	public Object handleErrors(Object exception) {
		//Check for the standard codes and notify accordingly
		return null;
		
	}

	public LoadResponse loadShipment(LoadRequest confirmShipmentRequest) {
		String shipType = confirmShipmentRequest.getShipperServiceType().getServiceTypeCode();
		//Map<String,Object> carrierClientMap =    carrierClientLocator.getCarrierClientMap();
		carrierClient = (CarrierClient)carrierClientMap.get(shipType);
		LoadResponse loadShipmentResponse = carrierClient.loadShipment(confirmShipmentRequest);
		return loadShipmentResponse;
	}

	public UnLoadResponse unLoadShipment(UnLoadRequest unloadRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void initializeMap(){
		carrierClientMap =    carrierClientLocator.getCarrierClientMap();
	}
}

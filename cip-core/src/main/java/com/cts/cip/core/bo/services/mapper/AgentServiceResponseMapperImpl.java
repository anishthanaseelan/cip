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
package com.cts.cip.core.bo.services.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.cts.cip.agent.model.AgentConstants;
import com.cts.cip.agent.model.CancelResponse;
import com.cts.cip.agent.model.CreateResponse;
import com.cts.cip.agent.model.Document;
import com.cts.cip.agent.model.ErrorDetails;
import com.cts.cip.agent.model.KeyValuePair;
import com.cts.cip.agent.model.LoadResponse;
import com.cts.cip.agent.model.ManifestResponse;
import com.cts.cip.agent.model.PackageStatus;
import com.cts.cip.agent.model.TrackingNumInfo;
import com.cts.cip.agent.model.UnLoadResponse;
import com.cts.cip.common.model.CancelShippingUnitResponse;
import com.cts.cip.common.model.CreateShippingUnitResponse;
import com.cts.cip.common.model.Error;
import com.cts.cip.common.model.LoadShippingUnitResponse;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingDocument;
import com.cts.cip.common.model.ShippingDocumentContentType;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.common.model.ShippingUnitStatus;
import com.cts.cip.common.model.Status;
import com.cts.cip.common.model.UnloadShippingUnitResponse;

public class AgentServiceResponseMapperImpl implements AgentServiceResponseMapper {

	private ModelMapper modelMapper = new ModelMapper();

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public CreateShippingUnitResponse mapFromCreateResponse(CreateResponse createResponse, ShippingUnit shippingUnitReq) {
		CreateShippingUnitResponse createCOMResponse = new CreateShippingUnitResponse();
		createCOMResponse.setStatus(modelMapper.map(createResponse.getStatus(), Status.class));
		List<ErrorDetails> errorDetails = createResponse.getStatus().getErrors();
		if (errorDetails != null && !errorDetails.isEmpty()) {
			com.cts.cip.common.model.Error error = new com.cts.cip.common.model.Error();
			error.setErrorCode(createResponse.getStatus().getErrors().get(0).getErrorCode());
			error.setErrorDescription(createResponse.getStatus().getErrors().get(0).getErrorDescription());
			createCOMResponse.getStatus().getErrors().add(error);
		}
		createCOMResponse.setTrackingNumber(createResponse.getTrackingNumber());
		createCOMResponse.getDocuments().addAll(getShippingDocuments(createResponse));
		createCOMResponse.setShippingUnit(getShippingUnit(createResponse, shippingUnitReq));
		return createCOMResponse;
	}

	/**
	 * Get Shipping Unit
	 * 
	 * @param createResponse
	 * @return
	 */
	private ShippingUnitBase getShippingUnit(CreateResponse createResponse, ShippingUnit shippingUnitReq) {
		ShippingUnitBase shipUnitBase = new ShippingUnitBase();
		if (createResponse.getPackageInfo() != null) {
			shipUnitBase.setReferenceID(createResponse.getPackageInfo().getPackageID());
		}
		
		if (null != createResponse.getParameterMap()){
			
			KeyValuePair[] props = createResponse.getParameterMap().getProperties();
			com.cts.cip.common.model.KeyValuePair[] labelProperties = new com.cts.cip.common.model.KeyValuePair[props.length];
			
			for (int index=0; index <props.length;index++ ){
				if(null != props[index]){
					labelProperties[index] = new com.cts.cip.common.model.KeyValuePair();
					modelMapper.map(props[index], labelProperties[index]);
				}
				
			}
			
			for(KeyValuePair pair : props){
				if (null != pair){
					String key = pair.getKey();
					//shipUnitBase.getLabelParamMap().put(pair.getKey(), pair.getValue());
				
					switch(key){
						case AgentConstants.POSTAL_BARCODE:
							shipUnitBase.setPostalBarCode(pair.getValue());
							break;
						case AgentConstants.ROUTING_CODE:
							shipUnitBase.setRoutingCode(pair.getValue());
							break;
						case AgentConstants.ROUTING_CODE_VERSION:
							shipUnitBase.setVersion(pair.getValue());
							break;
						case AgentConstants.WEIGHT_UOM:
							shipUnitBase.setPackageWeightUOM(pair.getValue());
							break;
						case AgentConstants.WEIGHT:
							shipUnitBase.setPackageWeight(pair.getValue());
							break;
						case AgentConstants.MAXICODE:
							shipUnitBase.setMaxiCode(pair.getValue());
							break;
						case AgentConstants.MAXICODE_REQD_FLAG:
							shipUnitBase.setMaxiCodeReqd(pair.getValue());
							break;
						case AgentConstants.ROUTING_CODE_VER_DATE:
							shipUnitBase.setVersionDate(pair.getValue());
							break;
						case AgentConstants.SERVICE_NAME:
							shipUnitBase.setServiceName(pair.getValue());
							break;
						case AgentConstants.SERVICE_ICON_P1:
							shipUnitBase.setServiceIcon_p1(pair.getValue());
							break;
						case AgentConstants.SERVICE_ICON_P2:
							shipUnitBase.setServiceIcon_p2(pair.getValue());
							break;
						case AgentConstants.SERVICE_ICON_P3:
							shipUnitBase.setServiceIcon_p3(pair.getValue());
							break;
						case AgentConstants.PROPERTY_1:
							shipUnitBase.setPropertyOne(pair.getValue());
							break;
						case AgentConstants.PROPERTY_2:
							shipUnitBase.setPropertyTwo(pair.getValue());
							break;
						case AgentConstants.PROPERTY_3:
							shipUnitBase.setPropertyThree(pair.getValue());
							break;
						case AgentConstants.PROPERTY_4:
							shipUnitBase.setPropertyFour(pair.getValue());
							break;
						case AgentConstants.PROPERTY_5:
							shipUnitBase.setPropertyFive(pair.getValue());
							break;
						case AgentConstants.PROPERTY_6:
							shipUnitBase.setPropertySix(pair.getValue());
							break;
						case AgentConstants.PROPERTY_7:
							shipUnitBase.setPropertySeven(pair.getValue());
							break;
						case AgentConstants.PROPERTY_8:
							shipUnitBase.setPropertyEight(pair.getValue());
							break;
						case AgentConstants.PROPERTY_9:
							shipUnitBase.setPropertyNine(pair.getValue());
							break;
						case AgentConstants.PROPERTY_10:
							shipUnitBase.setPropertyTen(pair.getValue());
							break;
						
						
					}
				}
				
				
			
			}
			shipUnitBase.setLabelParameters(labelProperties);
			
		
		}

		shipUnitBase.setTrackingNumber(createResponse.getTrackingNumber());
		return shipUnitBase;
	}

	

	/**
	 * Set ShippingDocuments
	 * 
	 * @param createResponse
	 * @return
	 */
	private Collection<? extends ShippingDocument> getShippingDocuments(CreateResponse createResponse) {
		List<ShippingDocument> shipDocsList = new ArrayList<>();
		for (Document doc : createResponse.getDocuments()) {
			ShippingDocument shipDoc = new ShippingDocument();
			shipDoc.setBase64Content(doc.getBase64Content());
			shipDoc.setContentType(ShippingDocumentContentType.fromValue(doc.getContentType()));
			shipDoc.setUrl(doc.getUrl());
			shipDocsList.add(shipDoc);

		}

		return shipDocsList;
	}

	@Override
	public CancelShippingUnitResponse mapFromCancelResponse(CancelResponse cancelResponse) {
		CancelShippingUnitResponse cancelShippingUnitResponse = new CancelShippingUnitResponse();
		cancelShippingUnitResponse.setStatus(modelMapper.map(cancelResponse.getStatus(), Status.class));
		cancelShippingUnitResponse.getShippingUnitStatusList().addAll(getCancelShipUnitStatusList(cancelResponse));

		return cancelShippingUnitResponse;
	}

	/**
	 * Get List of ShipUnitStatus for Cancel Response
	 * 
	 * @param cancelResponse
	 * @return
	 */
	private Collection<? extends ShippingUnitStatus> getCancelShipUnitStatusList(CancelResponse cancelResponse) {

		List<ShippingUnitStatus> shipUnitList = new ArrayList<>();
		for (TrackingNumInfo trackingNumInfo : cancelResponse.getTrackingNumInfoList()) {
			ShippingUnitStatus shippingUnitStatus = new ShippingUnitStatus();

			shippingUnitStatus.setStatus(modelMapper.map(trackingNumInfo.getStatus(), Status.class));
			ShippingUnitBase base = new ShippingUnitBase();
			base.setReferenceID(trackingNumInfo.getTrackingNumber());
			shippingUnitStatus.setShippingUnit(base);
			shipUnitList.add(shippingUnitStatus);
		}

		return shipUnitList;
	}

	@Override
	public ManifestShippingUnitsResponse mapFromManifestResponse(ManifestResponse manifestResponse) {
		ManifestShippingUnitsResponse manifestShippingUnitsResponse = new ManifestShippingUnitsResponse();
		manifestShippingUnitsResponse.setStatus(modelMapper.map(manifestResponse.getStatus(), Status.class));
		manifestShippingUnitsResponse.getShippingUnitStatusList()
				.addAll(getManifestShipUnitStatusList(manifestResponse));
		return manifestShippingUnitsResponse;
	}

	private Collection<? extends ShippingUnitStatus> getManifestShipUnitStatusList(ManifestResponse manifestResponse) {
		
		List<ShippingUnitStatus> shipUnitList = new ArrayList<>();
		if(manifestResponse.getPackageStatusList() != null) {
			for (PackageStatus packageStatus : manifestResponse.getPackageStatusList()) {
				
				ShippingUnitStatus shippingUnitStatus = new ShippingUnitStatus();
				if(packageStatus.getStatus() != null) {
					shippingUnitStatus.setStatus(convertAgentStatusToCipStatus(packageStatus.getStatus()));
					shippingUnitStatus.getStatus().setResponseStatusCode(packageStatus.getStatus().getResponseStatusCode());
				}
				ShippingUnitBase base = new ShippingUnitBase();
				base.setReferenceID(packageStatus.getPackageInfo().getPackageID());
				shippingUnitStatus.setShippingUnit(base);
				shipUnitList.add(shippingUnitStatus);
			}
		}
		return shipUnitList;
	}

	@Override
	public LoadShippingUnitResponse mapFromLoadResponse(LoadResponse loadResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnloadShippingUnitResponse mapFromUnLoadResponse(UnLoadResponse unLoadResponse) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * Converts the agent status to Cip status.
	 * @param status
	 * @return
	 */
	private Status convertAgentStatusToCipStatus(com.cts.cip.agent.model.Status status ) {
		
		Status cipStatus = new Status();
		List<Error> errors =  new ArrayList<Error>(0);
		List<ErrorDetails> errorDetails = status.getErrors();
		if ( errorDetails != null ) {
			for(ErrorDetails errorDetail: errorDetails) {
				Error error = new Error();
				error.setErrorCode(errorDetail.getErrorCode());
				error.setErrorDescription(errorDetail.getErrorDescription());
				error.setErrorSeverity(errorDetail.getErrorSeverity());
				errors.add(error);
			}
		}
		cipStatus.getErrors().addAll(errors);
		return cipStatus;
	}
}
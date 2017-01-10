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
package com.cts.cip.core.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.CarrierAgentException;
import com.cts.cip.common.exceptions.CarrierAgentFailure;
import com.cts.cip.common.exceptions.MultipleResourceExistException;
import com.cts.cip.common.exceptions.NodeServiceFailed;
import com.cts.cip.common.exceptions.RequestValidationException;
import com.cts.cip.common.exceptions.ResourceExistException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.RuleEngineException;
import com.cts.cip.common.exceptions.RuleValidationException;
import com.cts.cip.common.exceptions.SUAlreadyLoadedException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.CreateShippingUnitResponse;
import com.cts.cip.common.model.GetDocumentsResponse;
import com.cts.cip.common.model.ShippingDocument;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.common.model.ShippingUnitState;
import com.cts.cip.common.model.ShippingUnitType;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ResponseStatus;
import com.cts.cip.core.bo.services.CarrierAgentService;
import com.cts.cip.core.bo.services.CommonService;
import com.cts.cip.core.bo.services.DocumentService;
import com.cts.cip.core.bo.services.LoadService;
import com.cts.cip.core.bo.services.NodeDetailService;
import com.cts.cip.core.bo.services.RuleEngineService;
import com.cts.cip.core.bo.services.ShippingUnitRepoService;
import com.cts.cip.util.CipValidator;
import com.cts.cip.util.ValidationResult;

public class ShippingUnitBOImpl implements ShippingUnitBO {

	Logger logger = LoggerFactory.getLogger(ShippingUnitBOImpl.class);

	ShippingUnitRepoService shippingUnitRepoService;

	CarrierAgentService carrierAgentService;

	RuleEngineService ruleEngineService;

	DocumentService documentService;

	LoadService loadService;
	
	NodeDetailService nodeDetailService;
	
	CommonService commonService;
	
	String validationEnabled;
	
	public ShippingUnitRepoService getShippingUnitRepoService() {
		return shippingUnitRepoService;
	}

	public void setShippingUnitRepoService(ShippingUnitRepoService shippingUnitRepoService) {
		this.shippingUnitRepoService = shippingUnitRepoService;
	}

	public CarrierAgentService getCarrierAgentService() {
		return carrierAgentService;
	}

	public void setCarrierAgentService(CarrierAgentService carrierAgentService) {
		this.carrierAgentService = carrierAgentService;
	}

	public RuleEngineService getRuleEngineService() {
		return ruleEngineService;
	}

	public void setRuleEngineService(RuleEngineService ruleEngineService) {
		this.ruleEngineService = ruleEngineService;
	}

	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}

	public LoadService getLoadService() {
		return loadService;
	}

	public void setLoadService(LoadService loadService) {
		this.loadService = loadService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	@Override
	public CreateShippingUnitResponse initate(ShippingUnit shippingUnitReq)
			throws BusinessException, SystemFailure, RequestValidationException {

		CreateShippingUnitResponse shippingUnitRes;		

		// TODO Need to be changes in the Model
		shippingUnitReq.getShippingUnitBase().setType(ShippingUnitType.CARTON);

		// Check the state transition
		preInitiateValidation(shippingUnitReq);
		

		// Do the process
		shippingUnitRes = processInitiate(shippingUnitReq);

		return shippingUnitRes;
	}

	@Override
	public ShippingUnit cancel(String suRefId) throws BusinessException, SystemFailure {
		ShippingUnit shippingUnit;

		shippingUnit = preCancelProcessing(suRefId);

		preCancelValidation(shippingUnit);

		shippingUnit = processCancel(shippingUnit);
		return shippingUnit;
	}

	private void preCancelValidation(ShippingUnit shippingUnit) throws SUAlreadyLoadedException, SystemFailure {
		try {
			if ( loadService.isSUExist(shippingUnit.getShippingUnitBase().getReferenceID()) ){
				throw new SUAlreadyLoadedException ( "The SU attempted is already part of an active Load");
			}
		} catch (MultipleResourceExistException e) {
			logger.error(e.getMessage(),e);
			throw new SystemFailure ( "The SU attempted is already part of multiple active Load : " + e.getMessage() );
		}
		return;
	}

	@Override
	public ShippingUnit get(String refId) throws ResourceNotExistException, SystemFailure {

		return getActiveShipment(refId);
	}
	
	@Override
	public ShippingUnit get(String refId,boolean activeShipment)  throws ResourceNotExistException, SystemFailure {
		
		if (activeShipment) {
			return getActiveShipment(refId);
		} else {
			return getShipment(refId);
		}
	}

	@Override
	public ShippingUnit getShipUnit(String trackingNumber) throws SystemFailure, ResourceNotExistException {
		ShippingUnit activeShippingUnit;
		List<ShippingUnit> suList = shippingUnitRepoService.findShipUnitByTrackingNum(trackingNumber);
		activeShippingUnit = validateSU(trackingNumber, suList);
		return activeShippingUnit;
	}
	
	
	@Override
	public GetDocumentsResponse getSUDocuments(ShippingUnit shippingUnit) throws BusinessException {
		List<ShippingDocument> docList = new ArrayList<>();

		docList = shippingUnitRepoService.getDocuments(shippingUnit.getShippingUnitBase().getReferenceID());
		//Update a reprint flag
	
		GetDocumentsResponse reprintDocumentResponse=  buildReprintResponse(shippingUnit,docList);
		return reprintDocumentResponse;
	}
	
	private ShippingUnit processCancel(ShippingUnit shippingUnit) throws CarrierAgentException, SystemFailure {

		ShippingUnit resSU = shippingUnit;
		
		ShippingUnitState status = shippingUnit.getShippingUnitBase().getState();
		if (status.equals(ShippingUnitState.EXCEPTION)) {
			resSU = updateStatus(shippingUnit, ShippingUnitState.CANCELLED);
		} else if (status.equals(ShippingUnitState.INITIATED)) {
			resSU = updateStatus(shippingUnit, ShippingUnitState.CANCELLED);
		} else if (status.equals(ShippingUnitState.TRACKINGNUMBER_GENERATED)) {
			carrierAgentService.cancelShippingUnit(shippingUnit);
			resSU = updateStatus(shippingUnit, ShippingUnitState.CANCELLED);
		} else if (status.equals(ShippingUnitState.LABEL_CREATED)) {
			carrierAgentService.cancelShippingUnit(shippingUnit);
			resSU = updateStatus(shippingUnit, ShippingUnitState.CANCELLED);
		}
		return resSU;
	}

	private void preInitiateValidation(ShippingUnit shippingUnitReq) throws ResourceExistException, SystemFailure {
		if (isActiveShippingUnitExist(shippingUnitReq.getShippingUnitBase().getReferenceID())) {
			throw new ResourceExistException(" Requested Shipping Unit "
					+ shippingUnitReq.getShippingUnitBase().getReferenceID() + " already exist");
		}
		return;
	}

	private ShippingUnit preCancelProcessing(String suRefId)
			throws ResourceNotExistException, MultipleResourceExistException, SystemFailure {

		ShippingUnit activeSU = getActiveShipment(suRefId);

		if (activeSU == null) {
			throw new ResourceNotExistException("No active shipment found for  " + suRefId);
		}
		return activeSU;
	}

	private CreateShippingUnitResponse processInitiate(ShippingUnit shippingUnitReq)
			throws SystemFailure, BusinessException {
		CreateShippingUnitResponse response;

		// Persist
		persisteSU(shippingUnitReq);
		// Validate against Rules
		validateInitiateRequest(shippingUnitReq);

				
		// Get Tracking Number
		generateTrackingNumber(shippingUnitReq);
		
		logger.debug("Generated Tracking Number..");
		// Generate Documents
		List<ShippingDocument> docList  = null;
		
		docList = generateDocuments(shippingUnitReq);
		
		logger.debug("docList::" +docList);
		// Update Response
		response = buildResponse(shippingUnitReq, docList);
		return response;
	}

	private CreateShippingUnitResponse buildResponse(ShippingUnit shippingUnitReq, List<ShippingDocument> docList) {
		CreateShippingUnitResponse createResponse = new CreateShippingUnitResponse();
		createResponse.setTrackingNumber(shippingUnitReq.getTrackingNumber());
		if (!docList.isEmpty()) {
			createResponse.getDocuments().addAll(docList);
		}
		createResponse.setShippingUnit(shippingUnitReq.getShippingUnitBase());

		return createResponse;

	}

	private GetDocumentsResponse buildReprintResponse(ShippingUnit shippingUnitReq, List<ShippingDocument> docList) {
		GetDocumentsResponse reprintResponse = new GetDocumentsResponse();
		reprintResponse.setTrackingNumber(shippingUnitReq.getTrackingNumber());
		if (!docList.isEmpty()) {
			reprintResponse.getDocuments().addAll(docList);
		}
		reprintResponse.setShippingUnit(shippingUnitReq.getShippingUnitBase());
		return reprintResponse;

	}

	private List<ShippingDocument> generateDocuments(ShippingUnit shippingUnitReq)
			throws SystemFailure, BusinessException {

		List<ShippingDocument> docList = null;

		try {
			docList = documentService.generateDocuments(shippingUnitReq);
		} catch (BusinessException e) {
			updateStatus(shippingUnitReq, ShippingUnitState.EXCEPTION);
			throw e;
		} catch (SystemFailure e) {
			updateStatus(shippingUnitReq, ShippingUnitState.FAILED);
			throw e;
		}
		shippingUnitRepoService.persistDocuments (shippingUnitReq.getShippingUnitBase().getReferenceID(), docList);
		
		updateStatus(shippingUnitReq, ShippingUnitState.LABEL_CREATED);

		return docList;
	}

	private ShippingUnit generateTrackingNumber(ShippingUnit shippingUnitReq)
			throws CarrierAgentException, SystemFailure {
		ShippingUnit shippingUnitRes;
		try {
			shippingUnitRes = carrierAgentService.generateTrackingNumber(shippingUnitReq);
		} catch (CarrierAgentException e) {
			updateStatus(shippingUnitReq, ShippingUnitState.EXCEPTION);
			throw e;
		} catch (CarrierAgentFailure e) {
			updateStatus(shippingUnitReq, ShippingUnitState.FAILED);
			throw e;
		}
		shippingUnitRes = updateStatus(shippingUnitRes, ShippingUnitState.TRACKINGNUMBER_GENERATED);
		shippingUnitRes = shippingUnitRepoService.update(shippingUnitRes);

		return shippingUnitRes;
	}

	private ShippingUnit validateInitiateRequest(ShippingUnit shippingUnitReq)
			throws RuleValidationException, SystemFailure {
		try {
			logger.debug("validateInitiateRequest entry");
			ruleEngineService.validateRules(shippingUnitReq);
		} catch (RuleEngineException e) {
			updateStatus(shippingUnitReq, ShippingUnitState.FAILED);
			throw e;
		} catch (RuleValidationException e) {
			updateStatus(shippingUnitReq, ShippingUnitState.EXCEPTION);
			throw e;
		}
		return updateStatus(shippingUnitReq, ShippingUnitState.VALIDATED);
	}

	private ShippingUnit persisteSU(ShippingUnit shippingUnitReq) {
		shippingUnitReq.getShippingUnitBase().setState(ShippingUnitState.INITIATED);
		shippingUnitRepoService.save(shippingUnitReq);
		return shippingUnitReq;
	}

	private ShippingUnit updateStatus(ShippingUnit shippingUnitReq, ShippingUnitState status) throws SystemFailure {
		shippingUnitReq.getShippingUnitBase().setState(status);
		shippingUnitRepoService.updateStatus(shippingUnitReq);
		return shippingUnitReq;
	}

	@Override
	public Boolean isActiveShippingUnitExist(String refSUId) throws SystemFailure {
		ShippingUnit activeSU = null;
		try {
			activeSU = getActiveShipment(refSUId);
		} catch (ResourceNotExistException e) {
			logger.debug( e.getMessage() , e );
			return false;
		} 
		return (activeSU != null);
	}

	@Override
	public void validateRequest(ShippingUnit shippingUnitReq) throws NodeServiceFailed, BusinessException {
		
		int nodeId = shippingUnitReq.getOrder().getDistributionCenterID();	
		
		if(validationEnabled!=null && validationEnabled.equalsIgnoreCase("false")) {
			logger.debug("Validation is disabled in the configuration");
		} else {
			CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnitReq);		
			if (!requestValidationResult.getStatus().equals(ResponseStatus.SUCCESS)) {			
				logger.debug("The shipment unit request validation failed " + requestValidationResult.getErrorDesc());
				logger.debug("Request Validation is failed " + requestValidationResult );
				throw new BusinessException("The shipment unit request validation failed " + requestValidationResult.getErrorDesc(),requestValidationResult.getErrorList());
			
			} else {			
				logger.debug("The shipment unit request validation is successful");
			}
		}
		

		// Update the carrier code based on the custom ship via. If the code
		// mapping is not found in the repository the value of the service id
		// will be used
		Map<String, String> shipViaMap = commonService.getShipViaMap();
		String carrierCode = "";
		String shipVia="";
		if (shippingUnitReq.getShipperDetails() != null) {
			if (shippingUnitReq.getShipperDetails().getShipperServiceType() != null) {
				shipVia = shippingUnitReq.getShipperDetails().getShipperServiceType().getShipperServiceTypeID();
				carrierCode = shipViaMap
						.get(shippingUnitReq.getShipperDetails().getShipperServiceType().getShipperServiceTypeID());
				if (carrierCode != null && !carrierCode.equalsIgnoreCase("")) {
					shippingUnitReq.getShipperDetails().getShipperServiceType().setShipperServiceTypeCode(carrierCode);
				} else {
					shippingUnitReq.getShipperDetails().getShipperServiceType().setShipperServiceTypeCode(
							shippingUnitReq.getShipperDetails().getShipperServiceType().getShipperServiceTypeID());
				}
			}
		}
		if(carrierCode==null || carrierCode.equalsIgnoreCase("")) {
			throw new RuleValidationException("The given ship via " + shipVia + " is not associated with any carrier service" );
		}
		logger.debug("The value of the carrier code: " + carrierCode );				
		String serviceCode = shippingUnitReq.getShipperDetails().getShipperServiceType().getShipperServiceTypeCode();
		ValidationResult validationResult = nodeDetailService.isActiveNodeService(nodeId, serviceCode);		

		if (!validationResult.isSuccesful()) {
			throw new RuleValidationException(validationResult.getDescription());
		}
		return;
	}

	@Override
	public ShippingUnit confirm(ShippingUnitBase suBase)
			throws ResourceNotExistException, SystemFailure {

		ShippingUnit activeSu = getActiveShipment(suBase.getReferenceID());

		return updateStatus(activeSu, ShippingUnitState.CONFIRMED);
	}

	private ShippingUnit getActiveShipment(String suRefId)
			throws ResourceNotExistException, SystemFailure {

		ShippingUnit activeShippingUnit;
		List<ShippingUnit> shippingUnits = shippingUnitRepoService.findById(suRefId);
		activeShippingUnit = validateSU(suRefId, shippingUnits);
		return activeShippingUnit;
	}
	
	
	private ShippingUnit getShipment(String surefId) throws ResourceNotExistException, SystemFailure {
		ShippingUnit shippingUnit = null;
		List<ShippingUnit> shippingUnits = shippingUnitRepoService.findById(surefId);
		if (shippingUnits!=null) {
			shippingUnit = shippingUnits.get(0);
		}
		
		return shippingUnit;
	}

	private ShippingUnit validateSU(String id, List<ShippingUnit> shippingUnits)
			throws SystemFailure {
		ShippingUnit activeShippingUnit = null;
		for (ShippingUnit shippingUnit : shippingUnits) {
			if (isActive(shippingUnit.getShippingUnitBase().getState())) {
				if (activeShippingUnit == null) {
					activeShippingUnit = shippingUnit;
				} else {
					throw new SystemFailure(
							"More than one Active SU found for  :" + id);
				}
			}
		}
		/*if (activeShippingUnit == null) {
			throw new SystemFailure("The status is not active for the ship unit : " + id );
		}*/
		return activeShippingUnit;
	}

	private Boolean isActive(ShippingUnitState state) {
		// TODO
		/* Logic need to be added to support the synchronous request
		 which has two different state for the same id. Like when one request
		 set the state as TRACKINGNUMBER_GENERATED and other request try to
		 create the shipment again */
		
		
		if (!(state.equals(ShippingUnitState.CANCELLED) || state.equals(ShippingUnitState.EXCEPTION)
				|| state.equals(ShippingUnitState.FAILED ) || state.equals(ShippingUnitState.TRACKINGNUMBER_GENERATED) || state.equals(ShippingUnitState.VALIDATED))) {
			return true;
		}

		return false;
	}

	@Override
	public List<ShippingUnit> findAll() {
		return shippingUnitRepoService.findAll();
	}

	@Override
	public List<ShippingUnit> getInOffset(Integer offset, Integer limit) {

		return shippingUnitRepoService.findByPage(offset, limit);
	}

	@Override
	public Integer findTotalCount() {
		return shippingUnitRepoService.findTotalCount();
	}

	@Override
	public String getCarrierServiceNameByCode(String id) {
		return shippingUnitRepoService.getCarrierServiceNameByCode(id);
		
	}

	public NodeDetailService getNodeDetailService() {
		return nodeDetailService;
	}

	public void setNodeDetailService(NodeDetailService nodeDetailService) {
		this.nodeDetailService = nodeDetailService;
	}

	public String getValidationEnabled() {
		return validationEnabled;
	}

	public void setValidationEnabled(String validationEnabled) {
		this.validationEnabled = validationEnabled;
	}





}

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
package com.cts.cip.core.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.LoadInfo;
import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.CarrierAgentException;
import com.cts.cip.common.exceptions.MultipleResourceExistException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.Error;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.LoadBase;
import com.cts.cip.common.model.LoadShippingUnitResponse;
import com.cts.cip.common.model.LoadState;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.common.model.ShippingUnitStatus;
import com.cts.cip.common.model.Status;
import com.cts.cip.common.model.TotalUnitWeight;
import com.cts.cip.common.model.UnloadShippingUnitResponse;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ResponseStatus;
import com.cts.cip.core.bo.services.CarrierAgentService;
import com.cts.cip.core.bo.services.LoadRepoService;
import com.cts.cip.core.bo.services.ShippingUnitService;
import com.cts.cip.core.repo.constants.CIPCoreConstants;
import com.cts.cip.util.CipValidator;
import com.cts.cip.util.CommonUtility;

public class LoadBOImpl implements LoadBO {

	Logger logger = LoggerFactory.getLogger(LoadBOImpl.class);

	LoadRepoService loadRepoService;

	ShippingUnitService shippingUnitService;

	CarrierAgentService carrierAgentService;
	
	ShippingUnitBO shippingUnitBO;
	
	public LoadRepoService getLoadRepoService() {
		return loadRepoService;
	}

	public void setLoadRepoService(LoadRepoService loadRepoService) {
		this.loadRepoService = loadRepoService;
	}

	public ShippingUnitService getShippingUnitService() {
		return shippingUnitService;
	}

	public void setShippingUnitService(ShippingUnitService shippingUnitService) {
		this.shippingUnitService = shippingUnitService;
	}

	public CarrierAgentService getCarrierAgentService() {
		return carrierAgentService;
	}

	public void setCarrierAgentService(CarrierAgentService carrierAgentService) {
		this.carrierAgentService = carrierAgentService;
	}

	public ShippingUnitBO getShippingUnitBO() {
		return shippingUnitBO;
	}

	public void setShippingUnitBO(ShippingUnitBO shippingUnitBO) {
		this.shippingUnitBO = shippingUnitBO;
	}

	/**
	 * 
	 */
	public LoadBOImpl() {

	}

	@Override
	public LoadShippingUnitResponse loadSUs(LoadBase loadBase, List<ShippingUnitBase> shipBaseUnits) 
			throws SystemFailure, BusinessException {

		LoadShippingUnitResponse loadSUResponse = null;
		Load activeLoad = null;
		try {
			activeLoad = getActiveLoad(loadBase.getLoadReferenceId());
		} catch (ResourceNotExistException e) {
			logger.info("This is a Fresh Load !!" + e.getMessage(), e);
			loadBase.setLoadState(LoadState.LOADING);
			LoadBase loadBaseObj = getLoadWithCarrierInfo(loadBase, shipBaseUnits);
			activeLoad = createLoad(loadBaseObj);
		}
		try  {
			loadSUResponse = addShippingUnits(activeLoad, shipBaseUnits);
		} catch (BusinessException|SystemFailure ex) {
			logger.debug("Exception occured while loading::" + ex);
			Status status = createStatus(CIPCoreConstants.RESPONSE_FAIL_DESC, ex.getMessage(), 
					"Problem encountered loading shipping units to  "+ activeLoad.getLoadDetails().getLoadReferenceId() + " : " + ex.getMessage(), true);
			loadSUResponse = createLoadShippingUnitResponse(status, activeLoad.getLoadDetails(), null);
		}
		return loadSUResponse;

	}
	
	private LoadBase getLoadWithCarrierInfo (LoadBase load, List<ShippingUnitBase> shipBaseUnits) throws SystemFailure, 
		BusinessException {
		//Get the Carrier Service Name and its id  from any of the shipping unit add it to the load
		//1. Get the ShippingUnit Details
		
		ShippingUnit shippingUnit = shippingUnitBO.get(shipBaseUnits.get(0).getReferenceID(),false);
		String serviceTypeCd = shippingUnit.getShipperDetails().getShipperServiceType().getShipperServiceTypeCode();
		logger.debug("serviceTypeId" + serviceTypeCd);
		CarrierInfo carrierInfo = loadRepoService.getCarrierInfoByServiceType(serviceTypeCd);
		load.setCarrierID(carrierInfo.getId());
		load.setCarrierName(carrierInfo.getName());
		
		return load;
	}
	
	@Override
	public UnloadShippingUnitResponse unloadShippingUnit(LoadBase loadBase, List<ShippingUnitBase> shipBaseUnits)
			throws SystemFailure, BusinessException {
		UnloadShippingUnitResponse unloadSURes = new UnloadShippingUnitResponse();
		Load liveLoad = null;
		
		
		try  {
			if (null == loadBase || (null != loadBase && (null == loadBase.getLoadReferenceId()))){
				if (!shipBaseUnits.isEmpty()){
					liveLoad = getAssociatedLoad(shipBaseUnits);
				}
			}else{
				liveLoad = getLiveLoad(loadBase.getLoadReferenceId());
			}
			
			
			unloadSURes = removeShippingUnits(liveLoad, shipBaseUnits);
			
		} catch (BusinessException|SystemFailure be) {
			logger.error("Exception occured while unloading::" + be);
			Status status = createStatus(CIPCoreConstants.RESPONSE_FAIL_DESC, be.getMessage(), 
					"Problem in while unloading shipping units on "+be.getMessage(), true);
			unloadSURes = createUnloadShippingUnitResponse(status, loadBase, null);
		}
		return unloadSURes;

	}

	private Load getAssociatedLoad(List<ShippingUnitBase> shipBaseUnits)
			throws MultipleResourceExistException, ResourceNotExistException {
		Load liveLoad = null;
		
		for (ShippingUnitBase sUnit : shipBaseUnits ){
			
			try{
				liveLoad = getBySU(sUnit.getReferenceID());
				if (null != liveLoad)
					break;
				
				}catch (ResourceNotExistException rne){
					logger.error("Unable to find associated load for " + sUnit.getReferenceID());
				}
			
		}
		if (liveLoad == null)
			throw new ResourceNotExistException(" No Live load found ");
		
		return liveLoad;
	}
	private UnloadShippingUnitResponse removeShippingUnits(Load activeLoad, List<ShippingUnitBase> shippingUnits)
			throws ResourceNotExistException, SystemFailure {
		
		List<ShippingUnitStatus> shippingUnitStatusList = new ArrayList<ShippingUnitStatus>(0);
		boolean allSUsFailed = true;
		int totalSuccessfulCount = 0;
		int totalFailureCount = 0;
		Load load = null;
		
		for (ShippingUnitBase shippingUnitBase : shippingUnits) {
			try {
				load = loadRepoService.removeSU(activeLoad, shippingUnitBase);
				shippingUnitStatusList.add(getSuccessShippingUnits(shippingUnitBase, 
						shippingUnitBase.getReferenceID() + " is successfully unloaded from "+load.getLoadDetails().getLoadReferenceId()));
				totalSuccessfulCount = totalSuccessfulCount + 1;
				allSUsFailed = false;
				
			} catch (ResourceNotExistException rne){
				logger.error("Exception while executing the method removeShippingUnits() ", rne);
				shippingUnitStatusList.add(getErroredShippingUnits(shippingUnitBase, rne.getMessage()));
				totalFailureCount = totalFailureCount + 1;
			}
		}
		UnloadShippingUnitResponse unloadSUResponse = null;
		try {
			if (load != null) {
				Load updatedLoad = getById(load.getLoadDetails().getId());
				Load loadObj = cancelIfEmpty(updatedLoad);

				String loadRefId = loadObj.getLoadDetails().getLoadReferenceId();
				String statusMsg = "";
				Status status = null;

				if (allSUsFailed) {
					statusMsg = "No ship units got unloaded from total shipping unit count of " + shippingUnits.size()
							+ "";
					status = createStatus(CIPCoreConstants.RESPONSE_FAIL_DESC, CIPCoreConstants.RESPONSE_FAIL_DESC,
							statusMsg, true);
				} else {
					statusMsg = totalFailureCount > 0
							? getUnloadPartialStausMsg(loadRefId, totalSuccessfulCount, shippingUnits.size())
							: getUnloadSuccessStausMsg(loadRefId, totalSuccessfulCount);

					status = createStatus(CIPCoreConstants.RESPONSE_SUCCESS_DESC,
							CIPCoreConstants.RESPONSE_SUCCESS_DESC, statusMsg, false);
				}
				unloadSUResponse = createUnloadShippingUnitResponse(status, updatedLoad.getLoadDetails(),
						shippingUnitStatusList);
			}
		} catch (ResourceNotExistException e) {
			logger.error("Could not retrive back the load", e);
			throw new SystemFailure("Could not retrive back the load" + activeLoad.getLoadDetails().getId());
		}
		Map<String, String> detailsMap =  loadRepoService.getLoadCountAndWeightDetails(activeLoad.getLoadDetails().getId());
		setUnitCountWeight(detailsMap,unloadSUResponse);
		unloadSUResponse.getStatus().setErrorCount(String.valueOf(totalFailureCount));
		return unloadSUResponse;
	}
	
	private UnloadShippingUnitResponse createUnloadShippingUnitResponse (Status status, LoadBase load, 
			List<ShippingUnitStatus> shippingUnitStatusList) {
		
		UnloadShippingUnitResponse unloadSUResponse = new UnloadShippingUnitResponse();
		//unloadSUResponse.setLoad(load);
		if(shippingUnitStatusList != null) {
			unloadSUResponse.getShippingUnitStatusList().addAll(shippingUnitStatusList);
		}
		unloadSUResponse.setStatus(status);

		return unloadSUResponse;
	} 
	
	private String getUnloadPartialStausMsg(String loadRefId, int totalSuccessfulCount, int totalCount) {
		StringBuilder sb = new StringBuilder();
		sb.append("Warning:: ");
		sb.append(loadRefId);
		sb.append(" is partillay unloaded with ");
		sb.append(totalSuccessfulCount);
		sb.append(" shipping units of total shippings units " + totalCount);
		sb.append(". And failure shippings units count is " + (totalCount - totalSuccessfulCount));
		return sb.toString();
	}
	private String getUnloadSuccessStausMsg(String loadRefId, int totalSuccessfulCount) {
		StringBuilder sb = new StringBuilder();
		sb.append(loadRefId);
		sb.append("  is successfully unloaded with ");
		sb.append(totalSuccessfulCount);
		sb.append(" shipping units");
		return sb.toString();
	}
	
	private Load cancelIfEmpty(Load load) {
		// If everything unloaded cancel the Load
		if (isLive(load.getLoadDetails().getLoadState())
				&& (load.getShippingUnits() == null || load.getShippingUnits().isEmpty()))

			cancelLoad(load);

		return load;

	}

	private Load cancelLoad(Load load) {

		updateStatus(load, LoadState.CANCELLED);

		return load;
	}

	private LoadShippingUnitResponse addShippingUnits(Load activeLoad, List<ShippingUnitBase> shippingUnits)
			throws SystemFailure, BusinessException {
		
		if (!isLive(activeLoad.getLoadDetails().getLoadState())) {
			throw new BusinessException("Load " + activeLoad.getLoadDetails().getLoadReferenceId() + " is not live");
		}
		
		List<ShippingUnitStatus> shippingUnitStatusList = new ArrayList<ShippingUnitStatus>(0);
		boolean isErrorShipUnit = false;
		boolean allSUsFailed = true;
		boolean isCurrentSUReadyToLoad = true;
		int totalSuccessfulCount = 0;
		int totalFailureCount = 0;
		Load loadObj = null;
		for (ShippingUnitBase shippingUnit : shippingUnits) {
			
			try {
				if (!shippingUnitService.isSUActive(shippingUnit.getReferenceID())) {
					shippingUnitStatusList.add(getErroredShippingUnits(shippingUnit, 
							shippingUnit.getReferenceID() + " is not active shipping unit or not exist. "));
					isErrorShipUnit = true;
					isCurrentSUReadyToLoad = false;
					
				}
				if (isSUExist(shippingUnit.getReferenceID())) {
					shippingUnitStatusList.add(getErroredShippingUnits(shippingUnit, 
							shippingUnit.getReferenceID() + " is already associted with another load. "));
					isErrorShipUnit = true;
					isCurrentSUReadyToLoad = false;
				}
				
				if (!isErrorShipUnit && isCurrentSUReadyToLoad) {
					loadObj = loadRepoService.addSU(activeLoad, shippingUnit);
					shippingUnitStatusList.add(getSuccessShippingUnits(shippingUnit, 
							shippingUnit.getReferenceID() + " is successfully loaded into "+loadObj.getLoadDetails().getLoadReferenceId()));
					allSUsFailed = false;
					totalSuccessfulCount = totalSuccessfulCount + 1;
				} else {
					totalFailureCount = totalFailureCount + 1;
				}
				isErrorShipUnit = false;
				isCurrentSUReadyToLoad = true;
				
			}catch (SystemFailure sf) {
				logger.error("Could not retrive back the load", sf);
				shippingUnitStatusList.add(getErroredShippingUnits(shippingUnit, shippingUnit.getReferenceID()+ " can not be Loaded:" +sf.getMessage()));
			}
			
			
		}
		LoadShippingUnitResponse loadSUResponse = null;
		try {
			if (loadObj != null) {
				Load updatedLoad = getById(loadObj.getLoadDetails().getId());
				// Cancel the load if nothing was loaded .. This is a Fail Safe
				// .. Need
				// to be revisited
				Load activeLoadObj = cancelIfEmpty(updatedLoad);

				String loadRefId = activeLoadObj.getLoadDetails().getLoadReferenceId();
				String statusMsg = "";
				Status status = null;
				if (allSUsFailed) {
					statusMsg = "No ship units got loaded.";
					status = createStatus(CIPCoreConstants.RESPONSE_FAIL_DESC, CIPCoreConstants.RESPONSE_FAIL_DESC,
							statusMsg, true);
				} else {
					statusMsg = totalFailureCount > 0
							? getPartialStausMsg(loadRefId, totalSuccessfulCount, shippingUnits.size())
							: getSuccessStausMsg(loadRefId, totalSuccessfulCount);
					status = createStatus(CIPCoreConstants.RESPONSE_SUCCESS_DESC,
							CIPCoreConstants.RESPONSE_SUCCESS_DESC, statusMsg, false);
				}
				loadSUResponse = createLoadShippingUnitResponse(status, updatedLoad.getLoadDetails(),
						shippingUnitStatusList);
			}
		} catch (ResourceNotExistException e) {
			logger.error("Could not retrive back the load", e);
			throw new SystemFailure("Could not retrive back the load" + activeLoad.getLoadDetails().getId());
		}
		if (totalSuccessfulCount < 1 && totalFailureCount > 0){
			Status status = createStatus(CIPCoreConstants.RESPONSE_FAIL_DESC, CIPCoreConstants.RESPONSE_FAIL_DESC,
					"ShipUnits already associated with load", true);
			loadSUResponse = createLoadShippingUnitResponse(status, activeLoad.getLoadDetails(),
					shippingUnitStatusList);
				
		}
		Map<String, String> detailsMap =  loadRepoService.getLoadCountAndWeightDetails(activeLoad.getLoadDetails().getId());
		setUnitCountWeight(detailsMap,loadSUResponse);
		loadSUResponse.getStatus().setErrorCount(String.valueOf(totalFailureCount));
		return loadSUResponse;
	}
	
	
	
	
	private void setUnitCountWeight(Map<String, String> detailsMap, LoadShippingUnitResponse loadSUResponse) {
		if (null != loadSUResponse.getLoad() ){
			TotalUnitWeight weight = new TotalUnitWeight();
			weight.setUnitWeight(Float.valueOf(detailsMap.get("totalWeight")));
			loadSUResponse.getLoad().setTotalUnitCount(Integer.valueOf(detailsMap.get("unitCount")));
			loadSUResponse.getLoad().setTotalUnitWeight(weight);
		}
			
	}

	private void setUnitCountWeight(Map<String, String> detailsMap, UnloadShippingUnitResponse  loadSUResponse) {
		if (null != loadSUResponse.getLoad() ){
			TotalUnitWeight weight = new TotalUnitWeight();
			weight.setUnitWeight(Float.valueOf(detailsMap.get("totalWeight")));
			loadSUResponse.getLoad().setTotalUnitCount(Integer.valueOf(detailsMap.get("unitCount")));
			loadSUResponse.getLoad().setTotalUnitWeight(weight);
		}

			
	}
	
	private void setUnitCountWeight(Map<String, String> detailsMap, ManifestShippingUnitsResponse manifestResponse) {

		if (null != manifestResponse.getLoad() ){
			TotalUnitWeight weight = new TotalUnitWeight();
			weight.setUnitWeight(Float.valueOf(detailsMap.get("totalWeight")));
			manifestResponse.getLoad().setTotalUnitCount(Integer.valueOf(detailsMap.get("unitCount")));
			manifestResponse.getLoad().setTotalUnitWeight(weight);
		}
		
	}
	
	
	private String getPartialStausMsg(String loadRefId, int totalSuccessfulCount, int totalCount) {
		StringBuilder sb = new StringBuilder();
		sb.append("Warning:: ");
		sb.append(loadRefId);
		sb.append(" is partillay loaded with ");
		sb.append(totalSuccessfulCount);
		sb.append(" shipping units of total shippings units " + totalCount);
		sb.append(". And failure shippings units count is " + (totalCount - totalSuccessfulCount));
		return sb.toString();
	}
	private String getSuccessStausMsg(String loadRefId, int totalSuccessfulCount) {
		StringBuilder sb = new StringBuilder();
		sb.append(loadRefId);
		sb.append("  is successfully loaded with ");
		sb.append(totalSuccessfulCount);
		sb.append(" shipping units");
		return sb.toString();
	}
	
	
	
	private LoadShippingUnitResponse createLoadShippingUnitResponse (Status status, LoadBase load, 
			List<ShippingUnitStatus> shippingUnitStatusList) {
		
		LoadShippingUnitResponse loadSUResponse = new LoadShippingUnitResponse();
		loadSUResponse.setLoad(load);
		if (null != shippingUnitStatusList)
			loadSUResponse.getShippingUnitStatusList().addAll(shippingUnitStatusList);
		loadSUResponse.setStatus(status);

		return loadSUResponse;
	} 

	private ShippingUnitStatus getErroredShippingUnits(ShippingUnitBase shippingUnit, String errorMsg) {
		
		ShippingUnitStatus suStatus  = new ShippingUnitStatus ();
		suStatus.setShippingUnit(shippingUnit);
		suStatus.setStatus(createStatus(CIPCoreConstants.RESPONSE_FAIL_DESC, 
				CIPCoreConstants.RESPONSE_FAIL_DESC, errorMsg, true));
		
		return suStatus;
	}
	
	private ShippingUnitStatus getSuccessShippingUnits(ShippingUnitBase shippingUnit, String successMsg) {
		
		ShippingUnitStatus suStatus  = new ShippingUnitStatus ();
		suStatus.setShippingUnit(shippingUnit);
		suStatus.setStatus(createStatus(CIPCoreConstants.RESPONSE_SUCCESS_DESC, 
				CIPCoreConstants.RESPONSE_SUCCESS_DESC, successMsg, false));
		return suStatus;
	}
	
	private Status createStatus(String statusCd, String statusMsg, String statusDesc, boolean isError ) {
		
		Status status = new Status();
		status.setResponseStatusCode(statusCd);
		status.setResponseStatusDescription(statusDesc);
		if (isError) {
			Error error = new Error();
			error.setErrorCode(statusCd);
			error.setErrorDescription(statusDesc);
			status.getErrors().add(error);
		}
		return status;
	}

	private boolean isSUExist(String referenceID) throws SystemFailure {
		Load load = null;

		try {
			load = getBySU(referenceID);
		} catch (MultipleResourceExistException e) {
			logger.error(" We got a visitor(s) from past !! ", e);
			throw new SystemFailure(e.getMessage());
		} catch (ResourceNotExistException e) {
			logger.info(e.getMessage() + e);
			return false;
		}
		return (load != null);
	}

	private Load createLoad(LoadBase loadBase) {
		Load load = new Load();
		load.setLoadDetails(loadBase);
		return loadRepoService.create(load);
	}

	private Load getActiveLoad(String loadRefId) throws ResourceNotExistException, SystemFailure {

		List<Load> loads = loadRepoService.findByRefId(loadRefId);

		return getActiveLoad(loads);
	}

	private Load getLiveLoad(String loadRefId) throws ResourceNotExistException, SystemFailure {

		List<Load> loads = loadRepoService.findByRefId(loadRefId);

		return getLiveLoad(loads);
	}

	private Load getActiveLoad(List<Load> loads) throws SystemFailure, ResourceNotExistException {

		Load retLoad = null;
		for (Load load : loads) {

			if (isActive(load.getLoadDetails().getLoadState())) {
				if (retLoad != null) {
					throw new SystemFailure("More then one active load exist");
				}
				retLoad = load;
			}
		}

		if (retLoad == null)
			throw new ResourceNotExistException(" No Active load found ");

		return retLoad;
	}

	private Load getLiveLoad(List<Load> loads) throws SystemFailure, ResourceNotExistException {

		Load retLoad = null;
		for (Load load : loads) {

			if (isLive(load.getLoadDetails().getLoadState())) {
				if (retLoad != null) {
					throw new SystemFailure("More then one active load exist");
				}
				retLoad = load;
			}
		}
		if (retLoad == null)
			throw new ResourceNotExistException(" No Live load found ");
		return retLoad;
	}

	private Boolean isActive(LoadState loadState) {
		if (loadState.compareTo(LoadState.LOADING) == 0 || loadState.compareTo(LoadState.MANIFESTED) == 0) {
			return true;
		}
		return false;
	}

	private Boolean isLive(LoadState loadState) {
		if (loadState.compareTo(LoadState.LOADING) == 0) {
			return true;
		}
		return false;
	}

	private Load updateStatus(Load load, LoadState loadState) {
		load.getLoadDetails().setLoadState(loadState);
		return loadRepoService.updateStatus(load);
	}

	@Override
	public List<Load> get(String referanceId) throws ResourceNotExistException {

		return loadRepoService.findByRefId(referanceId);
	}

	@Override
	public Load getBySU(String surefId) throws MultipleResourceExistException, ResourceNotExistException {

		return loadRepoService.findBySURefId(surefId);

	}

	@Override
	public List<Load> findByPage(SearchCriteria searchCriteria) throws ResourceNotExistException {
		return loadRepoService.findByPage(searchCriteria);
	}

	@Override
	public Load getById(Integer id) throws ResourceNotExistException {

		return loadRepoService.findById(id);
	}

	@Override
	public LoadInfo findLoadInfoById(int id) throws ResourceNotExistException {
		return loadRepoService.findLoadInfoById(id);
	}

	@Override
	public LoadInfo findLoadInfoByName(String loadName) throws ResourceNotExistException {
		return loadRepoService.findLoadInfoByName(loadName);
	}

	@Override
	public Long findCountByCriteria(SearchCriteria searchCriteria) throws ResourceNotExistException {
		return loadRepoService.findCountByCriteria(searchCriteria);
	}

	@Override
	public <T> void  validateRequest(T request) throws BusinessException {
		
		String validationEnabled = CommonUtility.getPropertyValue("validation.enabled","cip-core.properties");
		if(validationEnabled!=null && validationEnabled.equalsIgnoreCase("false")) {
			logger.debug("Validation is disabled in the configuration");
		} else {
			CIPResponse<String> requestValidationResult = CipValidator.validateRequest(request);
			String requestName = request.getClass().getName();
			if (!requestValidationResult.getStatus().equals(ResponseStatus.SUCCESS)) {			
				logger.debug(requestName + " validation failed: " + requestValidationResult.getErrorDesc());
				logger.debug("Request Validation is failed " + requestValidationResult );
				throw new BusinessException(requestName + "validation failed:: " + requestValidationResult.getErrorDesc(),requestValidationResult.getErrorList());
			
			} else {			
				logger.debug(requestName + "validation  is successful");
			}
		}
		return;	
	}
	@Override
	public ManifestShippingUnitsResponse manifestLoad(LoadBase load) throws ResourceNotExistException, 
		CarrierAgentException, SystemFailure {
		int errorCount = 0;
		Load activeLoad = getLiveLoad(load.getLoadReferenceId());
		//Get Carrier Service Name 
		String carrierId = activeLoad.getLoadDetails().getCarrierID();
		logger.debug("carrierId ::" + carrierId);
		CarrierInfo carrierInfo = loadRepoService.getCarrierInfoById(carrierId);
		logger.debug("carrierName ::" + carrierInfo.getName());
		activeLoad.getLoadDetails().setCarrierName(carrierInfo.getName());
		
		ManifestShippingUnitsResponse manifestResponse = carrierAgentService.manifestLoad(activeLoad);
		if( manifestResponse == null || manifestResponse.getStatus() == null) {
			throw new SystemFailure("Problem while Manifesting the shipping units.");
		}
		Status topLevelStatus = null;
		String resStatusCode = manifestResponse.getStatus().getResponseStatusCode();
		String errorCode = null;
		String errorDesc = "";
		boolean manifestFailed = true;
		List<ShippingUnitStatus> suStatusList = new ArrayList<ShippingUnitStatus>(0);
		
		if ("0".equals(resStatusCode) && manifestResponse.getShippingUnitStatusList().size() == 0) {
			activeLoad = updateStatus(activeLoad, LoadState.FAILED_MANIFEST);
			topLevelStatus = new Status();
			errorCode = ResponseStatus.FAILURE.name();
			errorDesc = "Manifest got failed.";
			errorCount = activeLoad.getShippingUnits().size();
		} 
		else if ("0".equals(resStatusCode) && manifestResponse.getShippingUnitStatusList().size() > 0) 
		{
			Map<String, ShippingUnitBase> suBaseMap = getSUBaseDistinctMap(activeLoad, 
					manifestResponse.getShippingUnitStatusList());
			if(!suBaseMap.isEmpty()) {
				List<ShippingUnitBase> suBaseList  = new ArrayList<ShippingUnitBase>( suBaseMap.values() );
				shippingUnitService.confirmShipment(suBaseList);
				activeLoad = updateStatus(activeLoad, LoadState.PARTIAL_MANIFEST);
				errorDesc = "Manifest is partially successful.";
				errorCode = ResponseStatus.SUCCESS.name();
				errorCount = manifestResponse.getShippingUnitStatusList().size();
				manifestFailed = false;
			} else {
				activeLoad = updateStatus(activeLoad, LoadState.FAILED_MANIFEST);
				errorDesc = "Manifest got failed.";
				errorCode = ResponseStatus.FAILURE.name();
				errorCount = activeLoad.getShippingUnits().size();
			}
			
		} else if ("1".equals(resStatusCode)) {
			activeLoad = updateStatus(activeLoad, LoadState.MANIFESTED);
			shippingUnitService.confirmShipment(activeLoad.getShippingUnits());
			suStatusList = getShippingUnitStatusList(activeLoad.getShippingUnits());
			errorCode = ResponseStatus.SUCCESS.name();
			errorDesc = "Manifest is successfull.";
			manifestFailed = false;
		}
		topLevelStatus = new Status();
		topLevelStatus.setResponseStatusCode(errorCode);
		topLevelStatus.setResponseStatusDescription(errorDesc);
		manifestResponse.setLoad(activeLoad.getLoadDetails());
		manifestResponse.getShippingUnitStatusList().addAll(suStatusList);
		
		if (!manifestFailed ){
			Map<String, String> detailsMap =  loadRepoService.getManifestCountAndWeightDetails(activeLoad.getLoadDetails().getId());
			setUnitCountWeight(detailsMap,manifestResponse);	
		}
		topLevelStatus.setErrorCount(String.valueOf(errorCount));
		manifestResponse.setStatus(topLevelStatus);
		return manifestResponse;
	}

	private Map<String, ShippingUnitBase> getSUBaseDistinctMap (Load activeLoad, List<ShippingUnitStatus> shippingUnitStatusList) {
		Map<String, ShippingUnitBase> suBaseMap = new HashMap<String, ShippingUnitBase>(0);
		for (ShippingUnitBase shippingUnitBase : activeLoad.getShippingUnits()) {
			suBaseMap.put(shippingUnitBase.getReferenceID(), shippingUnitBase);
		}
		for(ShippingUnitStatus shippingUnitStatus : shippingUnitStatusList) {
			Status suStatus = shippingUnitStatus.getStatus();
			boolean isResFailCode = false;
			if (suStatus != null) {
				String resFailureCode = shippingUnitStatus.getStatus().getResponseStatusCode();
				if(!resFailureCode.isEmpty() && (resFailureCode.equals("0") || resFailureCode.equals("FAILURE")))  
					isResFailCode = true;
			}
			String refIdKey = shippingUnitStatus.getShippingUnit().getReferenceID();
			if (suBaseMap.containsKey(refIdKey) && isResFailCode) {
				suBaseMap.remove(refIdKey);
			}
		}
		return suBaseMap;
	}
	
	private List<ShippingUnitStatus> getShippingUnitStatusList (List<ShippingUnitBase> suBaseList) {
		List<ShippingUnitStatus> suStatusList = new ArrayList<ShippingUnitStatus>(0);
		for (ShippingUnitBase shippingUnitBase : suBaseList) {
			ShippingUnitStatus suStatus = new ShippingUnitStatus();
			suStatus.setShippingUnit(shippingUnitBase);
			suStatusList.add(suStatus);
		}
		return suStatusList;
	}
	
}

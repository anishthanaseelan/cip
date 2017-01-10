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
package com.cts.cip.master.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.cts.cip.common.dto.CarrierDetail;
import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.CarrierServiceInfo;
import com.cts.cip.common.dto.RuleInfo;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.mapper.CarrierModelMapper;
import com.cts.cip.master.mapper.RestResourceMapper;
import com.cts.cip.master.repository.dao.MasterDataService;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.Rule;



public class MasterCarrierServiceImpl implements MasterCarrierService {
	
	Logger logger = LoggerFactory.getLogger(MasterCarrierServiceImpl.class);
	
	MasterDataService masterDataService;

	public List<CarrierInfo> findAllCarriers() {
		CarrierModelMapper carrierModelMapper = new CarrierModelMapper();
		List<Carrier> carrierList = masterDataService.findAllCarriers();
		List<CarrierInfo> carrierInfoList = new ArrayList<CarrierInfo>();
		for (Carrier carrier : carrierList) {
			CarrierInfo carrierInfo = carrierModelMapper.toCarrierInfo(carrier);
			carrierInfoList.add(carrierInfo);
		}
		return carrierInfoList;
	}

	public List<CarrierInfo> findCarriersByPage(int pageNumber, int pageSize, String sortField, String sortDirection) {
		List<Carrier> carrierList = masterDataService.findCarriersByPage(pageNumber, pageSize, sortField,
				sortDirection);
		CarrierModelMapper carrierModelMapper = new CarrierModelMapper();
		List<CarrierInfo> carrierDtoList = new ArrayList<CarrierInfo>();
		for (Carrier carrier : carrierList) {
			CarrierInfo carrierDto = carrierModelMapper.toCarrierInfo(carrier);
			carrierDtoList.add(carrierDto);
		}
		return carrierDtoList;
	}

	@Override
	public int findCarriersCount() {
		return masterDataService.findCarriersCount();
	}

	@Override
	public CarrierDetail findCarrierById(int id) {
		
		Carrier carrier =  masterDataService.findCarrierById(id);
		CarrierDetail carrierDetail=null;
		if (carrier!=null) {
			CarrierModelMapper carrierModelMapper = new CarrierModelMapper();		
			carrierDetail = carrierModelMapper.toCarrierDetail(carrier);	
			// Update the rules list for the each carrier service
			for(CarrierServiceInfo carrierServiceInfo : carrierDetail.getCarrierServiceInfoList()) {
				List<RuleInfo> ruleInfos =  findRulesByService(carrierServiceInfo.getCode());
				carrierServiceInfo.setRuleInfos(ruleInfos);
			}
		}
		return carrierDetail;
	}

	@Override
	public StatusResponse updateCarrier(CarrierDetail carrierDetail) throws BusinessException, SystemFailure {
		StatusResponse statusResponse = null;
		try {
	
				Carrier carrier = masterDataService.findCarrierById(carrierDetail.getId());
				if (carrier == null) {
					statusResponse = RestResourceMapper.buildErrorResponse(
							"Error: No carriers was found for the id provided","002");
				} else {
					CarrierModelMapper carrierModelMapper = new CarrierModelMapper();
					carrierModelMapper.toCarrierEntity(carrierDetail, carrier);
					masterDataService.updateCarrier(carrier);
					statusResponse = RestResourceMapper.buildSuccessResponse("carrier", String.valueOf(carrier.getId()),
							"Updated");
				}
			 
		} catch (DataAccessException e) {
			throw new SystemFailure ( "System Failure in updating the carrier : " + e.getMessage());		
			
		}

		return statusResponse;
	}

	@Override
	public List<RuleInfo> findRulesByService(String serviceCode) {
		
		CarrierModelMapper carrierModelMapper = new CarrierModelMapper();
		List<RuleInfo> ruleInfoList =  new ArrayList<RuleInfo>();
		
		List<Rule> ruleList = masterDataService.findRulesByService(serviceCode);
		for(Rule rule : ruleList) {
			RuleInfo ruleInfo  = carrierModelMapper.toRuleInfo(rule);
			ruleInfoList.add(ruleInfo);
		}
		
		return ruleInfoList;
	}

	public MasterDataService getMasterDataService() {
		return masterDataService;
	}

	public void setMasterDataService(MasterDataService masterDataService) {
		this.masterDataService = masterDataService;
	}

	


}

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
package com.cts.cip.master.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.dto.CarrierDetail;
import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.CarrierServiceInfo;
import com.cts.cip.common.dto.LocationInfo;
import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.dto.RuleDefinitionInfo;
import com.cts.cip.common.dto.RuleInfo;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ResponseStatus;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.CarrierService;
import com.cts.cip.master.repository.entities.Location;
import com.cts.cip.master.repository.entities.Rule;
import com.cts.cip.master.repository.entities.RuleDefinition;
import com.cts.cip.master.repository.entities.RuleTypeDefinition;
import com.cts.cip.util.CipValidator;
import com.cts.cip.util.CommonUtility;

public class CarrierModelMapper {
	
	Logger logger = LoggerFactory.getLogger(CarrierModelMapper.class);
	
	private ModelMapper modelMapper = new ModelMapper();
	
	
	
	public Carrier toCarrierEntity(CarrierInfo carrierInfo) {
		Carrier carrier = modelMapper.map(carrierInfo, Carrier.class);	
		String url = carrierInfo.getIconUrl();
		String fileName = CommonUtility.getFileNameFromUrl(url);
		carrier.setIconUrl(fileName);
		return carrier;
	}
	
	public CarrierInfo toCarrierInfo(Carrier carrier) {
		CarrierInfo carrierInfo = modelMapper.map(carrier, CarrierInfo.class);			
		String fileName =  carrierInfo.getIconUrl();
		String url = CommonUtility.getUrlFromFileName(fileName,"/properties/cip-master.properties");		
		carrierInfo.setIconUrl(url);
		return carrierInfo;
	}
	
	

		
	
	public CarrierDetail toCarrierDetail(Carrier carrier) {
		//modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		modelMapper.createTypeMap(Carrier.class, CarrierDetail.class).setPostConverter(
		        new Converter<Carrier, CarrierDetail>() {
		          public CarrierDetail convert(MappingContext<Carrier, CarrierDetail> context) {
		            List<CarrierService> CarriierSreviceList = context.getSource().getCarrierServiceList();
		            context.getDestination().carrierServiceInfoList = new ArrayList<CarrierServiceInfo>();
		            if(CarriierSreviceList!=null) {
		            	for (CarrierService carrierService :CarriierSreviceList){
		            		context.getDestination().carrierServiceInfoList.add(modelMapper.map(carrierService, CarrierServiceInfo.class));	
		            	}	
		            }
		            return context.getDestination();
		          }
		        });
		CarrierDetail carrierDetail = modelMapper.map(carrier, CarrierDetail.class);
		if (carrier.getLocation()!=null) {
			LocationInfo locationInfo = modelMapper.map(carrier.getLocation(), LocationInfo.class);		
			carrierDetail.setLocationInfo(locationInfo);
		}
		String fileName =  carrierDetail.getIconUrl();
		String url = CommonUtility.getUrlFromFileName(fileName,"/properties/cip-master.properties");		
		carrierDetail.setIconUrl(url);
		
		// Update the carrier Service Count;
		for(CarrierServiceInfo carrierServiceInfo:carrierDetail.getCarrierServiceInfoList()) {
			
			carrierDetail.setTotalServices(carrierDetail.getTotalServices()+1);
			if(carrierServiceInfo.isActive()) {
				carrierDetail.setTotalActiveServices(carrierDetail.getTotalActiveServices()+1);
			} else {
				carrierDetail.setTotalActiveServices(carrierDetail.getTotalActiveServices()+1);
			}
		}
		return carrierDetail;
	}
	
	public Carrier toCarrierEntity(CarrierDetail carrierDetail,Carrier carrier) throws BusinessException {
		
		String validationEnabled = CommonUtility.getPropertyValue("validation.enabled","/properties/cip-master.properties");
		
		if(validationEnabled!=null && validationEnabled.equalsIgnoreCase("false")) {
			logger.debug("Validation is disabled in the configuration");
		} else {
		
			CIPResponse<String> requestValidationResult = CipValidator.validateRequest(carrierDetail);
		
			if (!requestValidationResult.getStatus().equals(ResponseStatus.SUCCESS)) {			
				logger.debug("Carrier Details Validation is failed " + requestValidationResult );
				throw new BusinessException("Carrier Details validation failed:: " + requestValidationResult.getErrorDesc(),requestValidationResult.getErrorList());
			
			} 
		}
		
		
		
		// Map general attributes of the carrier
		carrier.setActive(carrierDetail.isActive());
		carrier.setDescription(carrierDetail.getDescription());
		carrier.setName(carrierDetail.getName());
		carrier.setScac(carrierDetail.getScac());
		
		// Map the address
		if (carrierDetail.getLocationInfo()!=null) {
			LocationInfo updatedLocation = carrierDetail.getLocationInfo();
			Location location = toLocationEntity(updatedLocation, carrier.getLocation());
			carrier.setLocation(location);
		}
		
		//Populate CarrierService Map based on id as key and active status as value
		Map<String,Boolean> serviceStatusMap =  new HashMap<String,Boolean>();
		Map<String, CarrierServiceInfo> carrierSvcInfoMap =  new HashMap<String,CarrierServiceInfo>();
		
		for (CarrierServiceInfo carrierServiceInfo: carrierDetail.getCarrierServiceInfoList()) {
			carrierSvcInfoMap.put(carrierServiceInfo.getCode(), carrierServiceInfo);
			serviceStatusMap.put(carrierServiceInfo.getCode(),carrierServiceInfo.isActive());
		}
		// Update mapping for carrier service lever 
		for (CarrierService carrierService: carrier.getCarrierServiceList()) {
			CarrierServiceInfo carrierServiceInfo =  carrierSvcInfoMap.get(carrierService.getCode());
			if(serviceStatusMap.get(carrierService.getCode())!=null) {
				carrierService.setActive(serviceStatusMap.get(carrierService.getCode()));
			}
			if(!carrierDetail.isActive()) {
				carrierService.setActive(false);
			}
			if(carrierServiceInfo.getScac()!=null && !carrierServiceInfo.getScac().equalsIgnoreCase("")) {
				carrierService.setScac(carrierServiceInfo.getScac());
			}
			if(carrierServiceInfo.getLocationPrimaryContactEmail()!=null && ! carrierServiceInfo.getLocationPrimaryContactEmail().equalsIgnoreCase("")) {
				carrierService.getLocation().setPrimaryContactEmail(carrierServiceInfo.getLocationPrimaryContactEmail());
			}
			if(carrierServiceInfo.getLocationPrimaryContactName()!=null &&! carrierServiceInfo.getLocationPrimaryContactName().equalsIgnoreCase("")) {
				carrierService.getLocation().setPrimaryContactName(carrierServiceInfo.getLocationPrimaryContactName());
			}
			if(carrierServiceInfo.getLocationPrimaryContactNumber()!=null && ! carrierServiceInfo.getLocationPrimaryContactNumber().equalsIgnoreCase("")) {
				carrierService.getLocation().setPrimaryContactNumber(carrierServiceInfo.getLocationPrimaryContactNumber());
			}
			
			updateRuleData(carrierService,carrierServiceInfo);
			
			
		}
		
		// Update the mapping foe rule data
		
		return carrier;
		
		
	}

	private void updateRuleData(CarrierService carrierService, CarrierServiceInfo carrierServiceInfo) {
		HashMap<String,String> ruleInfoMap = new HashMap<String,String>();
		String ruleKey;
		for (RuleInfo ruleInfo : carrierServiceInfo.getRuleInfos()) {			
			for (RuleDefinitionInfo ruleDefinitionInfo : ruleInfo.getRuleDefintionInfos()) {
				ruleKey = String.valueOf(ruleInfo.getId()) + ruleDefinitionInfo.getFieldId();
				if(ruleDefinitionInfo.getFieldValue()!=null && !ruleDefinitionInfo.getFieldValue().equalsIgnoreCase("")) {
					ruleInfoMap.put(ruleKey,ruleDefinitionInfo.getFieldValue());
				}
			}			
		}
		
		for(Rule rule:  carrierService.getRulesList()) {
			for(RuleDefinition ruleDefinition : rule.getRuleDefinitionList()) {
				ruleKey = String.valueOf(ruleDefinition.getId().getRuleId()) + String.valueOf(ruleDefinition.getId().getFieldId());
				ruleDefinition.setValue(ruleInfoMap.get(ruleKey));
				
			}
		}
		
	}

	public NodeDetail toNodeDetail(Carrier carrier) {
		// TODO Auto-generated method stub
		return null;
	}

	public RuleInfo toRuleInfo(Rule rule) {
		
		RuleInfo ruleInfo = new RuleInfo();
		ruleInfo.setId(rule.getId());
		ruleInfo.setName(rule.getName());
		ruleInfo.setDescription(rule.getDescription());
		ruleInfo.setRuleTypeName(rule.getRuleType().getName());
		
		Map<Integer,RuleTypeDefinition> ruleTypeDefMap = new HashMap<Integer,RuleTypeDefinition>();
		int fieldId = 0;
		List<RuleDefinitionInfo> ruleDefinitionInfos = new ArrayList<RuleDefinitionInfo>();
		
		for (RuleTypeDefinition ruleTypeDefinition :  rule.getRuleType().getRuleTypeDefinitions()) {
			fieldId = (int) ruleTypeDefinition.getId().getFieldId();
			ruleTypeDefMap.put(fieldId, ruleTypeDefinition);
		}
		
		for(RuleDefinition ruleDefinition : rule.getRuleDefinitionList()) {
			RuleTypeDefinition ruleTypeDefinition = ruleTypeDefMap.get((int)ruleDefinition.getId().getFieldId());
			RuleDefinitionInfo ruleDefinitionInfo = new RuleDefinitionInfo();
			ruleDefinitionInfo.setFieldValue(ruleDefinition.getValue());
			ruleDefinitionInfo.setFieldDisplayName(ruleTypeDefinition.getDisplayFieldName());
			ruleDefinitionInfo.setFieldName(ruleTypeDefinition.getFieldName());
			ruleDefinitionInfo.setFieldId((int)ruleDefinition.getId().getFieldId());
			ruleDefinitionInfos.add(ruleDefinitionInfo);
			
		}
		ruleInfo.setRuleDefintionInfos(ruleDefinitionInfos);
		return ruleInfo;
	}
	
	public Rule toRuleEntity(RuleInfo rule) {
		return null;
	}	
	
	public Location toLocationEntity(LocationInfo updatedLocation,Location location) {
		if(updatedLocation.getCity()!=null && ! updatedLocation.getCity().equalsIgnoreCase("")) {
			location.setCity(updatedLocation.getCity());
		}
		if(updatedLocation.getCountryCode()!=null && ! updatedLocation.getCountryCode().equalsIgnoreCase("")) {
			location.setCountryCode(updatedLocation.getCountryCode());		
		}
		if(updatedLocation.getName()!=null && ! updatedLocation.getName().equalsIgnoreCase("")) {
			location.setName(updatedLocation.getName());
		}
		if(updatedLocation.getPrimaryContactName()!=null && ! updatedLocation.getPrimaryContactName().equalsIgnoreCase("")) {
			location.setPrimaryContactName(updatedLocation.getPrimaryContactName());
		}
		if(updatedLocation.getPrimaryContactNumber()!=null && ! updatedLocation.getPrimaryContactNumber().equalsIgnoreCase("")) {
			location.setPrimaryContactNumber(updatedLocation.getPrimaryContactNumber());
		}
		if(updatedLocation.getPrimaryContactEmail()!=null && ! updatedLocation.getPrimaryContactEmail().equalsIgnoreCase("")) {
			location.setPrimaryContactEmail(updatedLocation.getPrimaryContactEmail());
		}
		if(updatedLocation.getStateCode()!=null && ! updatedLocation.getStateCode().equalsIgnoreCase("")) {
			location.setStateCode(updatedLocation.getStateCode());
		}
		if(updatedLocation.getZipCode()!=null && ! updatedLocation.getZipCode().equalsIgnoreCase("")) {
			location.setZipCode(updatedLocation.getZipCode());
		}
		return location;
	}
}	
	
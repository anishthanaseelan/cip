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
package com.cts.cip.core.mapper;

import org.springframework.stereotype.Service;

import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.rule.repository.entity.Carrier;
import com.cts.cip.rule.repository.entity.Location;
import com.cts.cip.util.CommonUtility;

/**
 * @author 417765
 *
 */
@Service
public class CarrierMapperImpl implements CarrierMapper{
	
	

	/* (non-Javadoc)
	 * @see com.cts.cip.core.repository.mappers.CarrierMapper#mapToModel(com.cts.cip.rule.repository.entity.Carrier)
	 */
	@Override
	public CarrierInfo mapToModel(Carrier carrier) {

		CarrierInfo carrierInfo = new CarrierInfo();
		carrierInfo.setId(String.valueOf(carrier.getId()));
		carrierInfo.setDescription(carrier.getDescription());
		carrierInfo.setActive(carrier.isActive());
		String url = CommonUtility.getUrlFromFileName(carrier.getIconUrl(),"cip-core.properties");
		carrierInfo.setIconUrl(url);
		carrierInfo.setLocationId(String.valueOf(carrier.getLocation().getId()));
		carrierInfo.setName(carrier.getName());
		carrierInfo.setScac(carrier.getScac());
		return carrierInfo;
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.core.repository.mappers.CarrierMapper#mapToEntity(com.cts.cip.common.dto.CarrierInfo)
	 */
	@Override
	public Carrier mapToEntity(CarrierInfo carrierInfo) {

		Carrier carrier = new Carrier();
		carrier.setId(Integer.parseInt(carrierInfo.getId()));
		carrier.setDescription(carrierInfo.getDescription());
		carrier.setActive(carrierInfo.isActive());
		carrier.setIconUrl(carrierInfo.getIconUrl());
		Location location = new Location();
		location.setId(Integer.parseInt(carrierInfo.getLocationId()));
		carrier.setLocation(location);
		carrier.setName(carrierInfo.getName());
		carrier.setScac(carrierInfo.getScac());
		return carrier;
	}
}
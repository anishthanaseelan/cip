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
package com.cts.cip.kpi.bo.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.dto.ShipmentLoadInfo;
import com.cts.cip.common.exceptions.CriteriaValidationException;
import com.cts.cip.kpi.repository.dao.ShippingUnitRepo;

public class ShippingListServiceImpl implements ShippingListService {

	ShippingUnitRepo shippingUnitRepo;

	

	Logger logger = LoggerFactory.getLogger(ShippingListServiceImpl.class);	

	@Override
	public Integer getgetShippingUnitCount() {
		return shippingUnitRepo.getShippingUnitCount();
	}

	@Override
	public List<ShipmentLoadInfo> getShipUnitList(SearchCriteria searchCriteria) throws CriteriaValidationException {
		return shippingUnitRepo.getShippingUnitList(searchCriteria);
	}

	@Override
	public Long getShippingUnitsCriteriaCount(SearchCriteria searchCriteria) throws CriteriaValidationException {
		return shippingUnitRepo.getShippingUnitsCriteriaCount(searchCriteria);
	}

	public ShippingUnitRepo getShippingUnitRepo() {
		return shippingUnitRepo;
	}

	public void setShippingUnitRepo(ShippingUnitRepo shippingUnitRepo) {
		this.shippingUnitRepo = shippingUnitRepo;
	}
	

}

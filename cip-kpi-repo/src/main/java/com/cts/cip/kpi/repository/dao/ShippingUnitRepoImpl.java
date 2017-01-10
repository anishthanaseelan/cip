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
package com.cts.cip.kpi.repository.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.dto.ShipmentLoadInfo;
import com.cts.cip.common.exceptions.CriteriaValidationException;
import com.cts.cip.common.model.kpi.CountByCarrier;
import com.cts.cip.common.model.kpi.SUKPIFilterCriteria;



@Service("shippingUnitRepoKPI")
public class ShippingUnitRepoImpl implements ShippingUnitRepo {
	
	@Autowired
	ShippingUnitDao repo;	
	
	Logger logger  =  LoggerFactory.getLogger(ShippingUnitRepoImpl.class);	
	
	@Override
	public Integer getShippingUnitCount(){
		return repo.getShippingUnitCount();
	}	

	@Override
	public List<ShipmentLoadInfo> getShippingUnitList(SearchCriteria searchCriteria) throws CriteriaValidationException {		
		return repo.getShippingUnitList(searchCriteria);
	}

	@Override
	public Long getShippingUnitsCriteriaCount(SearchCriteria searchCriteria) throws CriteriaValidationException {
		return repo.getShippingUnitsCriteriaCount(searchCriteria);
		
	}
	

}

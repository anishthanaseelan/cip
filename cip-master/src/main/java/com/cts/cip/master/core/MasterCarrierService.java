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

import java.util.List;

import com.cts.cip.common.dto.CarrierDetail;
import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.RuleInfo;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.StatusResponse;

public interface MasterCarrierService {
	public List<CarrierInfo> findAllCarriers();	
	public List<CarrierInfo> findCarriersByPage(int pageSize, int pageNumber,String sortField,String sortDirection);
	public CarrierDetail findCarrierById(int id);
	public int findCarriersCount();	
	StatusResponse updateCarrier(CarrierDetail carrierDetail) throws BusinessException, SystemFailure;
	public List<RuleInfo> findRulesByService(String serviceCode);
}

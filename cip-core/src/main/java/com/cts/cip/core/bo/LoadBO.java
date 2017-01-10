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

import java.util.List;

import com.cts.cip.common.dto.LoadInfo;
import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.CarrierAgentException;
import com.cts.cip.common.exceptions.MultipleResourceExistException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.LoadBase;
import com.cts.cip.common.model.LoadShippingUnitResponse;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.common.model.UnloadShippingUnitResponse;

public interface LoadBO {

	List<Load> get(String referanceId) throws ResourceNotExistException;

	//Load load(LoadBase load, List<ShippingUnitBase> shippingUnits) throws SystemFailure, BusinessException;

	/*Load unload(LoadBase load, List<ShippingUnitBase> shippingUnits)
			throws SystemFailure, com.cts.cip.common.exceptions.BusinessException;*/

	//Load manifest(LoadBase load) throws MultipleResourceExistException, ResourceNotExistException, CarrierAgentException, SystemFailure;

	Load getBySU(String surefId) throws MultipleResourceExistException, ResourceNotExistException;
	

	List<Load> findByPage(SearchCriteria searchCriteria) throws ResourceNotExistException;	

	Load getById(Integer id) throws ResourceNotExistException;

	LoadInfo findLoadInfoById(int id) throws ResourceNotExistException;

	LoadInfo findLoadInfoByName(String loadName) throws ResourceNotExistException;

	Long findCountByCriteria(SearchCriteria searchCriteria) throws ResourceNotExistException;

	<T> void  validateRequest(T request) throws BusinessException;
	
	ManifestShippingUnitsResponse manifestLoad(LoadBase load) throws ResourceNotExistException, CarrierAgentException, SystemFailure ;
	
	LoadShippingUnitResponse loadSUs(LoadBase load, List<ShippingUnitBase> shippingUnits) throws SystemFailure, BusinessException;
	
	UnloadShippingUnitResponse unloadShippingUnit(LoadBase load, List<ShippingUnitBase> shippingUnits) throws SystemFailure, BusinessException;
}

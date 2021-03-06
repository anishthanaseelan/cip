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
package com.cts.cip.core.bo.services;

import java.util.List;
import java.util.Map;

import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.LoadInfo;
import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.exceptions.MultipleResourceExistException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.ShippingUnitBase;

public interface LoadRepoService {

	Load findById(Integer id) throws ResourceNotExistException;
	
	List<Load> findByRefId(String loadReferenceId) throws ResourceNotExistException;
	
	Load findBySURefId(String surefId) throws ResourceNotExistException, MultipleResourceExistException;

	Load create(Load load);

	Load addSU(Load activeLoad, ShippingUnitBase shippingUnit);

	Load removeSU(Load activeLoad, List<ShippingUnitBase> shippingUnits) throws ResourceNotExistException;
	
	Load removeSU(Load activeLoad, ShippingUnitBase shippingUnit) throws ResourceNotExistException;

	Load updateStatus(Load activeLoad);

	List<Load> findByPage(SearchCriteria searchCriteria) throws ResourceNotExistException;

	LoadInfo findLoadInfoById(int id) throws ResourceNotExistException;

	LoadInfo findLoadInfoByName(String loadName) throws ResourceNotExistException;

	Long findCountByCriteria(SearchCriteria searchCriteria) throws ResourceNotExistException ;
	
	CarrierInfo getCarrierInfoByServiceType(String serviceTypeCd) throws SystemFailure, ResourceNotExistException ;
	
	CarrierInfo getCarrierInfoById(String carrierId) throws SystemFailure, ResourceNotExistException ;
	
	Map<String,String> getLoadCountAndWeightDetails(Integer loadId);
	
	Map<String, String> getManifestCountAndWeightDetails(Integer loadReferenceId) ;

}

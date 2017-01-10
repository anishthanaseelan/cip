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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.LoadInfo;
import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.exceptions.MultipleResourceExistException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.core.repository.LoadRepo;

public class LoadRepoServiceImpl implements LoadRepoService {

	@Autowired
	LoadRepo repo;

	Logger logger = LoggerFactory.getLogger(LoadRepoServiceImpl.class);

	@Override
	public List<Load> findByRefId(String loadReferenceId) throws ResourceNotExistException {

		return repo.findByRefId(loadReferenceId);
	}

	@Override
	public Load create(Load load) {
		return repo.create(load);
	}

	@Override
	public Load addSU(Load activeLoad, ShippingUnitBase shippingUnit) {
		return repo.addSU(activeLoad, shippingUnit);

	}

	@Override
	public Load removeSU(Load activeLoad, List<ShippingUnitBase> shippingUnit) throws ResourceNotExistException {
		return repo.removeSU(activeLoad, shippingUnit);
	}
	
	
	@Override
	public Load removeSU(Load activeLoad, ShippingUnitBase shippingUnit) throws ResourceNotExistException {
		return repo.removeSU(activeLoad, shippingUnit);
	}

	@Override
	public Load updateStatus(Load activeLoad) {
		return repo.updateStatus(activeLoad);
	}

	@Override
	public Load findBySURefId(String suRefId) throws ResourceNotExistException, MultipleResourceExistException {
		return repo.findBySURefId(suRefId);
	}

	@Override
	public Load findById(Integer id) throws ResourceNotExistException {
		return repo.findById(id);
	}

	@Override
	public List<Load> findByPage(SearchCriteria searchCriteria) throws ResourceNotExistException {
		
		return repo.findbyPage(searchCriteria);
	}

	@Override
	public LoadInfo findLoadInfoById(int id) throws ResourceNotExistException {
		
		return repo.findLoadInfoById(id);
	}

	@Override
	public LoadInfo findLoadInfoByName(String loadName) throws ResourceNotExistException {
		return repo.findLoadInfoByName(loadName);
	}

	@Override
	public Long findCountByCriteria(SearchCriteria searchCriteria) throws ResourceNotExistException {
		return repo.findCountByCriteria(searchCriteria);
	}

	/* (non-Javadoc)
	 * @see com.cts.cip.core.bo.services.LoadRepoService#getCarrierInfo(int)
	 */
	@Override
	public CarrierInfo getCarrierInfoByServiceType(String serviceTypeCd) throws SystemFailure, ResourceNotExistException {
		return repo.findCarrierByServiceTypeCd(serviceTypeCd);
	}

	@Override
	public CarrierInfo getCarrierInfoById(String carrierId) throws SystemFailure, ResourceNotExistException {
		return repo.findCarrierById(carrierId);
	}

	@Override
	public Map<String, String> getLoadCountAndWeightDetails(Integer loadReferenceId) {
		return repo.getLoadCountAndWeightDetails(loadReferenceId);
		
	}

	@Override
	public Map<String, String> getManifestCountAndWeightDetails(Integer loadReferenceId) {
		return repo.getManifestCountAndWeightDetails(loadReferenceId);
	}

	

}

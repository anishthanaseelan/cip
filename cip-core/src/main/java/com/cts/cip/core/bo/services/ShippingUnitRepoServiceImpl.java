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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.ShippingDocument;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.core.repository.ShippingUnitRepo;

public class ShippingUnitRepoServiceImpl implements ShippingUnitRepoService {

	@Autowired
	ShippingUnitRepo repo;

	Logger logger = LoggerFactory.getLogger(ShippingUnitRepoServiceImpl.class);

	public ShippingUnit findById(Integer id) {
		return repo.findById(id);
	}

	public List<ShippingUnit> findById(String refId) throws ResourceNotExistException {
		logger.debug("Finding shipping unit " + refId);
		return repo.findById(refId);
	}

	public ShippingUnit save(ShippingUnit shippingUnit) {

		return repo.save(shippingUnit);

	}

	public void updateStatus(ShippingUnit shippingUnit) throws SystemFailure {
		repo.updateStatus(shippingUnit);
		return;

	}

	public ShippingUnit update(ShippingUnit shippingUnit) {

		return repo.update(shippingUnit);

	}

	public List<ShippingUnit> findAll() {
		return repo.findAll();
	}

	public List<ShippingUnit> findByPage(Integer offset, Integer limit) {
		return repo.findByPage(offset, limit);
	}

	public Integer findTotalCount() {

		return repo.findTotalCount();
	}

	public List<ShippingUnit> findShipUnitByTrackingNum(String trackingNumber) throws ResourceNotExistException {
		logger.debug("Finding shipping unit for tracking Number " + trackingNumber);
		return repo.findByTrackingNum(trackingNumber);
	}

	public String getCarrierServiceNameByCode(String carrierServiceCode) {
		return repo.getCarrierServiceNameByCode(carrierServiceCode);
		
	}

	public List<ShippingDocument> getDocuments(String shippingUnitId) {
		return repo.getDocuments(shippingUnitId);
	}

	public void persistDocuments(String shippingUnitId, List<ShippingDocument> docList) throws SystemFailure{
		repo.persistDocuments(shippingUnitId, docList );
	}

}

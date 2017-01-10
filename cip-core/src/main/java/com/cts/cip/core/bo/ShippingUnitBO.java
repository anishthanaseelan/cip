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

import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.NodeServiceFailed;
import com.cts.cip.common.exceptions.RequestValidationException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.CreateShippingUnitResponse;
import com.cts.cip.common.model.GetDocumentsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;

public interface ShippingUnitBO {
	public ShippingUnit get(String refId) throws ResourceNotExistException, SystemFailure;

	public CreateShippingUnitResponse initate(ShippingUnit shippingUnit)
			throws BusinessException, SystemFailure, RequestValidationException;

	public ShippingUnit cancel(String suRefId) throws BusinessException, SystemFailure;

	public List<ShippingUnit> findAll();

	public List<ShippingUnit> getInOffset(Integer offset, Integer limit);

	public Integer findTotalCount();

	public Boolean isActiveShippingUnitExist(String refSUId) throws SystemFailure;

	public ShippingUnit confirm(ShippingUnitBase suBase)
			throws ResourceNotExistException, SystemFailure;

	public ShippingUnit getShipUnit(String trackingNumber) throws SystemFailure, ResourceNotExistException;

	public GetDocumentsResponse getSUDocuments(ShippingUnit shippingUnit) throws BusinessException;
	
	String getCarrierServiceNameByCode(String id);

	public void validateRequest(ShippingUnit shippingUnit) throws NodeServiceFailed, BusinessException;

	ShippingUnit get(String refId, boolean activeShipment) throws ResourceNotExistException, SystemFailure;

}

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
package com.cts.cip.core.repository;

import java.util.List;

import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.ShippingDocument;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.core.repository.entities.TransactionLog;

public interface ShippingUnitRepo {

	public ShippingUnit findById(Integer id);

	public List<ShippingUnit> findById(String id) throws ResourceNotExistException;

	public ShippingUnit updateStatus(ShippingUnit shippingUnit) throws SystemFailure;

	public ShippingUnit save(ShippingUnit model);

	public ShippingUnit update(ShippingUnit shippingUnit);

	public List<ShippingUnit> findByPage(Integer pageNumber, Integer pageSize);

	public List<ShippingUnit> findAll();

	public Integer findTotalCount();

	public List<ShippingUnit> findByTrackingNum(String trackingNumber) throws ResourceNotExistException;
	

	String getCarrierServiceNameByCode(String carrierServiceCode);
	
	void saveTransaction(TransactionLog transactionLog);
	List<TransactionLog> findTransactionsById (String id , String eventOrigin);

	public List<ShippingDocument> getDocuments(String shippingUnitId);

	public void persistDocuments(String shippingUnitId, List<ShippingDocument> docList) throws SystemFailure;
	

}

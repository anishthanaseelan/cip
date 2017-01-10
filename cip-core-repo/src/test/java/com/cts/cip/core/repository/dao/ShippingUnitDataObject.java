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
package com.cts.cip.core.repository.dao;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.cts.cip.common.model.Address;
import com.cts.cip.common.model.Dimensions;
import com.cts.cip.common.model.FileFormat;
import com.cts.cip.common.model.InvoiceDetails;
import com.cts.cip.common.model.Item;
import com.cts.cip.common.model.Order;
import com.cts.cip.common.model.Price;
import com.cts.cip.common.model.ShipperDetails;
import com.cts.cip.common.model.ShipperServiceType;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.common.model.ShippingUnitState;
import com.cts.cip.common.model.Weight;
import com.cts.cip.common.model.WeightAndDimensions;

@Service
public class ShippingUnitDataObject {

	public ShippingUnit getSU(String suRefID) {

		ShippingUnit su = new ShippingUnit();

		ShippingUnitBase shippingUnitBase = new ShippingUnitBase();

		shippingUnitBase.setReferenceID(suRefID);
		shippingUnitBase.setState(ShippingUnitState.INITIATED);
		su.setShippingUnitBase(shippingUnitBase);

		ShipperDetails shipperDetails = new ShipperDetails();
		ShipperServiceType serviceType = new ShipperServiceType();
		serviceType.setShipperServiceTypeCode("20");

		shipperDetails.setShipperServiceType(serviceType);
		su.setShipperDetails(shipperDetails);

		WeightAndDimensions wdims = new WeightAndDimensions();
		Weight weight = new Weight();
		weight.setWeight(10.01f);
		wdims.setWeight(weight);
		Dimensions dims = new Dimensions();
		dims.setHeight(10.31f);
		dims.setLength(10.31f);
		dims.setWidth(10.31f);
		wdims.setDimensions(dims);
		su.setWeightAndDimensions(wdims);

		Address address = new Address();
		address.setIndividualName("Anish Kaspaar");
		address.setAddressLine1("Address Line 1");
		address.setAddressLine2("Address Line 2");
		address.setAddressLine3("Address Line 3");
		address.setAddressLine4("Address Line 4");
		address.setCity("Bentonville");
		address.setCompanyName("Cognizant");
		address.setCountry("USA");
		address.setEmailAddress("email@email.com");
		address.setFaxNumber("1234556789");
		address.setPhoneNumber("324156556");
		address.setState("AR");
		address.setZipCode("123456");

		Order order = new Order();
		order.setBillingAddress(address);
		order.setDeliveryToAddress(address);
		order.setReturnAddress(address);
		order.setOrderNumber("Order Number1");
		order.setPoNumber("Order PO Number 1");
		order.setTrasactionControlNumber("00000000000000000012121211212");

		su.setOrder(order);

		InvoiceDetails invoice = new InvoiceDetails();

		Price price = new Price();
		price.setAmount(10.00F);

		invoice.setSalesTax(price);
		invoice.setShipmentTotalCost(price);
		invoice.setShippingAndHandlingCharges(price);
		invoice.setTotalOrderItemsCost(price);

		Item item = new Item();
		item.setDescription("Mr Mouse");
		item.setItemNumber("12234234");
		item.setQuantity((short) 2);
		item.setSequenceNumber((short) 1);
		item.setTotalPrice(price);
		item.setUnitPrice(price);

		ArrayList<Item> items = new ArrayList<>();
		items.add(item);
		items.add(item);
		items.add(item);
		items.add(item);

		// TODO : Why the method is gone invoice.setItems(items);

		su.setInvoiceDetails(invoice);
		su.setRequestedOutputFileFormat(FileFormat.PDF);

		return su;
	}

}

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
package com.cts.cip.agent.model;

import java.util.Calendar;

public class OrderDetails {

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getTrasactionControlNumber() {
		return trasactionControlNumber;
	}

	public void setTrasactionControlNumber(String trasactionControlNumber) {
		this.trasactionControlNumber = trasactionControlNumber;
	}

	public Calendar getDatePlannedShipment() {
		return datePlannedShipment;
	}

	public void setDatePlannedShipment(Calendar datePlannedShipment) {
		this.datePlannedShipment = datePlannedShipment;
	}

	public PackageInfo getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}

	public ShippingOptions getShippingOptions() {
		return shippingOptions;
	}

	public void setShippingOptions(ShippingOptions shippingOptions) {
		this.shippingOptions = shippingOptions;
	}

	public InvoiceDetails getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(InvoiceDetails invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	public BillToAddress getBillToAddress() {
		return billToAddress;
	}

	public void setBillToAddress(BillToAddress billToAddress) {
		this.billToAddress = billToAddress;
	}

	private String orderNumber;
	private String poNumber;
	private String trasactionControlNumber;
	private Calendar datePlannedShipment;
	private PackageInfo packageInfo;
	private ShippingOptions shippingOptions;
	private InvoiceDetails invoiceDetails;
	private BillToAddress billToAddress;

}

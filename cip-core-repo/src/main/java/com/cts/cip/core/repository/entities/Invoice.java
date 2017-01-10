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
package com.cts.cip.core.repository.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the invoice database table.
 * 
 */
@Entity
@Table(name = "invoice")
@NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i")
public class Invoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "sales_tax")
	private Float salesTax;

	@Column(name = "shipment_total")
	private Float shipmentTotal;

	@Column(name = "shipping_handling")
	private Float shippingHandling;

	@Column(name = "sub_total")
	private Float subTotal;

	// bi-directional many-to-one association to InvoiceItem
	@OneToMany(mappedBy = "invoice", fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
	private List<InvoiceItem> invoiceItems;

	public Invoice() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Float getSalesTax() {
		return this.salesTax;
	}

	public void setSalesTax(Float salesTax) {
		this.salesTax = salesTax;
	}

	public Float getShipmentTotal() {
		return this.shipmentTotal;
	}

	public void setShipmentTotal(Float shipmentTotal) {
		this.shipmentTotal = shipmentTotal;
	}

	public Float getShippingHandling() {
		return this.shippingHandling;
	}

	public void setShippingHandling(Float shippingHandling) {
		this.shippingHandling = shippingHandling;
	}

	public Float getSubTotal() {
		return this.subTotal;
	}

	public void setSubTotal(Float subTotal) {
		this.subTotal = subTotal;
	}

	public List<InvoiceItem> getInvoiceItems() {
		return this.invoiceItems;
	}

	public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

}
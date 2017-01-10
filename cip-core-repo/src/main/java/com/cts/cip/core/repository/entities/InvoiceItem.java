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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the invoice_item database table.
 * 
 */
@Entity
@IdClass(InvoiceItemPK.class)
@Table(name = "invoice_item")
@NamedQuery(name = "InvoiceItem.findAll", query = "SELECT i FROM InvoiceItem i")
public class InvoiceItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private Integer invoiceId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "line_number", nullable = false)
	private short lineNumber;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "invoice_id", nullable = false, insertable = false, updatable = false)
	private Invoice invoice;

	private String description;

	private Short quantity;

	@Column(name = "secondary_description")
	private String secondaryDescription;

	@Column(name = "total_price")
	private Float totalPrice;

	@Column(name = "unit_price")
	private Float unitPrice;

	public InvoiceItem() {
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public short getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(short lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Short getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	public String getSecondaryDescription() {
		return this.secondaryDescription;
	}

	public void setSecondaryDescription(String secondaryDescription) {
		this.secondaryDescription = secondaryDescription;
	}

	public Float getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Float getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}
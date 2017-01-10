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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the invoice_item database table.
 * 
 */
@Embeddable
public class InvoiceItemPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "invoice_id")
	private Integer invoiceId;

	@Column(name = "line_number")
	private short lineNumber;

	public InvoiceItemPK() {
	}

	public Integer getInvoiceId() {
		return this.invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public short getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(short lineNumber) {
		this.lineNumber = lineNumber;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof InvoiceItemPK)) {
			return false;
		}
		InvoiceItemPK castOther = (InvoiceItemPK) other;
		return (this.invoiceId == castOther.invoiceId) && (this.lineNumber == castOther.lineNumber);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.invoiceId;
		hash = hash * prime + ((int) this.lineNumber);

		return hash;
	}
}
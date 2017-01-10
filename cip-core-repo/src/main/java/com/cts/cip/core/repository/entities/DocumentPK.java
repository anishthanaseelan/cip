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
 * The primary key class for the document database table.
 * 
 */
@Embeddable
public class DocumentPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "shipping_unit_id")
	private String shippingUnitId;

	@Column(name = "document_format")
	private String documentFormat;
	
	@Column(name = "document_type")
	private String documentType;

	/**
	 * @return the shippingUnitId
	 */
	public String getShippingUnitId() {
		return shippingUnitId;
	}

	/**
	 * @param shippingUnitId the shippingUnitId to set
	 */
	public void setShippingUnitId(String shippingUnitId) {
		this.shippingUnitId = shippingUnitId;
	}

	/**
	 * @return the documentFormat
	 */
	public String getDocumentFormat() {
		return documentFormat;
	}

	/**
	 * @param documentFormat the documentFormat to set
	 */
	public void setDocumentFormat(String documentFormat) {
		this.documentFormat = documentFormat;
	}

	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	

	
}
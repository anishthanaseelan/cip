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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the document database table.
 * 
 */
@Entity
@Table(name = "document")
@NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d")
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocumentPK id;
	
	@Column(name = "document_name")
	private String documentName;

	@Column(name = "document_url")
	private String documentUrl;

	/**
	 * @return the id
	 */
	public DocumentPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(DocumentPK id) {
		this.id = id;
	}

	/**
	 * @return the documentName
	 */
	public String getDocumentName() {
		return documentName;
	}

	/**
	 * @param documentName the documentName to set
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * @return the documentUrl
	 */
	public String getDocumentUrl() {
		return documentUrl;
	}

	/**
	 * @param documentUrl the documentUrl to set
	 */
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	

	

}
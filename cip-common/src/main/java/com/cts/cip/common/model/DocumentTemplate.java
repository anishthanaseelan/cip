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
/**
 * 
 */
package com.cts.cip.common.model;



import com.cts.cip.common.model.ShippingDocumentType;

/**
 * @author 417765
 *
 */
public class DocumentTemplate {

	/**
	 * Default constructor
	 */
	public DocumentTemplate() {
		// TODO Auto-generated constructor stub
	}
	
	private int id;
	
	private String path;
	
	private ShippingDocumentType type;
	
	private ShippingDocumentContentType outputContentType;


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the templatePath
	 */
	public String getTemplatePath() {
		return path;
	}

	/**
	 * @param templatePath the templatePath to set
	 */
	public void setTemplatePath(String templatePath) {
		this.path = templatePath;
	}

	/**
	 * @return the templateType
	 */
	public ShippingDocumentType getTemplateType() {
		return type;
	}

	/**
	 * @param templateType the templateType to set
	 */
	public void setTemplateType(ShippingDocumentType templateType) {
		this.type = templateType;
	}

	public ShippingDocumentContentType getOutputContentType() {
		return outputContentType;
	}

	public void setOutputContentType(ShippingDocumentContentType outputContentType) {
		this.outputContentType = outputContentType;
	}


}

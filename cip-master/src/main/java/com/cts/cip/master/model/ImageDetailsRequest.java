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
package com.cts.cip.master.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author 417765
 *
 */
@XmlRootElement(name = "ImageDetailsRequest")
public class ImageDetailsRequest {

	/**
	 * 
	 */
	public ImageDetailsRequest() {
		// TODO Auto-generated constructor stub
	}
	
	private String fileName;
	
	private String base64Content;
	
	/*private String parentResourceName;
	
	private Integer parentResourceId;*/

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the base64Content
	 */
	public String getBase64Content() {
		return base64Content;
	}

	/**
	 * @param base64Content the base64Content to set
	 */
	public void setBase64Content(String base64Content) {
		this.base64Content = base64Content;
	}

	/**
	 * @return the parentResourceName
	 *//*
	public String getParentResourceName() {
		return parentResourceName;
	}

	*//**
	 * @param parentResourceName the parentResourceName to set
	 *//*
	public void setParentResourceName(String parentResourceName) {
		this.parentResourceName = parentResourceName;
	}

	*//**
	 * @return the parentResourceId
	 *//*
	public Integer getParentResourceId() {
		return parentResourceId;
	}

	*//**
	 * @param parentResourceId the parentResourceId to set
	 *//*
	public void setParentResourceId(Integer parentResourceId) {
		this.parentResourceId = parentResourceId;
	}*/

}

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
package com.cts.cip.master.repository.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the template database table.
 * 
 */
@Entity
@Table(name="template_group")
//@IdClass(TemplateGroupPK.class)
@NamedQuery(name="TemplateGroup.findAll", query="SELECT r FROM TemplateGroup r")
public class TemplateGroup implements Serializable {
	private static final long serialVersionUID = 1L;

		
	@Id
	@Column(name="template_id")
	private int id;

//	@Id
	@Column(name="carrier_service_code")
	private short carrierServiceCode;


	private String name;

	@OneToMany(mappedBy = "templateGroup", fetch = FetchType.EAGER)
	private List<Template> templateList;
	

	public TemplateGroup() {
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<Template> getTemplateList() {
		return templateList;
	}

	public void setTemplateList(List<Template> templateList) {
		this.templateList = templateList;
	}


	public short getCarrierServiceCode() {
		return carrierServiceCode;
	}


	public void setCarrierServiceCode(short carrierServiceCode) {
		this.carrierServiceCode = carrierServiceCode;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	
		
}
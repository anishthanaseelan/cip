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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the template database table.
 * 
 */
@Entity
@Table(name = "template")
@NamedQuery(name = "Template.findAll", query = "SELECT r FROM Template r")
public class Template implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String description;

	private String name;

	@Column(name = "template_file_format")
	private String templateOutputFormat;

	@Column(name = "template_file_path")
	private String templatePath;

	@Column(name = "template_type")
	private String templateDocumentType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="template_group_id")
	private TemplateGroup templateGroup;
	
/*	@OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
	private List<TemplateGroup> templateGroupList;*/

	public Template() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateOutputFormat() {
		return templateOutputFormat;
	}

	public void setTemplateOutputFormat(String templateOutputFormat) {
		this.templateOutputFormat = templateOutputFormat;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getTemplateDocumentType() {
		return templateDocumentType;
	}

	public void setTemplateDocumentType(String templateDocumentType) {
		this.templateDocumentType = templateDocumentType;
	}

	public TemplateGroup getTemplateGroup() {
		return templateGroup;
	}

	public void setTemplateGroup(TemplateGroup templateGroup) {
		this.templateGroup = templateGroup;
	}

}
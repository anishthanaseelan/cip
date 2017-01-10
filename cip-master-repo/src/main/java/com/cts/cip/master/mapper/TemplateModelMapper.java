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
package com.cts.cip.master.mapper;

import com.cts.cip.common.model.DocumentTemplate;
import com.cts.cip.common.model.ShippingDocumentContentType;
import com.cts.cip.common.model.ShippingDocumentType;
import com.cts.cip.master.repository.entities.Template;

public class TemplateModelMapper {

	public Template toTemplateEntity(DocumentTemplate docTemplate) {
		Template template = new Template();

		template.setDescription(null);
		template.setId(docTemplate.getId());
		template.setName("");
		template.setTemplateDocumentType(docTemplate.getTemplateType().value());
		template.setTemplateOutputFormat(docTemplate.getOutputContentType().value());
		template.setTemplatePath(docTemplate.getTemplatePath());

		return template;
	}

	public DocumentTemplate toDocTemplate(Template template) {
		DocumentTemplate docTemplate = new DocumentTemplate();
		docTemplate.setId(template.getId());
		docTemplate.setOutputContentType(ShippingDocumentContentType.valueOf(template.getTemplateOutputFormat()));
		docTemplate.setTemplatePath(template.getTemplatePath());
		docTemplate.setTemplateType(ShippingDocumentType.valueOf(template.getTemplateDocumentType()));
		return docTemplate;
	}

}

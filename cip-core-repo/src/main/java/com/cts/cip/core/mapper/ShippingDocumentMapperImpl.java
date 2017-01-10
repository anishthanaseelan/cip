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
package com.cts.cip.core.mapper;

import org.springframework.stereotype.Service;

import com.cts.cip.common.model.ShippingDocument;
import com.cts.cip.common.model.ShippingDocumentContentType;
import com.cts.cip.common.model.ShippingDocumentType;
import com.cts.cip.core.repository.entities.Document;
import com.cts.cip.core.repository.entities.DocumentPK;

/**
 * @author 417765
 *
 */
@Service
public class ShippingDocumentMapperImpl implements ShippingDocumentMapper{

	@Override
	public ShippingDocument mapToModel(Document docEntity) {
		
		ShippingDocument shippingDocument = new ShippingDocument();
		shippingDocument.setDocId(docEntity.getId().getShippingUnitId());
		shippingDocument.setDocumentName(docEntity.getDocumentName());
		shippingDocument.setType(ShippingDocumentType.valueOf(docEntity.getId().getDocumentType()));
		shippingDocument.setContentType(ShippingDocumentContentType.valueOf(docEntity.getId().getDocumentFormat()));
		shippingDocument.setUrl(docEntity.getDocumentUrl());
		
		return shippingDocument;
	}

	@Override
	public Document mapToEntity(ShippingDocument model) {
		
		Document  doc = new Document();
		DocumentPK docPk = new DocumentPK();
		docPk.setShippingUnitId(model.getDocId());
		docPk.setDocumentType(model.getType().value());
		docPk.setDocumentFormat(model.getContentType().value());
		doc.setId(docPk);
		System.out.println("Document Name" + model.getDocumentName());
		doc.setDocumentName(model.getDocumentName());
		doc.setDocumentUrl(model.getUrl());
		
		return doc;
	}

	
}

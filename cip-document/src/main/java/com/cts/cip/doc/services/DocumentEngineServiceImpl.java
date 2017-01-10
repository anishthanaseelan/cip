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
package com.cts.cip.doc.services;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.exceptions.UnsupportedTargetFormatException;
import com.cts.cip.common.model.DocumentTemplate;
import com.cts.cip.common.model.GetDocumentsResponse;
import com.cts.cip.common.model.Item;
import com.cts.cip.common.model.ShippingDocument;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.response.CIPResponseBuilder;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.doc.constants.CipDocumentConstants;
import com.cts.cip.doc.engine.FOPDocumentGenerator;
import com.cts.cip.doc.engine.ZPLDocumentGenerator;


public class DocumentEngineServiceImpl implements DocumentEngineService {

	Logger logger = LoggerFactory.getLogger(DocumentEngineServiceImpl.class);
	
	MasterService masterService;
	
	ZPLDocumentGenerator zplEngine;
	
	FOPDocumentGenerator fopEngine;	

	String labelPath;	
	
	String labelURL;

	@Override
	public CIPResponse<GetDocumentsResponse> create(ShippingUnit request) {
		GetDocumentsResponse payload = new GetDocumentsResponse();
		CIPResponseBuilder<GetDocumentsResponse> responseBuilder = new CIPResponseBuilder<>();
		logger.debug(" inside DocumentEngineServiceImpl ::: method create - "+request.getShipperDetails().getShipperServiceType().getShipperServiceTypeCode());
		List<DocumentTemplate> templates = getTemplateList(request);
		List<ShippingDocument> documents = null;
		try {
			documents = createDocuments(request, templates);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return responseBuilder.buildPayload(e, null);
		}
		payload.setDocuments(documents);
		return responseBuilder.buildPayload(null, payload);

	}

	private List<ShippingDocument> createDocuments(ShippingUnit payload, List<DocumentTemplate> templates)
			throws SystemFailure, BusinessException {
		List<ShippingDocument> documents = new ArrayList<>();
		List<String> giftMessageList = null;
		List<Item> itemList = new ArrayList<Item>();
		if(payload!= null && payload.getInvoiceDetails() != null){			
			for(Item item : payload.getInvoiceDetails().getItems()){				
				if(item.getGiftMessages() != null && item.getGiftMessages().getGiftMessage() != null){					
					 for(String message : item.getGiftMessages().getGiftMessage()){						 
						 if(giftMessageList == null){
							 giftMessageList = new ArrayList<String>();
						 }						 
						 giftMessageList.add(item.getSequenceNumber()+" "+message);
					 }					
					 payload.setGiftMessage(giftMessageList);
				}				
				if(item.getSecondaryDescriptions() != null && item.getSecondaryDescriptions().getDescription()!= null){					
					List<Item> tmpList = null;
					for(String desc: item.getSecondaryDescriptions().getDescription()){
						Item tmpItem = new Item();
						if(tmpList == null){
							tmpList = new ArrayList<Item>();
							tmpList.add(item);
							tmpItem.setItemNumber("Serial #(s)");
						}
						tmpItem.setDescription(desc);
						tmpList.add(tmpItem);
					}
					itemList.addAll(tmpList);
					
				}
				else{					
					itemList.add(item);
				}				
			}
			payload.setItemList(itemList);
			
			if(payload.getRequestedOutputFileFormat().value().equals("ZPL")){
				String encodedString = payload.getMaxiCode();				
				logger.debug("Maxicode before conversion"+encodedString);
				String finalString = new String();
				finalString = encodedString.replace(CipDocumentConstants.RS_SEPERATOR, "_1E");
				finalString = finalString.replace(CipDocumentConstants.GS_SEPERATOR, "_1D");
				finalString = finalString.replace(CipDocumentConstants.FS_SEPERATOR, "_1C");
				finalString = finalString.replace(CipDocumentConstants.EOT_SEPERATOR, "_04");
				logger.debug("Maxicode after conversion"+finalString);
				payload.setMaxiCode(finalString);
			}			
		}
		
		String xmlAsString = "";
		try {
			xmlAsString = convertToXml(payload, ShippingUnit.class);
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
			throw new SystemFailure(e.getMessage());	
		}
		for (DocumentTemplate template : templates) {
			String targetFileName = createFileName(payload.getShippingUnitBase().getReferenceID(), template);
			String generatedabelURL = labelURL+targetFileName;						
			logger.debug("The Url is " + labelURL);
			switch (payload.getRequestedOutputFileFormat()) {
			case PDF:
			case JPG:
			case PNG:				
				try {
					fopEngine.generate(xmlAsString, template.getTemplatePath(), template.getOutputContentType(),
							labelPath + targetFileName);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new SystemFailure(e.getMessage());
				}
				break;
			case ZPL:	
				try {
				zplEngine.generate(xmlAsString, template.getTemplatePath(), labelPath + targetFileName);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new SystemFailure(e.getMessage());
				}
				break;
			default:
				throw new UnsupportedTargetFormatException(
						"Target file format " + template.getOutputContentType() + " is not supported");

			}
			documents.add(createDocumentPayload(template, targetFileName, generatedabelURL));
		}
		return documents;
	}

	private ShippingDocument createDocumentPayload(DocumentTemplate template, String targetFileName, String url) {
		ShippingDocument document = new ShippingDocument();
		document.setContentType(template.getOutputContentType());
		document.setType(template.getTemplateType());
		document.setDocumentName(targetFileName);
		document.setUrl(url); 
		return document;
	}

	private String createFileName(String referenceID, DocumentTemplate template) {
		String suffix;
		String extn;
		switch(template.getTemplateType()){
			case INVOICE_WITH_LABEL:
			case LABEL:	
				suffix = "1";
				break;
			case REPRINT_LABEL:
				suffix = "2";
				break;
			default:
				suffix = "0";
				break;
		}
		
		switch(template.getOutputContentType()){
		case ZPL:
			extn = "BUF";
			break;
		default:
			extn = template.getOutputContentType().toString();
			break;
		}
		
		
		
		return referenceID + "_" + suffix + "." + extn;
	}

	private String convertToXml(Object source, Class<ShippingUnit> type) throws JAXBException {		
		StringWriter sw = new StringWriter();
		JAXBContext carContext = JAXBContext.newInstance(type);
		Marshaller carMarshaller = carContext.createMarshaller();
		carMarshaller.marshal(source, sw);
		return sw.toString();
	}

	private List<DocumentTemplate> getTemplateList(ShippingUnit request) {
		List<DocumentTemplate> templates = masterService.getTempleteByCarrierService(
				request.getShipperDetails().getShipperServiceType().getShipperServiceTypeCode(),
				request.getRequestedOutputFileFormat());
		return templates;
	}

	public MasterService getMasterService() {
		return masterService;
	}

	public void setMasterService(MasterService masterService) {
		this.masterService = masterService;
	}

	public ZPLDocumentGenerator getZplEngine() {
		return zplEngine;
	}

	public void setZplEngine(ZPLDocumentGenerator zplEngine) {
		this.zplEngine = zplEngine;
	}

	public FOPDocumentGenerator getFopEngine() {
		return fopEngine;
	}

	public void setFopEngine(FOPDocumentGenerator fopEngine) {
		this.fopEngine = fopEngine;
	}

	public String getLabelPath() {
		return labelPath;
	}

	public void setLabelPath(String labelPath) {
		this.labelPath = labelPath;
	}

	public String getLabelURL() {
		return labelURL;
	}

	public void setLabelURL(String labelURL) {
		this.labelURL = labelURL;
	}	

}

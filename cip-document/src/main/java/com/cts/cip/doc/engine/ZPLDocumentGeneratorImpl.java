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
package com.cts.cip.doc.engine;

import java.io.File;
import java.io.StringReader;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.exceptions.SystemFailure;


public class ZPLDocumentGeneratorImpl implements ZPLDocumentGenerator  {

	private static Logger logger = LoggerFactory.getLogger(ZPLDocumentGeneratorImpl.class);		
	
	TransformerFactory transformerFactory;

	public void generate(String xmlAsString, String templatePath,String targetFileName) throws SystemFailure {	 
		logger.info("ZPLDocumentGenerator :: generate method invoked ");	
		try {
			Transformer transformer = transformerFactory.newTransformer(new StreamSource(templatePath));
			Source src = new StreamSource(new StringReader(xmlAsString));
			Result outputTarget = new StreamResult(new File(targetFileName));
			transformer.transform(src, outputTarget);
		} catch (TransformerException e) {
			logger.error(e.getMessage(), e);
			throw new SystemFailure(e.getMessage());	
		}	
		
	}

	public TransformerFactory getTransformerFactory() {
		return transformerFactory;
	}

	public void setTransformerFactory(TransformerFactory transformerFactory) {
		this.transformerFactory = transformerFactory;
	}

}

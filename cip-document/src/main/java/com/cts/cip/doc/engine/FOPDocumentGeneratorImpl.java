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

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.exceptions.UnsupportedTargetFormatException;
import com.cts.cip.common.model.ShippingDocumentContentType;


public class FOPDocumentGeneratorImpl implements FOPDocumentGenerator, ApplicationContextAware, ErrorListener, ErrorHandler {

	private static Logger logger = LoggerFactory.getLogger(FOPDocumentGeneratorImpl.class);	
	
	private ApplicationContext context;
	
	private FopFactory fopFactory;

	public void generate(String xmlAsString, String templatePath, ShippingDocumentContentType outputContentType,
			String targetFileName) throws SystemFailure {

		logger.info("Data for fop is : " + xmlAsString);

		Transformer transformer = (Transformer) context.getBean(templatePath);

		transformer.setParameter("versionParam", "2.0");

		transformer.setErrorListener(this);

		try (OutputStream out = new java.io.BufferedOutputStream(new java.io.FileOutputStream(targetFileName))) {

			Fop fop = fopFactory.newFop(targetFOPtype(outputContentType), out);

			Source src = new StreamSource(new StringReader(xmlAsString));

			Result res = new SAXResult(fop.getDefaultHandler());

			transformer.transform(src, res);

			transformer.clearParameters();

			out.flush();

		} catch (FOPException | TransformerException | UnsupportedTargetFormatException | IOException e) {
			logger.error(e.getMessage(), e);
			throw new SystemFailure(e.getMessage());	
		}
		logger.info("Completed the generating the Document. And its path is--> " + targetFileName);
	}

	private String targetFOPtype(ShippingDocumentContentType outputContentType)
			throws UnsupportedTargetFormatException {
		switch (outputContentType) {
		case PDF:
			return MimeConstants.MIME_PDF;
		case PNG:
			return MimeConstants.MIME_PNG;
		case JPG:
			return MimeConstants.MIME_JPEG;
		case PCL:
			return MimeConstants.MIME_PCL;
		default:
			throw new UnsupportedTargetFormatException(
					"Target format " + outputContentType.toString() + " is not supported");
		}

	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		logger.error(exception.getMessage(), exception);
	}

	@Override
	public void error(SAXParseException exception) throws SAXException {
		logger.error(exception.getMessage(), exception);
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		logger.error(exception.getMessage(), exception);
	}

	@Override
	public void warning(TransformerException exception) throws TransformerException {
		logger.error(exception.getMessage(), exception);
	}

	@Override
	public void error(TransformerException exception) throws TransformerException {
		logger.error(exception.getMessage(), exception);
	}

	@Override
	public void fatalError(TransformerException exception) throws TransformerException {
		logger.error(exception.getMessage(), exception);
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
		
	}

	public FopFactory getFopFactory() {
		return fopFactory;
	}

	public void setFopFactory(FopFactory fopFactory) {
		this.fopFactory = fopFactory;
	}

}

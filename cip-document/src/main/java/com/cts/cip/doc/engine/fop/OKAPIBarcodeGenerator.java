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
package com.cts.cip.doc.engine.fop;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import uk.org.okapibarcode.MakeCustomBarcode;

public class OKAPIBarcodeGenerator {
	
	static Logger logger = LoggerFactory.getLogger(OKAPIBarcodeGenerator.class);

	public static org.w3c.dom.DocumentFragment generate(String dataInput,int type,int symbolHeight,boolean reverseColour,
			String foregroundColour,String backgroundColour,int symbolScale,int symbolColumns,
			int symbolVersion,int symbolECC,String primaryData,int encodeMode,boolean dataGs1Mode,
			boolean dataBinaryMode,boolean supressHrt, boolean superHrt,boolean makeSquare,boolean addReaderInit) throws ParserConfigurationException,
			SAXException, IOException {		
		MakeCustomBarcode makeCustomBarcode = new MakeCustomBarcode();		
		String svgXMLString = makeCustomBarcode.process(dataInput,type,symbolHeight,reverseColour,
				foregroundColour,backgroundColour,symbolScale,symbolColumns,
				symbolVersion,symbolECC,primaryData,encodeMode,dataGs1Mode,
				dataBinaryMode,supressHrt, superHrt,makeSquare,addReaderInit,0,0);		
		svgXMLString = svgXMLString.replaceAll("[^\\p{Print}]", "");		
		org.jdom.input.SAXBuilder saxBuilder = new SAXBuilder();
		try {
		    org.jdom.Document doc = saxBuilder.build(new StringReader(svgXMLString));
		    return getDOMFragment(getDOM(doc));
		} catch (JDOMException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static org.w3c.dom.Document getDOM(org.jdom.Document doc) {
        org.jdom.output.DOMOutputter output = new org.jdom.output.DOMOutputter();
        try {
            return output.output(doc);
        } catch (JDOMException e) {
        	logger.error(e.getMessage(), e);
            return null;
        }
	}
	
	public static org.w3c.dom.DocumentFragment getDOMFragment(org.w3c.dom.Document doc) {	        
        org.w3c.dom.DocumentFragment frag = doc.createDocumentFragment();
        frag.appendChild(doc.getDocumentElement());
        return frag;
    }
     
}

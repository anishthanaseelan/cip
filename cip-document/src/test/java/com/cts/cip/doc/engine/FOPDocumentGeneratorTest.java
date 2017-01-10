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

import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.ShippingDocumentContentType;


@ContextConfiguration(locations = { "classpath:spring\\document-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)

public class FOPDocumentGeneratorTest {

	@Autowired
	FOPDocumentGenerator fopEngine;
	
	@Autowired
	@Qualifier("sampleInput")
	StreamSource streamSource;
	
	@Test
	public void testGenerate() throws SystemFailure{

		Assert.assertTrue(fopEngine!= null);
		
		fopEngine.generate(streamSource.getSystemId(), "sampleShippingLabel", ShippingDocumentContentType.PDF, "SampleLabel.pdf");
	}
	
	
	
}

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
package com.cts.cip.core.repository.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.cip.common.model.ShippingDocument;
import com.cts.cip.common.model.ShippingDocumentContentType;
import com.cts.cip.common.model.ShippingDocumentType;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.core.repository.ShippingUnitRepo;
import com.cts.cip.core.test.util.TestDBUtility;

@ContextConfiguration(locations = { "classpath:spring/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test-jpa")
public class ShippingUnitRepoImplTest {
	
	Logger logger = LoggerFactory.getLogger(ShippingUnitRepoImplTest.class);

	@Autowired
	ShippingUnitRepo shippingUnitRepo;

	@Autowired
	TestDBUtility dbUtil;
	
	@Autowired
	ShippingUnitDataObject suDataObj;

	@Before
	public void initDB() {

		try {
			dbUtil.setDBData("src/test/resources/db/TransationTestData.sql");
		} catch (IOException e) {
			logger.error("Couldn't read sql file" + e.getMessage() , e);
		}
	}

	
	@Test
	public void testFind() {
		ShippingUnit shippingUnit = shippingUnitRepo.findById(1);
		assertTrue(shippingUnit.getShippingUnitBase().getId() == 1);
	}

	@Test
	public void testSave() {
		
		String suRefID = "12312312";

		shippingUnitRepo.save(suDataObj.getSU(suRefID));

		try {
			List<ShippingUnit> suList = shippingUnitRepo.findById(suRefID);
			if (suList == null || suList.isEmpty()) {
				fail("Data not fetched back");
			} else {
				assertTrue(suList.get(0).getShippingUnitBase().getReferenceID().equals(suRefID));
			}
		} catch (Exception ex) {

			logger.error(ex.getMessage() , ex);
			fail("Got Exception");
		}
	} 
	
	@Test
	public void testGetPersistedDocuments() {
		
		List<ShippingDocument> shippingDocs = null;
		String shippingUnitId =  "1000000000001";
		try {
			shippingDocs = shippingUnitRepo.getDocuments(shippingUnitId);
			if (shippingDocs == null || shippingDocs.isEmpty()) {
				fail("Data not fetched");
			} else {
				assertTrue(shippingDocs.get(0).getDocId().equals(shippingUnitId));
			}
		} catch (Exception ex) {

			logger.error(ex.getMessage() , ex);
			fail("Got Exception");
		}
	} 
	
	@Test
	public void testDocumentPersist() {
		
		List<ShippingDocument> shippingDocs = new ArrayList<ShippingDocument>(0);
		ShippingDocument shippingDoc1 = createShippingDocument(ShippingDocumentType.LABEL, 
				ShippingDocumentContentType.PDF, "/test/test/", "ups_label1");
		shippingDocs.add(shippingDoc1);
		ShippingDocument shippingDoc2 = createShippingDocument(ShippingDocumentType.LABEL, 
				ShippingDocumentContentType.PDF, "/test/test/", "ups_label1");
		shippingDocs.add(shippingDoc2);
		String suId = "1000000001";
		try {
			shippingUnitRepo.persistDocuments(suId, shippingDocs);
			
			shippingDocs = shippingUnitRepo.getDocuments(suId);
			if (shippingDocs == null || shippingDocs.isEmpty()) {
				fail("Data not fetched");
			} else {
				assertTrue(shippingDocs.get(0).getDocId().equals(suId));
			}
		} catch (Exception ex) {

			logger.error(ex.getMessage() , ex);
			fail("Got Exception");
		}
	}
	
	private ShippingDocument createShippingDocument(ShippingDocumentType type,  
			ShippingDocumentContentType fileType, String url, String name) {
		
		ShippingDocument shippingDocument1 = new ShippingDocument();
		shippingDocument1.setUrl(url);
		shippingDocument1.setDocumentName(name);
		shippingDocument1.setType(type);
		shippingDocument1.setContentType(fileType);
		
		return shippingDocument1;
	}

}

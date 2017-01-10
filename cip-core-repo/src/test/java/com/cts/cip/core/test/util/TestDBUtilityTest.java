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
package com.cts.cip.core.test.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations = { "classpath:spring/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test-jpa")
@Transactional
public class TestDBUtilityTest {

	@Autowired
	TestDBUtility testUtility;

	@PersistenceContext
	private EntityManager em;

	Logger logger = LoggerFactory.getLogger(TestDBUtilityTest.class);

	@Test
	public void test() {
		try {
			testUtility.setDBData("src/test/resources/db/TransationTestData.sql");
			assertTrue(em.createNativeQuery("select count(*) from shipping_unit").getResultList().size() > 0);
		} catch (IOException e) {
			logger.error(e.getMessage() , e);
			fail("Couldn't read sql File");
		}
		return;
	}

}

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
package com.cts.cip.core.bo.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.exceptions.NodeServiceFailed;
import com.cts.cip.core.utilities.CarrierDetail;
import com.cts.cip.core.utilities.CarrierDetailsMap;
import com.cts.cip.util.ValidationResult;

public class NodeCarrierServiceTest {

	Logger logger = LoggerFactory.getLogger(NodeCarrierServiceTest.class);

	private void getValidationResult(int i, String string, String valString) {

		NodeDetailServiceImpl nodeCarrierService = new NodeDetailServiceImpl();
		CarrierDetail carrierDetail = new CarrierDetailsMap();
		nodeCarrierService.setCarrierDetail(carrierDetail);
		ValidationResult validationResult = null;
		try {
			validationResult = nodeCarrierService.isActiveNodeService(i, string);
		} catch (NodeServiceFailed e) {
			logger.info(e.getMessage(), e);
			fail("testInvalidNode : Node Service Failed");
		}
		if (validationResult != null) {
			assertEquals(validationResult.getCode(), valString);
		} else {
			fail("validationResult is empty");
		}

		return;
	}
	
	@Ignore
	@Test
	public void testInvalidNode() {

		getValidationResult(1008, "1", "01");

	}



	@Ignore
	@Test
	public void testInvlidCarrierService() {

		getValidationResult(1001, "78", "04");

	}

	
	@Test
	public void testValidCarrierService() {

		getValidationResult(1001, "2", "00");

	}

	@Ignore
	@Test
	public void testInActiveService() {
		getValidationResult(1004, "9", "03");

	}

	@Ignore
	@Test
	public void testInActiveNode() {

		getValidationResult(1005, "1", "02");
	}

}

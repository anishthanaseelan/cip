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
package com.cts.cip.core.bo;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.GetDocumentsResponse;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.common.model.ShippingUnitState;
import com.cts.cip.core.repository.dao.ShippingUnitDataObject;
import com.cts.cip.core.test.util.TestDBUtility;

@ContextConfiguration(locations = { "classpath:spring/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test-jpa")

public class ShippingUnitBOTest {

	Logger logger = LoggerFactory.getLogger(ShippingUnitBOTest.class);

	@Autowired
	ShippingUnitBO bo;

	@Autowired
	TestDBUtility dbUtil;

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	ShippingUnitDataObject suDataObj;


	
	@Before // Need to find a way to execute this method only once
	public void initDB() {
		try {
			dbUtil.setDBData("src/test/resources/db/TransationTestData.sql");
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		return;
	}
	
	@Test
	public void testFindAll() {
		
		List<ShippingUnit> suList = bo.findAll();
		
		if ( suList== null || suList.isEmpty())
			fail ( "Find All didn't find anything");
		
		return;
	}
	
	@Test
	public void testConfirm(){
		ShippingUnitBase suBase = new ShippingUnitBase();
		ShippingUnit su = null;
		suBase.setReferenceID("98476984756924001");
		
		try {
			su = bo.confirm(suBase);
		} catch (ResourceNotExistException e) {
			logger.error(e.getMessage(),e);
			fail ( "Su Not found");
		} catch (SystemFailure e) {
			logger.error(e.getMessage(),e);
			fail ( "System Error : " + e.getMessage());
		}
		if ( su == null){
			fail ( "SU was not returned ");
		} else if ( su.getShippingUnitBase().getState().compareTo(ShippingUnitState.CONFIRMED) != 0 )
			fail ( "SU was not confirmed");
	
	}

	
	@Test
	public void testFindTotalCount(){
		if (  bo.findTotalCount() == 0 )
			fail ( "0 count was returmd");
	}
	
	@Test
	public void testCancel() {
		
		try {
			bo.cancel("98476984756924003");
		} catch (BusinessException | SystemFailure e) {
			logger.error(e.getMessage(), e);
			fail ( "testCancel:BusinessException: " + e.getMessage() );
		} 		
		
	}

	@Test
	public void testGet() {
		
		ShippingUnit su = null;
		String suRefId = "98476984756924001";
			try {
				su = bo.get(suRefId);
			} catch (ResourceNotExistException | SystemFailure e) {
				logger.error(e.getMessage(), e);
				fail ( "testGet:Exception: " + e.getMessage() );
			}
		if ( su == null ){
			fail ( " Get Failed : Null SU returned");
		}else if ( !su.getShippingUnitBase().getReferenceID().equals(suRefId)){
			fail ( "Correct SU was not retrived");
		}
		return;
	}
	
	@Test 
	public void testIsActiveShippingUnitExist (){
		try {
			if ( bo.isActiveShippingUnitExist("98476984756924001") != true )
				fail ( "BO says the su is not active");
		} catch (SystemFailure e) {
			logger.error(e.getMessage(), e);
			fail ( "System error : " + e.getMessage() );
		}
	}
	
	@Test
	public void testGetInOffset(){
		
		List<ShippingUnit> su = bo.getInOffset(1, 3);
		
		if ( su == null || su.isEmpty() || su.size() != 3)
			fail ( "Correct number if rows not returned");
	}
	

	@Test
	public void testGetDocuments() {

		ShippingUnit su = null;
		GetDocumentsResponse documentsResponse = null;
		String suRefId = "98476984756924041";
		try {
			su = bo.get(suRefId);
			documentsResponse = bo.getSUDocuments(su);
		} catch (SystemFailure | BusinessException e) {
			logger.error(e.getMessage(), e);
			fail("testGet:BusinessException: " + e.getMessage());
		}
		if (su == null) {
			fail(" Get Failed : Null SU returned");
		} else if (!su.getShippingUnitBase().getReferenceID().equals(suRefId)) {
			fail("Correct SU was not retrived");
		} else if (documentsResponse.getDocuments().isEmpty()) {
			fail("Document list was not retrived");
		}

		return;
	}

	
	@Test
	public void testGetDocumentsByTrackNum() {

		ShippingUnit su = null;
		GetDocumentsResponse documentsResponse = null;
		String suRefId = "98476984756924041";
		try {
			su = bo.getShipUnit("672750540831");
			documentsResponse = bo.getSUDocuments(su);
		} catch (SystemFailure | BusinessException e) {
			logger.error(e.getMessage(), e);
			fail("testGet:BusinessException: " + e.getMessage());
		}
		if (su == null) {
			fail(" Get Failed : Null SU returned");
		} else if (!su.getShippingUnitBase().getReferenceID().equals(suRefId)) {
			fail("Correct SU was not retrived");
		} else if (documentsResponse.getDocuments().isEmpty()) {
			fail("Document list was not retrived");
		}

		return;
	}
	
	@Test
	public void testInitiate(){
		// TODO Still constructing
		String suRefID = "897987696";
		try {
			bo.initate(suDataObj.getSU(suRefID));
		} catch (SystemFailure e) {
			logger.error(e.getMessage(), e);
			//fail ( "System error : " + e.getMessage() );
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			fail ( "BusinessException : " + e.getMessage() );
		} catch ( Exception e ){
			logger.error(e.getMessage(), e);
		}
	}
	
	@Test
	public void testGetCarrierServiceByName() {
		String carrierServiceName = bo.getCarrierServiceNameByCode("15");		
		assert(carrierServiceName.equals("FEDEX"));
	}
	
	//TODO 	initate	cancel	confirm	findAll	getInOffset	findTotalCount	isActiveShippingUnitExist



}

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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.cip.common.dto.LoadInfo;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.CarrierAgentException;
import com.cts.cip.common.exceptions.MultipleResourceExistException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.Load;
import com.cts.cip.common.model.LoadBase;
import com.cts.cip.common.model.LoadState;
import com.cts.cip.common.model.ManifestShippingUnitsResponse;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.core.test.util.TestDBUtility;

@ContextConfiguration(locations = { "classpath:spring/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test-jpa")

public class LoadBOTest {

	Logger logger = LoggerFactory.getLogger(LoadBOTest.class);

	@Autowired
	LoadBO bo;

	@Autowired
	TestDBUtility dbUtil;

	@PersistenceContext
	private EntityManager em;

	@Before // Need to find a way to execute this method only once
	public void initDB() {

		try {
			dbUtil.setDBData("src/test/resources/db/TransationTestData.sql");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return;
	}

	@Test
	public void testGetLoad() {
		testGet();

		testGetBySU();

		testGetBySUFail();

	}
	
	@Test 
	public void testGetLoadInfoo () {
		try {
			LoadInfo loadInfo =  bo.findLoadInfoById(4);
			assert(loadInfo.getShipmentLoadInfoList().size()==22);
		} catch (ResourceNotExistException e) {			
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void testAddToLoad() {
		String loadRef = "Load 24";
		String nonExistingLoadref = "Load 17";
		String suRef = "98476984756924015";
		String suRef1 = "98476984756924016";
		testAddNewToNewLoad( loadRef , suRef);
		testAddNewToExistingLoad( loadRef , suRef1);
		testAddOldToNewLoad(nonExistingLoadref , suRef1);
	}

	private void testAddOldToNewLoad( String nonExistingLoadref, String suRef) {

		Load load = loadTestLoad(nonExistingLoadref, suRef);

		List<Load> loadsOut = getLoad(load);

		if ((loadsOut != null && !loadsOut.isEmpty())
				&& loadsOut.get(0).getLoadDetails().getLoadState().compareTo(LoadState.CANCELLED) != 0) {
			fail("Load was created but is active");
		}

	}
	
	@Test
	public void  testGetLoadByName() {
		try {
			LoadInfo loadInfo = bo.findLoadInfoByName("Load 1");
			assert(loadInfo.getLoadBase().getTotalUnitCount()==2);
		} catch (ResourceNotExistException e) {
			e.printStackTrace();
			fail ("Load not found");
			
		}
	}

	private void testGetBySU() {
		Load load = null;
		try {
			load = bo.getBySU("98476984756924019");
		} catch (MultipleResourceExistException e) {
			logger.info(e.getMessage(), e);
			fail("testGetBySU: MultipleResourceExistException : " + e.getMessage());
		} catch (ResourceNotExistException e) {
			logger.info(e.getMessage(), e);
			fail("testGetBySU: ResourceNotExistException : " + e.getMessage());
		}
		if (load == null) {
			fail("GetBySU failed : Null Load returned");
		} else if (!"Load 4".equals(load.getLoadDetails().getLoadReferenceId())) {
			fail("testGetBySU: Expected Load was not retrived ");
		}

	}

	private void testGetBySUFail() {
		Load load = null;
		try {
			load = bo.getBySU("98476984756924001");
		} catch (ResourceNotExistException e) {
			logger.error(e.getMessage(), e);
			fail("testGetBySUFail: ResourceNotExistException : " + e.getMessage());
		} catch (MultipleResourceExistException e) {
			logger.info(e.getMessage(), e);
			// Test Passed !!
		}

		if (load != null) {
			fail("Load shouldn't have retrived ");
		}

	}

	private void testGet() {
		String testLoadRefId = "Load 1";
		List<Load> loads = null;
		try {
			loads = bo.get(testLoadRefId);
		} catch (ResourceNotExistException e) {
			logger.error(e.getMessage(), e);
			fail(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			fail("testGet: Unhandled Exception thrown : " + e.getMessage());
		}
		if (loads == null || loads.isEmpty()) {
			fail("testGet: ResourceNotExistException exception not thrown");
		} else {
			for (Load load : loads) {
				if (!load.getLoadDetails().getLoadReferenceId().equals(testLoadRefId)) {
					fail("Load " + testLoadRefId + " was not returned");
				}
			}
		}
	}

	private void testAddNewToNewLoad( String loadRef , String suRef) {

		Load load = loadTestLoad( loadRef , suRef);

		List<Load> loadsOut = getLoad(load);

		if (loadsOut == null) {
			fail(" AddToNewLoad failed : Null load returned");
		} else if (loadsOut.get(0).getLoadDetails().getId() == null) {
			fail("testAddToNewLoad: Id not generated");
		} else if (loadsOut.get(0).getShippingUnits() == null || loadsOut.get(0).getShippingUnits().isEmpty()) {
			fail("testAddToNewLoad: SU was not added");
		} else if (!loadsOut.get(0).getShippingUnits().get(0).getReferenceID().equals(suRef)) {
			fail("testAddToNewLoad: SU was not added properly");
		}

	}

	private Load loadTestLoad(String string, String string2) {

		LoadBase loadIn = new LoadBase();

		List<ShippingUnitBase> shippingUnits = new ArrayList<>();
		loadIn.setLoadReferenceId(string);
		shippingUnits.add(new ShippingUnitBase());
		shippingUnits.get(0).setReferenceID(string2);

		return runLoad(loadIn, shippingUnits);
	}
	


	private List<Load> getLoad(Load load) {
		List<Load> loadsOut = null;
		try {
			loadsOut = bo.get(load.getLoadDetails().getLoadReferenceId());
		} catch (ResourceNotExistException e) {
			logger.error(e.getMessage(), e);
			fail("Load not persisted");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			fail("Got unhandled Exception :" + e.getMessage());
		}

		return loadsOut;
	}

	private Load runLoad(LoadBase loadIn, List<ShippingUnitBase> shippingUnits) {
		Load load = null;
		/*try {
			load = bo.load(loadIn, shippingUnits);
		} catch (SystemFailure | BusinessException e) {
			logger.error(e.getMessage(), e);
			fail("runLoad: MultipleResourceExistException : " + e.getMessage());
		}*/
		return load;

	}

	private void testAddNewToExistingLoad( String loadRef , String suRef ) {

		Load load = loadTestLoad(loadRef, suRef);

		List<Load> loadsOut = getLoad(load);

		validateForAddToExistingLoad(loadsOut);
	}

	private void validateForAddToExistingLoad(List<Load> loadsOut) {

		if (loadsOut == null) {
			fail("AddToExistingLoad failed : Null load returned ");
		} else if (loadsOut.get(0).getLoadDetails().getId() == null) {
			fail("Id not generated");
		} else if (loadsOut.get(0).getShippingUnits().size() != 2) {
			fail(" The second shippingUnit was not added");
		} else if (loadsOut.size() != 1) {
			fail("Additional Loads were created " + loadsOut.size());
		} else if (!("98476984756924016".equals(loadsOut.get(0).getShippingUnits().get(0).getReferenceID())
				|| "98476984756924016".equals(loadsOut.get(0).getShippingUnits().get(1).getReferenceID()))) {
			fail(" The second shippingUnit was not added properly");
		}

	}
	
	@Test
	public void testUnload(){
		String loadRef = "Load 5";
		String suRef = "98476984756924005";
		//testUnLoadAndCancel( loadRef , suRef);
		testAddNewToNewLoad ( loadRef , suRef);
	}
	
	/*@Test
	public void testUnloadNonExistingLoad(){
		
		String loadRef = "Load 295";
		String suRef = "98476984756924371";
	
		LoadBase loadIn = new LoadBase();

		List<ShippingUnitBase> shippingUnits = new ArrayList<>();
		loadIn.setLoadReferenceId(loadRef);
		shippingUnits.add(new ShippingUnitBase());
		shippingUnits.get(0).setReferenceID(suRef);
				
		try {
			//bo.unload ( loadIn , shippingUnits ) ;
		} catch (BusinessException e) {
			logger.info(e.getMessage(), e);
		} catch (SystemFailure e) {
			logger.error(e.getMessage(), e);
			fail ( " Got System Failure : " + e.getMessage());
		}
	}

	private void testUnLoadAndCancel( String loadRef , String suRef ) {
		List<Load> loadsOut = null;
		Load load = loadTestLoad( loadRef , suRef );

		if (load == null || load.getShippingUnits().isEmpty()) {
			fail("UnLoadAndCancel : Null/Empty Load returned by load");
		} else {
			// UNLOAD THE SU
			try {
				//load = bo.unload(load.getLoadDetails(), load.getShippingUnits());
			} catch (SystemFailure e) {
				logger.error(e.getMessage(), e);
				fail("testUnLoadAndCancel: MultipleResourceExistException : " + e.getMessage());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
				fail("testUnLoadAndCancel: ResourceNotExistException : " + e.getMessage());
			}
		}

		if (load == null) {
			fail("UnLoadAndCancel: Null Load returned b unload");
		} else {

			// GET THE LOAD BACK
			loadsOut = getLoad(load);
			// TEST
			validateFortestUnLoadAndCancel(loadsOut);
		}
	}*/

	private void validateFortestUnLoadAndCancel(List<Load> loadsOut) {
		if (loadsOut == null) {
			fail("Unload Failed : Null Load returned ");
		} else if (loadsOut.get(0).getLoadDetails().getId() == null) {
			fail("Id not generated in testUnLoadAndCancel");
		} else if (loadsOut.get(0).getShippingUnits().size() != 0) {
			fail(" The shipping unit was not unloaded");
		} else if (loadsOut.size() != 1) {
			fail("Additional Loads were created in testUnLoadAndCancel" + loadsOut.size());

		} else if (loadsOut.get(0).getLoadDetails().getLoadState().compareTo(LoadState.CANCELLED) == 0) {
			fail("The load was not cancelled in testUnLoadAndCancel" + loadsOut.get(0).getLoadDetails().getLoadState());
		}

	}

	/*@Ignore
	@Test
	public void testManifest() {
		Load load = null;
		LoadBase loadIn = new LoadBase();

		loadIn.setLoadReferenceId("Load 3");

		try {
			load = bo.manifest(loadIn);
		} catch (SystemFailure | ResourceNotExistException | CarrierAgentException | MultipleResourceExistException e) {
			logger.error(e.getMessage(), e);
			fail("Exception " + e.getMessage());
		}
		if (load == null) {
			fail("Manifesting Failed : Null Load returned ");

		} else if (load.getLoadDetails().getLoadState().compareTo(LoadState.MANIFESTED) != 0) {
			fail("Status didn't change to MANIFESTED");
		}

	}*/
	@Ignore
	@Test
	public void testManifest() {
		ManifestShippingUnitsResponse manifestSUResponse = null;
		
		String loadRef = "Load 24";
		String suRef = "98476984756924015";
		String suRef1 = "98476984756924016";
		
		testAddNewToNewLoad( loadRef , suRef);
		testAddNewToExistingLoad( loadRef , suRef1);
		
		LoadBase loadIn = new LoadBase();
		loadIn.setLoadReferenceId(loadRef);

		try {
			manifestSUResponse = bo.manifestLoad(loadIn);
			assertTrue(manifestSUResponse.getLoad().getLoadState().compareTo(LoadState.MANIFESTED) == 0);
		} catch (SystemFailure | ResourceNotExistException | CarrierAgentException e) {
			logger.error(e.getMessage(), e);
			fail("Exception " + e.getMessage());
		}
		if (manifestSUResponse == null) {
			fail("Manifesting Failed : Null Load returned ");

		} else if (manifestSUResponse.getLoad().getLoadState().compareTo(LoadState.MANIFESTED) != 0) {
			fail("Status didn't change to MANIFESTED");
		}

	}
	
}

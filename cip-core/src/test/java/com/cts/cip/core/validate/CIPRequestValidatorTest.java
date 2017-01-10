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
/**
 * 
 */
package com.cts.cip.core.validate;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.model.CreateShippingUnitRequest;
import com.cts.cip.common.model.DimensionsUOM;
import com.cts.cip.common.model.LoadShippingUnitRequest;
import com.cts.cip.common.model.ManifestShippingUnitsRequest;
import com.cts.cip.common.model.ReprintShippingLabelRequest;
import com.cts.cip.common.model.ShippingUnit;
import com.cts.cip.common.model.UnloadShippingUnitRequest;
import com.cts.cip.common.model.WeightUOM;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ResponseStatus;
import com.cts.cip.util.CipValidator;

/**
 * @author 417765
 *
 */
public class CIPRequestValidatorTest {

	Logger logger  =  LoggerFactory.getLogger(CIPRequestValidatorTest.class);
	
	String createShipUnitrequestPath;
	String loadShipUnitrequestPath;
	String unloadShipUnitrequestPath;
	String maifestShipUnitrequestPath;
	String reprintShipUnitrequestPath;
	
	@Before
	public void setUp() throws Exception {
		
		createShipUnitrequestPath = "/xml/CreateShippingUnitRequest.xml";
		loadShipUnitrequestPath = "/xml/LoadRequest.xml";
		unloadShipUnitrequestPath = "/xml/UnloadShippingUnitRequest.xml";
		maifestShipUnitrequestPath = "/xml/ManifestShippingUnitsRequest.xml";
		reprintShipUnitrequestPath = "/xml/ReprintShippingLabelRequest.xml";
	}
	
	@After
	public void tearDown() throws Exception {
		createShipUnitrequestPath = null;
	}
	
	@Test
	public void testValidateShippingUnit_Pass() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.SUCCESS) == 0);
		
	}
	@Test
	public void testValidateShippingUnitBasePattern_Failure() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		shippingUnit.getShippingUnitBase().setTransactionId("abcde");
		shippingUnit.getShippingUnitBase().setReferenceID("CARTON1234");
		shippingUnit.getShippingUnitBase().setDescription("¶");
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	}
	@Test
	public void testValidateShippingUnitBaseSize_Failure() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		shippingUnit.getShippingUnitBase().setTransactionId("80239402348092348230984092348302984092834098234083209480329");
		shippingUnit.getShippingUnitBase().setReferenceID("123456901234569012345690");
		shippingUnit.getShippingUnitBase().setDescription("An UPS InfoNotice is a form that UPS leaves (usually on your door) when no one is available "
				+ "for delivery or pickup. An UPS InfoNotice includes the information needed to track a shipment, change delivery options, or pick up a shipment.");
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	}
	
	@Test
	public void testValidateWeightAndDimensionsEmpty_Failure() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		shippingUnit.setWeightAndDimensions(null);
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	} 
	
	
	@Test
	public void testValidateWeightAndDimentionsMax_Failure() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		shippingUnit.getWeightAndDimensions().getWeight().setWeight(1000.98f);
		shippingUnit.getWeightAndDimensions().getWeight().setWeightUOM(WeightUOM.LBS);
		
		shippingUnit.getWeightAndDimensions().getDimensions().setHeight(1000f);
		shippingUnit.getWeightAndDimensions().getDimensions().setWidth(1000f);
		shippingUnit.getWeightAndDimensions().getDimensions().setLength(1000f);
		shippingUnit.getWeightAndDimensions().getDimensions().setDimensionUOM(DimensionsUOM.IN);
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	}
	
	
	@Test
	public void testValidateWeightAndDimentionsMin_failure() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		shippingUnit.getWeightAndDimensions().getWeight().setWeight(0.0001f);
		shippingUnit.getWeightAndDimensions().getWeight().setWeightUOM(WeightUOM.LBS);
		
		shippingUnit.getWeightAndDimensions().getDimensions().setHeight(0.0001f);
		shippingUnit.getWeightAndDimensions().getDimensions().setWidth(0.0001f);
		shippingUnit.getWeightAndDimensions().getDimensions().setLength(0.0001f);
		shippingUnit.getWeightAndDimensions().getDimensions().setDimensionUOM(DimensionsUOM.IN);
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	}
	
	@Test
	public void testShipperDetails_EmptyCheck() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		
		shippingUnit.setShipperDetails(null);
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	}
	
	@Test
	public void testShipperDetails_Pattern_Check() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		
		shippingUnit.getShipperDetails().setBillingAccountID("abcded");
		shippingUnit.getShipperDetails().setMeterID("abcded");
		
		shippingUnit.getShipperDetails().getShipperServiceType().setShipperServiceTypeCode("test<");
		shippingUnit.getShipperDetails().getShipperServiceType().setShipperServiceTypeName("test<");
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	}
	
	@Test
	public void testShipperDetails_MaxLength_Check() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		
		shippingUnit.getShipperDetails().setBillingAccountID("1232132132131231232131232132131231232432423");
		shippingUnit.getShipperDetails().setMeterID("1232132132131231232131232132131231232432423");
		
		shippingUnit.getShipperDetails().getShipperServiceType().setShipperServiceTypeCode("Helajlkjkljsdkljklkllkkljkljljklkljlkjlkj");
		shippingUnit.getShipperDetails().getShipperServiceType().setShipperServiceTypeName("ljlllkljljasdjfljsaldflkasdflkajsdfjlkasdjfsdlajfajsdlfjlkasdj");
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	}
	
	@Test
	public void testShipperDetails_AddressDetails_PatternAndSize_Check() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		
		shippingUnit.getShipperDetails().getShipperAddress().setIndividualName("Venkata$ test test test test test test test test test test test test test ");
		shippingUnit.getShipperDetails().getShipperAddress().setCompanyName("Venkata$ test test test test test test test test test test test test ");
		shippingUnit.getShipperDetails().getShipperAddress().setAddressLine1("123 @abc Street test test test test test test test test test test test");
		shippingUnit.getShipperDetails().getShipperAddress().setAddressLine2("123 @abc Street test test test test test test test test test test test");
		shippingUnit.getShipperDetails().getShipperAddress().setAddressLine3("123 @abc Street test test test test test test test test test test test");
		shippingUnit.getShipperDetails().getShipperAddress().setAddressLine4("123 @abc Street test test test test test test test test test test test");
		
		shippingUnit.getShipperDetails().getShipperAddress().setCity("Bentonville-test test test test test test test test test test test");
		shippingUnit.getShipperDetails().getShipperAddress().setState("123");
		shippingUnit.getShipperDetails().getShipperAddress().setZipCode("abcdekljlkj");
		shippingUnit.getShipperDetails().getShipperAddress().setPhoneNumber("PhoneNumberTest");
		shippingUnit.getShipperDetails().getShipperAddress().setFaxNumber("FaxNumberTest");
		shippingUnit.getShipperDetails().getShipperAddress().setEmailAddress("_2323-#mydomain.comtest test test test test test test test test test test test test test test");
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	}
	
	@Test
	public void testShipperDetails_AddressDetails_NotBlank_Check() {
		
		CreateShippingUnitRequest createShippingUnitRequest = getCreateShippingUnitRequest(createShipUnitrequestPath);
		ShippingUnit shippingUnit = createShippingUnitRequest.getShippingUnit();
		
		shippingUnit.getShipperDetails().getShipperAddress().setIndividualName(null);
		shippingUnit.getShipperDetails().getShipperAddress().setAddressLine1(null);
		
		shippingUnit.getShipperDetails().getShipperAddress().setCity(null);
		shippingUnit.getShipperDetails().getShipperAddress().setState(null);
		shippingUnit.getShipperDetails().getShipperAddress().setCountry(null);
		shippingUnit.getShipperDetails().getShipperAddress().setZipCode(null);
		shippingUnit.getShipperDetails().getShipperAddress().setPhoneNumber(null);
		
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(shippingUnit);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.FAILURE) == 0);
	}
	
	@Test
	public void testValidateReprintShipUnit_Pass() {
		
		ReprintShippingLabelRequest repReq = getReprintShippingLabelRequest(reprintShipUnitrequestPath);
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(repReq);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.SUCCESS) == 0);
	}
	
	
	@Test
	public void testValidateManifestShipUnit_Pass() {
		
		ManifestShippingUnitsRequest manifestReq = getManifestShippingUnitsRequest(maifestShipUnitrequestPath);
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(manifestReq);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.SUCCESS) == 0);
	}
	
	
	
	@Test
	public void testValidateUnLoadShipUnit_Pass() {
		
		UnloadShippingUnitRequest unloadUnitRequest = getUnLoadShippingUnitRequest(unloadShipUnitrequestPath);
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(unloadUnitRequest);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.SUCCESS) == 0);
	}
	
	
	@Test
	public void testValidateLoadShipUnit_Pass() {
		
		LoadShippingUnitRequest loadUnitRequest = getLoadShippingUnitRequest(loadShipUnitrequestPath);
		CIPResponse<String> requestValidationResult = CipValidator.validateRequest(loadUnitRequest);
		System.out.println(requestValidationResult.toString());
		assertTrue(requestValidationResult.getStatus().compareTo(ResponseStatus.SUCCESS) == 0);
		
	}
	
	/**
	 * 
	 * @param xmlPath
	 * @return
	 */
	
	private CreateShippingUnitRequest getCreateShippingUnitRequest(String xmlPath) {
		
		CreateShippingUnitRequest createShippingUnitRequest = null;
		try {
			URL url  = this.getClass().getResource(xmlPath);
			File file = new File(url.toURI().getPath());
			JAXBContext jaxbContext = JAXBContext.newInstance(CreateShippingUnitRequest.class);
	
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			createShippingUnitRequest = (CreateShippingUnitRequest) jaxbUnmarshaller.unmarshal(file);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return createShippingUnitRequest;
	}
	
	/**
	 * 
	 * @param xmlPath
	 * @return
	 */
	
	private LoadShippingUnitRequest getLoadShippingUnitRequest(String xmlPath) {
		
		LoadShippingUnitRequest loadShippingUnitRequest = null;
		try {
			URL url  = this.getClass().getResource(xmlPath);
			File file = new File(url.toURI().getPath());
			JAXBContext jaxbContext = JAXBContext.newInstance(LoadShippingUnitRequest.class);
	
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			loadShippingUnitRequest = (LoadShippingUnitRequest) jaxbUnmarshaller.unmarshal(file);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return loadShippingUnitRequest;
	}
	
	/**
	 * 
	 * @param xmlPath
	 * @return
	 */
	private UnloadShippingUnitRequest getUnLoadShippingUnitRequest(String xmlPath) {

		UnloadShippingUnitRequest unloadShippingUnitRequest = null;
		try {
			URL url  = this.getClass().getResource(xmlPath);
			File file = new File(url.toURI().getPath());
			JAXBContext jaxbContext = JAXBContext.newInstance(UnloadShippingUnitRequest.class);
	
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			unloadShippingUnitRequest = (UnloadShippingUnitRequest) jaxbUnmarshaller.unmarshal(file);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return unloadShippingUnitRequest;
	}
	
	/**
	 * 
	 * @param xmlPath
	 * @return
	 */
	private ManifestShippingUnitsRequest getManifestShippingUnitsRequest(String xmlPath) {
		ManifestShippingUnitsRequest manifestShippingUnitsRequest = null;
		try {
			URL url  = this.getClass().getResource(xmlPath);
			File file = new File(url.toURI().getPath());
			JAXBContext jaxbContext = JAXBContext.newInstance(ManifestShippingUnitsRequest.class);
	
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			manifestShippingUnitsRequest = (ManifestShippingUnitsRequest) jaxbUnmarshaller.unmarshal(file);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return manifestShippingUnitsRequest;
	}
	
	/**
	 * 
	 * @param xmlPath
	 * @return
	 */
	private ReprintShippingLabelRequest getReprintShippingLabelRequest(String xmlPath) {
		ReprintShippingLabelRequest reprintRequest = null;
		try {
			URL url  = this.getClass().getResource(xmlPath);
			File file = new File(url.toURI().getPath());
			JAXBContext jaxbContext = JAXBContext.newInstance(ReprintShippingLabelRequest.class);
	
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			reprintRequest = (ReprintShippingLabelRequest) jaxbUnmarshaller.unmarshal(file);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return reprintRequest;
	}

}

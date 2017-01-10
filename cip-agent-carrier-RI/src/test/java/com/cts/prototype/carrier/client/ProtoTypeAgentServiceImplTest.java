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
package com.cts.prototype.carrier.client;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.cip.agent.model.Address;
import com.cts.cip.agent.model.CancelRequest;
import com.cts.cip.agent.model.CancelResponse;
import com.cts.cip.agent.model.CarrierReferenceDetails;
import com.cts.cip.agent.model.CountryCode;
import com.cts.cip.agent.model.CreateRequest;
import com.cts.cip.agent.model.CreateResponse;
import com.cts.cip.agent.model.DimensionsUOM;
import com.cts.cip.agent.model.InvoiceDetails;
import com.cts.cip.agent.model.LoadRequest;
import com.cts.cip.agent.model.LoadResponse;
import com.cts.cip.agent.model.ManifestRequest;
import com.cts.cip.agent.model.ManifestResponse;
import com.cts.cip.agent.model.OrderDetails;
import com.cts.cip.agent.model.PackageDimensions;
import com.cts.cip.agent.model.PackageInfo;
import com.cts.cip.agent.model.PackageWeightInfo;
import com.cts.cip.agent.model.ShipToAddress;
import com.cts.cip.agent.model.ShipperAddress;
import com.cts.cip.agent.model.ShipperDetails;
import com.cts.cip.agent.model.ShipperService;
import com.cts.cip.agent.model.ShippingOptions;
import com.cts.cip.agent.model.SpecialService;
import com.cts.cip.agent.model.UnLoadRequest;
import com.cts.cip.agent.model.UnLoadResponse;
import com.cts.cip.agent.model.WeightUOM;

@ContextConfiguration(locations={"classpath:spring/carrier-RI-test-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProtoTypeAgentServiceImplTest {

	public static final String PACKAGE_ID = "300010";
	
	@Autowired
	ModelCarrierAgentServiceImpl carrierImpl;
	
	CreateRequest createRequest;
	CancelRequest cancelRequest;
	ManifestRequest manifestRequest;
	
	
	@Before
	public void setUp() throws Exception {
		mockCreateShipmentRequest();
		mockCancelRequest();
		mockManifestRequest();
	}

	@After
	public void tearDown() throws Exception { 
	}
	
	private void mockCreateShipmentRequest() {

		createRequest = new CreateRequest();

		ShipToAddress shipToAddress = new ShipToAddress();
		OrderDetails orderDetails = new OrderDetails();
		String requestTransactionID = "1234560001214";

		ShipperService shipperServiceType = new ShipperService();
		ShipperAddress shipperAddress = new ShipperAddress();
		ShipperDetails shipperDetails = new ShipperDetails();

		shipperDetails.setShipperServiceType(shipperServiceType);
		shipperServiceType.setServiceTypeCode("2");

		Address address = new Address();
		address.setIndividualName("Sender1");
		address.setCompanyName("Sender Company");
		address.setAddress1("3904 SW Feather St");
		address.setAddress2("Apt AAA");
		address.setAddress3("Address line 3");
		address.setCity("Bentonville");
		address.setState("AR");
		address.setPhoneNumber("4794180400");
		address.setZipCode("72712");
		shipperAddress.setAddress(address);
		shipperDetails.setShipperAddress(shipperAddress);
		shipperDetails.setBillingAccountID("3Y026Y");
		createRequest.setShipperDetails(shipperDetails);
		createRequest.setRequestTransactionID(requestTransactionID);

		// Receiver Address

		Address toAddress = new Address();
		toAddress.setIndividualName("CCC Receiver");
		toAddress.setAddress1("3115 SW I St ");
		toAddress.setAddress2("123");
		toAddress.setCity("Bentonville");
		toAddress.setState("AR");
		toAddress.setZipCode("72712");
		toAddress.setCountryCode(CountryCode.US);
		toAddress.setPhoneNumber("1234567890");
		shipToAddress.setAddress(toAddress);
		createRequest.setShipToAddress(shipToAddress);

		orderDetails.setOrderNumber("ORDER1232343434");
		orderDetails.setPoNumber("PO1232322");
		
		Calendar cal = Calendar.getInstance();
		
		
		orderDetails.setDatePlannedShipment(cal);

		PackageInfo packageInfo = new PackageInfo();
		PackageDimensions pkgDimensions = new PackageDimensions();
		PackageWeightInfo pkgWeight = new PackageWeightInfo();
		packageInfo.setPackageID(PACKAGE_ID);

		pkgDimensions.setDimensionUOM(DimensionsUOM.IN);
		pkgDimensions.setHeight((float) 10.5);
		pkgDimensions.setWidth((float) 10.4);
		pkgDimensions.setLength((float) 10.8);

		pkgWeight.setPackageWeight(20.2);
		pkgWeight.setWeightUOM(WeightUOM.LBS);
		packageInfo.setPackageWeightInfo(pkgWeight);
		packageInfo.setPackageDimensions(pkgDimensions);
		orderDetails.setPackageInfo(packageInfo);

		InvoiceDetails invoiceDetails = new InvoiceDetails();
		invoiceDetails.setTotalOrderItemsCost(200.02);

		orderDetails.setInvoiceDetails(invoiceDetails);
		
		CarrierReferenceDetails refDetails = new CarrierReferenceDetails();
		refDetails.setType("IK");
		refDetails.setValue("IK12321323");
		createRequest.getReferenceDetails().add(refDetails);
		
		ShippingOptions so  = new ShippingOptions();
		so.setSpecialServiceType(SpecialService.DELIVERY_CONFIRM_SIGN_REQD);
		orderDetails.setShippingOptions(so);
		createRequest.setOrderDetails(orderDetails);

	}
	
	private void mockManifestRequest() {
		manifestRequest = new ManifestRequest();
		ShipperService shipperServiceType = new ShipperService();
		shipperServiceType.setServiceTypeCode("2");
		shipperServiceType.setCarrierName("UPS");
		manifestRequest.setShipperServiceType(shipperServiceType);
		List<PackageInfo> pkgInfoList = new ArrayList<PackageInfo>();
		PackageInfo pkgInfo = new PackageInfo();
		pkgInfo.setPackageID(PACKAGE_ID);
		pkgInfoList.add(pkgInfo);
		manifestRequest.setPackageList(pkgInfoList);

	}

	private CancelRequest mockCancelRequest() {
		cancelRequest = new CancelRequest();
		List<String> trackingNumList = new ArrayList<String>();
		trackingNumList.add("672750527937");
		trackingNumList.add("672750527926");
		cancelRequest.setTrackingNumberList(trackingNumList);
		ShipperService shipperServiceType = new ShipperService();
		shipperServiceType.setServiceTypeCode("2");
		cancelRequest.setShipperServiceType(shipperServiceType);
		return cancelRequest;
	}

	@Test
	public void testCreateShipment() {
	
		CreateResponse createResponse = carrierImpl.createShipment(createRequest);
		Assert.assertNotNull(createResponse.getTrackingNumber());
		Assert.assertNotNull(createResponse.getStatus());
		Assert.assertEquals("SUCCESS", createResponse.getStatus().getResponseStatusDescription());
	}

	@Test
	public void testCancelShipment() {
		
		CancelResponse cancelResponse = carrierImpl.cancelShipment(cancelRequest);
		Assert.assertNotNull(cancelResponse.getStatus());
		Assert.assertEquals("SUCCESS", cancelResponse.getStatus().getResponseStatusDescription());
	}

	@Test
	public void testLoadShipment() {
		LoadRequest loadRequest = new LoadRequest();
		LoadResponse loadResponse = carrierImpl.loadShipment(loadRequest);
		Assert.assertNotNull(loadResponse.getStatus());
		Assert.assertEquals("SUCCESS", loadResponse.getStatus().getResponseStatusDescription());	
	}

	@Test
	public void testMainfestTrailer() {
		ManifestRequest manifestRequest = new ManifestRequest();
		ManifestResponse manifestResponse = carrierImpl.mainfestTrailer(manifestRequest);
		Assert.assertNotNull(manifestResponse.getStatus());
		Assert.assertEquals("SUCCESS", manifestResponse.getStatus().getResponseStatusDescription());	
	}

	@Test
	public void testUnLoadShipment() {
		UnLoadRequest unloadRequest = new UnLoadRequest();
		UnLoadResponse unloadResponse = carrierImpl.unLoadShipment(unloadRequest);
		Assert.assertNotNull(unloadResponse.getStatus());
		Assert.assertEquals("SUCCESS", unloadResponse.getStatus().getResponseStatusDescription());	
	}

	@Ignore
	@Test
	public void testTrackShipment() {
		fail("Not yet implemented");
	}

}

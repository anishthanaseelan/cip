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
package com.cts.cip.carrier.agent.service;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.agent.model.Address;
import com.cts.cip.agent.model.CancelRequest;
import com.cts.cip.agent.model.CancelResponse;
import com.cts.cip.agent.model.CountryCode;
import com.cts.cip.agent.model.CreateRequest;
import com.cts.cip.agent.model.CreateResponse;
import com.cts.cip.agent.model.DimensionsUOM;
import com.cts.cip.agent.model.InvoiceDetails;
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
import com.cts.cip.agent.model.WeightUOM;

public class CarrierAgentServiceImplTest {

	Logger logger = LoggerFactory.getLogger(CarrierAgentServiceImplTest.class);
	Client restClient;
	WebTarget rootWebTarget;
	CreateRequest createRequest;
	CancelRequest cancelRequest;
	ManifestRequest manifestRequest;

	static final String CREATE_SHIPMENT_URI = "createShipment";
	static final String AGENT_URI = "http://localhost:9183/carrier-agent/shipmentService/";
	static final String PACKAGE_ID = "700009";


	

	@Before
	public void setUp() throws Exception {
		restClient = ClientBuilder.newClient();
		rootWebTarget = restClient.target(AGENT_URI);
		mockCreateShipmentRequest();
		mockCancelRequest();
		mockManifestRequest();

	}

	@After
	public void tearDown() throws Exception {
		
		
		
	}
	private void mockManifestRequest() {
		manifestRequest = new ManifestRequest();
		ShipperService shipperServiceType = new ShipperService();
		shipperServiceType.setServiceTypeCode("99");
		shipperServiceType.setCarrierName("ModelCarrier");
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
		shipperServiceType.setServiceTypeCode("99");
		cancelRequest.setShipperServiceType(shipperServiceType);
		return cancelRequest;
	}



	private void mockCreateShipmentRequest() {

		createRequest = new CreateRequest();

		ShipToAddress shipToAddress = new ShipToAddress();
		OrderDetails orderDetails = new OrderDetails();
		String requestTransactionID = "12100000000000";

		ShipperService shipperServiceType = new ShipperService();
		ShipperAddress shipperAddress = new ShipperAddress();
		ShipperDetails shipperDetails = new ShipperDetails();

		shipperDetails.setShipperServiceType(shipperServiceType);
		shipperServiceType.setServiceTypeCode("99");

		Address address = new Address();
		address.setIndividualName("ABC Sender");
		address.setCompanyName("ABC Company");
		address.setAddress1("1234 Universal Street");
		address.setAddress2("Apt 1");
		address.setAddress3("Unit 1");
		address.setCity("Dallas");
		address.setState("TX");
		address.setPhoneNumber("1234567890");
		address.setZipCode("12345");
		shipperAddress.setAddress(address);
		shipperDetails.setShipperAddress(shipperAddress);
		shipperDetails.setBillingAccountID("ABC012");
		createRequest.setShipperDetails(shipperDetails);
		createRequest.setRequestTransactionID(requestTransactionID);

		// Receiver Address

		Address toAddress = new Address();
		toAddress.setIndividualName("ABC Receiver");
		toAddress.setAddress1("1234 Universal Street ");
		toAddress.setAddress2("Apt3");
		toAddress.setCity("Tampa");
		toAddress.setState("FL");
		toAddress.setZipCode("12702");
		toAddress.setPhoneNumber("1234567890");
		toAddress.setCountryCode(CountryCode.US);
		shipToAddress.setAddress(toAddress);
		createRequest.setShipToAddress(shipToAddress);

		orderDetails.setOrderNumber("ORDER1232343434");
		orderDetails.setPoNumber("PO1232322");
		orderDetails.setDatePlannedShipment(Calendar.getInstance());

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

		createRequest.setOrderDetails(orderDetails);

	}
	
	
	
	@Test
	public void testCreateShipment_CarrierRI() {

		createRequest.getShipperDetails().getShipperServiceType()
				.setServiceTypeCode("99");

		WebTarget childWebTarget = restClient.target("http://localhost:9183/cip-agent/Agent/createShipment");
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.post(Entity.entity(createRequest, MediaType.APPLICATION_XML));
		CreateResponse createResponse = response.readEntity(CreateResponse.class);

		if (response.getStatus() != 200) {
			logger.error("Status is not OK " + response.getStatus());
			
			Assert.fail("Received Status 500");
		}

		Assert.assertNotNull(createResponse);
		Assert.assertEquals("SUCCESS", createResponse.getStatus().getResponseStatusDescription());
		Assert.assertNotNull(createResponse.getTrackingNumber());
		logger.debug("Tracking Number : " + createResponse.getTrackingNumber());
		response.close();

	}

	
	@Test
	public void testCreateShipment_CarrierRI_Failure() {

		createRequest.getShipperDetails().getShipperServiceType()
				.setServiceTypeCode("2");

		WebTarget childWebTarget = restClient.target("http://localhost:9183/cip-agent/Agent/createShipment");
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.post(Entity.entity(createRequest, MediaType.APPLICATION_XML));
		CreateResponse createResponse = response.readEntity(CreateResponse.class);

		if (response.getStatus() != 200) {
			logger.error("Status is not OK " + response.getStatus());
			
			Assert.fail("Received Status 500");
		}

		Assert.assertEquals("FAILURE", createResponse.getStatus().getResponseStatusDescription());
		Assert.assertEquals("Carrier or  Ship Type is Invalid / Not Supported", createResponse.getStatus().getErrors().get(0).getErrorDescription());
		Assert.assertNull(createResponse.getTrackingNumber());
	
		response.close();

	}
	
	@Test
	public void testCancelShipment() {
		WebTarget childWebTarget = restClient.target("http://localhost:9183/cip-agent/Agent/cancelShipment");
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.post(Entity.entity(cancelRequest, MediaType.APPLICATION_XML));
		CancelResponse cancelResponse = response.readEntity(CancelResponse.class);

		if (response.getStatus() != 200) {
			logger.error("Status is not OK " + response.getStatus());
		}

		Assert.assertNotNull(cancelResponse);
		Assert.assertEquals("SUCCESS",cancelResponse.getStatus().getResponseStatusDescription());
		response.close();
	}

	 @Ignore
	public void testLoadShipment() {
		fail("Not yet implemented");
	}

	@Test
	public void testMainfestTrailer() {
		manifestRequest.getShipperServiceType().setServiceTypeCode("99");
		manifestRequest.getShipperServiceType().setCarrierName("ModelCarrier");
		manifestRequest.setRequestTransactionID(UUID.randomUUID().toString());
		WebTarget childWebTarget = restClient.target("http://localhost:9183/cip-agent/Agent/manifestTrailer");
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.post(Entity.entity(manifestRequest, MediaType.APPLICATION_XML));
		ManifestResponse manifestResponse = response.readEntity(ManifestResponse.class);

		if (response.getStatus() != 200) {
			logger.error("Status is not OK " + response.getStatus());
		}

		logger.debug("Manifest Response");
		Assert.assertNotNull(manifestResponse);
		Assert.assertEquals("SUCCESS", manifestResponse.getStatus().getResponseStatusDescription());
		response.close();
	}


}

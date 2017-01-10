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
package com.cts.cip.master.core;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cts.cip.master.exception.ImageUploadException;
import com.cts.cip.master.exception.ImageValidationException;
import com.cts.cip.master.exception.ResourceNotAvailableException;
import com.cts.cip.master.model.ImageDetailsRequest;
import com.cts.cip.master.model.ImageDetailsResponse;
import com.cts.cip.master.repository.dao.CarrierRepository;
import com.cts.cip.master.util.TestDataProvider;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class MasterImageSvcTest {
	
	Logger logger = LoggerFactory.getLogger(MasterImageSvcTest.class);

	@Mock
	CarrierRepository carrierRepository;

	@InjectMocks
	private MasterImageService masterImageSvc = new MasterImageServiceImpl();
	
	 @Rule
     public ExpectedException thrown = ExpectedException.none();


	@Before
	public void setUp() {

	}

	/*
	 * This method is used to test the sample getAllCarrier flow with out any exception. 
	 */
	@Test	
	public void testValidateRequest_1() {
		thrown.expect(ImageValidationException.class);
		thrown.expectMessage("File Name is Empty");
		ImageDetailsRequest request = new ImageDetailsRequest();		
		masterImageSvc.uploadImage(request, null, null);
	}
	
	@Test	
	public void testValidateRequest_2() {
		thrown.expect(ImageValidationException.class);
		thrown.expectMessage("Image Content is Empty.");
		ImageDetailsRequest request = new ImageDetailsRequest();
		request.setFileName("TestFileName");	
		masterImageSvc.uploadImage(request, null, null);
	}
	
	@Test	
	public void testValidateRequest_3() {
		thrown.expect(ImageValidationException.class);
		thrown.expectMessage("Parent Resource Name is Empty.");
		ImageDetailsRequest request = new ImageDetailsRequest();
		request.setFileName("TestFileName");
		request.setBase64Content("test");
		masterImageSvc.uploadImage(request, null, null);
		
	}
	
	@Test	
	public void testValidateRequest_4() {
		thrown.expect(ImageValidationException.class);
		thrown.expectMessage("Parent ResourceId is Empty.");
		ImageDetailsRequest request = new ImageDetailsRequest();
		request.setFileName("TestFileName");
		request.setBase64Content("adjlf");
		masterImageSvc.uploadImage(request, "test", null);
		
	}
	
	@Test
	public void testUploadImage_1() {
		thrown.expect(ResourceNotAvailableException.class);
		thrown.expectMessage("The given parent resource does not exist.");
		ImageDetailsRequest request = new ImageDetailsRequest();
		request.setFileName("TestFileName");
		request.setBase64Content("adjlf");		
		masterImageSvc.uploadImage(request, "CARRIER", 1);
		Mockito.when(carrierRepository.findById(1)).thenReturn(null);
	}
	
	@Test
	public void testUploadImage_2() {
		thrown.expect(ImageUploadException.class);
		thrown.expectMessage("CARRIER- Image File Type is Empty.");
		Mockito.when(carrierRepository.findById(1)).thenReturn(TestDataProvider.getCarrier("Simple"));
		ImageDetailsRequest request = new ImageDetailsRequest();
		request.setFileName("TestFileName");
		request.setBase64Content("adjlf");		
		masterImageSvc.uploadImage(request, "CARRIER", 1);
	}
	
	@Test
	public void testUploadImage_3() {		
		Mockito.when(carrierRepository.findById(1)).thenReturn(TestDataProvider.getCarrier("Simple"));
		ImageDetailsRequest request = new ImageDetailsRequest();
		request.setFileName("TestFileName.jpg");
		request.setBase64Content("R0lGODlhPQBEAPeoAJosM//AwO/AwHVYZ/z595kzAP/s7P+goOXMv8+fhw/v739/f+8PD98fH/8mJl+fn/9ZWb8/PzWlwv///6wWGbImAPgTEMImIN9gUFCEm/gDALULDN8PAD6atYdCTX9gUNKlj8wZAKUsAOzZz+UMAOsJAP/Z2ccMDA8PD/95eX5NWvsJCOVNQPtfX/8zM8+QePLl38MGBr8JCP+zs9myn/8GBqwpAP/GxgwJCPny78lzYLgjAJ8vAP9fX/+MjMUcAN8zM/9wcM8ZGcATEL+QePdZWf/29uc/P9cmJu9MTDImIN+/r7+/vz8/P8VNQGNugV8AAF9fX8swMNgTAFlDOICAgPNSUnNWSMQ5MBAQEJE3QPIGAM9AQMqGcG9vb6MhJsEdGM8vLx8fH98AANIWAMuQeL8fABkTEPPQ0OM5OSYdGFl5jo+Pj/+pqcsTE78wMFNGQLYmID4dGPvd3UBAQJmTkP+8vH9QUK+vr8ZWSHpzcJMmILdwcLOGcHRQUHxwcK9PT9DQ0O/v70w5MLypoG8wKOuwsP/g4P/Q0IcwKEswKMl8aJ9fX2xjdOtGRs/Pz+Dg4GImIP8gIH0sKEAwKKmTiKZ8aB/f39Wsl+LFt8dgUE9PT5x5aHBwcP+AgP+WltdgYMyZfyywz78AAAAAAAD///8AAP9mZv///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAEAAKgALAAAAAA9AEQAAAj/AFEJHEiwoMGDCBMqXMiwocAbBww4nEhxoYkUpzJGrMixogkfGUNqlNixJEIDB0SqHGmyJSojM1bKZOmyop0gM3Oe2liTISKMOoPy7GnwY9CjIYcSRYm0aVKSLmE6nfq05QycVLPuhDrxBlCtYJUqNAq2bNWEBj6ZXRuyxZyDRtqwnXvkhACDV+euTeJm1Ki7A73qNWtFiF+/gA95Gly2CJLDhwEHMOUAAuOpLYDEgBxZ4GRTlC1fDnpkM+fOqD6DDj1aZpITp0dtGCDhr+fVuCu3zlg49ijaokTZTo27uG7Gjn2P+hI8+PDPERoUB318bWbfAJ5sUNFcuGRTYUqV/3ogfXp1rWlMc6awJjiAAd2fm4ogXjz56aypOoIde4OE5u/F9x199dlXnnGiHZWEYbGpsAEA3QXYnHwEFliKAgswgJ8LPeiUXGwedCAKABACCN+EA1pYIIYaFlcDhytd51sGAJbo3onOpajiihlO92KHGaUXGwWjUBChjSPiWJuOO/LYIm4v1tXfE6J4gCSJEZ7YgRYUNrkji9P55sF/ogxw5ZkSqIDaZBV6aSGYq/lGZplndkckZ98xoICbTcIJGQAZcNmdmUc210hs35nCyJ58fgmIKX5RQGOZowxaZwYA+JaoKQwswGijBV4C6SiTUmpphMspJx9unX4KaimjDv9aaXOEBteBqmuuxgEHoLX6Kqx+yXqqBANsgCtit4FWQAEkrNbpq7HSOmtwag5w57GrmlJBASEU18ADjUYb3ADTinIttsgSB1oJFfA63bduimuqKB1keqwUhoCSK374wbujvOSu4QG6UvxBRydcpKsav++Ca6G8A6Pr1x2kVMyHwsVxUALDq/krnrhPSOzXG1lUTIoffqGR7Goi2MAxbv6O2kEG56I7CSlRsEFKFVyovDJoIRTg7sugNRDGqCJzJgcKE0ywc0ELm6KBCCJo8DIPFeCWNGcyqNFE06ToAfV0HBRgxsvLThHn1oddQMrXj5DyAQgjEHSAJMWZwS3HPxT/QMbabI/iBCliMLEJKX2EEkomBAUCxRi42VDADxyTYDVogV+wSChqmKxEKCDAYFDFj4OmwbY7bDGdBhtrnTQYOigeChUmc1K3QTnAUfEgGFgAWt88hKA6aCRIXhxnQ1yg3BCayK44EWdkUQcBByEQChFXfCB776aQsG0BIlQgQgE8qO26X1h8cEUep8ngRBnOy74E9QgRgEAC8SvOfQkh7FDBDmS43PmGoIiKUUEGkMEC/PJHgxw0xH74yx/3XnaYRJgMB8obxQW6kL9QYEJ0FIFgByfIL7/IQAlvQwEpnAC7DtLNJCKUoO/w45c44GwCXiAFB/OXAATQryUxdN4LfFiwgjCNYg+kYMIEFkCKDs6PKAIJouyGWMS1FSKJOMRB/BoIxYJIUXFUxNwoIkEKPAgCBZSQHQ1A2EWDfDEUVLyADj5AChSIQW6gu10bE/JG2VnCZGfo4R4d0sdQoBAHhPjhIB94v/wRoRKQWGRHgrhGSQJxCS+0pCZbEhAAOw==");		
		ImageDetailsResponse  response = masterImageSvc.uploadImage(request, "CARRIER", 1);
		assertThat(response.getStatusResponse().getCode()).isEqualTo("1");
		assertThat(response.getStatusResponse().getDescription()).isEqualTo("Successfully Uploaded the TestFileName.jpg for UPS");
	}
}

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
package com.cts.cip.response;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.model.ShippingUnitBase;
import com.cts.cip.common.response.CIPResponseBuilder;
import com.cts.cip.common.response.model.CIPResponse;
import com.cts.cip.common.response.model.ErrorDetail;

public class CIPResponseBuilderTest {

	
	@Test
	public void testBuild1() {
		CIPResponseBuilder<ShippingUnitBase> response = new CIPResponseBuilder<>();
		Response wsrsResponse = null;
		try{
			throw new BusinessException("Hello"); 
		} catch ( Exception e ){
			wsrsResponse = response.build(e, MediaType.APPLICATION_XML_TYPE);
		}
		
		if ( !wsrsResponse.getMediaType().equals(MediaType.APPLICATION_XML_TYPE))
			fail ( "Correct Media type was not returned");
		
		if ( !wsrsResponse.getEntity().getClass().equals(CIPResponse.class))
			fail ( "The Payload is not of correct type");
		return;
	}
	
	@Test
	public void testBuild2() {
		CIPResponseBuilder<ShippingUnitBase> response = new CIPResponseBuilder<>();
		Response wsrsResponse = null;
		List<ErrorDetail> errorList = new ArrayList<>();
		ErrorDetail error = new ErrorDetail();
		error.setErrorMessage("Error Message");
		errorList.add(error);
		try{
			throw new BusinessException("error msg" , errorList ); 
		} catch ( Exception e ){
			wsrsResponse = response.build(e, MediaType.APPLICATION_JSON_TYPE);
		}
		
		if ( !wsrsResponse.getMediaType().equals(MediaType.APPLICATION_JSON_TYPE))
			fail ( "Correct Media type was not returned");
		
		if ( !wsrsResponse.getEntity().getClass().equals(CIPResponse.class))
			fail ( "The Payload is not of correct type");
		
		if ( !((CIPResponse<ShippingUnitBase>)wsrsResponse.getEntity()).getErrorList().get(0).getErrorMessage().equals("Error Message"))
			fail ( "Couldnt read the error meg from the Error List");
		
		return;
	}
	@Test
	public void testBuild3() {
		CIPResponseBuilder<ShippingUnitBase> response = new CIPResponseBuilder<>();
		Response wsrsResponse = null;
		List<ErrorDetail> errorList = new ArrayList<>();
		ErrorDetail error = new ErrorDetail();
		error.setErrorMessage("Error Message");
		errorList.add(error);
		try{
			throw new ResourceNotExistException("error msg" ); 
		} catch ( Exception e ){
			wsrsResponse = response.build(e, MediaType.APPLICATION_XML_TYPE);
		}
		
		if ( !wsrsResponse.getMediaType().equals(MediaType.APPLICATION_XML_TYPE))
			fail ( "Correct Media type was not returned");
		
		if ( !wsrsResponse.getEntity().getClass().equals(CIPResponse.class))
			fail ( "The Payload is not of correct type");

		
		return;
	}
}

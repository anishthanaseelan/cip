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
package com.cts.cip.common.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author 417765
 *
 */
public class BusinessWebException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7450496566924170612L;

	/**
	 * 
	 */
	public BusinessWebException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param status
	 */
	public BusinessWebException(String message) {
		super(Response.status(Status.EXPECTATION_FAILED).entity(message).build());
	}
}

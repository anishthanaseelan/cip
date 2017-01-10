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
package com.cts.cip.master.exception;

/**
 * @author 417765
 *
 */
public class ResourceNotAvailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4453842383672445280L;

	/**
	 * 
	 */
	public ResourceNotAvailableException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public ResourceNotAvailableException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ResourceNotAvailableException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ResourceNotAvailableException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ResourceNotAvailableException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
	
	
}

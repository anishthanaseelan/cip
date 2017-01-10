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
package com.cts.cip.agent.model;

public class ErrorCodeMapDto { 
	
	private String carrier;
	private String carrierErrCd;
	private String carrierErrDesc;
	private String errCategoryCd;
	private String errCategoryDesc;
	
	
	
	
	@Override
	public String toString() {
		return "ErrorCodeMapDto [carrier=" + carrier + ", carrierErrCd=" + carrierErrCd + ", carrierErrDesc="
				+ carrierErrDesc + ", errCategoryCd=" + errCategoryCd + ", errCategoryDesc=" + errCategoryDesc + "]";
	}




	public String getCarrier() {
		return carrier;
	}




	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}




	public String getCarrierErrCd() {
		return carrierErrCd;
	}




	public void setCarrierErrCd(String carrierErrCd) {
		this.carrierErrCd = carrierErrCd;
	}




	public String getCarrierErrDesc() {
		return carrierErrDesc;
	}




	public void setCarrierErrDesc(String carrierErrDesc) {
		this.carrierErrDesc = carrierErrDesc;
	}




	public String getErrCategoryCd() {
		return errCategoryCd;
	}




	public void setErrCategoryCd(String errCategoryCd) {
		this.errCategoryCd = errCategoryCd;
	}




	public String getErrCategoryDesc() {
		return errCategoryDesc;
	}




	public void setErrCategoryDesc(String errCategoryDesc) {
		this.errCategoryDesc = errCategoryDesc;
	}
	

	
	


}

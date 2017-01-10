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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CreateRequest {
	public String getRequestTransactionID() {
		return requestTransactionID;
	}
	public void setRequestTransactionID(String requestTransactionID) {
		this.requestTransactionID = requestTransactionID;
	}
	
	public ShipperDetails getShipperDetails() {
		return shipperDetails;
	}
	public void setShipperDetails(ShipperDetails shipperDetails) {
		this.shipperDetails = shipperDetails;
	}
	public ShipToAddress getShipToAddress() {
		return shipToAddress;
	}
	public void setShipToAddress(ShipToAddress shipToAddress) {
		this.shipToAddress = shipToAddress;
	}
	public String getOutputFileFormat() {
		return outputFileFormat;
	}
	public void setOutputFileFormat(String outputFileFormat) {
		this.outputFileFormat = outputFileFormat;
	}
	public OrderDetails getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(OrderDetails orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<CarrierReferenceDetails> getReferenceDetails() {
		if (null  ==referenceDetails ){
			referenceDetails = new ArrayList<CarrierReferenceDetails>();
		}
		return referenceDetails;
	}
	public void setReferenceDetails(List<CarrierReferenceDetails> referenceDetails) {
		this.referenceDetails = referenceDetails;
	}
	private ShipperDetails shipperDetails;
	private ShipToAddress shipToAddress;
	private String outputFileFormat;
	private OrderDetails orderDetails;
	private String requestTransactionID;
	private List<CarrierReferenceDetails> referenceDetails;

}

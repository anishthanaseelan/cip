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

package com.cts.cip.common.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GetDocumentsResponse {
    protected Status status;

    protected ShippingUnitBase shippingUnit;

    protected String serviceType;

    protected String trackingNumber;

    protected List<ShippingDocument> documents;

    protected String responseTransactionID;

	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public ShippingUnitBase getShippingUnit() {
		return shippingUnit;
	}


	public void setShippingUnit(ShippingUnitBase shippingUnit) {
		this.shippingUnit = shippingUnit;
	}


	public String getServiceType() {
		return serviceType;
	}


	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}


	public String getTrackingNumber() {
		return trackingNumber;
	}


	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}


	public String getResponseTransactionID() {
		return responseTransactionID;
	}


	public void setResponseTransactionID(String responseTransactionID) {
		this.responseTransactionID = responseTransactionID;
	}


	public void setDocuments(List<ShippingDocument> documents) {
		this.documents = documents;
	}
	
    public List<ShippingDocument> getDocuments() {
        if (documents == null) {
            documents = new ArrayList<ShippingDocument>();
        }
        return this.documents;
    }

    
}

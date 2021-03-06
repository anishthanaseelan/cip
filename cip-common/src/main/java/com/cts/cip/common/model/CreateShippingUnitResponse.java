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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.06.16 at 10:02:27 AM CDT 
//


package com.cts.cip.common.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="status" type="{http://www.cts.com/cip/Common}status"/>
 *         &lt;element name="shippingUnit" type="{http://www.cts.com/cip/Common}shippingUnitBase"/>
 *         &lt;element name="serviceType" type="{http://www.cts.com/cip/Common}stringType"/>
 *         &lt;element name="trackingNumber" type="{http://www.cts.com/cip/Common}stringType"/>
 *         &lt;element name="documents" type="{http://www.cts.com/cip/Common}shippingDocument" maxOccurs="unbounded"/>
 *         &lt;element name="totalAmount" type="{http://www.cts.com/cip/Common}price"/>
 *       &lt;/sequence>
 *       &lt;attribute name="responseTransactionID" type="{http://www.cts.com/cip/Common}stringType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "status",
    "shippingUnit",
    "serviceType",
    "trackingNumber",
    "documents",
    "totalAmount"
})
@XmlRootElement(name = "CreateShippingUnitResponse", namespace = "http://www.cts.com/cip/CreateShippingUnitResponse")
public class CreateShippingUnitResponse {
	/*@XmlElement(namespace = "http://www.cts.com/cip/CreateShippingUnitResponse", required = true)
	protected List<FieldStatus> fieldStatusList;*/
	@XmlElement(namespace = "http://www.cts.com/cip/CreateShippingUnitResponse", required = true)
    protected Status status;
    @XmlElement(namespace = "http://www.cts.com/cip/CreateShippingUnitResponse", required = true)
    protected ShippingUnitBase shippingUnit;
    @XmlElement(namespace = "http://www.cts.com/cip/CreateShippingUnitResponse", required = true)
    protected String serviceType;
    @XmlElement(namespace = "http://www.cts.com/cip/CreateShippingUnitResponse", required = true)
    protected String trackingNumber;
    @XmlElement(namespace = "http://www.cts.com/cip/CreateShippingUnitResponse", required = true)
    protected List<ShippingDocument> documents;
    @XmlElement(namespace = "http://www.cts.com/cip/CreateShippingUnitResponse", required = true)
    protected Price totalAmount;
    @XmlAttribute(name = "responseTransactionID")
    protected String responseTransactionID;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the shippingUnit property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingUnitBase }
     *     
     */
    public ShippingUnitBase getShippingUnit() {
        return shippingUnit;
    }

    /**
     * Sets the value of the shippingUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingUnitBase }
     *     
     */
    public void setShippingUnit(ShippingUnitBase value) {
        this.shippingUnit = value;
    }

    /**
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceType(String value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the trackingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }

    /**
     * Sets the value of the trackingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingNumber(String value) {
        this.trackingNumber = value;
    }

    /**
     * Gets the value of the documents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocuments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShippingDocument }
     * 
     * 
     */
    public List<ShippingDocument> getDocuments() {
        if (documents == null) {
            documents = new ArrayList<ShippingDocument>();
        }
        return this.documents;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Price }
     *     
     */
    public Price getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Price }
     *     
     */
    public void setTotalAmount(Price value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the responseTransactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponseTransactionID() {
        return responseTransactionID;
    }

    /**
     * Sets the value of the responseTransactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponseTransactionID(String value) {
        this.responseTransactionID = value;
    }
    
    /*public List<FieldStatus> getFieldStatusList() {
		return fieldStatusList;
	}

	public void setFieldStatusList(List<FieldStatus> fieldStatusList) {
		this.fieldStatusList = fieldStatusList;
	}
*/
}

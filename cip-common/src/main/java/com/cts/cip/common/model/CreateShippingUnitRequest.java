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
// Generated on: 2016.06.16 at 10:27:10 AM CDT 
//


package com.cts.cip.common.model;

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
 *         &lt;element name="shippingUnit" type="{http://www.cts.com/cip/CreateShippingUnitRequest}shippingUnit"/>
 *       &lt;/sequence>
 *       &lt;attribute name="requestTransactionID" type="{http://www.cts.com/cip/Common}stringType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "shippingUnit"
})
@XmlRootElement(name = "createShippingUnitRequest", namespace = "http://www.cts.com/cip/CreateShippingUnitRequest")
public class CreateShippingUnitRequest {

    @XmlElement(namespace = "http://www.cts.com/cip/CreateShippingUnitRequest", required = true)
    protected ShippingUnit shippingUnit;
    
    
    @XmlAttribute(name = "requestTransactionID")
    protected String requestTransactionID;

    /**
     * Gets the value of the shippingUnit property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingUnit }
     *     
     */
    public ShippingUnit getShippingUnit() {
        return shippingUnit;
    }

    /**
     * Sets the value of the shippingUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingUnit }
     *     
     */
    public void setShippingUnit(ShippingUnit value) {
        this.shippingUnit = value;
    }

    /**
     * Gets the value of the requestTransactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestTransactionID() {
        return requestTransactionID;
    }

    /**
     * Sets the value of the requestTransactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestTransactionID(String value) {
        this.requestTransactionID = value;
    }

}
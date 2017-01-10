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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
 *         &lt;element name="load" type="{http://www.cts.com/cip/Common}loadBase"/>
 *         &lt;element name="shippingBaseUnits" type="{http://www.cts.com/cip/Common}shippingUnitBase" maxOccurs="unbounded"/>
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
    "load",
    "shippingBaseUnits"
})
@XmlRootElement(name = "LoadShippingUnitRequest", namespace = "http://www.cts.com/cip/LoadShippingUnitRequest")
public class LoadShippingUnitRequest {

    @XmlElement(namespace = "", required = true)
    @NotNull(message="{cip_common.loadbase.load.empty}")
    @Valid
    protected LoadBase load;
    
    @XmlElement(namespace = "", required = true)
    @Valid
    @NotNull(message="{cip_common.shippingunitbaselist.empty}")
    protected List<ShippingUnitBase> shippingBaseUnits;
    
    @Pattern(regexp = "^[A-Za-z0-9]+$", message="{cip_common.reqtransactionid.pattern}")
    @Size(min = 0, max = 36,  message = "{cip_common.reqtransactionid.maxlength}")
    @XmlAttribute(name = "requestTransactionID")
    protected String requestTransactionID;

    /**
     * Gets the value of the load property.
     * 
     * @return
     *     possible object is
     *     {@link LoadBase }
     *     
     */
    public LoadBase getLoad() {
        return load;
    }

    /**
     * Sets the value of the load property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoadBase }
     *     
     */
    public void setLoad(LoadBase value) {
        this.load = value;
    }

    /**
     * Gets the value of the shippingBaseUnits property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the shippingBaseUnits property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getShippingBaseUnits().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ShippingUnitBase }
     * 
     * 
     */
    public List<ShippingUnitBase> getShippingBaseUnits() {
        if (shippingBaseUnits == null) {
            shippingBaseUnits = new ArrayList<ShippingUnitBase>();
        }
        return this.shippingBaseUnits;
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

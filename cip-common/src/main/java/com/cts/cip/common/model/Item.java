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

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for item complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="item">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sequenceNumber" type="{http://www.cts.com/cip/Common}shortType" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.cts.com/cip/Common}shortType" minOccurs="0"/>
 *         &lt;element name="vasCode" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="itemNumber" type="{http://www.cts.com/cip/Common}stringType"/>
 *         &lt;element name="description" type="{http://www.cts.com/cip/Common}stringType"/>
 *         &lt;element name="unitPrice" type="{http://www.cts.com/cip/Common}price"/>
 *         &lt;element name="totalPrice" type="{http://www.cts.com/cip/Common}price"/>
 *         &lt;element name="giftMessages" type="{http://www.cts.com/cip/CreateShippingUnitRequest}giftMessagesType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item", namespace = "http://www.cts.com/cip/CreateShippingUnitRequest", propOrder = {
    "sequenceNumber",
    "quantity",
    "vasCode",
    "itemNumber",
    "description",
    "secondaryDescriptions",
    "unitPrice",
    "totalPrice",
    "giftMessages"
})
public class Item {

	@Max(value = 999, message = "{cip_common.shippingunit.invoiceDetails.item.sequenceNumber.maxlimit}")
    @Min(value = 0, message = "{cip_common.shippingunit.invoiceDetails.item.sequenceNumber.minlimit}")
    protected Short sequenceNumber;
    
    @Max(value = 999, message = "{cip_common.shippingunit.invoiceDetails.item.quantity.maxlimit}")
    @Min(value = 0, message = "{cip_common.shippingunit.invoiceDetails.item.quantity.minlimit}")
    protected Short quantity;
    
    //@DecimalMax(value = "999.99", message = "{cip_common.shippingunit.invoiceDetails.item.vascode.maxlimit}")
    //@DecimalMin(value = "0.00", message = "{cip_common.shippingunit.invoiceDetails.item.vascode.minlimit}")
    //protected Float vasCode;
    
    @Pattern(regexp = "^[A-Za-z0-9\\s\\(\\)\\[\\]\\{\\}\\\\^\\$\\|\\?\\*\\+\\.\\<\\>\\-\\=\\!\\_\\,\\*]+$", 
    	   	message="{cip_common.shippingunit.invoiceDetails.item.vascode.pattern}")
    	    @Size(min = 0, max = 100,  message = "{cip_common.shippingunit.invoiceDetails.item.vascode.maxlength}")
    protected String vasCode;
    
    @XmlElement(required = true)
    @Pattern(regexp = "^[0-9A-za-z]+$", 
   	message="{cip_common.shippingunit.invoiceDetails.item.itemnumber.pattern}")
    @Size(min = 0, max = 20,  message = "{cip_common.shippingunit.invoiceDetails.item.itemnumber.size}")
    protected String itemNumber;
    
    //@XmlElement(required = true)
    //@Pattern(regexp = "^[A-Za-z0-9\\s\\(\\)\\[\\]\\{\\}\\\\^\\$\\|\\?\\*\\+\\.\\<\\>\\-\\=\\!\\_\\,\\*]+$", 
	//message="{cip_common.shippingunit.invoiceDetails.item.description.pattern}")
    @Size(min = 0, max = 250, message = "{cip_common.shippingunit.invoiceDetails.item.description.maxlength}")
    protected String description;
    
    @XmlElement(required = true)
    @Valid
    protected Price unitPrice;
    @XmlElement(required = true)
    @Valid
    protected Price totalPrice;
    


	protected SecondaryDescriptionType secondaryDescriptions;
    
   /* @XmlElement(required = true)
    @Pattern(regexp = "^[A-Za-z0-9\\s\\(\\)\\[\\]\\{\\}\\\\^\\$\\|\\?\\*\\+\\.\\<\\>\\-\\=\\!\\_\\;\\*]+$", 
	message="{cip_common.shippingunit.invoiceDetails.item.giftMessage.pattern}")
    @Size(min = 0, max = 100, message = "{cip_common.shippingunit.invoiceDetails.item.giftMessage.maxlength}")
    protected String giftMessage;*/
 
	

	//Not validating since it can be empty or can accept any kind of special characters.
    protected GiftMessagesType giftMessages;
    


    /**
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setSequenceNumber(Short value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setQuantity(Short value) {
        this.quantity = value;
    }

    

    public String getVasCode() {
		return vasCode;
	}

	public void setVasCode(String vasCode) {
		this.vasCode = vasCode;
	}

	public GiftMessagesType getGiftMessages() {
		return giftMessages;
	}

	public void setGiftMessages(GiftMessagesType giftMessages) {
		this.giftMessages = giftMessages;
	}

	/**
     * Gets the value of the itemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemNumber() {
        return itemNumber;
    }

    /**
     * Sets the value of the itemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemNumber(String value) {
        this.itemNumber = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Price }
     *     
     */
    public Price getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Price }
     *     
     */
    public void setUnitPrice(Price value) {
        this.unitPrice = value;
    }

    /**
     * Gets the value of the totalPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Price }
     *     
     */
    public Price getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the value of the totalPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Price }
     *     
     */
    public void setTotalPrice(Price value) {
        this.totalPrice = value;
    }

   /* *//**
     * Gets the value of the giftMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     *//*
    public String getGiftMessage() {
        return giftMessage;
    }

    *//**
     * Sets the value of the giftMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     *//*
    public void setGiftMessage(String value) {
        this.giftMessage = value;
    }*/
    public SecondaryDescriptionType getSecondaryDescriptions() {
		return secondaryDescriptions;
	}

	public void setSecondaryDescriptions(SecondaryDescriptionType secondaryDescriptions) {
		this.secondaryDescriptions = secondaryDescriptions;
	}
}

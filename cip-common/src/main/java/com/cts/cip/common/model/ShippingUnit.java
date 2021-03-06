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

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for shippingUnit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="shippingUnit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="shippingUnitBase" type="{http://www.cts.com/cip/Common}shippingUnitBase"/>
 *         &lt;element name="weightAndDimensions" type="{http://www.cts.com/cip/Common}weightAndDimensions"/>
 *         &lt;element name="shipperDetails" type="{http://www.cts.com/cip/CreateShippingUnitRequest}shipperDetails"/>
 *         &lt;element name="order" type="{http://www.cts.com/cip/CreateShippingUnitRequest}order"/>
 *         &lt;element name="invoiceDetails" type="{http://www.cts.com/cip/CreateShippingUnitRequest}invoiceDetails"/>
 *         &lt;element name="requestedOutputFileFormat" type="{http://www.cts.com/cip/Common}fileFormat"/>
 *         &lt;element name="carrierReference" type="{http://www.cts.com/cip/CreateShippingUnitRequest}carrierReference" minOccurs="0"/>
 *         &lt;element name="trackingNumber" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="base64LabelContentRequired" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="base64LabelContent" type="{http://www.cts.com/cip/Common}binayBase64Type" minOccurs="0"/>
 *         &lt;element name="labelPath" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="postalBarCode" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="maxiCode" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="routingCode" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="versionDate" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="packageWeight" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="packageWeightUOM" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/>
 *         &lt;element name="isMaxiCodeReqd" type="{http://www.cts.com/cip/Common}stringType" minOccurs="0"/> 
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shippingUnit", namespace = "http://www.cts.com/cip/CreateShippingUnitRequest", propOrder = {
    "shippingUnitBase",
    "weightAndDimensions",
    "shipperDetails",
    "order",
    "invoiceDetails",
    "requestedOutputFileFormat",
    "carrierReference",
    "trackingNumber",
    "base64LabelContentRequired",
    "base64LabelContent",
    "labelPath",
    "postalBarCode",
    "maxiCode",
    "routingCode",
    "version",
    "versionDate",
    "packageWeight",
    "packageWeightUOM",
    "isMaxiCodeReqd",
    "lastUpdatedTimeStamp",
    "serviceName",
    "serviceIcon_p1",
    "serviceIcon_p2",
    "serviceIcon_p3",
    "propertyOne",
    "propertyTwo",
    "propertyThree",
    "propertyFour",
    "propertyFive",
    "propertySix",
    "propertySeven",
    "propertyEight",
    "propertyNine",
    "propertyTen",
    "labelParameters",
    "giftMessage",
    "itemList" 
})

@XmlRootElement
public class ShippingUnit {

	
    @XmlElement(required = true)    
    @NotNull
    @Valid
    protected ShippingUnitBase shippingUnitBase;
    @XmlElement(required = true)
    @NotNull
    @Valid
    protected WeightAndDimensions weightAndDimensions;
    @XmlElement(required = true)
    @Valid
    @NotNull(message="{cip_common.shippingunit.shipperdetails.empty}")
    protected ShipperDetails shipperDetails;
    @XmlElement(required = true)
    @Valid
    @NotNull
    protected Order order;
    @XmlElement(required = true)
    @Valid
    @NotNull
    protected InvoiceDetails invoiceDetails;
    @XmlElement(required = true, defaultValue = "PDF")
    @XmlSchemaType(name = "string")
    protected FileFormat requestedOutputFileFormat;
    @Valid
    protected CarrierReference carrierReference;
    protected String trackingNumber;
    protected String base64LabelContentRequired;
    protected byte[] base64LabelContent;
    protected String labelPath;
    
    protected String postalBarCode;
    protected String maxiCode;
    protected String routingCode;
    protected String version;
    protected String versionDate;

	protected String packageWeight;
	protected String packageWeightUOM;
	protected String isMaxiCodeReqd;
    private Date lastUpdatedTimeStamp;
   
	protected KeyValuePair[] labelParameters;
	protected String serviceName;
	protected String serviceIcon_p1;
	protected String serviceIcon_p2;
	protected String serviceIcon_p3;
	
	protected String propertyOne;
	protected String propertyTwo;
	protected String propertyThree;
	protected String propertyFour;
	protected String propertyFive;
	protected String propertySix;
	protected String propertySeven;
	protected String propertyEight;
	protected String propertyNine;
	protected String propertyTen;

	
	
	
	protected List<String> giftMessage;
    protected List<Item> itemList; 
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceIcon_p1() {
		return serviceIcon_p1;
	}

	public void setServiceIcon_p1(String serviceIcon_p1) {
		this.serviceIcon_p1 = serviceIcon_p1;
	}

	public String getServiceIcon_p2() {
		return serviceIcon_p2;
	}

	public void setServiceIcon_p2(String serviceIcon_p2) {
		this.serviceIcon_p2 = serviceIcon_p2;
	}

	public String getServiceIcon_p3() {
		return serviceIcon_p3;
	}

	public void setServiceIcon_p3(String serviceIcon_p3) {
		this.serviceIcon_p3 = serviceIcon_p3;
	}
	
	public KeyValuePair[] getLabelParameters() {
		return labelParameters;
	}

	public void setLabelParameters(KeyValuePair[] labelParameters) {
		this.labelParameters = labelParameters;
	}
	
    public void setLastUpdatedTimeStamp(Date lastUpdatedTimeStamp) {
		this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
	}

	/**
     * Gets the value of the shippingUnitBase property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingUnitBase }
     *     
     */
    public ShippingUnitBase getShippingUnitBase() {
        return shippingUnitBase;
    }

    /**
     * Sets the value of the shippingUnitBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingUnitBase }
     *     
     */
    public void setShippingUnitBase(ShippingUnitBase value) {
        this.shippingUnitBase = value;
    }

    /**
     * Gets the value of the weightAndDimensions property.
     * 
     * @return
     *     possible object is
     *     {@link WeightAndDimensions }
     *     
     */
    public WeightAndDimensions getWeightAndDimensions() {
        return weightAndDimensions;
    }

    /**
     * Sets the value of the weightAndDimensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link WeightAndDimensions }
     *     
     */
    public void setWeightAndDimensions(WeightAndDimensions value) {
        this.weightAndDimensions = value;
    }

    /**
     * Gets the value of the shipperDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ShipperDetails }
     *     
     */
    public ShipperDetails getShipperDetails() {
        return shipperDetails;
    }

    /**
     * Sets the value of the shipperDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipperDetails }
     *     
     */
    public void setShipperDetails(ShipperDetails value) {
        this.shipperDetails = value;
    }

    /**
     * Gets the value of the order property.
     * 
     * @return
     *     possible object is
     *     {@link Order }
     *     
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     * 
     * @param value
     *     allowed object is
     *     {@link Order }
     *     
     */
    public void setOrder(Order value) {
        this.order = value;
    }

    /**
     * Gets the value of the invoiceDetails property.
     * 
     * @return
     *     possible object is
     *     {@link InvoiceDetails }
     *     
     */
    public InvoiceDetails getInvoiceDetails() {
        return invoiceDetails;
    }

    /**
     * Sets the value of the invoiceDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link InvoiceDetails }
     *     
     */
    public void setInvoiceDetails(InvoiceDetails value) {
        this.invoiceDetails = value;
    }

    /**
     * Gets the value of the requestedOutputFileFormat property.
     * 
     * @return
     *     possible object is
     *     {@link FileFormat }
     *     
     */
    public FileFormat getRequestedOutputFileFormat() {
        return requestedOutputFileFormat;
    }

    /**
     * Sets the value of the requestedOutputFileFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link FileFormat }
     *     
     */
    public void setRequestedOutputFileFormat(FileFormat value) {
        this.requestedOutputFileFormat = value;
    }

    /**
     * Gets the value of the carrierReference property.
     * 
     * @return
     *     possible object is
     *     {@link CarrierReference }
     *     
     */
    public CarrierReference getCarrierReference() {
        return carrierReference;
    }

    /**
     * Sets the value of the carrierReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarrierReference }
     *     
     */
    public void setCarrierReference(CarrierReference value) {
        this.carrierReference = value;
    }

    

    /**
	 * @return the trackingNumber
	 */
	public String getTrackingNumber() {
		return trackingNumber;
	}

	/**
	 * @param trackingNumber the trackingNumber to set
	 */
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	/**
     * Gets the value of the base64LabelContentRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBase64LabelContentRequired() {
        return base64LabelContentRequired;
    }

    /**
     * Sets the value of the base64LabelContentRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBase64LabelContentRequired(String value) {
        this.base64LabelContentRequired = value;
    }

    /**
     * Gets the value of the base64LabelContent property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getBase64LabelContent() {
        return base64LabelContent;
    }

    /**
     * Sets the value of the base64LabelContent property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setBase64LabelContent(byte[] value) {
        this.base64LabelContent = value;
    }

    /**
     * Gets the value of the labelPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLabelPath() {
        return labelPath;
    }

    /**
     * Sets the value of the labelPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLabelPath(String value) {
        this.labelPath = value;
    }

	public Date getLastUpdatedTimeStamp() {
		
		return lastUpdatedTimeStamp;
	}

	/**
	 * @return the postalBarCode
	 */
	public String getPostalBarCode() {
		return postalBarCode;
	}

	/**
	 * @param postalBarCode the postalBarCode to set
	 */
	public void setPostalBarCode(String postalBarCode) {
		this.postalBarCode = postalBarCode;
	}

	/**
	 * @return the maxiCode
	 */
	public String getMaxiCode() {
		return maxiCode;
	}

	/**
	 * @param maxiCode the maxiCode to set
	 */
	public void setMaxiCode(String maxiCode) {
		this.maxiCode = maxiCode;
	}

	/**
	 * @return the routingCode
	 */
	public String getRoutingCode() {
		return routingCode;
	}

	/**
	 * @param routingCode the routingCode to set
	 */
	public void setRoutingCode(String routingCode) {
		this.routingCode = routingCode;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the versionDate
	 */
	public String getVersionDate() {
		return versionDate;
	}

	/**
	 * @param versionDate the versionDate to set
	 */
	public void setVersionDate(String versionDate) {
		this.versionDate = versionDate;
	}

	public String getPackageWeight() {
		return packageWeight;
	}

	public void setPackageWeight(String packageWeight) {
		this.packageWeight = packageWeight;
	}

	public String getPackageWeightUOM() {
		return packageWeightUOM;
	}

	public void setPackageWeightUOM(String packageWeightUOM) {
		this.packageWeightUOM = packageWeightUOM;
	}



	public String getIsMaxiCodeReqd() {
		return isMaxiCodeReqd;
	}

	public void setIsMaxiCodeReqd(String isMaxiCodeReqd) {
		this.isMaxiCodeReqd = isMaxiCodeReqd;
	}

	public List<String> getGiftMessage() {
		return giftMessage;
	}

	public void setGiftMessage(List<String> giftMessage) {
		this.giftMessage = giftMessage;
	}

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}

	public String getPropertyOne() {
		return propertyOne;
	}

	public void setPropertyOne(String propertyOne) {
		this.propertyOne = propertyOne;
	}

	public String getPropertyTwo() {
		return propertyTwo;
	}

	public void setPropertyTwo(String propertyTwo) {
		this.propertyTwo = propertyTwo;
	}

	public String getPropertyThree() {
		return propertyThree;
	}

	public void setPropertyThree(String propertyThree) {
		this.propertyThree = propertyThree;
	}

	public String getPropertyFour() {
		return propertyFour;
	}

	public void setPropertyFour(String propertyFour) {
		this.propertyFour = propertyFour;
	}

	public String getPropertyFive() {
		return propertyFive;
	}

	public void setPropertyFive(String propertyFive) {
		this.propertyFive = propertyFive;
	}

	public String getPropertySix() {
		return propertySix;
	}

	public void setPropertySix(String propertySix) {
		this.propertySix = propertySix;
	}

	public String getPropertySeven() {
		return propertySeven;
	}

	public void setPropertySeven(String propertySeven) {
		this.propertySeven = propertySeven;
	}

	public String getPropertyEight() {
		return propertyEight;
	}

	public void setPropertyEight(String propertyEight) {
		this.propertyEight = propertyEight;
	}

	public String getPropertyNine() {
		return propertyNine;
	}

	public void setPropertyNine(String propertyNine) {
		this.propertyNine = propertyNine;
	}

	public String getPropertyTen() {
		return propertyTen;
	}

	public void setPropertyTen(String propertyTen) {
		this.propertyTen = propertyTen;
	}
	

}

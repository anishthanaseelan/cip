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

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for dimensions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="dimensions">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="length" type="{http://www.cts.com/cip/Common}decType" minOccurs="0"/>
 *         &lt;element name="width" type="{http://www.cts.com/cip/Common}decType" minOccurs="0"/>
 *         &lt;element name="height" type="{http://www.cts.com/cip/Common}decType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dimensionUOM" type="{http://www.cts.com/cip/Common}dimensionsUOM" default="IN" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dimensions", propOrder = {
    "length",
    "width",
    "height"
})
public class Dimensions {
	
	
	@DecimalMax(value = "999.99", message = "{cip_common.shippingunit.weightanddimensions.dimensions.length.maxlimit}")
   @DecimalMin(value = "0.01", message = "{cip_common.shippingunit.weightanddimensions.dimensions.length.minlimit}")
	//@Pattern(regexp = "^\\d*\\.?\\d{2}$", message="{cip_common.shippingunit.weightanddimensions.decimal}")
	@Digits(fraction=2,integer=3 , message="{cip_common.shippingunit.weightanddimensions.decimal}")
	
	protected Float length;
	
	@DecimalMax(value = "999.99", message = "{cip_common.shippingunit.weightanddimensions.dimensions.width.maxlimit}")
    @DecimalMin(value = "0.01", message = "{cip_common.shippingunit.weightanddimensions.dimensions.width.minlimit}")
	// @Pattern(regexp = "^\\d*\\.?\\d{2}$", message="{cip_common.shippingunit.weightanddimensions.decimal}")
	@Digits(fraction=2,integer=3 , message="{cip_common.shippingunit.weightanddimensions.decimal}")
    protected Float width;
	
	@DecimalMax(value = "999.99", message = "{cip_common.shippingunit.weightanddimensions.dimensions.height.maxlimit}")
	@DecimalMin(value = "0.01", message = "{cip_common.shippingunit.weightanddimensions.dimensions.height.minlimit}")
	// @Pattern(regexp = "^\\d*\\.?\\d{2}$", message="{cip_common.shippingunit.weightanddimensions.decimal}")
	@Digits(fraction=2,integer=3 , message="{cip_common.shippingunit.weightanddimensions.decimal}")
    protected Float height;
	
    @XmlAttribute(name = "dimensionUOM", namespace = "http://www.cts.com/cip/Common")
    protected DimensionsUOM dimensionUOM;

    /**
     * Gets the value of the length property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setLength(Float value) {
        this.length = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setWidth(Float value) {
        this.width = value;
    }

    /**
     * Gets the value of the height property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getHeight() {
        return height;
    }

    /**
     * Sets the value of the height property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setHeight(Float value) {
        this.height = value;
    }

    /**
     * Gets the value of the dimensionUOM property.
     * 
     * @return
     *     possible object is
     *     {@link DimensionsUOM }
     *     
     */
    public DimensionsUOM getDimensionUOM() {
        if (dimensionUOM == null) {
            return DimensionsUOM.IN;
        } else {
            return dimensionUOM;
        }
    }

    /**
     * Sets the value of the dimensionUOM property.
     * 
     * @param value
     *     allowed object is
     *     {@link DimensionsUOM }
     *     
     */
    public void setDimensionUOM(DimensionsUOM value) {
        this.dimensionUOM = value;
    }

}

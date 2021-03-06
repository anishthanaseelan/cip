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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for specialService.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="specialService">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="RESIDENTIAL"/>
 *     &lt;enumeration value="SIGNATURE_ON_CONFIRMATION"/>
 *     &lt;enumeration value="CASH_ON_DELIVERY"/>
 *     &lt;enumeration value="HOLD_AT_LOCATION"/>
 *     &lt;enumeration value="HAZARDOUS_MATERIAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "specialService", namespace = "http://www.cts.com/cip/CreateShippingUnitRequest")
@XmlEnum
public enum SpecialService {

    RESIDENTIAL,
    SIGNATURE_ON_CONFIRMATION,
    CASH_ON_DELIVERY,
    HOLD_AT_LOCATION,
    HAZARDOUS_MATERIAL;

    public String value() {
        return name();
    }

    public static SpecialService fromValue(String v) {
        return valueOf(v);
    }

}

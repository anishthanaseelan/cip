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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cts.cip.common.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ShippingUnits_QNAME = new QName("http://www.cts.com/cip/GetShippingUnits", "shippingUnits");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cts.cip.common.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ShippingUnits }
     * 
     */
    public ShippingUnits createShippingUnits() {
        return new ShippingUnits();
    }

    /**
     * Create an instance of {@link ShippingUnitList }
     * 
     */
    public ShippingUnitList createShippingUnitList() {
        return new ShippingUnitList();
    }

    /**
     * Create an instance of {@link CreateShippingUnitRequest }
     * 
     */
    public CreateShippingUnitRequest createCreateShippingUnitRequest() {
        return new CreateShippingUnitRequest();
    }

    /**
     * Create an instance of {@link ShippingUnit }
     * 
     */
    public ShippingUnit createShippingUnit() {
        return new ShippingUnit();
    }

    /**
     * Create an instance of {@link ShipperDetails }
     * 
     */
    public ShipperDetails createShipperDetails() {
        return new ShipperDetails();
    }

    /**
     * Create an instance of {@link CarrierReference }
     * 
     */
    public CarrierReference createCarrierReference() {
        return new CarrierReference();
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link ShipperServiceType }
     * 
     */
    public ShipperServiceType createShipperServiceType() {
        return new ShipperServiceType();
    }

    /**
     * Create an instance of {@link HazardousMaterial }
     * 
     */
    public HazardousMaterial createHazardousMaterial() {
        return new HazardousMaterial();
    }

    /**
     * Create an instance of {@link InvoiceDetails }
     * 
     */
    public InvoiceDetails createInvoiceDetails() {
        return new InvoiceDetails();
    }

    /**
     * Create an instance of {@link ShippingOptions }
     * 
     */
    public ShippingOptions createShippingOptions() {
        return new ShippingOptions();
    }

    /**
     * Create an instance of {@link Order }
     * 
     */
    public Order createOrder() {
        return new Order();
    }

    /**
     * Create an instance of {@link ShippingUnitStatus }
     * 
     */
    public ShippingUnitStatus createShippingUnitStatus() {
        return new ShippingUnitStatus();
    }

    /**
     * Create an instance of {@link Error }
     * 
     */
    public Error createError() {
        return new Error();
    }

    /**
     * Create an instance of {@link Load }
     * 
     */
    public Load createLoad() {
        return new Load();
    }

    /**
     * Create an instance of {@link Price }
     * 
     */
    public Price createPrice() {
        return new Price();
    }

    /**
     * Create an instance of {@link WeightAndDimensions }
     * 
     */
    public WeightAndDimensions createWeightAndDimensions() {
        return new WeightAndDimensions();
    }

    /**
     * Create an instance of {@link ShippingDocument }
     * 
     */
    public ShippingDocument createShippingDocument() {
        return new ShippingDocument();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link Weight }
     * 
     */
    public Weight createWeight() {
        return new Weight();
    }

    /**
     * Create an instance of {@link LoadBase }
     * 
     */
    public LoadBase createLoadBase() {
        return new LoadBase();
    }

    /**
     * Create an instance of {@link TotalUnitWeight }
     * 
     */
    public TotalUnitWeight createTotalUnitWeight() {
        return new TotalUnitWeight();
    }

    /**
     * Create an instance of {@link ShippingUnitBase }
     * 
     */
    public ShippingUnitBase createShippingUnitBase() {
        return new ShippingUnitBase();
    }

    /**
     * Create an instance of {@link ShippingUnitDocument }
     * 
     */
    public ShippingUnitDocument createShippingUnitDocument() {
        return new ShippingUnitDocument();
    }

    /**
     * Create an instance of {@link Dimensions }
     * 
     */
    public Dimensions createDimensions() {
        return new Dimensions();
    }

    /**
     * Create an instance of {@link Status }
     * 
     */
    public Status createStatus() {
        return new Status();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShippingUnits }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.cts.com/cip/GetShippingUnits", name = "shippingUnits")
    public JAXBElement<ShippingUnits> createShippingUnits(ShippingUnits value) {
        return new JAXBElement<ShippingUnits>(_ShippingUnits_QNAME, ShippingUnits.class, null, value);
    }

}
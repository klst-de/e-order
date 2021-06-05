//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}BUYER_PARTY"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}SUPPLIER_PARTY"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}INVOICE_PARTY" minOccurs="0"/>
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}SHIPMENT_PARTIES" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "buyerparty",
    "supplierparty",
    "invoiceparty",
    "shipmentparties"
})
@XmlRootElement(name = "ORDER_PARTIES")
public class ORDERPARTIES {

    @XmlElement(name = "BUYER_PARTY", required = true)
    protected BUYERPARTY buyerparty;
    @XmlElement(name = "SUPPLIER_PARTY", required = true)
    protected SUPPLIERPARTY supplierparty;
    @XmlElement(name = "INVOICE_PARTY")
    protected INVOICEPARTY invoiceparty;
    @XmlElement(name = "SHIPMENT_PARTIES")
    protected SHIPMENTPARTIES shipmentparties;

    /**
     * Gets the value of the buyerparty property.
     * 
     * @return
     *     possible object is
     *     {@link BUYERPARTY }
     *     
     */
    public BUYERPARTY getBUYERPARTY() {
        return buyerparty;
    }

    /**
     * Sets the value of the buyerparty property.
     * 
     * @param value
     *     allowed object is
     *     {@link BUYERPARTY }
     *     
     */
    public void setBUYERPARTY(BUYERPARTY value) {
        this.buyerparty = value;
    }

    /**
     * Gets the value of the supplierparty property.
     * 
     * @return
     *     possible object is
     *     {@link SUPPLIERPARTY }
     *     
     */
    public SUPPLIERPARTY getSUPPLIERPARTY() {
        return supplierparty;
    }

    /**
     * Sets the value of the supplierparty property.
     * 
     * @param value
     *     allowed object is
     *     {@link SUPPLIERPARTY }
     *     
     */
    public void setSUPPLIERPARTY(SUPPLIERPARTY value) {
        this.supplierparty = value;
    }

    /**
     * Gets the value of the invoiceparty property.
     * 
     * @return
     *     possible object is
     *     {@link INVOICEPARTY }
     *     
     */
    public INVOICEPARTY getINVOICEPARTY() {
        return invoiceparty;
    }

    /**
     * Sets the value of the invoiceparty property.
     * 
     * @param value
     *     allowed object is
     *     {@link INVOICEPARTY }
     *     
     */
    public void setINVOICEPARTY(INVOICEPARTY value) {
        this.invoiceparty = value;
    }

    /**
     * Gets the value of the shipmentparties property.
     * 
     * @return
     *     possible object is
     *     {@link SHIPMENTPARTIES }
     *     
     */
    public SHIPMENTPARTIES getSHIPMENTPARTIES() {
        return shipmentparties;
    }

    /**
     * Sets the value of the shipmentparties property.
     * 
     * @param value
     *     allowed object is
     *     {@link SHIPMENTPARTIES }
     *     
     */
    public void setSHIPMENTPARTIES(SHIPMENTPARTIES value) {
        this.shipmentparties = value;
    }

}
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.17 at 10:18:46 PM CEST 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.IndicatorType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.QuantityType;


/**
 * Line Trade Delivery
 * 
 * <p>Java class for LineTradeDeliveryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineTradeDeliveryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PartialDeliveryAllowedIndicator" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}IndicatorType" minOccurs="0"/>
 *         &lt;element name="RequestedQuantity" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}QuantityType"/>
 *         &lt;element name="AgreedQuantity" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}QuantityType" minOccurs="0"/>
 *         &lt;element name="PackageQuantity" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}QuantityType" minOccurs="0"/>
 *         &lt;element name="PerPackageUnitQuantity" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}QuantityType" minOccurs="0"/>
 *         &lt;element name="ShipToTradeParty" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:128}TradePartyType" minOccurs="0"/>
 *         &lt;element name="UltimateShipToTradeParty" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:128}TradePartyType" minOccurs="0"/>
 *         &lt;element name="ShipFromTradeParty" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:128}TradePartyType" minOccurs="0"/>
 *         &lt;element name="RequestedDespatchSupplyChainEvent" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:128}SupplyChainEventType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RequestedDeliverySupplyChainEvent" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:128}SupplyChainEventType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeDeliveryType", propOrder = {
    "partialDeliveryAllowedIndicator",
    "requestedQuantity",
    "agreedQuantity",
    "packageQuantity",
    "perPackageUnitQuantity",
    "shipToTradeParty",
    "ultimateShipToTradeParty",
    "shipFromTradeParty",
    "requestedDespatchSupplyChainEvent",
    "requestedDeliverySupplyChainEvent"
})
public class LineTradeDeliveryType {

    @XmlElement(name = "PartialDeliveryAllowedIndicator")
    protected IndicatorType partialDeliveryAllowedIndicator;
    @XmlElement(name = "RequestedQuantity", required = true)
    protected QuantityType requestedQuantity;
    @XmlElement(name = "AgreedQuantity")
    protected QuantityType agreedQuantity;
    @XmlElement(name = "PackageQuantity")
    protected QuantityType packageQuantity;
    @XmlElement(name = "PerPackageUnitQuantity")
    protected QuantityType perPackageUnitQuantity;
    @XmlElement(name = "ShipToTradeParty")
    protected TradePartyType shipToTradeParty;
    @XmlElement(name = "UltimateShipToTradeParty")
    protected TradePartyType ultimateShipToTradeParty;
    @XmlElement(name = "ShipFromTradeParty")
    protected TradePartyType shipFromTradeParty;
    @XmlElement(name = "RequestedDespatchSupplyChainEvent")
    protected List<SupplyChainEventType> requestedDespatchSupplyChainEvent;
    @XmlElement(name = "RequestedDeliverySupplyChainEvent")
    protected List<SupplyChainEventType> requestedDeliverySupplyChainEvent;

    /**
     * Gets the value of the partialDeliveryAllowedIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link IndicatorType }
     *     
     */
    public IndicatorType getPartialDeliveryAllowedIndicator() {
        return partialDeliveryAllowedIndicator;
    }

    /**
     * Sets the value of the partialDeliveryAllowedIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndicatorType }
     *     
     */
    public void setPartialDeliveryAllowedIndicator(IndicatorType value) {
        this.partialDeliveryAllowedIndicator = value;
    }

    /**
     * Gets the value of the requestedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getRequestedQuantity() {
        return requestedQuantity;
    }

    /**
     * Sets the value of the requestedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setRequestedQuantity(QuantityType value) {
        this.requestedQuantity = value;
    }

    /**
     * Gets the value of the agreedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getAgreedQuantity() {
        return agreedQuantity;
    }

    /**
     * Sets the value of the agreedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setAgreedQuantity(QuantityType value) {
        this.agreedQuantity = value;
    }

    /**
     * Gets the value of the packageQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getPackageQuantity() {
        return packageQuantity;
    }

    /**
     * Sets the value of the packageQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setPackageQuantity(QuantityType value) {
        this.packageQuantity = value;
    }

    /**
     * Gets the value of the perPackageUnitQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getPerPackageUnitQuantity() {
        return perPackageUnitQuantity;
    }

    /**
     * Sets the value of the perPackageUnitQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setPerPackageUnitQuantity(QuantityType value) {
        this.perPackageUnitQuantity = value;
    }

    /**
     * Gets the value of the shipToTradeParty property.
     * 
     * @return
     *     possible object is
     *     {@link TradePartyType }
     *     
     */
    public TradePartyType getShipToTradeParty() {
        return shipToTradeParty;
    }

    /**
     * Sets the value of the shipToTradeParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePartyType }
     *     
     */
    public void setShipToTradeParty(TradePartyType value) {
        this.shipToTradeParty = value;
    }

    /**
     * Gets the value of the ultimateShipToTradeParty property.
     * 
     * @return
     *     possible object is
     *     {@link TradePartyType }
     *     
     */
    public TradePartyType getUltimateShipToTradeParty() {
        return ultimateShipToTradeParty;
    }

    /**
     * Sets the value of the ultimateShipToTradeParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePartyType }
     *     
     */
    public void setUltimateShipToTradeParty(TradePartyType value) {
        this.ultimateShipToTradeParty = value;
    }

    /**
     * Gets the value of the shipFromTradeParty property.
     * 
     * @return
     *     possible object is
     *     {@link TradePartyType }
     *     
     */
    public TradePartyType getShipFromTradeParty() {
        return shipFromTradeParty;
    }

    /**
     * Sets the value of the shipFromTradeParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePartyType }
     *     
     */
    public void setShipFromTradeParty(TradePartyType value) {
        this.shipFromTradeParty = value;
    }

    /**
     * Gets the value of the requestedDespatchSupplyChainEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestedDespatchSupplyChainEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestedDespatchSupplyChainEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplyChainEventType }
     * 
     * 
     */
    public List<SupplyChainEventType> getRequestedDespatchSupplyChainEvent() {
        if (requestedDespatchSupplyChainEvent == null) {
            requestedDespatchSupplyChainEvent = new ArrayList<SupplyChainEventType>();
        }
        return this.requestedDespatchSupplyChainEvent;
    }

    /**
     * Gets the value of the requestedDeliverySupplyChainEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestedDeliverySupplyChainEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestedDeliverySupplyChainEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplyChainEventType }
     * 
     * 
     */
    public List<SupplyChainEventType> getRequestedDeliverySupplyChainEvent() {
        if (requestedDeliverySupplyChainEvent == null) {
            requestedDeliverySupplyChainEvent = new ArrayList<SupplyChainEventType>();
        }
        return this.requestedDeliverySupplyChainEvent;
    }

}

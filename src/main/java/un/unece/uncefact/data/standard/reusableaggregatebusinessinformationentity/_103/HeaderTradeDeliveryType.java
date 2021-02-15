//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.10 at 09:19:13 PM CET 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Header Trade Delivery
 * 
 * <p>Java class for HeaderTradeDeliveryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderTradeDeliveryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ShipToTradeParty" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}TradePartyType" minOccurs="0"/>
 *         &lt;element name="ShipFromTradeParty" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}TradePartyType" minOccurs="0"/>
 *         &lt;element name="RequestedDeliverySupplyChainEvent" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}SupplyChainEventType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RequestedDespatchSupplyChainEvent" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}SupplyChainEventType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderTradeDeliveryType", propOrder = {
    "shipToTradeParty",
    "shipFromTradeParty",
    "requestedDeliverySupplyChainEvent",
    "requestedDespatchSupplyChainEvent"
})
public class HeaderTradeDeliveryType {

    @XmlElement(name = "ShipToTradeParty")
    protected TradePartyType shipToTradeParty;
    @XmlElement(name = "ShipFromTradeParty")
    protected TradePartyType shipFromTradeParty;
    @XmlElement(name = "RequestedDeliverySupplyChainEvent")
    protected List<SupplyChainEventType> requestedDeliverySupplyChainEvent;
    @XmlElement(name = "RequestedDespatchSupplyChainEvent")
    protected List<SupplyChainEventType> requestedDespatchSupplyChainEvent;

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

}
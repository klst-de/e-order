//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.14 at 10:16:57 PM CET 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.TextType;


/**
 * Legal Organization
 * 
 * <p>Java class for LegalOrganizationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LegalOrganizationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}IDType" minOccurs="0"/>
 *         &lt;element name="TradingBusinessName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}TextType" minOccurs="0"/>
 *         &lt;element name="PostalTradeAddress" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}TradeAddressType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LegalOrganizationType", propOrder = {
    "id",
    "tradingBusinessName",
    "postalTradeAddress"
})
public class LegalOrganizationType {

    @XmlElement(name = "ID")
    protected IDType id;
    @XmlElement(name = "TradingBusinessName")
    protected TextType tradingBusinessName;
    @XmlElement(name = "PostalTradeAddress")
    protected TradeAddressType postalTradeAddress;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setID(IDType value) {
        this.id = value;
    }

    /**
     * Gets the value of the tradingBusinessName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTradingBusinessName() {
        return tradingBusinessName;
    }

    /**
     * Sets the value of the tradingBusinessName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTradingBusinessName(TextType value) {
        this.tradingBusinessName = value;
    }

    /**
     * Gets the value of the postalTradeAddress property.
     * 
     * @return
     *     possible object is
     *     {@link TradeAddressType }
     *     
     */
    public TradeAddressType getPostalTradeAddress() {
        return postalTradeAddress;
    }

    /**
     * Sets the value of the postalTradeAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradeAddressType }
     *     
     */
    public void setPostalTradeAddress(TradeAddressType value) {
        this.postalTradeAddress = value;
    }

}

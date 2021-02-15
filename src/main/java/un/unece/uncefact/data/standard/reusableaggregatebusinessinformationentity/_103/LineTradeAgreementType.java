//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.10 at 09:19:13 PM CET 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Line Trade Agreement
 * 
 * <p>Java class for LineTradeAgreementType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineTradeAgreementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BuyerOrderReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}ReferencedDocumentType" minOccurs="0"/>
 *         &lt;element name="NetPriceProductTradePrice" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}TradePriceType"/>
 *         &lt;element name="BlanketOrderReferencedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}ReferencedDocumentType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineTradeAgreementType", propOrder = {
    "buyerOrderReferencedDocument",
    "netPriceProductTradePrice",
    "blanketOrderReferencedDocument"
})
public class LineTradeAgreementType {

    @XmlElement(name = "BuyerOrderReferencedDocument")
    protected ReferencedDocumentType buyerOrderReferencedDocument;
    @XmlElement(name = "NetPriceProductTradePrice", required = true)
    protected TradePriceType netPriceProductTradePrice;
    @XmlElement(name = "BlanketOrderReferencedDocument")
    protected ReferencedDocumentType blanketOrderReferencedDocument;

    /**
     * Gets the value of the buyerOrderReferencedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public ReferencedDocumentType getBuyerOrderReferencedDocument() {
        return buyerOrderReferencedDocument;
    }

    /**
     * Sets the value of the buyerOrderReferencedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public void setBuyerOrderReferencedDocument(ReferencedDocumentType value) {
        this.buyerOrderReferencedDocument = value;
    }

    /**
     * Gets the value of the netPriceProductTradePrice property.
     * 
     * @return
     *     possible object is
     *     {@link TradePriceType }
     *     
     */
    public TradePriceType getNetPriceProductTradePrice() {
        return netPriceProductTradePrice;
    }

    /**
     * Sets the value of the netPriceProductTradePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link TradePriceType }
     *     
     */
    public void setNetPriceProductTradePrice(TradePriceType value) {
        this.netPriceProductTradePrice = value;
    }

    /**
     * Gets the value of the blanketOrderReferencedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public ReferencedDocumentType getBlanketOrderReferencedDocument() {
        return blanketOrderReferencedDocument;
    }

    /**
     * Sets the value of the blanketOrderReferencedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferencedDocumentType }
     *     
     */
    public void setBlanketOrderReferencedDocument(ReferencedDocumentType value) {
        this.blanketOrderReferencedDocument = value;
    }

}
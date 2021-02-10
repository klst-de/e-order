//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.02.10 at 09:19:13 PM CET 
//


package un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ExchangedDocumentContextType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.ExchangedDocumentType;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._103.SupplyChainTradeTransactionType;


/**
 * Order-X_Basic Message Structure
 * 
 * <p>Java class for SCRDMCCBDACIOMessageStructureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SCRDMCCBDACIOMessageStructureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExchangedDocumentContext" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}ExchangedDocumentContextType"/>
 *         &lt;element name="ExchangedDocument" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}ExchangedDocumentType"/>
 *         &lt;element name="SupplyChainTradeTransaction" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}SupplyChainTradeTransactionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SCRDMCCBDACIOMessageStructureType", propOrder = {
    "exchangedDocumentContext",
    "exchangedDocument",
    "supplyChainTradeTransaction"
})
public class SCRDMCCBDACIOMessageStructureType {

    @XmlElement(name = "ExchangedDocumentContext", required = true)
    protected ExchangedDocumentContextType exchangedDocumentContext;
    @XmlElement(name = "ExchangedDocument", required = true)
    protected ExchangedDocumentType exchangedDocument;
    @XmlElement(name = "SupplyChainTradeTransaction", required = true)
    protected SupplyChainTradeTransactionType supplyChainTradeTransaction;

    /**
     * Gets the value of the exchangedDocumentContext property.
     * 
     * @return
     *     possible object is
     *     {@link ExchangedDocumentContextType }
     *     
     */
    public ExchangedDocumentContextType getExchangedDocumentContext() {
        return exchangedDocumentContext;
    }

    /**
     * Sets the value of the exchangedDocumentContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangedDocumentContextType }
     *     
     */
    public void setExchangedDocumentContext(ExchangedDocumentContextType value) {
        this.exchangedDocumentContext = value;
    }

    /**
     * Gets the value of the exchangedDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ExchangedDocumentType }
     *     
     */
    public ExchangedDocumentType getExchangedDocument() {
        return exchangedDocument;
    }

    /**
     * Sets the value of the exchangedDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExchangedDocumentType }
     *     
     */
    public void setExchangedDocument(ExchangedDocumentType value) {
        this.exchangedDocument = value;
    }

    /**
     * Gets the value of the supplyChainTradeTransaction property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyChainTradeTransactionType }
     *     
     */
    public SupplyChainTradeTransactionType getSupplyChainTradeTransaction() {
        return supplyChainTradeTransaction;
    }

    /**
     * Sets the value of the supplyChainTradeTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyChainTradeTransactionType }
     *     
     */
    public void setSupplyChainTradeTransaction(SupplyChainTradeTransactionType value) {
        this.supplyChainTradeTransaction = value;
    }

}

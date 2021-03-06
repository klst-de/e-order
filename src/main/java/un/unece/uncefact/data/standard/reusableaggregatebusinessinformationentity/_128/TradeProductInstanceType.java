//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.04.17 at 10:18:46 PM CEST 
//


package un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._128;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import un.unece.uncefact.data.standard.unqualifieddatatype._128.IDType;


/**
 * Trade Product Instance
 * 
 * <p>Java class for TradeProductInstanceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeProductInstanceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BatchID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}IDType" minOccurs="0"/>
 *         &lt;element name="SerialID" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128}IDType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeProductInstanceType", propOrder = {
    "batchID",
    "serialID"
})
public class TradeProductInstanceType {

    @XmlElement(name = "BatchID")
    protected IDType batchID;
    @XmlElement(name = "SerialID")
    protected IDType serialID;

    /**
     * Gets the value of the batchID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getBatchID() {
        return batchID;
    }

    /**
     * Sets the value of the batchID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setBatchID(IDType value) {
        this.batchID = value;
    }

    /**
     * Gets the value of the serialID property.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getSerialID() {
        return serialID;
    }

    /**
     * Sets the value of the serialID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setSerialID(IDType value) {
        this.serialID = value;
    }

}

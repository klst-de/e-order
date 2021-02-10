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
import un.unece.uncefact.data.standard.qualifieddatatype._103.DeliveryTermsCodeType;
import un.unece.uncefact.data.standard.qualifieddatatype._103.DeliveryTermsFunctionCodeType;


/**
 * Trade Delivery Terms
 * 
 * <p>Java class for TradeDeliveryTermsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeDeliveryTermsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DeliveryTypeCode" type="{urn:un:unece:uncefact:data:standard:QualifiedDataType:103}DeliveryTermsCodeType" minOccurs="0"/>
 *         &lt;element name="FunctionCode" type="{urn:un:unece:uncefact:data:standard:QualifiedDataType:103}DeliveryTermsFunctionCodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeDeliveryTermsType", propOrder = {
    "deliveryTypeCode",
    "functionCode"
})
public class TradeDeliveryTermsType {

    @XmlElement(name = "DeliveryTypeCode")
    protected DeliveryTermsCodeType deliveryTypeCode;
    @XmlElement(name = "FunctionCode")
    protected DeliveryTermsFunctionCodeType functionCode;

    /**
     * Gets the value of the deliveryTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryTermsCodeType }
     *     
     */
    public DeliveryTermsCodeType getDeliveryTypeCode() {
        return deliveryTypeCode;
    }

    /**
     * Sets the value of the deliveryTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryTermsCodeType }
     *     
     */
    public void setDeliveryTypeCode(DeliveryTermsCodeType value) {
        this.deliveryTypeCode = value;
    }

    /**
     * Gets the value of the functionCode property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryTermsFunctionCodeType }
     *     
     */
    public DeliveryTermsFunctionCodeType getFunctionCode() {
        return functionCode;
    }

    /**
     * Sets the value of the functionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryTermsFunctionCodeType }
     *     
     */
    public void setFunctionCode(DeliveryTermsFunctionCodeType value) {
        this.functionCode = value;
    }

}

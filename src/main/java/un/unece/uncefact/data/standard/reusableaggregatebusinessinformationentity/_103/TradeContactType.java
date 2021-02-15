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
import un.unece.uncefact.data.standard.unqualifieddatatype._103.TextType;


/**
 * Trade Contact
 * 
 * <p>Java class for TradeContactType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TradeContactType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PersonName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}TextType" minOccurs="0"/>
 *         &lt;element name="DepartmentName" type="{urn:un:unece:uncefact:data:standard:UnqualifiedDataType:103}TextType" minOccurs="0"/>
 *         &lt;element name="TelephoneUniversalCommunication" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}UniversalCommunicationType" minOccurs="0"/>
 *         &lt;element name="EmailURIUniversalCommunication" type="{urn:un:unece:uncefact:data:standard:ReusableAggregateBusinessInformationEntity:103}UniversalCommunicationType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TradeContactType", propOrder = {
    "personName",
    "departmentName",
    "telephoneUniversalCommunication",
    "emailURIUniversalCommunication"
})
public class TradeContactType {

    @XmlElement(name = "PersonName")
    protected TextType personName;
    @XmlElement(name = "DepartmentName")
    protected TextType departmentName;
    @XmlElement(name = "TelephoneUniversalCommunication")
    protected UniversalCommunicationType telephoneUniversalCommunication;
    @XmlElement(name = "EmailURIUniversalCommunication")
    protected UniversalCommunicationType emailURIUniversalCommunication;

    /**
     * Gets the value of the personName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getPersonName() {
        return personName;
    }

    /**
     * Sets the value of the personName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setPersonName(TextType value) {
        this.personName = value;
    }

    /**
     * Gets the value of the departmentName property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the value of the departmentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDepartmentName(TextType value) {
        this.departmentName = value;
    }

    /**
     * Gets the value of the telephoneUniversalCommunication property.
     * 
     * @return
     *     possible object is
     *     {@link UniversalCommunicationType }
     *     
     */
    public UniversalCommunicationType getTelephoneUniversalCommunication() {
        return telephoneUniversalCommunication;
    }

    /**
     * Sets the value of the telephoneUniversalCommunication property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniversalCommunicationType }
     *     
     */
    public void setTelephoneUniversalCommunication(UniversalCommunicationType value) {
        this.telephoneUniversalCommunication = value;
    }

    /**
     * Gets the value of the emailURIUniversalCommunication property.
     * 
     * @return
     *     possible object is
     *     {@link UniversalCommunicationType }
     *     
     */
    public UniversalCommunicationType getEmailURIUniversalCommunication() {
        return emailURIUniversalCommunication;
    }

    /**
     * Sets the value of the emailURIUniversalCommunication property.
     * 
     * @param value
     *     allowed object is
     *     {@link UniversalCommunicationType }
     *     
     */
    public void setEmailURIUniversalCommunication(UniversalCommunicationType value) {
        this.emailURIUniversalCommunication = value;
    }

}
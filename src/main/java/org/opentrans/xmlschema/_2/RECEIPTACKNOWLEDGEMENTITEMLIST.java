//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.opentrans.xmlschema._2;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{http://www.opentrans.org/XMLSchema/2.1}RECEIPTACKNOWLEDGEMENT_ITEM" maxOccurs="unbounded"/>
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
    "receiptacknowledgementitem"
})
@XmlRootElement(name = "RECEIPTACKNOWLEDGEMENT_ITEM_LIST")
public class RECEIPTACKNOWLEDGEMENTITEMLIST {

    @XmlElement(name = "RECEIPTACKNOWLEDGEMENT_ITEM", required = true)
    protected List<RECEIPTACKNOWLEDGEMENTITEM> receiptacknowledgementitem;

    /**
     * Gets the value of the receiptacknowledgementitem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiptacknowledgementitem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRECEIPTACKNOWLEDGEMENTITEM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RECEIPTACKNOWLEDGEMENTITEM }
     * 
     * 
     */
    public List<RECEIPTACKNOWLEDGEMENTITEM> getRECEIPTACKNOWLEDGEMENTITEM() {
        if (receiptacknowledgementitem == null) {
            receiptacknowledgementitem = new ArrayList<RECEIPTACKNOWLEDGEMENTITEM>();
        }
        return this.receiptacknowledgementitem;
    }

}

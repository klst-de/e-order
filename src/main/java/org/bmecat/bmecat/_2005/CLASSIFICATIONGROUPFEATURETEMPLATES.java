//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.22 at 08:34:27 PM CET 
//


package org.bmecat.bmecat._2005;

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
 *         &lt;element ref="{http://www.bmecat.org/bmecat/2005}CLASSIFICATION_GROUP_FEATURE_TEMPLATE" maxOccurs="unbounded"/>
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
    "classificationgroupfeaturetemplate"
})
@XmlRootElement(name = "CLASSIFICATION_GROUP_FEATURE_TEMPLATES")
public class CLASSIFICATIONGROUPFEATURETEMPLATES {

    @XmlElement(name = "CLASSIFICATION_GROUP_FEATURE_TEMPLATE", required = true)
    protected List<CLASSIFICATIONGROUPFEATURETEMPLATE> classificationgroupfeaturetemplate;

    /**
     * Gets the value of the classificationgroupfeaturetemplate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classificationgroupfeaturetemplate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCLASSIFICATIONGROUPFEATURETEMPLATE().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CLASSIFICATIONGROUPFEATURETEMPLATE }
     * 
     * 
     */
    public List<CLASSIFICATIONGROUPFEATURETEMPLATE> getCLASSIFICATIONGROUPFEATURETEMPLATE() {
        if (classificationgroupfeaturetemplate == null) {
            classificationgroupfeaturetemplate = new ArrayList<CLASSIFICATIONGROUPFEATURETEMPLATE>();
        }
        return this.classificationgroupfeaturetemplate;
    }

}

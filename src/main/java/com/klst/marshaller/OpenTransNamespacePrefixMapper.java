package com.klst.marshaller;

import java.util.HashMap;
import java.util.Map;

import com.klst.eorder.api.NamespacePrefixMapperFactory;
/* in java 1.8 'NamespacePrefixMapper' is not in API (restriction on required library ... jdk1.8.0_241\jre\lib\rt.jar')
 * to compile in eclipse define access rule.
 * 
 * Proposal JEP-320(http://openjdk.java.net/jeps/320) to remove the Java EE and CORBA modules from the JDK.
 * In Java SE 11, the module has been removed. To use JAX-WS and JAXB you need to add them to your project as separate libraries.
 * 
 * @see https://jesperdj.com/2018/09/30/jaxb-on-java-9-10-11-and-beyond/
 */
//import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

//@see https://www.intertech.com/Blog/jaxb-tutorial-customized-namespace-prefixes-example-using-namespaceprefixmapper/
//@see http://hwellmann.blogspot.com/2011/03/jaxb-marshalling-with-custom-namespace.html
/**
* Implementation of {@link NamespacePrefixMapper} that maps the schema
* namespaces more to readable names. Used by the jaxb marshaller. Requires
* setting the property "com.sun.xml.bind.namespacePrefixMapper" to an instance
* of this class.
* <p>
* Requires dependency on JAXB implementation jars
*
*/
public class OpenTransNamespacePrefixMapper extends NamespacePrefixMapper implements NamespacePrefixMapperFactory {

	@Override // implements Factory
	public NamespacePrefixMapper createNamespacePrefixMapper() {
		return getNamespacePrefixMapper();
	}
	static NamespacePrefixMapper getNamespacePrefixMapper() {
		return new OpenTransNamespacePrefixMapper();
	}

	/**
	 * eBusiness standard BMEcat allows to manage product catalogues by
	 * Bundesverband Materialwirtschaft, Einkauf und Logistik e.V. (BME)
	 * <p>
	 * https://de.wikipedia.org/wiki/BMEcat
	 */
	private static final String BMECAT="http://www.bmecat.org/bmecat/2005";

	/**
	 * Describing Media Content of Binary Data in XML
	 */
	private static final String XMIME="http://www.w3.org/2005/05/xmlmime";

	/**
	 * XML Signature
	 * <p>
	 * https://en.wikipedia.org/wiki/XML_Signature
	 */
	private static final String XSIG="http://www.w3.org/2000/09/xmldsig#";
	
	private Map<String, String> namespaceMap = new HashMap<>();
	 
	/**
	 * Create mappings.
	 */
	private OpenTransNamespacePrefixMapper() {
		namespaceMap.put(BMECAT, "bmecat");
		namespaceMap.put(XMIME, "xmime");
		namespaceMap.put(XSIG, "xsig");
	}

	/* (non-Javadoc)
	 * Returning null when not found based on spec.
	 * @see com.sun.xml.bind.marshaller.NamespacePrefixMapper#getPreferredPrefix(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		return namespaceMap.getOrDefault(namespaceUri, suggestion);
	}

}

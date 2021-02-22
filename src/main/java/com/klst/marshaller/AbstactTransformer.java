package com.klst.marshaller;

//import static javax.xml.bind.JAXBContext.newInstance;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/* in java 1.8 'NamespacePrefixMapper' is not in API (restriction on required library ... jdk1.8.0_241\jre\lib\rt.jar')
 * to compile in eclipse define access rule.
 * 
 * Proposal JEP-320(http://openjdk.java.net/jeps/320) to remove the Java EE and CORBA modules from the JDK.
 * In Java SE 11, the module has been removed. To use JAX-WS and JAXB you need to add them to your project as separate libraries.
 * 
 * @see https://jesperdj.com/2018/09/30/jaxb-on-java-9-10-11-and-beyond/
 */
//TODO add JAXB in a separate lib
//import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import un.unece.uncefact.data.standard.scrdmccbdaciomessagestructure._1.SCRDMCCBDACIOMessageStructureType;

@Named
/* Notice 
 * that there are two @Singleton annotations, 
 * one in javax.inject and the other in the javax.ebj package. 
 * I'm referring to them by their fully-qualified names to avoid confusion.
 * @see https://stackoverflow.com/questions/26832051/singleton-vs-applicationscope
 * @see https://github.com/javax-inject/javax-inject
 */
@javax.inject.Singleton
public abstract class AbstactTransformer {

	private static final Logger LOG = Logger.getLogger(AbstactTransformer.class.getName());
	
	final JAXBContext jaxbContext;
	
	// this is a SINGLETON! Use getInstance() in subclasses
	@SuppressWarnings("unused")
	private AbstactTransformer() {
		this.jaxbContext = null;
	}
	
	// ctor
	protected AbstactTransformer(String contentPath, AbstactTransformer instance) {
		LOG.fine("contentPath:"+contentPath);
		if(instance==null) try {
//			System.setProperty(JAXBContext.JAXB_CONTEXT_FACTORY, "com.sun.xml.bind.v2.JAXBContextFactory"); // Versuch1
			System.setProperty(JAXBContext.JAXB_CONTEXT_FACTORY, "com.sun.xml.bind.v2.ContextFactory"); // Versuch2
			
//			System.setProperty("javax.xml.bind.JAXBContextFactory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
//			javax.xml.bind.JAXBContext.newInstance(contentPath);
//			com.sun.xml.bind.v2.ContextFactory.createContext(contentPath, null, null)
			this.jaxbContext = 
//				newInstance(contentPath);
				javax.xml.bind.JAXBContext.newInstance(contentPath); // java
					// macht return newInstance( contextPath, getContextClassLoader());
					// ==>  newInstance( String contextPath, ClassLoader classLoader, Map<String,?>  properties  )
					// und dort wird prop JAXB_CONTEXT_FACTORY genutzt
/* 
Versuch2:
java.lang.NoClassDefFoundError: com/sun/istack/FinalArrayList
	at com.sun.xml.bind.v2.ContextFactory.createContext(ContextFactory.java:225)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at javax.xml.bind.ContextFinder.newInstance(ContextFinder.java:171)
            try {
                Method m = spFactory.getMethod("createContext",String.class,ClassLoader.class,Map.class);
                // any failure in invoking this method would be considered fatal
                context = m.invoke(null,contextPath,classLoader,properties); <======= 171
                
	at javax.xml.bind.ContextFinder.newInstance(ContextFinder.java:131)
	    try {
            Class spFactory = safeLoadClass(className,classLoader);      <============ 130
            return newInstance(contextPath, spFactory, classLoader, properties);  <=== 131 (kam schon weiter)
        } catch (ClassNotFoundException x) {
        
	at javax.xml.bind.ContextFinder.find(ContextFinder.java:305)
	at javax.xml.bind.JAXBContext.newInstance(JAXBContext.java:431)
	at javax.xml.bind.JAXBContext.newInstance(JAXBContext.java:394)
	at javax.xml.bind.JAXBContext.newInstance(JAXBContext.java:298)
	
Versuch1:
java.lang.NoClassDefFoundError: javax/xml/bind/JAXBContextFactory
	at java.lang.ClassLoader.defineClass1(Native Method)
	at java.lang.ClassLoader.defineClass(ClassLoader.java:756)
	at java.security.SecureClassLoader.defineClass(SecureClassLoader.java:142)
	at java.net.URLClassLoader.defineClass(URLClassLoader.java:468)
	at java.net.URLClassLoader.access$100(URLClassLoader.java:74)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:369)
	at java.net.URLClassLoader$1.run(URLClassLoader.java:363)
	at java.security.AccessController.doPrivileged(Native Method)
	at java.net.URLClassLoader.findClass(URLClassLoader.java:362)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:355)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
	at javax.xml.bind.ContextFinder.safeLoadClass(ContextFinder.java:579)
          if (classLoader == null) {
              return Class.forName(className);
          } else {
              return classLoader.loadClass(className);            <============ 579
          }

	at javax.xml.bind.ContextFinder.newInstance(ContextFinder.java:130)
	    try {
            Class spFactory = safeLoadClass(className,classLoader);      <============ 130
            return newInstance(contextPath, spFactory, classLoader, properties);
        } catch (ClassNotFoundException x) {

	
	at javax.xml.bind.ContextFinder.find(ContextFinder.java:305) :
            return newInstance( contextPath, factoryClassName, classLoader, properties );
	
	
	at javax.xml.bind.JAXBContext.newInstance(JAXBContext.java:431) :
        return ContextFinder.find(               <=========== 431
                        / * The default property name according to the JAXB spec * /
                        JAXB_CONTEXT_FACTORY,

                        / * the context path supplied by the client app * /
                        contextPath,

                        / * class loader to be used * /
                        classLoader,
                        properties );
	
	at javax.xml.bind.JAXBContext.newInstance(JAXBContext.java:394)
	at javax.xml.bind.JAXBContext.newInstance(JAXBContext.java:298)
	at com.klst.marshaller.AbstactTransformer.<init>(AbstactTransformer.java:73)
	at com.klst.marshaller.CioTransformer.<init>(CioTransformer.java:42)
	at com.klst.marshaller.CioTransformer.<clinit>(CioTransformer.java:24)
	at com.klst.marshaller.OrderTransformerTest.setup(OrderTransformerTest.java:41)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:59)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:56)
	at org.junit.internal.runners.statements.RunBefores.invokeMethod(RunBefores.java:33)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:24)
	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
	at org.junit.runners.BlockJUnit4ClassRunner$1.evaluate(BlockJUnit4ClassRunner.java:100)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:366)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:103)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:63)
	at org.junit.runners.ParentRunner$4.run(ParentRunner.java:331)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:79)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:329)
	at org.junit.runners.ParentRunner.access$100(ParentRunner.java:66)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:293)
	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:413)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:115)
	at org.junit.vintage.engine.execution.RunnerExecutor.execute(RunnerExecutor.java:43)
	at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
	at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:193)
	at java.util.Iterator.forEachRemaining(Iterator.java:116)
	at java.util.Spliterators$IteratorSpliterator.forEachRemaining(Spliterators.java:1801)
	at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:482)
	at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:472)
	at java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
	at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
	at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
	at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
	at org.junit.vintage.engine.VintageTestEngine.executeAllChildren(VintageTestEngine.java:82)
	at org.junit.vintage.engine.VintageTestEngine.execute(VintageTestEngine.java:73)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:108)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:88)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.lambda$execute$0(EngineExecutionOrchestrator.java:54)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.withInterceptedStreams(EngineExecutionOrchestrator.java:67)
	at org.junit.platform.launcher.core.EngineExecutionOrchestrator.execute(EngineExecutionOrchestrator.java:52)
	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:96)
	at org.junit.platform.launcher.core.DefaultLauncher.execute(DefaultLauncher.java:84)
	at org.eclipse.jdt.internal.junit5.runner.JUnit5TestReference.run(JUnit5TestReference.java:98)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:41)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:542)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:770)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:464)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:210)
Caused by: java.lang.ClassNotFoundException: javax.xml.bind.JAXBContextFactory
	at java.net.URLClassLoader.findClass(URLClassLoader.java:382)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:418)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:355)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:351)
	... 71 more


 */
//				com.sun.xml.bind.api.JAXBRIContext.newInstance(contentPath);
			LOG.finer("jaxbContext:\n"+jaxbContext.toString()); // displays path and Classes known to context
			instance = this;
		} catch (JAXBException ex) {
			throw new TransformationException(TransformationException.JAXB_INSTANTIATE_ERROR, ex);
		} else {
			this.jaxbContext = instance.jaxbContext;
		}
	}

	public boolean isValid(File xmlfile) {
		String resource = getResource();
		try {
			Source xmlFile = new StreamSource(xmlfile);
			Validator validator = this.getSchemaValidator(); // throws SAXException, Exception
			validator.validate(xmlFile);
			LOG.config("validate against "+resource+" passed.");
		} catch (SAXException ex) {
			LOG.warning("validate against "+resource+" failed, SAXException: "+ex.getMessage());
			return false;
		} catch (Exception ex) {
			LOG.severe("validate "+ex.getMessage());
		}
		return true;
	}
	
	public Validator getSchemaValidator() throws SAXException {
		return getSchemaValidator(getResource());
	}
	
	public abstract <T> T toModel(InputStream xmlInputStream);
	
	<T extends Object> T toModel(InputStream xmlInputStream, Class<T> declaredType) {
		try {
			Unmarshaller unmarshaller = createUnmarshaller();
			return unmarshaller.unmarshal(new StreamSource(xmlInputStream), declaredType).getValue();
		} catch (JAXBException ex) {
			throw new TransformationException(TransformationException.MARSHALLING_ERROR, ex);
		}
	}

	public byte[] fromModel(Object document) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(16000);
		try {
			Marshaller marshaller = createMarshaller();
			marshaller.marshal((SCRDMCCBDACIOMessageStructureType)document, outputStream);
		} catch (JAXBException ex) {
			throw new TransformationException(TransformationException.MARSHALLING_ERROR, ex);
		}
		return outputStream.toByteArray();
	}

	abstract String getResource();
	
	Validator getSchemaValidator(String resource) throws SAXException {
		LOG.fine("resource:"+resource + " Class:"+this.getClass());
		URL schemaURL = this.getClass().getResource(resource);
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		LOG.finer("schemaURL:"+schemaURL);
		Schema schema = sf.newSchema(schemaURL);
		return schema.newValidator();
	}

	abstract NamespacePrefixMapper getNamespacePrefixMapper();
	
	// -- private
	
	private Unmarshaller createUnmarshaller() throws JAXBException {
		return jaxbContext.createUnmarshaller();
	}

	// override the default namespace prefixes ns1, ns2, ... created by the Marshaller.
	// @see http://hwellmann.blogspot.com/2011/03/jaxb-marshalling-with-custom-namespace.html
	private Marshaller createMarshaller() throws JAXBException {
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatXmlOutput());
		
		// see https://stackoverflow.com/questions/277996/remove-standalone-yes-from-generated-xml
//		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); 
//		marshaller.setProperty("com.sun.xml.internal.bind.xmlDeclaration", Boolean.FALSE);
//		marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		
		// see https://stackoverflow.com/questions/2161350/jaxb-xjc-code-generation-schemalocation-missing-in-xml-generated-by-marshall
//		marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 http://docs.oasis-open.org/ubl/os-UBL-2.1/xsd/maindoc/UBL-Invoice-2.1.xsd");
        try {
//        	marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", getNamespacePrefixMapper());
//            	  ??????????? : com.sun.xml.bind.namespacePrefixMapper
//        	marshaller.setProperty("com.sun.xml.bind.marshaller.namespacePrefixMapper", getNamespacePrefixMapper());
        	marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", getNamespacePrefixMapper());
        } catch(PropertyException ex) {
            // In case another JAXB implementation is used
			throw new TransformationException(TransformationException.NAMESPACE_PREFIX_MAPPER_ERROR, ex);
        }

		return marshaller;
	}

	protected Boolean formatXmlOutput() {
		return Boolean.TRUE;
	}

}

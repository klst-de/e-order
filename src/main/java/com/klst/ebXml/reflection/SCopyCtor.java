package com.klst.ebXml.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.inject.Singleton;

@Named
/* Notice 
 * that there are two @Singleton annotations, 
 * one in javax.inject and the other in the javax.ebj package. 
 * I'm referring to them by their fully-qualified names to avoid confusion.
 * @see https://stackoverflow.com/questions/26832051/singleton-vs-applicationscope
 * @see https://github.com/javax-inject/javax-inject
 */
@Singleton
// TODO rename, der Name ist historisch bedingt, neu: Macro , Util , ...
public class SCopyCtor {

	private static final Logger LOG = Logger.getLogger(SCopyCtor.class.getName());

	public static SCopyCtor SINGLETON = new SCopyCtor();

	public static SCopyCtor getInstance() {
		return SINGLETON;
	}
	
	private static final String TYPE_NAME_SEPARATOR = "::";

	Map<String, String> getterFieldMap = new HashMap<String, String>();
	
	private SCopyCtor() {
		// Ausnahmen: getter getYYY liefert nicht Fieldname YYY
		// un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._100
		getterFieldMap.put("getID", "id");
		getterFieldMap.put("getURIUniversalCommunication", "uriUniversalCommunication");
		
		// org.opentrans.xmlschema._2
		getterFieldMap.put("getEMAILAndPUBLICKEY", "emailAndPUBLICKEY");

	}
	
	Map<String, Field> fieldsByName = new HashMap<String, Field>();
	
	private Map<String, Field> getFieldsByName(Class<?> type) {
		return getFieldsByName(type, type);
	}
	private Map<String, Field> getFieldsByName(Class<?> type, Class<?> otype) {
		Field[] fields = type.getDeclaredFields();
		for(int i=0; i<fields.length; i++) {
			Field field = fields[i];
			if(Modifier.isPublic(field.getModifiers()) || Modifier.isProtected(field.getModifiers())) {
				Field prev = fieldsByName.put(otype.getCanonicalName()+TYPE_NAME_SEPARATOR+field.getName(), field);
				if(prev==null) LOG.fine("added "+otype.getCanonicalName()+TYPE_NAME_SEPARATOR+field.getName());
			}
		}
		if(type.getSuperclass()==null || type.getSuperclass()==Object.class) {
			return fieldsByName;
		}
		return getFieldsByName(type.getSuperclass(), otype);
	}
	
	private static final String get = "get";
	private static final String set = "set";

	Map<String, Method> settersByName = new HashMap<String, Method>();
	Map<String, Method> gettersByName = new HashMap<String, Method>();

	public Map<String, Method> getSettersByName(Class<?> type) {
		// setter Signatur: void setXXX(T arg)
		return getMethodByName(type, set, 1, settersByName);
	}
	public Map<String, Method> getGettersByName(Class<?> type) {
		// getter Signatur: T getXXX()
		return getMethodByName(type, get, 0, gettersByName);
	}
		
	private Map<String, Method> getMethodByName(Class<?> type, String prefix, int parameterCount, Map<String, Method> methodByName) {
		return getMethodByName(type, type, prefix, parameterCount, methodByName);
	}
	private Map<String, Method> getMethodByName(Class<?> type, Class<?> otype, String prefix, int parameterCount, Map<String, Method> methodByName) {
		Method[] methods = type.getDeclaredMethods();
		for(int i=0; i<methods.length; i++) {
			Method method = methods[i];
			if(Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers())) {
				String methodName = method.getName();
				if(methodName.startsWith(prefix) && method.getParameterCount()==parameterCount) {
					Method prev = methodByName.put(otype.getCanonicalName()+TYPE_NAME_SEPARATOR+methodName, method);
					if(prev==null) LOG.fine("added "+otype.getCanonicalName()+TYPE_NAME_SEPARATOR+methodName);
				}
			}
		}
		if(type.getSuperclass()==null || type.getSuperclass()==Object.class) {
			return methodByName;
		}
		return getMethodByName(type.getSuperclass(), otype, prefix, parameterCount, methodByName);
	}
	
	/* obj class extends doc class! Bsp: class HeaderTradeDelivery extends HeaderTradeDeliveryType
	 * Initialisiert alle Felder von obj mit den Werten von doc per setter/getter, also: obj.setXXX(doc.getXXX())
	 * - setXXX und getXXX müssen in doc class definiert sein 
	 * -- auch wenn setXXX/getXXX in super von doc definiert sind, 
	 *    Bsp. OT Remarks extends REMARKS, super class REMARKS extends TypeMLSTRING64000 extends DtMLSTRING
	 *    getValue() kommt aus DtMLSTRING
	 */
	public void invokeCopy(Object obj, Object doc) {
		LOG.fine("for "+doc);
		getFieldsByName(doc.getClass());
		getSettersByName(doc.getClass());
		getGettersByName(doc.getClass());
		List<String> getterNames = new ArrayList<>(gettersByName.keySet());
		// es interessieren nur die der Klasse doc.getClass() und die der Oberklassen dazu
		for(int i=0; i<getterNames.size(); i++) {
			String getterName = getterNames.get(i);
			if(!getterName.startsWith(doc.getClass().getCanonicalName()+TYPE_NAME_SEPARATOR)) {
				continue;
			}
			Method getter = gettersByName.get(getterName);
			getterName = getterName.substring(getterName.indexOf(TYPE_NAME_SEPARATOR)+TYPE_NAME_SEPARATOR.length());
			// die getter können korrespondierende setter haben
			String setterName = set+getterName.substring(set.length());
			String setterKey = doc.getClass().getCanonicalName()+TYPE_NAME_SEPARATOR + setterName;
			if(settersByName.containsKey(setterKey)) {
				Method setter = settersByName.get(setterKey); // potentieller Setter muss den Parameter == Result des getters haben
				if(getter.getReturnType()==setter.getParameterTypes()[0]) {						
					LOG.fine(setterName+" ( "+getterName+"() ) ");
					try {
						setter.invoke(obj, getter.invoke(doc)); // obj.setXXX( doc.getXXX() )
				    } catch (Exception e) {
				        LOG.warning(setterName+" ( "+getterName+" ) " + "Exception:"+e);
				    }						
				} else {
					LOG.warning(setterName+" + "+getterName + " typen passen nicht");
				}
			} else {
				// if(settersByName.containsKey(setterName)) false ==> es gibt keinen passenden Setter
				// dann muss es ein member/field geben mit name==getterName (beginnend mit Kleinbuchstaben)
				// ausser ausnahmen in this.getterFieldMap "getID" ...
				String fieldName = getFieldnameLowerCaseFirstLetter(getterName);
				
				LOG.fine("List<?> "+fieldName+" = "+getterName);
				String fieldNameWithTypePrefix = doc.getClass().getCanonicalName()+TYPE_NAME_SEPARATOR+fieldName;
				if(fieldsByName.containsKey(fieldNameWithTypePrefix)) {
					Field field = fieldsByName.get(fieldNameWithTypePrefix);
					setFieldValueWithReflection(obj, doc, field, getter);
				} else {
					// in openTrans ist es anders (auch mit Ausnahmen)
					//ALLOWORCHARGE::List<?> alloworchargedescr = getALLOWORCHARGEDESCR
					//ADDRESS::List<?> emailandpublickey zu getEMAILAndPUBLICKEY nicht gefunden.
					fieldName = getFieldnameLowerCase(getterName);					
					
					LOG.fine(doc.getClass().getSimpleName()+"::List<?> "+fieldName+" = "+getterName+"()");
					fieldNameWithTypePrefix = doc.getClass().getCanonicalName()+TYPE_NAME_SEPARATOR+fieldName;
					if(fieldsByName.containsKey(fieldNameWithTypePrefix)) {
						Field field = fieldsByName.get(fieldNameWithTypePrefix);
						setFieldValueWithReflection(obj, doc, field, getter);
					} else {
						LOG.warning(doc.getClass().getSimpleName()+"::List<?> "+fieldName+" zu "+getterName + " nicht gefunden.");
					}
				}
			}
		} 
		
	}
	
	/*
	 * in UNCEFACT und in UBL kann man aus <getterName> den <fieldName> ermitteln,
	 * Beispiel:     getShipToTradeParty() { // bzw. List<IDType> getGlobalID()
	 * liefert   return shipToTradeParty;                return this.globalID;
	 * 
	 * Bis auf getterFieldMap Ausnahmen "getID" geht es auf! 
	 * Beispiel ===> in TradePartyType: List<IDType> getID() und liefert field id und nicht "ID";
	 * 
	 * Bei anderen schemas gilt es nicht, Beispiel openTrans:
	 *               getPAYMENT() {
	 * liefert   return payment; 
	 */
	private String getFieldnameLowerCaseFirstLetter(String getterName) {
		String fieldname = getterFieldMap.get(getterName);
		if(fieldname!=null) return fieldname;
		int length = get.length();
		return getterName.substring(length, length+1).toLowerCase()+getterName.substring(length+1);
	}
	private String getFieldnameLowerCase(String getterName) {
		String fieldname = getterFieldMap.get(getterName);
		if(fieldname!=null) return fieldname;
		int length = get.length();
		return getterName.substring(length, getterName.length()).toLowerCase();
	}
	
	// see https://stackoverflow.com/questions/24094871/set-field-value-with-reflection
	// macht dynamisch: obj.setXXX( doc.getXXX() ) für Listen
	//            also: obj.YYY = doc.getYYY(), d.h. YYY ist field und getYYY ist getter
	private static void setFieldValueWithReflection(Object obj, Object doc, Field field, Method getter) {
	    try {
	    	field.setAccessible(true);
	    	field.set(obj, getter.invoke(doc));
	    } catch (Exception e) {
	        LOG.warning("List<?> "+field.getName()+" = "+getter.getName() + "Exception:"+e);
	    }	
	}
	
    private static final String METHOD_GETVALUE = "getValue";
    // == Getter.getValue(Object codeType, String clazz)
	public Object invokeGetValue(Object codeType, String clazz) {
		Class<?> type = null;
		Object object = null;
		try {
			// dynamisch die Klasse laden
			type = Class.forName(clazz);
			object = type.cast(codeType); // == object = ("clazz"type)codeType
		} catch (ClassCastException e) {
			LOG.fine(e.getMessage());
			return null;
		} catch (ClassNotFoundException e) {
			LOG.config("ClassNotFound " + e.getMessage());
			// kann in e-order passieren, siehe com.klst.edoc.untdid.DocumentNameCode#valueOf // config statt warning
			return null;
		}

		if(type.isInstance(object)) {
			return invokeGetXX(METHOD_GETVALUE, type, object);
//			try {
//				Method getValue = type.getDeclaredMethod(METHOD_GETVALUE);
//				return getValue.invoke(object);
//			} catch (NoSuchMethodException e) {
//				LOG.severe(METHOD_GETVALUE + "() not defined for " + codeType.getClass().getSimpleName());
//				e.printStackTrace(); // darf nicht passieren
//			} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
//				LOG.severe(e.getMessage());
//				e.printStackTrace(); // darf nicht passieren
//			}					
		} else {
			LOG.info("Object "+codeType + " isInstance of "+codeType.getClass().getName() + " NOT "+clazz);
		}

		return null;
		
	}
	
	private Object invokeGetXX(String method, Class<?> type, Object object) {
		try {
			Method getValue = type.getDeclaredMethod(method);
			return getValue.invoke(object);
		} catch (NoSuchMethodException e) {
			LOG.severe(method + "() not defined for " + type.getSimpleName());
			e.printStackTrace(); // darf nicht passieren
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			LOG.severe(e.getMessage());
			e.printStackTrace(); // darf nicht passieren
		}					
		return null;
	}
	
	/*  MACRO, die zwei Zeilen ersetzen den //-code , aus CrossIndustryInvoice ctor
	 
		SCopyCtor.getInstance().newFieldInstance(this, "exchangedDocument", documentNameCode.getValueAsString());
		SCopyCtor.getInstance().set(getExchangedDocument(), "typeCode", documentNameCode.getValueAsString());
//		exchangedDocument = new ExchangedDocumentType();
//		DocumentCodeType documentCode = new DocumentCodeType();
//		documentCode.setValue(documentNameCode.getValueAsString());
//		exchangedDocument.setTypeCode(documentCode);
//		super.setExchangedDocument(exchangedDocument);
 
	 */
	public Field newFieldInstance(Object obj, String fieldName, Object value) {
		if(value==null) return null;
		Field field = null; // declared field in obj super
		Class<?> fieldType = null;
		try {
			// das .getSuperclass() ist notwendig, weil die Attribute in super <className>Type sind
			if(obj.getClass().getSimpleName().endsWith("Type")) {
				field = obj.getClass().getDeclaredField(fieldName);
			} else {
				field = obj.getClass().getSuperclass().getDeclaredField(fieldName);
			}
			field.setAccessible(true);
			if(field.get(obj)==null) {
				fieldType = field.getType();
				field.set(obj, fieldType.newInstance());
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return null;
		}
		return field;
	}

    private static final String METHOD_SETVALUE = "setValue"; // setValue(String value)
    private static final String METHOD_SETID = "setID"; // setID(IDType id)
    private static final String METHOD_SETINDICATOR = "setIndicator";
    private static final String METHOD_SETUNITCODE = "setUnitCode"; // wg. Quantity
    private static final String METHOD_GETUNITCODE = "getUnitCode"; // wg. Quantity
    
	private static final String UDT="urn:un:unece:uncefact:data:standard:UnqualifiedDataType:128";
    private Class<?> typeUDT_ID = un.unece.uncefact.data.standard.unqualifieddatatype._128.IDType.class;
//    private Class<?> CLASS_QuantityType = un.unece.uncefact.data.specification.corecomponenttypeschemamodule._2.QuantityType.class;
    private Class<?> CLASS_QuantityType = un.unece.uncefact.data.standard.unqualifieddatatype._128.QuantityType.class;
    private Class<?> typeUDT_Quantity = un.unece.uncefact.data.standard.unqualifieddatatype._128.QuantityType.class;
//    private Class<?> CLASS_AmountType = un.unece.uncefact.data.specification.corecomponenttypeschemamodule._2.AmountType.class;
    
	private void set(Field field, Object obj, String fieldName, Object value) {
		if(value==null) return;
		Class<?> fieldType = field.getType();
		
		String methodName = METHOD_SETVALUE;
		try { // "setValue" existiert ? ==> ausführen
			Method setValue = fieldType.getDeclaredMethod(methodName, value.getClass());	
			setValue.invoke(field.get(obj), value.getClass().cast(value));
			return;
		} catch (NoSuchMethodException e) {
			LOG.config(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//			e.printStackTrace();
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return;
		}
				
		methodName = METHOD_SETID;
		try { // "setID" existiert ? ==> ausführen: .setID((ID)value)
			// mit IDType ist der Mapper an unqualifieddatatype._103 bzw CLASS_IDType gebunden
			Method setID = fieldType.getDeclaredMethod(methodName, typeUDT_ID);	
			setID.invoke(field.get(obj), typeUDT_ID.cast(value));
			return;
		} catch (NoSuchMethodException e) {
			LOG.config(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//			e.printStackTrace();
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return;
		}
		
		// boolean indicator, z.B. partialDeliveryAllowedIndicator ==> setIndicator(Boolean value)
		if(value.getClass()==Boolean.class) {
			methodName = METHOD_SETINDICATOR;
			try {
				Object fo = field.get(obj); // IndicatorType?
				Method setter = fo.getClass().getDeclaredMethod(methodName, Boolean.class);
				setter.invoke(fo, Boolean.class.cast(value));
				return;
			} catch (NoSuchMethodException e) {
				LOG.config(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//				e.printStackTrace();
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		
		// fehlt: billedQuantity
		if(CLASS_QuantityType.isInstance(value) && value.getClass()!=CLASS_QuantityType) {
			// value is instance of a subclass of QuantityType, but not QuantityType itself
			// mögliche Methoden: setRequestedQuantity / setAgreedQuantity / setBasisQuantity
			if (set("setBilledQuantity", obj, field, value, typeUDT_Quantity)) return;
			if (set("setBasisQuantity", obj, field, value, typeUDT_Quantity)) return;
//			methodName = "setRequestedQuantity"; 
//			try {
//				Method setter = obj.getClass().getDeclaredMethod(methodName, CLASS_QuantityType);
//				setter.invoke(obj, CLASS_QuantityType.cast(value));
//				return;
//			} catch (NoSuchMethodException e) {
//				LOG.config(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////				e.printStackTrace();
//			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//				return;
//			}
//			methodName = "setBasisQuantity"; 
//			try {
//				Method setter = obj.getClass().getDeclaredMethod(methodName, CLASS_QuantityType);
//				setter.invoke(obj, CLASS_QuantityType.cast(value));
//				return;
//			} catch (NoSuchMethodException e) {
//				LOG.config(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////				e.printStackTrace();
//			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//				return;
//			}
		}
		LOG.warning("NO METHOD found for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);

//		if(CLASS_AmountType.isInstance(value) && value.getClass()!=CLASS_AmountType) {
//			// value is instance of a subclass of AmountType, but not AmountType itself
//			// mögliche Methoden: setLineTotalAmount / setChargeAmount
//			methodName = "setLineTotalAmount"; 
//			try {
//				Method setter = obj.getClass().getDeclaredMethod(methodName, CLASS_AmountType);
//				setter.invoke(obj, CLASS_AmountType.cast(value));
//				return;
//			} catch (NoSuchMethodException e) {
//				LOG.config(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////				e.printStackTrace();
//			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//				return;
//			}
//			methodName = "setChargeAmount"; 
//			try {
//				Method setter = obj.getClass().getDeclaredMethod(methodName, CLASS_AmountType);
//				setter.invoke(obj, CLASS_AmountType.cast(value));
//				return;
//			} catch (NoSuchMethodException e) {
//				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////				e.printStackTrace();
//			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//				return;
//			}
//		}

	}

	private boolean set(String methodName, Object obj, Field field, Object value, Class<?> valueType) {
		// TODO methodName selber rausfinden, dann para methodName raus
		// para valueType wird nicht genutzt
		Class<?> valueSuperType = value.getClass().getSuperclass();
		String fieldName = field.getName();
		try {
			Object fo = field.get(obj);
			// exception, wenn es methodName nicht gibt
			Method setter = obj.getClass().getDeclaredMethod(methodName, fo.getClass());
			
			/* methodName mit passender Signatur existiert:

Quantity: UBL nutzt die Klasse von CII:

un.unece.uncefact.data.specification.corecomponenttypeschemamodule._2.QuantityType mit
    protected BigDecimal value;
    protected String unitCode;
    protected String unitCodeListID;
    protected String unitCodeListAgencyID;
    protected String unitCodeListAgencyName;

                                    ^
                                    |extends
                                    |
com.klst.einvoice.unece.uncefact.Quantity  --- impl --> IQuantity

in LineTradeDeliveryType, ... ist aber 
un.unece.uncefact.data.standard.unqualifieddatatype._100 mit
    protected BigDecimal value;
    protected String unitCode;
    protected String unitCodeListID;
    protected String unitCodeListAgencyID;
    protected String unitCodeListAgencyName;

ich kopiere also um:

			 */
			LOG.info("value:"+value 
					+ "\n\t     Class:"+ value.getClass().getCanonicalName()
					+ "\n\tSuperClass:"+ value.getClass().getSuperclass().getCanonicalName()
//					+ "\n\texp.SClass:"+ CLASS_QuantityType.getCanonicalName());
				);
			if(CLASS_QuantityType==value.getClass().getSuperclass()) {
				Method setValue = fo.getClass().getDeclaredMethod(METHOD_SETVALUE, BigDecimal.class);
				Method setUnitCode = fo.getClass().getDeclaredMethod(METHOD_SETUNITCODE, String.class);
				setValue.invoke(fo, invokeGetXX(METHOD_GETVALUE, valueSuperType, value));
				setUnitCode.invoke(fo, invokeGetXX(METHOD_GETUNITCODE, valueSuperType, value));
			}
//			Method setValue = fo.getClass().getDeclaredMethod("setValue", BigDecimal.class);
//			Method setUnitCode = fo.getClass().getDeclaredMethod("setUnitCode", String.class);
//			setValue.invoke(fo, invokeGetXX("getValue", CLASS_QuantityType, value));
//			setUnitCode.invoke(fo, invokeGetXX("getUnitCode", CLASS_QuantityType, value));
			
			setter.invoke(obj, fo);
			LOG.info("DONE "+methodName);
			return true;
		} catch (NoSuchMethodException e) {
			LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//			e.printStackTrace();
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*  MACRO, die folgende Zeile ersetzt den //-code , aus ReferencedDocument.setDocumentCode
	 
		SCopyCtor.getInstance().set(this, "typeCode", code);
//		if(code==null) return;
//		DocumentCodeType documentCode = new DocumentCodeType();
//		documentCode.setValue(code);
//		super.setTypeCode(documentCode);	 
	 
	 */
	public void set(Object obj, String fieldName, Object value) {
		Field field = newFieldInstance(obj, fieldName, value);
		set(field, obj, fieldName, value);
	}

}

package com.klst.eorder.openTrans.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Logger;

public class Mapper {

	private static final Logger LOG = Logger.getLogger(Mapper.class.getName());

	private static final String get = "get";
	private static final String set = "set";

	public static Field newFieldInstance(Object obj, String fieldName, Object value) {
		if(value==null) return null;
		Field field = null; // declared field in obj super
		Class<?> fieldType = null;
		String typeSimpleName = obj.getClass().getSimpleName();
		try {
			// das .getSuperclass() ist notwendig, weil die Attribute in super <className>Type sind
//			if(obj.getClass().getSimpleName().endsWith("Type")) {
//				field = obj.getClass().getDeclaredField(fieldName);
//			} else {
//				field = obj.getClass().getSuperclass().getDeclaredField(fieldName);
//			}
			if(typeSimpleName.toUpperCase().equals(typeSimpleName)) {
				field = obj.getClass().getDeclaredField(fieldName);
			} else {
				field = obj.getClass().getSuperclass().getDeclaredField(fieldName);
			}
			field.setAccessible(true);
			if(field.get(obj)==null) {
				fieldType = field.getType();
				field.set(obj, fieldType.newInstance());
				LOG.info(">>>"+field.getName()+" newInstance "+typeSimpleName);
			} else {
				LOG.warning(">>>"+field.get(obj));
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e) {
			LOG.warning(typeSimpleName +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return null;
		}
		return field;
	}
	
	public static void set(Field field, Object obj, String fieldName, Object value) {
		if(value==null) return;
		Class<?> fieldType = field.getType();

		String methodName = "setValue"; // setValue(String value)
//		try { // "setValue" existiert für fieldType ? ==> ausführen
//			Method setValue = fieldType.getDeclaredMethod(methodName, value.getClass());	
//			setValue.invoke(field.get(obj), value.getClass().cast(value));
//			LOG.info("return !!!!!!!!!!!!!!!!!!");
//			return;
//		} catch (NoSuchMethodException e) {
//			LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////			e.printStackTrace();
//		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
//			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
//			e.printStackTrace();
//			return;
//		}			

		methodName = set+fieldName.toUpperCase();
		try { // "set<FIELDNAME>" existiert ? ==> ausführen
			Method setFIELDNAME = fieldType.getDeclaredMethod(methodName, value.getClass());	
			setFIELDNAME.invoke(field.get(obj), value.getClass().cast(value));
			return;
		} catch (NoSuchMethodException e) {
			LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//			e.printStackTrace();
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return;
		}
		
		methodName = get+fieldName.toUpperCase();
		try { // "get<FIELDNAME>" existiert ? ==> ausführen .add(value)
			LOG.info(">>>>>>>>>>>>>>>>>>>>> such Methode:"+methodName);
			Class<?> objType = obj.getClass().getSuperclass();
			Method getFIELDNAME = objType.getDeclaredMethod(methodName);
			LOG.info(">>>>>>>> aber TODO "+field.getName()+".setValue("+value+") >>>>>>>>>>>>>"+getFIELDNAME.getName());
			Object getResult = getFIELDNAME.invoke(obj);
//			getFIELDNAME.invoke(field.get(obj), value.getClass().cast(value));
			((List)getResult).add(field);
			return;
		} catch (NoSuchMethodException e) {
			LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//			e.printStackTrace();
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return;
		}
		
//		methodName = "setID"; // setID(IDType id)
//		try { // "setID" existiert ? ==> ausführen: .setID((ID)value)
//			// mit IDType ist der Mapper an unqualifieddatatype._103 gebunden
//			Method setID = fieldType.getDeclaredMethod(methodName, IDType.class);	
//			setID.invoke(field.get(obj), IDType.class.cast(value));
//			return;
//		} catch (NoSuchMethodException e) {
//			LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////			e.printStackTrace();
//		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
//			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
//			e.printStackTrace();
//			return;
//		}
//		
//		// boolean indicator, z.B. partialDeliveryAllowedIndicator ==> setIndicator(Boolean value)
//		if(value.getClass()==Boolean.class) {
//			methodName = "setIndicator";
//			try {
//				Object fo = field.get(obj); // IndicatorType?
//				Method setter = fo.getClass().getDeclaredMethod(methodName, Boolean.class);
//				setter.invoke(fo, Boolean.class.cast(value));
//			} catch (NoSuchMethodException e) {
//				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////				e.printStackTrace();
//			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		if(value instanceof QuantityType && value.getClass()!=QuantityType.class) {
//			// value is instance of a subclass of QuantityType, but not QuantityType itself
//			// mögliche Methoden: setRequestedQuantity / setAgreedQuantity / setBasisQuantity
//			methodName = "setRequestedQuantity"; 
//			try {
//				Method setter = obj.getClass().getDeclaredMethod(methodName, QuantityType.class);
//				setter.invoke(obj, QuantityType.class.cast(value));
//			} catch (NoSuchMethodException e) {
//				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////				e.printStackTrace();
//			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//			}
//			methodName = "setBasisQuantity"; 
//			try {
//				Method setter = obj.getClass().getDeclaredMethod(methodName, QuantityType.class);
//				setter.invoke(obj, QuantityType.class.cast(value));
//			} catch (NoSuchMethodException e) {
//				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////				e.printStackTrace();
//			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//			}
//		}
//
//		if(value instanceof AmountType && value.getClass()!=AmountType.class) {
//			// value is instance of a subclass of AmountType, but not AmountType itself
//			// mögliche Methoden: setLineTotalAmount / setChargeAmount
//			methodName = "setLineTotalAmount"; 
//			try {
//				Method setter = obj.getClass().getDeclaredMethod(methodName, AmountType.class);
//				setter.invoke(obj, AmountType.class.cast(value));
//			} catch (NoSuchMethodException e) {
//				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////				e.printStackTrace();
//			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//			}
//			methodName = "setChargeAmount"; 
//			try {
//				Method setter = obj.getClass().getDeclaredMethod(methodName, AmountType.class);
//				setter.invoke(obj, AmountType.class.cast(value));
//			} catch (NoSuchMethodException e) {
//				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
////				e.printStackTrace();
//			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//				e.printStackTrace();
//			}
//		}

	}

	public static void set(Object obj, String fieldName, Object value) {
		Field field = newFieldInstance(obj, fieldName, value);
		set(field, obj, fieldName, value);
	}

	public static void add(List list, Object objToAdd, Object value) {
		if(value==null) return;
		Class<?> objType = objToAdd.getClass().getSuperclass();

		String methodName = "setValue"; // setValue(String value)
		try { // "setValue" existiert für objType ? ==> ausführen
			Method setValue = objType.getDeclaredMethod(methodName, value.getClass());	
			setValue.invoke(objToAdd, value.getClass().cast(value));
			LOG.info(""+objType.getCanonicalName()+" - return !!!!!!!!!!!!!!!!!!");
			if(list.isEmpty()) {
				list.add(objToAdd.getClass().cast(objToAdd));				
			} else {
				LOG.warning("List<"+objToAdd.getClass().getSimpleName()+"> has "+list.size()+" elements, replacing the first.");
				list.set(0, objToAdd.getClass().cast(objToAdd));
			}
			return;
		} catch (NoSuchMethodException e) {
			LOG.warning(methodName + "() not defined for " + objToAdd.getClass().getSimpleName() + " and arg value:"+value);
//			e.printStackTrace();
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warning(objToAdd.getClass().getSimpleName() +"."+methodName + ": Exception:"+e);
			e.printStackTrace();
			return;
		}			
	}
	
}

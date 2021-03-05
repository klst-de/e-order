package com.klst.ebXml.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import un.unece.uncefact.data.standard.unqualifieddatatype._103.AmountType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.IDType;
import un.unece.uncefact.data.standard.unqualifieddatatype._103.QuantityType;

public class Mapper {

	private static final Logger LOG = Logger.getLogger(Mapper.class.getName());

	public static Field newFieldInstance(Object obj, String fieldName, Object value) {
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
	
	public static void set(Field field, Object obj, String fieldName, Object value) {
		if(value==null) return;
		Class<?> fieldType = field.getType();
		
		String methodName = "setValue"; // setValue(String value)
		try { // "setValue" existiert ? ==> ausführen
			Method setValue = fieldType.getDeclaredMethod(methodName, value.getClass());	
			setValue.invoke(field.get(obj), value.getClass().cast(value));
			return;
		} catch (NoSuchMethodException e) {
			LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//			e.printStackTrace();
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return;
		}
				
		methodName = "setID"; // setID(IDType id)
		try { // "setID" existiert ? ==> ausführen: .setID((ID)value)
			// mit IDType ist der Mapper an unqualifieddatatype._103 gebunden
			Method setID = fieldType.getDeclaredMethod(methodName, IDType.class);	
			setID.invoke(field.get(obj), IDType.class.cast(value));
			return;
		} catch (NoSuchMethodException e) {
			LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//			e.printStackTrace();
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return;
		}
		
		// boolean indicator, z.B. partialDeliveryAllowedIndicator ==> setIndicator(Boolean value)
		if(value.getClass()==Boolean.class) {
			methodName = "setIndicator";
			try {
				Object fo = field.get(obj); // IndicatorType?
				Method setter = fo.getClass().getDeclaredMethod(methodName, Boolean.class);
				setter.invoke(fo, Boolean.class.cast(value));
			} catch (NoSuchMethodException e) {
				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//				e.printStackTrace();
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(value instanceof QuantityType && value.getClass()!=QuantityType.class) {
			// value is instance of a subclass of QuantityType, but not QuantityType itself
			// mögliche Methoden: setRequestedQuantity / setAgreedQuantity / setBasisQuantity
			methodName = "setRequestedQuantity"; 
			try {
				Method setter = obj.getClass().getDeclaredMethod(methodName, QuantityType.class);
				setter.invoke(obj, QuantityType.class.cast(value));
			} catch (NoSuchMethodException e) {
				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//				e.printStackTrace();
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			methodName = "setBasisQuantity"; 
			try {
				Method setter = obj.getClass().getDeclaredMethod(methodName, QuantityType.class);
				setter.invoke(obj, QuantityType.class.cast(value));
			} catch (NoSuchMethodException e) {
				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//				e.printStackTrace();
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		if(value instanceof AmountType && value.getClass()!=AmountType.class) {
			// value is instance of a subclass of AmountType, but not AmountType itself
			// mögliche Methoden: setLineTotalAmount / setChargeAmount
			methodName = "setLineTotalAmount"; 
			try {
				Method setter = obj.getClass().getDeclaredMethod(methodName, AmountType.class);
				setter.invoke(obj, AmountType.class.cast(value));
			} catch (NoSuchMethodException e) {
				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//				e.printStackTrace();
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			methodName = "setChargeAmount"; 
			try {
				Method setter = obj.getClass().getDeclaredMethod(methodName, AmountType.class);
				setter.invoke(obj, AmountType.class.cast(value));
			} catch (NoSuchMethodException e) {
				LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
//				e.printStackTrace();
			} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

	}

	public static void set(Object obj, String fieldName, Object value) {
		Field field = newFieldInstance(obj, fieldName, value);
		set(field, obj, fieldName, value);
	}

}

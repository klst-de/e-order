package com.klst.ebXml.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import un.unece.uncefact.data.standard.unqualifieddatatype._103.IDType;

public class Mapper {

	private static final Logger LOG = Logger.getLogger(Mapper.class.getName());

	public static void set(Object obj, String fieldName, Object value) {
		if(value==null) return;
		Field field = null; // declared field in obj super
		Class<?> fieldType = null;
		try {
			field = obj.getClass().getSuperclass().getDeclaredField(fieldName);
			field.setAccessible(true);
			if(field.get(obj)==null) {
				fieldType = field.getType();
				field.set(obj, fieldType.newInstance());
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return;
		}
		
		String methodName = "setValue";
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
		
		
		methodName = "setID";
		try { // "setID" existiert ? ==> ausführen: .setID((ID)value)
			// mit IDType ist der Mapper an unqualifieddatatype._103 gebunden
			Method setValue = fieldType.getDeclaredMethod(methodName, IDType.class);	
			setValue.invoke(field.get(obj), IDType.class.cast(value));
			return;
		} catch (NoSuchMethodException e) {
			LOG.warning(methodName + "() not defined for " + obj.getClass().getSimpleName() +"."+fieldName + " and arg value:"+value);
			e.printStackTrace();
		} catch (IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
			return;
		}
	}
	
}

package com.klst.ebXml.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class Mapper {

	private static final Logger LOG = Logger.getLogger(Mapper.class.getName());


//	Field field = this.getClass().getDeclaredField("orderCurrencyCode");
//	field.get(obj aka this)==null
//	c = field.getType() // die Klasse für die ein ctor auszuführen ist, also CurrencyCodeType
//	field.set(obj, der ctor)
//	setValue , c.getDeclaredMethod(String name "setValue", Class<?> String) existiert ==>
	public static void set(Object obj, String fieldName, Object value) {
		if(value==null) return;
		try {
//			Field field = obj.getClass().getDeclaredField(fieldName);
			Class<? extends Object> cl = obj.getClass().getSuperclass();
			Field field = cl.getDeclaredField(fieldName);
			Class<?> fieldType = null;
			field.setAccessible(true);
			if(field.get(obj)==null) {
				fieldType = field.getType();
//				Constructor ctor = fieldType.getDeclaredConstructor(fieldType);
				field.set(obj, fieldType.newInstance());
			}
			Method setValue = fieldType.getDeclaredMethod("setValue", value.getClass()); // liefert exception
			if(setValue!=null) {
				// "setValue" existiert ==> ausführen
				LOG.info("value:"+value.getClass().cast(value));
//				LOG.info("field:"+fieldType.cast(field));
				setValue.invoke(field.get(obj), value.getClass().cast(value));
				return;
			}
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": NICHT IMPLEMENTIERT für value:"+value);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
			LOG.warning(obj.getClass().getSimpleName() +"."+fieldName + ": Exception:"+e);
			e.printStackTrace();
		}
	}
	
}

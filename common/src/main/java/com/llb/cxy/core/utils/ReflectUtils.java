package com.llb.cxy.core.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class ReflectUtils {

	/**
	 * 两个 对象copy
	 * 
	 * @param sourceClassType
	 * @param targetClassType
	 * @param sourceEntity
	 * @param targetEntity
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void objectCopy(Class<?> sourceClassType, Class<?> targetClassType, Object sourceEntity, Object targetEntity) {
		try {
			Field[] fields = sourceClassType.getDeclaredFields();
			for (Field field : fields) {
				if (!field.getName().equals("serialVersionUID")) {
					String filedName = field.getName();
					PropertyDescriptor oldPd = new PropertyDescriptor(filedName, sourceClassType);
					Method getMethod = oldPd.getReadMethod();// 获得get方法
					Object o = getMethod.invoke(sourceEntity);// 执行get方法返回一个Object
					if (o != null) {
						// 获取set方法
						PropertyDescriptor newPd = new PropertyDescriptor(filedName, targetClassType);
						Method setMethod = newPd.getWriteMethod();
						setMethod.invoke(targetEntity, o);
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 反射赋值
	 * 
	 * @param setMethod
	 * @param field
	 * @param entity
	 * @param objValue
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void ReflectType(Method setMethod, Field field, Object entity, Object objValue)
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(objValue != null && !objValue.equals("")) {
			String type = field.getType().toString(); // 获取属性的类型
			if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
				// "，后面跟类名
				setMethod.invoke(entity, (String) objValue);
			}
			if (type.equals("int") || type.equals("class java.lang.Integer")) {
				setMethod.invoke(entity, (Integer) objValue);
			}
			if (type.equals("short") || type.equals("class java.lang.Short")) {
				setMethod.invoke(entity, (Short) objValue);
			}
			if (type.equals("double") || type.equals("class java.lang.Double")) {
				setMethod.invoke(entity, (Double) objValue);
			}
			if (type.equals("boolean") || type.equals("class java.lang.Boolean")) {
				setMethod.invoke(entity, (Boolean) objValue);
			}
			if (type.equals("class java.util.Date")) {
				if (objValue instanceof java.util.Date) {
					setMethod.invoke(entity, objValue);
				} else {
					setMethod.invoke(entity, DateUtil.dateFormatByLength((String) objValue));
				}
			}
			if (type.equals("long") || type.equals("class java.lang.Long")) {
				if (objValue instanceof Long) {
					setMethod.invoke(entity, objValue);
				} else {
					setMethod.invoke(entity, Long.valueOf((String) objValue));
				}
			}
			if (type.equals("class java.math.BigDecimal")) {
				setMethod.invoke(entity, (BigDecimal) objValue);
			}
		}
	}

	/**
	 * 反射赋值 @Title: reflectType @Description: TODO(这里用一句话描述这个方法的作用) @author
	 * LiLuBing @param field @param entity @param objValue @throws
	 * NoSuchMethodException @throws IllegalAccessException @throws
	 * IllegalArgumentException @throws InvocationTargetException @throws
	 */
	public static void reflectType(Field field, Object entity, Object objValue)
			throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(objValue != null && !objValue.equals("")) {
			String type = field.getType().toString(); // 获取属性的类型
			if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
				// "，后面跟类名
				field.set(entity, (String) objValue);
			}
			if (type.equals("int") || type.equals("class java.lang.Integer")) {
				field.set(entity, (Integer) objValue);
			}
			if (type.equals("short") || type.equals("class java.lang.Short")) {
				field.set(entity, (Short) objValue);
			}
			if (type.equals("double") || type.equals("class java.lang.Double")) {
				field.set(entity, (Double) objValue);
			}
			if (type.equals("boolean") || type.equals("class java.lang.Boolean")) {
				field.set(entity, (Boolean) objValue);
			}
			if (type.equals("class java.util.Date")) {
				field.set(entity, DateUtil.dateFormatByLength((String) objValue));
			}
			if (type.equals("long") || type.equals("class java.lang.Long")) {
				field.set(entity, Long.valueOf((String) objValue));
			}
			if (type.equals("class java.math.BigDecimal")) {
				field.set(entity, (BigDecimal) objValue);
			}
		}
	}
	
	/**
	 * 两个 对象copy 并且判断字段是否相等
	 * 
	 * @param sourceEntity
	 * @param targetEntity
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static void objectCopyHasComparisonField(Class<?> oldClassType,
			Class<?> newClassType, Object sourceEntity, Object targetEntity)
			throws IntrospectionException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		// 获取元数据中间表所有方法
		Field[] fields = oldClassType.getDeclaredFields();
		for (Field field : fields) {
			if (!field.getName().equals("serialVersionUID")) {
				String filedName = field.getName();
				PropertyDescriptor pd = new PropertyDescriptor(filedName,
						oldClassType);
				Method getMethod = pd.getReadMethod();// 获得get方法
				Object value = getMethod.invoke(sourceEntity);// 执行get方法返回一个Object
				if (value != null) {
					// 获得属性的首字母并转换为大写，与setXXX对应
					String firstLetter = filedName.substring(0, 1)
							.toUpperCase();
					String setMethodName = "set" + firstLetter
							+ filedName.substring(1);
					// 获取元数据所有方法
					Field[] metFields = newClassType.getDeclaredFields();
					// 方法是否存在
					boolean fileExtis = false;
					for (int i = 0; i < metFields.length; i++) {
						if (metFields[i].getName().equals(filedName)) {
							fileExtis = true;
							break;
						}
					}
					// 方法存在则执行赋值
					if (fileExtis) {
						Method setMethod = newClassType.getMethod(
								setMethodName, new Class[] { field.getType() });
						ReflectUtils.ReflectType(setMethod, field,
								targetEntity, value);
					}
				}
			}
		}
	}

	/**
	 * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
	 * @param object : 子类对象
	 * @param fieldName : 父类中的属性名
	 * @param value : 将要设置的值
	 */
	public static void setFieldValue(Object object, String fieldName, Object value){
		//根据 对象和属性名通过反射 调用上面的方法获取 Field对象
		Field field = getDeclaredField(object, fieldName) ;
		//抑制Java对其的检查
		field.setAccessible(true) ;
		try {
			//将 object 中 field 所代表的值 设置为 value
			field.set(object, value) ;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 循环向上转型, 获取对象的 DeclaredField
	 * @param object : 子类对象
	 * @param fieldName : 父类中的属性名
	 * @return 父类中的属性对象
	 */
	public static Field getDeclaredField(Object object, String fieldName){
		Field field = null ;
		Class<?> clazz = object.getClass() ;
		for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName) ;
				return field ;
			} catch (Exception e) {
				//这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				//如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
			}
		}
		return null;
	}

	/**
	 * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
	 * @param object : 子类对象
	 * @param fieldName : 父类中的属性名
	 * @return : 父类中的属性值
	 */
	public static Object getFieldValue(Object object, String fieldName){

		//根据 对象和属性名通过反射 调用上面的方法获取 Field对象
		Field field = getDeclaredField(object, fieldName) ;
		//抑制Java对其的检查
		field.setAccessible(true) ;
		try {
			//获取 object 中 field 所代表的属性值
			return field.get(object) ;
		} catch(Exception e) {
			e.printStackTrace() ;
		}
		return null;
	}
}

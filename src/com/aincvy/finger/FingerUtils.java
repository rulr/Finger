package com.aincvy.finger;

import java.lang.reflect.Method;

/**
 * 工具类 <p>
 * 本类不可被继承<p>
 * 本类不可被 new <p>
 * @author World
 * @version alpha 0.0.1
 * @since JDK 1.7
 */
public final class FingerUtils {

	private FingerUtils(){
		
	}
	
	/**
	 * 把item 的首字符变成大写
	 * @param item
	 * @return
	 */
	public static String firstToUpper(String item) {
		if (item == null) {
			return null;
		}
		return item.replaceFirst(item.substring(0, 1), item.substring(0, 1).toUpperCase());
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	public static String getMethodName(String item) {
		return String.format("get%s", firstToUpper(item));
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	public static String setMethodName(String item) {
		return String.format("set%s", firstToUpper(item));
	}
	
	/**
	 * 在这个方法里面应该进行缓存操作
	 * @param claxx
	 * @param methodName
	 * @return
	 */
	public static Method getSetMethod(Class<?> claxx,String methodName) {
		for (Method item : claxx.getMethods()) {
			if (item.getName().equals(methodName)) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * 注： 如未设置 common-logging 的配置，debug方法可能不会产生输出
	 * @see #debug(String, Throwable)
	 * @param message
	 */
	public static void debug(String message) {
		debug(message, null);
	}
	
	/**
	 * 只有在调试模式下， 本方法的调用才为有效 ，设置调试的方法请看 FingerBus.Debug <p>
	 * 如果不想打开调试模式，还希望输出调试信息，请使用 FingerDebug 类<p>
	 * 注： 如未设置 common-logging 的配置，debug方法可能不会产生输出
	 * 
	 * @param message 调试消息
	 * @param t 
	 */
	public static void debug(String message,Throwable t){
		if (FingerBus.DEBUG) {
			FingerDebug.debug(message, t);
		}
	}
	
}

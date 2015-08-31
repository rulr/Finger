package com.aincvy.finger;

import java.lang.reflect.Method;

/**
 * ������ <p>
 * ���಻�ɱ��̳�<p>
 * ���಻�ɱ� new <p>
 * @author World
 * @version alpha 0.0.1
 * @since JDK 1.7
 */
public final class FingerUtils {

	private FingerUtils(){
		
	}
	
	/**
	 * ��item �����ַ���ɴ�д
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
	 * �������������Ӧ�ý��л������
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
	 * ע�� ��δ���� common-logging �����ã�debug�������ܲ���������
	 * @see #debug(String, Throwable)
	 * @param message
	 */
	public static void debug(String message) {
		debug(message, null);
	}
	
	/**
	 * ֻ���ڵ���ģʽ�£� �������ĵ��ò�Ϊ��Ч �����õ��Եķ����뿴 FingerBus.Debug <p>
	 * �������򿪵���ģʽ����ϣ�����������Ϣ����ʹ�� FingerDebug ��<p>
	 * ע�� ��δ���� common-logging �����ã�debug�������ܲ���������
	 * 
	 * @param message ������Ϣ
	 * @param t 
	 */
	public static void debug(String message,Throwable t){
		if (FingerBus.DEBUG) {
			FingerDebug.debug(message, t);
		}
	}
	
}

package com.aincvy.finger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Finger ������ <p>
 * ע�� ��δ���� common-logging �����ã�debug�������ܲ���������
 * 
 * @author World
 * @version alpha 0.0.1
 * @since JDK 1.7
 */
public final class FingerDebug {

	
	private static Log logger = null;
	
	static{
		logger = LogFactory.getLog(FingerDebug.class);
	}
	
	private FingerDebug(){
		
	}
	
	/**
	 * ע�� ��δ���� common-logging �����ã�debug�������ܲ���������
	 * @param message
	 * @param t
	 */
	public static void debug(Object message, Throwable t) {
		logger.debug(message, t);
	}
	
	public static void info(Object message, Throwable t) {
		logger.info(message, t);
	}
	
	public static void error(Object message, Throwable t) {
		logger.error(message, t);
	}
	
	public static void fatal(Object message, Throwable t) {
		logger.fatal(message, t);
	}
	
	public static void warn(Object message, Throwable t) {
		logger.warn(message, t);
	}
	
}

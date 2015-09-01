package com.aincvy.finger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Finger 调试类 <p>
 * 注： 如未设置 common-logging 的配置，debug方法可能不会产生输出
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
	 * 注： 如未设置 common-logging 的配置，debug方法可能不会产生输出
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

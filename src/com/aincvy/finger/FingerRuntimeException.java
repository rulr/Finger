package com.aincvy.finger;

/**
 * Finger运行时异常 <p>
 * @author World
 * @version alpha 0.0.1
 * @since JDK 1.7
 */
public class FingerRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FingerRuntimeException() {
		super();
	}

	public FingerRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FingerRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public FingerRuntimeException(String message) {
		super(message);
	}

	public FingerRuntimeException(Throwable cause) {
		super(cause);
	}
	
	
}

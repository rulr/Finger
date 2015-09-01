package com.aincvy.finger.bridge;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.aincvy.finger.FingerBus;
import com.aincvy.finger.FingerUtils;

/**
 * Finger 的Spring 桥
 * @author World
 * @since JDK 1.7
 * @version alpha 0.0.3
 */
public class FingerSpringBridge {

	static{
		try {
			Class.forName("com.aincvy.finger.FingerBus");
			FingerUtils.debug("Load FingerBus Over.");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Resource
	private boolean debug = false;
	
	@Resource
	private BasicDataSource basicDataSource = null;

	
	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}

	public void setBasicDataSource(BasicDataSource basicDataSource) {
		this.basicDataSource = basicDataSource;
		if(!FingerBus.isInited()){
			FingerBus.initParam(basicDataSource);
		}else{
			FingerBus.resetBasicDataSource(basicDataSource);
		}
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
		FingerBus.DEBUG = debug;
	}

	public boolean isDebug() {
		return debug;
	} 
	
}

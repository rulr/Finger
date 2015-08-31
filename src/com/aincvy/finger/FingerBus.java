package com.aincvy.finger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbcp2.BasicDataSource;

import com.aincvy.finger.cache.FingerCache;
import com.aincvy.finger.cache.FingerCacheClass;
import com.aincvy.finger.inf.IFingerEntity;
import com.aincvy.finger.inf.IFingerObject;

/**
 * Finger �������� <p>
 * @author World
 * @version alpha 0.0.6
 * @since JDK 1.7
 */
public final class FingerBus {
	
	static{
		// ��һЩ׼��
		FingerUtils.debug("prepare finger...");
		try {
			FingerUtils.debug("load cache...");
			//���ػ���
			Class.forName("com.aincvy.finger.cache.FingerCache");
			//���ص�����
			FingerUtils.debug("load fingerdebug...");
			Class.forName("com.aincvy.finger.FingerDebug");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static boolean _INITED = false;
	
	private static BasicDataSource basicDataSource = null;
	private static Map<Class<?>, Class<?>> classMap = null;
	
	
	/**
	 * ����ģʽ
	 */
	public static boolean DEBUG = false;
	
	/**
	 * ִ�и��²���֮�� ������Ӱ���������  �����Կ�����FingerExpandObject �࣬����������ʹ��
	 */
	public static int RESULT_RETURN_UPDATE_NUMS = 0x000A;
	
	/**
	 * ִ�в������֮�� �����ز����ID�� ������Ӱ�������
	 */
	public static int RESULT_RETURN_INSERT_ID = 0x000B;
	
	
	public static void initParam(BasicDataSource das) {
		if (_INITED) {
			throw new FingerRuntimeException("Finger �Ѿ�����ʼ����ϣ��޷��ٴν��г�ʼ��");
		}
		_INITED = true;
		
		classMap =  Collections.synchronizedMap(new HashMap<Class<?>, Class<?>>());
		resetBasicDataSource(das);
	}
	
	/**
	 * FingerBus �Ƿ��Ѿ���ʼ�����
	 * @return true: �� ��false: ��
	 */
	public static boolean isInited(){
		return _INITED;
	}
	
	
	public static void resetBasicDataSource(BasicDataSource das){
		checkInit();
		if (basicDataSource != null && !basicDataSource.isClosed()) {
			try {
				basicDataSource.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		basicDataSource = null;
		basicDataSource = das;
	}
	
	public static void checkInit() {
		if (!_INITED) {
			throw new FingerRuntimeException("Finger��Ҫ��ʼ��֮�����ʹ��");
		}
	}
	
	
	public static boolean registerObject(Class<? extends IFingerEntity> entityClass,Class<? extends IFingerObject> objectClass ) {
		checkInit();
		if (classMap.containsKey(entityClass) || classMap.containsValue(objectClass)) {
			throw new FingerRuntimeException("ʵ�������DAO���Ѿ�ע�����");
		}
		try {
			classMap.put(entityClass, objectClass);
			FingerCache.addCacheClass(entityClass);
			FingerCache.addCacheClass(objectClass);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public static Class<?> getDaoClass(Class<? extends IFingerEntity> claxx) {
		return classMap.get(claxx);
	}
	
	public static Class<?> getEntityClass(Class<? extends IFingerObject> claxx) {
		for (Entry<Class<?>, Class<?>> item : classMap.entrySet()) {
			if (item.getValue().equals(claxx)) {
				return item.getKey();
			}
		}
		return null;
	}
	
	public static <T> T dao(Class<? extends IFingerEntity> claxx) {
		checkInit();
		
		Class<?> daoClass = getDaoClass(claxx);
		if (daoClass == null) {
			throw new FingerRuntimeException("��ʵ����δע��; ");
		}
		
		FingerCacheClass fcs = FingerCache.getCacheClass(daoClass);
		if (fcs == null) {
			throw new FingerRuntimeException("δ���ڻ������ҵ������ͣ�����");
		}
		
		try {
			return fcs.getCachedInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T getDaoObject(String classPath) {
		try {
			return getDaoObject(Class.forName(classPath));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T getDaoObject(Class<?> claxx) {
		FingerCacheClass fcs = FingerCache.getCacheClass(claxx);
		if (fcs == null) {
			throw new FingerRuntimeException("δ���ڻ������ҵ������ͣ�����");
		}
		
		try {
			return fcs.getCachedInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Connection newConnection(){
		checkInit();
		try {
			return basicDataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 */
	public static void shutdown() {
		if (basicDataSource != null && !basicDataSource.isClosed()) {
			try {
				basicDataSource.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}

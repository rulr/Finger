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
 * Finger 控制总线 <p>
 * @author World
 * @version alpha 0.0.8
 * @since JDK 1.7
 */
public final class FingerBus {
	
	static{
		// 做一些准备
		FingerUtils.debug("prepare finger...");
		try {
			FingerUtils.debug("load cache...");
			//加载缓存
			Class.forName("com.aincvy.finger.cache.FingerCache");
			//加载调试类
			FingerUtils.debug("load fingerdebug...");
			Class.forName("com.aincvy.finger.FingerDebug");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static boolean _INITED = false;
	
	private static BasicDataSource basicDataSource = null;
	//类的Map , Key为实体类， Value为Dao类
	private static Map<Class<? extends IFingerEntity>, Class<? extends IFingerObject<? extends IFingerEntity>>> classMap = null;
	
	
	/**
	 * 调试模式
	 */
	public static boolean DEBUG = false;
	
	/**
	 * 执行更新操作之后 返回受影响的行数，  本属性可以在FingerExpandObject 类，及其子类中使用
	 */
	public static int RESULT_RETURN_UPDATE_NUMS = 0x000A;
	
	/**
	 * 执行插入操作之后 ，返回插入的ID， 而非受影响的行数
	 */
	public static int RESULT_RETURN_INSERT_ID = 0x000B;
	
	
	public static void initParam(BasicDataSource das) {
		if (_INITED) {
			throw new FingerRuntimeException("Finger 已经被初始化完毕，无法再次进行初始化");
		}
		_INITED = true;
		
		classMap =  Collections.synchronizedMap(new HashMap<Class<? extends IFingerEntity>, Class<? extends IFingerObject<? extends IFingerEntity>>>());
		resetBasicDataSource(das);
	}
	
	/**
	 * FingerBus 是否已经初始化完毕
	 * @return true: 是 ，false: 否
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
			throw new FingerRuntimeException("Finger需要初始化之后才能使用");
		}
	}
	
	
	public static <T extends IFingerEntity> boolean registerObject(Class<T> entityClass,Class<? extends IFingerObject<T>> objectClass ) {
		checkInit();
		if (classMap.containsKey(entityClass) || classMap.containsValue(objectClass)) {
			throw new FingerRuntimeException("实体类或者DAO类已经注册过了");
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
	
	public static Class<?> getEntityClass(Class<?> claxx) {
		for (Entry<Class<? extends IFingerEntity>, Class<? extends IFingerObject<? extends IFingerEntity>>> item : classMap.entrySet()) {
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
			throw new FingerRuntimeException("该实体类未注册; ");
		}
		
		FingerCacheClass fcs = FingerCache.getCacheClass(daoClass);
		if (fcs == null) {
			throw new FingerRuntimeException("未能在缓存中找到该类型，请检查");
		}
		
		try {
			return fcs.getCachedInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getDaoObject(String classPath) {
		try {
			return (T) getDaoObject(Class.forName(classPath));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T getDaoObject(Class<T> claxx) {
		FingerCacheClass fcs = FingerCache.getCacheClass(claxx);
		if (fcs == null) {
			throw new FingerRuntimeException("未能在缓存中找到该类型，请检查");
		}
		
		try {
			return fcs.getCachedInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取一个新的数据库链接
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

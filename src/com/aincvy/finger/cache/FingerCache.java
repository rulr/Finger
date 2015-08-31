package com.aincvy.finger.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.aincvy.finger.FingerRuntimeException;
import com.aincvy.finger.FingerUtils;

/**
 * Finger 缓存处理 <p>
 * 本类不可被继承，不可使用构造方法生产对象 <p>
 * 应使用本类的静态方法来获取缓存信息 <p>
 * @author World
 * @version alpha 0.0.2
 * @since JDK 1.7
 *
 */
public final class FingerCache {

	static{
		// lock cache file.
		
		map = Collections.synchronizedMap(new HashMap<String, FingerCacheClass>());
	}
	
	// public static values.
	public static long updateInterval = 0L;
	
	// private static values.
	private static Map<String, FingerCacheClass> map;
	
	private FingerCache(){
		
	}
	
	// methods.
	
	/**
	 * 清理缓存
	 */
	public static void clearCache(){
		map.clear();
	}
	
	/**
	 * 重建缓存，如果某类的缓存间隔 小于 {@value #updateInterval} ，则不进行更新操作 <p>
	 * @deprecated 写到一半发现这个方法没什么用处呀。。。  本方法并未进行完全实现，请不要使用本方法
	 */
	public static synchronized void rebuildCache() {
		clearCache();
		
		//lastUpdateTime = System.currentTimeMillis();
		for (Entry<String, FingerCacheClass> item : map.entrySet()) {
			FingerCacheClass fcs = item.getValue();
			if (fcs != null) {
				long l = System.currentTimeMillis() - fcs.getLastUpdateTime();
				if (l >= updateInterval) {
					//fcs.rebuildCache(Class.forName(fcs.getClassName()));
				}
			}
		}
	}
	
	/**
	 * 更新所有缓存 ，无视缓存的时间间隔
	 * @deprecated 写到一半发现这个方法没什么用处呀。。。  本方法并未进行完全实现，请不要使用本方法
	 */
	public static synchronized void updateAllCache() {
		for (Entry<String, FingerCacheClass> item : map.entrySet()) {
			if (item.getValue() != null) {
				item.getValue().resetLastUpdateTime();
			}
		}
		long temp = updateInterval;
		updateInterval = 0L;
		rebuildCache();
		updateInterval = temp;
	}
	
	
	public static FingerCacheClass addCacheClass(Class<?> claxx) {
		String name = claxx.getName();
		FingerUtils.debug("Add Finger Cache Class: " + name);
		if (map.containsKey(name)) {
			return getCacheClass(name);
		}
		FingerCacheClass fcs = new FingerCacheClass(claxx);
		map.put(name, fcs);
		return fcs;
	}
	
	public static FingerCacheClass getCacheClass(String name) {
		try {
			return map.get(name);
		} catch (Exception e) {
			throw new FingerRuntimeException("在尝试类型转换时失败",e);
		}
	}
	
	public static FingerCacheClass getCacheClass(Class<?> claxx) {
		return getCacheClass(claxx.getName());
	}

	
	
	
}

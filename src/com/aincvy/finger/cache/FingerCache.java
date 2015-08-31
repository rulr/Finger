package com.aincvy.finger.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.aincvy.finger.FingerRuntimeException;
import com.aincvy.finger.FingerUtils;

/**
 * Finger ���洦�� <p>
 * ���಻�ɱ��̳У�����ʹ�ù��췽���������� <p>
 * Ӧʹ�ñ���ľ�̬��������ȡ������Ϣ <p>
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
	 * ������
	 */
	public static void clearCache(){
		map.clear();
	}
	
	/**
	 * �ؽ����棬���ĳ��Ļ����� С�� {@value #updateInterval} ���򲻽��и��²��� <p>
	 * @deprecated д��һ�뷢���������ûʲô�ô�ѽ������  ��������δ������ȫʵ�֣��벻Ҫʹ�ñ�����
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
	 * �������л��� �����ӻ����ʱ����
	 * @deprecated д��һ�뷢���������ûʲô�ô�ѽ������  ��������δ������ȫʵ�֣��벻Ҫʹ�ñ�����
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
			throw new FingerRuntimeException("�ڳ�������ת��ʱʧ��",e);
		}
	}
	
	public static FingerCacheClass getCacheClass(Class<?> claxx) {
		return getCacheClass(claxx.getName());
	}

	
	
	
}

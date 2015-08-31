package com.aincvy.finger.cache;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.aincvy.finger.FingerRuntimeException;
import com.aincvy.finger.FingerUtils;

/**
 * Finger ������ ���� ������һ�� ���ʵ���� �����ȫ�� ���з���
 * @author World
 * @version alpha 0.0.1
 * @since JDK 1.7
 */
public class FingerCacheClass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// ���������� ��һʵ��
	private Object instanceObject;
	// ����������
	private Class<?> classObject;
	// ������ķ���
	private Map<String, Method> methodMap = null;

	private long lastUpdateTime = 0L;
	
	public FingerCacheClass(Class<?> claxx) {
		if (claxx == null) {
			throw new FingerRuntimeException("����������Ͳ���Ϊnull");
		}
		methodMap = Collections.synchronizedMap(new HashMap<String, Method>());
		rebuildCache(claxx);
	}
	
	/**
	 * �ؽ����������
	 * @param claxx
	 */
	public void rebuildCache(Class<?> claxx) {
		FingerUtils.debug("rebuilding " + claxx.getName() + " finger class info...");
		methodMap.clear();
		instanceObject = null;
		classObject = null;
		for (Method item : claxx.getMethods()) {
			methodMap.put(item.getName(), item);
		}
		classObject = claxx;
		lastUpdateTime = System.currentTimeMillis();
		
		FingerUtils.debug(methodMap.toString());
	}
	
	public long getLastUpdateTime(){
		return this.lastUpdateTime;
	}
	
	/**
	 * ��ȡ������� Class ����
	 * @return
	 */
	public Class<?> getCachedClass() {
		return this.classObject;
	}
	
	/**
	 * ��ȡ�������class ����ĵ�ʵ���� Ψһʵ�� <p>
	 * ����ͨ������ class.newInstance() ������ȡ���Ķ���
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public<T> T getCachedInstance() {
		if (instanceObject == null) {
			instanceObject = newInstance();
		}
		return (T)instanceObject;
	}
	
	/**
	 * ��ȡ�������ĳ������
	 * @param name ������
	 * @return
	 */
	public Method getMethod(String name) {
		return methodMap.get(name);
	}
	
	/**
	 * ��ȡĳ�����Ե�  setter�� ����������Ҫд��д
	 * @param name ������
	 * @return
	 */
	public Method getSetMethod(String name) {
		return methodMap.get(String.format("set%s", FingerUtils.firstToUpper(name)));
	}
	
	/**
	 * ��ȡĳ�����Ե�getter ������������Ҫ��д
	 * @param name ������
	 * @return
	 */
	public Method getGetMethod(String name){
		return methodMap.get(String.format("get%s", FingerUtils.firstToUpper(name)));
	}
	
	/**
	 * ����һ���µ�ʵ��
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public<T> T newInstance(){
		try {
			return (T)classObject.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new FingerRuntimeException("�ڲ���һ�������ʵ��ʱʧ��",e);
		}
	}
	
	/**
	 * ��ȡ�����������
	 * @return
	 */
	public String getClassName() {
		return classObject.getName();
	}
	
	/**
	 * �� lastUpdateTime ����Ϊ 0
	 */
	public void resetLastUpdateTime() {
		lastUpdateTime = 0L;
	}
	
}

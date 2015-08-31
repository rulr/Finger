package com.aincvy.finger.cache;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.aincvy.finger.FingerRuntimeException;
import com.aincvy.finger.FingerUtils;

/**
 * Finger 缓存类 对象， 缓存了一个 类的实例， 和类的全部 公有方法
 * @author World
 * @version alpha 0.0.1
 * @since JDK 1.7
 */
public class FingerCacheClass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// 缓存类对象的 单一实例
	private Object instanceObject;
	// 缓存的类对象
	private Class<?> classObject;
	// 缓存类的方法
	private Map<String, Method> methodMap = null;

	private long lastUpdateTime = 0L;
	
	public FingerCacheClass(Class<?> claxx) {
		if (claxx == null) {
			throw new FingerRuntimeException("被缓存的类型不能为null");
		}
		methodMap = Collections.synchronizedMap(new HashMap<String, Method>());
		rebuildCache(claxx);
	}
	
	/**
	 * 重建本类的属性
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
	 * 获取被缓存的 Class 对象
	 * @return
	 */
	public Class<?> getCachedClass() {
		return this.classObject;
	}
	
	/**
	 * 获取被缓存的class 对象的的实例的 唯一实例 <p>
	 * 即，通过调用 class.newInstance() 方法获取到的对象
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
	 * 获取被缓存的某个方法
	 * @param name 方法名
	 * @return
	 */
	public Method getMethod(String name) {
		return methodMap.get(name);
	}
	
	/**
	 * 获取某个属性的  setter， 属性名不需要写大写
	 * @param name 属性名
	 * @return
	 */
	public Method getSetMethod(String name) {
		return methodMap.get(String.format("set%s", FingerUtils.firstToUpper(name)));
	}
	
	/**
	 * 获取某个属性的getter ，属性名不需要大写
	 * @param name 属性名
	 * @return
	 */
	public Method getGetMethod(String name){
		return methodMap.get(String.format("get%s", FingerUtils.firstToUpper(name)));
	}
	
	/**
	 * 获取某个Boolean属性的值的方法 ，属性名不需要大写
	 * @param name 属性名
	 * @return
	 */
	public Method getIsMethod(String name){
		return methodMap.get(String.format("is%s", FingerUtils.firstToUpper(name)));
	}
	
	/**
	 * 产生一个新的实例
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public<T> T newInstance(){
		try {
			return (T)classObject.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new FingerRuntimeException("在产生一个类的新实例时失败",e);
		}
	}
	
	/**
	 * 获取被缓存的类名
	 * @return
	 */
	public String getClassName() {
		return classObject.getName();
	}
	
	/**
	 * 把 lastUpdateTime 设置为 0
	 */
	public void resetLastUpdateTime() {
		lastUpdateTime = 0L;
	}
	
}

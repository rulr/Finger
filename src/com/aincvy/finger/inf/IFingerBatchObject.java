package com.aincvy.finger.inf;

import java.util.List;

/**
 * 批处理Finger 对象 接口<p>
 * @author World
 * @version alpha 0.0.2
 * @since JDK 1.7
 */
public interface IFingerBatchObject<T extends IFingerEntity>{

	/**
	 * 批插入
	 * @param entities
	 * @return
	 */
	public int batchInsert(List<T> entities);
	
	/**
	 * 批删除
	 * @param id
	 * @return
	 */
	public int batchDelete(List<Object> ids);
	
	/**
	 * 批更新
	 * @param entities
	 * @return
	 */
	public int batchUpdate(List<T> entities);
	
	/**
	 * 批插入
	 * @param entities
	 * @return
	 */
	public int batchInsert(T []entities);
	
	/**
	 * 批删除
	 * @param entities
	 * @return
	 */
	public int batchDelete(Object []ids);
	
	/**
	 * 批更新
	 * @param entities
	 * @return
	 */
	public int batchUpdate(T []entities);
	
	
}

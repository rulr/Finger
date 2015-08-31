package com.aincvy.finger.inf;

import java.util.List;

/**
 * 批处理Finger 对象 接口<p>
 * @author World
 * @version alpha 0.0.1
 * @since JDK 1.7
 */
public interface IFingerBatchObject{

	/**
	 * 批插入
	 * @param entities
	 * @return
	 */
	public int batchInsert(List<? extends IFingerEntity> entities);
	
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
	public int batchUpdate(List<? extends IFingerEntity> entities);
	
	/**
	 * 批插入
	 * @param entities
	 * @return
	 */
	public int batchInsert(IFingerEntity []entities);
	
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
	public int batchUpdate(IFingerEntity []entities);
	
	
}

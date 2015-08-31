package com.aincvy.finger.inf;

import java.util.List;

/**
 * ������Finger ���� �ӿ�<p>
 * @author World
 * @version alpha 0.0.1
 * @since JDK 1.7
 */
public interface IFingerBatchObject{

	/**
	 * ������
	 * @param entities
	 * @return
	 */
	public int batchInsert(List<? extends IFingerEntity> entities);
	
	/**
	 * ��ɾ��
	 * @param id
	 * @return
	 */
	public int batchDelete(List<Object> ids);
	
	/**
	 * ������
	 * @param entities
	 * @return
	 */
	public int batchUpdate(List<? extends IFingerEntity> entities);
	
	/**
	 * ������
	 * @param entities
	 * @return
	 */
	public int batchInsert(IFingerEntity []entities);
	
	/**
	 * ��ɾ��
	 * @param entities
	 * @return
	 */
	public int batchDelete(Object []ids);
	
	/**
	 * ������
	 * @param entities
	 * @return
	 */
	public int batchUpdate(IFingerEntity []entities);
	
	
}

package com.aincvy.finger.inf;

import java.io.Serializable;
import java.util.List;

/**
 * Finger ������
 * @author World
 * @version alpha 0.0.2
 * @since JDK 1.7
 */
public interface IFingerDataRow extends List<Object>,Serializable {
	
	/**
	 * ��ȡ���е�ĳ������
	 * @param column ����
	 * @return
	 */
	public Object get(String column);
	
	/**
	 * ��ȡ�������ڵ����ݱ����
	 * @return
	 */
	public IFingerDataTable getTable();
	
	/**
	 * ��ȡ���������ݱ��е�������
	 * @return
	 */
	public int getRowIndex();
	
}

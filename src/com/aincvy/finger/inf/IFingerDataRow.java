package com.aincvy.finger.inf;

import java.io.Serializable;
import java.util.List;

/**
 * Finger 数据行
 * @author World
 * @version alpha 0.0.2
 * @since JDK 1.7
 */
public interface IFingerDataRow extends List<Object>,Serializable {
	
	/**
	 * 获取本行的某列数据
	 * @param column 列名
	 * @return
	 */
	public Object get(String column);
	
	/**
	 * 获取本行所在的数据表对象
	 * @return
	 */
	public IFingerDataTable getTable();
	
	/**
	 * 获取本行在数据表中的行索引
	 * @return
	 */
	public int getRowIndex();
	
}

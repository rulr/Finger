package com.aincvy.finger.inf;

import java.io.Serializable;
import java.util.List;

/**
 * Finger 数据表 接口
 * @author World
 * @version alpha 0.0.4
 * @since JDK 1.7
 */
public interface IFingerDataTable extends List<IFingerDataRow>,Serializable {

	/**
	 * 获取此表的名字 <br/>
	 * 如果本查询结果是利用多张表进行查询的，则返回第一列的表名字
	 * @return
	 */
	public String getTableName();
	
	/**
	 * 设置本表的名字
	 * @param name
	 */
	public void setTableName(String name);
	
	/**
	 * 获取本表的列名
	 * @return
	 */
	public List<String> getColumns();
	
	/**
	 * 设置本表的列名
	 * @param columns
	 */
	public void setColumns(List<String> columns);
	
	/**
	 * 添加新列,添加成功返回列名， 添加失败 返回null.
	 * @param name
	 * @return
	 */
	public String addColumn(String name);
	

	/**
	 * 获取表的 列数量
	 * @return
	 */
	public int getColumnNum();

	
	/**
	 * 根据索引获取列名
	 * @param index
	 * @return
	 */
	public String getColumnName(int index);
	
	/**
	 * 根据列名获取索引
	 * @param name
	 * @return
	 */
	public int getColumnIndex(String name);
	
}

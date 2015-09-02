package com.aincvy.finger.inf;

import java.util.List;

/**
 * 具有排序能力的Finger对象 <p>
 * @author World
 * @version alpha 0.0.5
 * @since JDK 1.7
 */
public interface IFingerPageableObject {

	/**
	 * 获取给定参数的 上一个id值 <p>
	 * @param id
	 * @return
	 */
	public Object previousId(Object id); 
	
	/**
	 * 获取给定参数的下一个id值 <p>
	 * @param id
	 * @return
	 */
	public Object nextId(Object id);
	
	/**
	 * 设置一页显示的数量
	 * @param pageNum 
	 */
	public void setPageSize(int pageSize);
	
	/**
	 * 获取指定页的内容 <p>
	 * @param pageNum  页码应从1开始计算， 0和1具有相同的效果
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> page(int pageNum,int pageSize);
	
	/**
	 * 获取指定页的内容 <p>
	 * @param pageNum  页码，应从1开始计算， 0和1具有相同的效果
	 * @return
	 */
	public <T> List<T> page(int pageNum);
	
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int totalPageNum();
	
	/**
	 * 按照每页 pageSize的数量进行分页，返回总页数
	 * @param pageSize 一页显示的数量
	 * @return
	 */
	public int totalPageNum(int pageSize);
	
	/**
	 * 设置查询的sql语句
	 * @param sql 
	 */
	public void setPageSql(String sql);
	
}

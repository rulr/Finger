package com.aincvy.finger.inf;

import java.sql.Connection;
import java.util.List;

/**
 * Finger 对象接口
 * @author World
 * @version alpha 0.0.8
 * @since JDK 1.7
 */
public interface IFingerObject {

	/**
	 * 插入实体类，使用一个新的数据库连接
	 * @param entity
	 * @return
	 */
	public int insert(IFingerEntity entity);
	
	/**
	 * 插入实体类，使用一个新的数据库连接
	 * @param entity 实体
	 * @param insertPK 是否插入主键
	 * @return
	 */
	public int insert(IFingerEntity entity,boolean insertPK);
	
	/**
	 * 插入实体类，使用给定的数据库连接
	 * @param con
	 * @param entity
	 * @return
	 */
	public int insert(Connection con,IFingerEntity entity);
	
	/**
	 * 插入实体类，使用给定的数据库连接 ，并决定是否插入主键信息
	 * @param con
	 * @param entity
	 * @param insertPK true:插入主键， false: 不插入主键
	 * @return
	 */
	public int insert(Connection con,IFingerEntity entity,boolean insertPK);
	
	/**
	 * 更新实体，使用新的数据库连接
	 * @param id
	 * @param entity
	 * @return
	 */
	public int update(Object id,IFingerEntity entity);
	

	/**
	 * 更新实体，使用给定的数据库连接
	 * @param con
	 * @param id
	 * @param entity
	 * @param flag 执行更新操作之后是否关闭链接  true:是，　false:否
	 * @return
	 */
	public int update(Connection con,Object id,IFingerEntity entity,boolean flag);
	
	
	/**
	 * 更新实体
	 * @param entity
	 * @return
	 */
	public int update(IFingerEntity entity);
	
	/**
	 * 删除某一条记录
	 * @param id 主键值
	 * @return
	 */
	public int delete(Object id);
	
	/**
	 * 使用给定的连接进行删除某项值
	 * @param con
	 * @param id
	 * @return
	 */
	public int delete(Connection con,Object id);
	
	/**
	 * 
	 * @return
	 */
	public <T> T fetchFirst();
	
	/**
	 * 
	 * @param condition  条件
	 * @return
	 */
	public <T> T fetchFirst(String condition);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public <T> T fetch(Object id);
	
	/**
	 * 获取表内的所有数据
	 * @return
	 */
	public <T> List<T> fetchTable();
	
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public <T> List<T> executeQuery(String sql);
	
	
	/**
	 * 获得表内的所有记录条数
	 * @return
	 */
	public int count();
	
	
}

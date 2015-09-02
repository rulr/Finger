package com.aincvy.finger.inf;

import java.sql.Connection;
import java.util.List;

/**
 * Finger 对象接口
 * @author World
 * @version alpha 0.1.2
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
	 * 设置Fetch时的SQL语句
	 * @param sql
	 */
	public void setFetchSql(String sql);
	
	/**
	 * 查询本表中的第一条记录
	 * @return
	 */
	public <T> T fetchFirst();
	
	/**
	 * 查询本表中的第一条记录
	 * @param condition 条件
	 * @param params 参数
	 * @return
	 */
	public <T> T fetchFirst(String condition,Object ...params);
	
	/**
	 * 根据主键 查找记录， 注：  不适合组合主键
	 * @param id 主键值
	 * @return
	 */
	public <T> T fetch(Object id);
	
	/**
	 * 获取表内的所有数据
	 * @return
	 */
	public <T> List<T> fetchTable();
	
	/**
	 * 执行查询操作
	 * @param sql
	 * @return
	 */
	public <T> List<T> executeQuery(String sql,Object ...param);
	
	
	/**
	 * 获得表内的所有记录条数
	 * @return
	 */
	public int count();
	
	/**
	 * 设置表明和 主键值
	 * @param tableName 表名
	 * @param pk 主键列名， 暂不支持多个主键的列
	 */
	public void setTableNameAndPrimaryKey(String tableName,String pk);
	
	
	/**
	 * 设置表明和 主键值
	 * @param tableName 表名
	 * @param pk 主键列名， 暂不支持多个主键的列
	 * @param rewriteFetchSql  是否重写FetchSql属性
	 */
	public void setTableNameAndPrimaryKey(String tableName,String pk,boolean rewriteFetchSql);
}

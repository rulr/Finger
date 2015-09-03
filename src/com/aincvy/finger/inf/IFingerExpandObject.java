package com.aincvy.finger.inf;

import java.util.List;

/**
 * Finger 扩展Object ，主攻查询操作
 * @author World
 * @since JDK 1.7
 * @version alpha 0.0.7
 */
public interface IFingerExpandObject<T extends IFingerEntity> {

	/**
	 * 拓展性方法， 执行一个查询语句
	 * 然后把SQL语句的查询结果放入一个IFingerDataTable 里面
	 * @param sql 查询语句
	 * @param params 参数列表
	 * @return
	 */
	public IFingerDataTable exQuery(String sql,Object ...params);
	
	/**
	 * 拓展性方法， 如果你认为本方法过于难用，可以采用直接写Sql语句的形式来使用别的方法
	 * @param claxx 实体模板
	 * @param rule 把结果转换成实体的规则 ,具体写法为: cName => customer , 即结果字段名 => 类属性名<br/>
	 *         如果规则没有说明的内容，则使用默认的 rule 
	 * @param sql 查询语句
	 * @param params 参数列表
	 * @return
	 */
	public List<T> exQuery(Class<T> claxx, String rule,String sql,Object ...params);
	
	/**
	 * 拓展性方法， 如果你认为本方法过于难用，可以采用直接写Sql语句的形式来使用别的方法 <br/>
	 * 只插入 rule内表述的字段， 返回值为 returnBack 所指示的内容
	 * @param rule 规则表述 ，如果数据库字段和类的属性名一样的话则用逗号分隔每个字段名 例如：  col1,col2,col3... <br/>
	 * 不一致的话，则应使用：  field1 => col1,field2 => col2,field3 => col3 的方式  <br/>
	 * 如果本项为null, 则使用默认设置的 rule 进行操作
	 * @param entity 要插入的实体类
	 * @param returnBack  具体使用的值 请看 FingerBus,如果给定的值不正确，则返回受影响的行数
	 * @return
	 */
	public int exInsert(String rule,T entity,int returnBack);
	
	

	/**
	 * 带规则的 事务更新 ,返回受影响的行数
	 * @param tid 如果不想使用事务，请写-1
	 * @param entity 要更新的实体
	 * @param rule  规则表示  如果数据库字段和类的属性名一样的话则用逗号分隔每个字段名 例如：  col1,col2,col3... <br/>
	 * 不一致的话，则应使用：  field1 => col1,field2 => col2,field3 => col3 的方式
	 * @return
	 */
	public int exTransactionUpdate(int tid,T entity,String rule);
	
}

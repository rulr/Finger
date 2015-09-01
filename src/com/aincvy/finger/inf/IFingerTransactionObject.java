package com.aincvy.finger.inf;

/**
 * 事务FingerObject 接口<p>
 * @author World
 * @since JDK 1.7
 * @version alpha 0.0.2
 */
public interface IFingerTransactionObject {

	/**
	 * 开始事务
	 * @return
	 */
	public int beginTransaction();
	
	/**
	 * 提交事务 ,提交之后会释放这个事务
	 * @param tid
	 */
	public void commit(int tid);
	
	/**
	 * 事务回滚，回滚之后会释放这个事务
	 * @param tid
	 */
	public void rollback(int tid);
	
	/**
	 * 事务插入
	 * @param tid
	 * @param entity
	 * @return
	 */
	public int transactionInsert(int tid,IFingerEntity entity);
	
	/**
	 * 事务更新
	 * @param tid
	 * @param entity
	 * @return
	 */
	public int transactionUpdate(int tid,IFingerEntity entity);
	
	/**
	 * 事务删除
	 * @param tid
	 * @param id
	 * @return
	 */
	public int transactionDelete(int tid, Object id);
}

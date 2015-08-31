package com.aincvy.finger.inf;

/**
 * ����FingerObject �ӿ�<p>
 * @author World
 * @since JDK 1.7
 * @version alpha 0.0.2
 */
public interface IFingerTransactionObject {

	/**
	 * ��ʼ����
	 * @return
	 */
	public int beginTransaction();
	
	/**
	 * �ύ���� ,�ύ֮����ͷ��������
	 * @param tid
	 */
	public void commit(int tid);
	
	/**
	 * ����ع����ع�֮����ͷ��������
	 * @param tid
	 */
	public void rollback(int tid);
	
	/**
	 * �������
	 * @param tid
	 * @param entity
	 * @return
	 */
	public int transactionInsert(int tid,IFingerEntity entity);
	
	/**
	 * �������
	 * @param tid
	 * @param entity
	 * @return
	 */
	public int transactionUpdate(int tid,IFingerEntity entity);
	
	/**
	 * ����ɾ��
	 * @param tid
	 * @param id
	 * @return
	 */
	public int transactionDelete(int tid, Object id);
}

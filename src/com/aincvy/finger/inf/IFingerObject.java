package com.aincvy.finger.inf;

import java.sql.Connection;
import java.util.List;

/**
 * Finger ����ӿ�
 * @author World
 * @version alpha 0.0.8
 * @since JDK 1.7
 */
public interface IFingerObject {

	/**
	 * ����ʵ���࣬ʹ��һ���µ����ݿ�����
	 * @param entity
	 * @return
	 */
	public int insert(IFingerEntity entity);
	
	/**
	 * ����ʵ���࣬ʹ��һ���µ����ݿ�����
	 * @param entity ʵ��
	 * @param insertPK �Ƿ��������
	 * @return
	 */
	public int insert(IFingerEntity entity,boolean insertPK);
	
	/**
	 * ����ʵ���࣬ʹ�ø��������ݿ�����
	 * @param con
	 * @param entity
	 * @return
	 */
	public int insert(Connection con,IFingerEntity entity);
	
	/**
	 * ����ʵ���࣬ʹ�ø��������ݿ����� ���������Ƿ����������Ϣ
	 * @param con
	 * @param entity
	 * @param insertPK true:���������� false: ����������
	 * @return
	 */
	public int insert(Connection con,IFingerEntity entity,boolean insertPK);
	
	/**
	 * ����ʵ�壬ʹ���µ����ݿ�����
	 * @param id
	 * @param entity
	 * @return
	 */
	public int update(Object id,IFingerEntity entity);
	

	/**
	 * ����ʵ�壬ʹ�ø��������ݿ�����
	 * @param con
	 * @param id
	 * @param entity
	 * @param flag ִ�и��²���֮���Ƿ�ر�����  true:�ǣ���false:��
	 * @return
	 */
	public int update(Connection con,Object id,IFingerEntity entity,boolean flag);
	
	
	/**
	 * ����ʵ��
	 * @param entity
	 * @return
	 */
	public int update(IFingerEntity entity);
	
	/**
	 * ɾ��ĳһ����¼
	 * @param id ����ֵ
	 * @return
	 */
	public int delete(Object id);
	
	/**
	 * ʹ�ø��������ӽ���ɾ��ĳ��ֵ
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
	 * @param condition  ����
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
	 * ��ȡ���ڵ���������
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
	 * ��ñ��ڵ����м�¼����
	 * @return
	 */
	public int count();
	
	
}

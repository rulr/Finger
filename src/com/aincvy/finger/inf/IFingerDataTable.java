package com.aincvy.finger.inf;

import java.io.Serializable;
import java.util.List;

/**
 * Finger ���ݱ� �ӿ�
 * @author World
 * @version alpha 0.0.3
 * @since JDK 1.7
 */
public interface IFingerDataTable extends List<IFingerDataRow>,Serializable {

	/**
	 * ��ȡ�˱������ <br/>
	 * �������ѯ��������ö��ű���в�ѯ�ģ��򷵻ص�һ�еı�����
	 * @return
	 */
	public String getTableName();
	
	/**
	 * ���ñ��������
	 * @param name
	 */
	public void setTableName(String name);
	
	/**
	 * ��ȡ���������
	 * @return
	 */
	public List<String> getColumns();
	
	/**
	 * ���ñ��������
	 * @param columns
	 */
	public void setColumns(List<String> columns);
	
	/**
	 * �������,��ӳɹ����������� ���ʧ�� ����null.
	 * @param name
	 * @return
	 */
	public String addColumn(String name);
	

	/**
	 * ��ȡ��� ������
	 * @return
	 */
	public int getColumnNum();

	
	/**
	 * ����������ȡ����
	 * @param index
	 * @return
	 */
	public String getColumnName(int index);
	
	/**
	 * ����������ȡ����
	 * @param name
	 * @return
	 */
	public int getColumnIndex(String name);
	
}

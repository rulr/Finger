package com.aincvy.finger.inf;

import java.util.List;

/**
 * Finger ��չObject ��������ѯ����
 * @author World
 * @since JDK 1.7
 * @version alpha 0.0.3
 */
public interface IFingerExpandObject {

	/**
	 * ��SQL���Ĳ�ѯ�������һ��IFingerDataTable ����
	 * @param sql ��ѯ���
	 * @param params �����б�
	 * @return
	 */
	public IFingerDataTable exQuery(String sql,Object ...params);
	
	/**
	 * 
	 * @param claxx ʵ��ģ��
	 * @param rule �ѽ��ת����ʵ��Ĺ��� ,����д��Ϊ: cName => customer , ������ֶ��� => ��������<br/>
	 *         �������û��˵�������ݣ��� ����������������ֶ�����ͬ����Ϣ��д����ʵ��
	 * @param sql ��ѯ���
	 * @param params �����б�
	 * @return
	 */
	public <T> List<T> exQuery(Class<T> claxx, String rule,String sql,Object ...params);
	
	/**
	 * ֻ���� rule�ڱ������ֶΣ� ����ֵΪ returnBack ��ָʾ������
	 * @param rule ������� ��������ݿ��ֶκ����������һ���Ļ����ö��ŷָ�ÿ���ֶ��� ���磺  col1,col2,col3... <br/>
	 * ��һ�µĻ�����Ӧʹ�ã�  field1 => col1,field2 => col2,field3 => col3 �ķ�ʽ
	 * @param entity Ҫ�����ʵ����
	 * @param returnBack  ����ʹ�õ�ֵ �뿴 FingerBus,���������ֵ����ȷ���򷵻���Ӱ�������
	 * @return
	 */
	public int exInsert(String rule,IFingerEntity entity,int returnBack);
	
	
	
}

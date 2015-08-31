package com.aincvy.finger.inf;

import java.util.List;

/**
 * ��������������Finger���� <p>
 * @author World
 * @version alpha 0.0.4
 * @since JDK 1.7
 */
public interface IFingerPageableObject {

	/**
	 * ��ȡ���������� ��һ��idֵ <p>
	 * @param id
	 * @return
	 */
	public Object previousId(Object id); 
	
	/**
	 * ��ȡ������������һ��idֵ <p>
	 * @param id
	 * @return
	 */
	public Object nextId(Object id);
	
	/**
	 * ����һҳ��ʾ������
	 * @param pageNum 
	 */
	public void setPageSize(int pageSize);
	
	/**
	 * ��ȡָ��ҳ������ <p>
	 * @param pageNum  ҳ��Ӧ��1��ʼ���㣬 0��1������ͬ��Ч��
	 * @param pageSize
	 * @return
	 */
	public <T> List<T> page(int pageNum,int pageSize);
	
	/**
	 * ��ȡָ��ҳ������ <p>
	 * @param pageNum  ҳ�룬Ӧ��1��ʼ���㣬 0��1������ͬ��Ч��
	 * @return
	 */
	public <T> List<T> page(int pageNum);
	
	
	/**
	 * ��ȡ��ҳ��
	 * @return
	 */
	public int totalPageNum();
	
	/**
	 * ����ÿҳ pageSize���������з�ҳ��������ҳ��
	 * @param pageSize һҳ��ʾ������
	 * @return
	 */
	public int totalPageNum(int pageSize);
	
}

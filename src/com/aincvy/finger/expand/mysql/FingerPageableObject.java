package com.aincvy.finger.expand.mysql;

import java.util.List;
import java.util.Map;

import com.aincvy.finger.FingerExpandObject;
import com.aincvy.finger.inf.IFingerEntity;
import com.aincvy.finger.inf.IFingerPageableObject;

/**
 * 带有排序功能的 Finger对象 <p>
 * 针对于Mysql 进行实现
 * @author World
 * @version alpha 0.1.3
 * @since JDK 1.7
 */
public abstract class FingerPageableObject<T extends IFingerEntity> extends FingerExpandObject<T> implements IFingerPageableObject<T>{

	protected int pageSize = 10;
	
	/**
	 * 排序使用的sql语句
	 */
	protected String pageSql = null;
	
	public FingerPageableObject() {
		
	}

	//SELECT bid FROM cigar_big_shots WHERE bid>'.$id.' ORDER BY bid ASC LIMIT 1'
	@Override
	public Object previousId(Object id) {
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(this.pk);
		sqlBuilder.append(" FROM `");
		sqlBuilder.append(this.table);
		sqlBuilder.append("` WHERE ");
		sqlBuilder.append(this.pk);
		sqlBuilder.append(" > ? ORDER BY ");
		sqlBuilder.append(this.pk);
		sqlBuilder.append(" ASC LIMIT 1");
		List<Map<String, Object>> list = query(sqlBuilder.toString(), id);
		if (list == null || list.size() <= 0) {
			return null;
		}
		return list.get(0).get(this.pk);
	}

	@Override
	public Object nextId(Object id) {
		StringBuffer sqlBuilder = new StringBuffer();
		sqlBuilder.append("SELECT ");
		sqlBuilder.append(this.pk);
		sqlBuilder.append(" FROM `");
		sqlBuilder.append(this.table);
		sqlBuilder.append("` WHERE ");
		sqlBuilder.append(this.pk);
		sqlBuilder.append(" < ? ORDER BY ");
		sqlBuilder.append(this.pk);
		sqlBuilder.append(" DESC LIMIT 1");
		List<Map<String, Object>> list = query(sqlBuilder.toString(), id);
		if (list == null || list.size() <= 0) {
			return null;
		}
		return list.get(0).get(this.pk);
	}

	@Override
	public List<T> page(int pageNum, int pageSize) {
		StringBuffer sqlBuilder = new StringBuffer(this.pageSql);
		sqlBuilder.append(" LIMIT ");
		
		int startIndex = 0;
		if (pageNum != 0) {
			startIndex = (pageNum - 1 ) * pageSize;
		}
		sqlBuilder.append(String.format("%d,%d", startIndex, pageSize));
		return executeQuery(sqlBuilder.toString());
	}

	@Override
	public int totalPageNum() {
		return totalPageNum(this.pageSize);
	}

	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public List<T> page(int pageNum) {
		return page(pageNum, this.pageSize);
	}

	@Override
	public int totalPageNum(int pageSize) {
		int count = count();
		if (count % pageSize == 0) {
			return count / pageSize;
		}
		return count / pageSize + 1;
	}

	@Override
	public void setPageSql(String sql) {
		this.pageSql = sql;
	}

	
	@Override
	public void setTableNameAndPrimaryKey(String tableName, String pk) {
		super.setTableNameAndPrimaryKey(tableName, pk);
		
		pageSql = String.format("SELECT * FROM `%s` ORDER BY %s ", this.table,this.pk);
	}
	
	
	
}

package com.aincvy.finger.expand.mysql;

import java.util.List;
import java.util.Map;

import com.aincvy.finger.FingerExpandObject;
import com.aincvy.finger.inf.IFingerPageableObject;

/**
 * 带有排序功能的 Finger对象 <p>
 * 针对于Mysql 进行实现
 * @author World
 * @version alpha 0.1.0
 * @since JDK 1.7
 */
public class FingerPageableObject extends FingerExpandObject implements IFingerPageableObject{

	protected int pageSize = 10;
	
	private String table;
	private String pk;
	private String sql = null;
	
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
	public <T> List<T> page(int pageNum, int pageSize) {
		StringBuffer sqlBuilder = new StringBuffer(this.sql);
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
	public <T> List<T> page(int pageNum) {
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
		this.sql = sql;
	}

	
	@Override
	public void setTableNameAndPrimaryKey(String tableName, String pk) {
		super.setTableNameAndPrimaryKey(tableName, pk);
		this.table = tableName;
		this.pk = pk;
		
		sql = String.format("SELECT * FROM `%s` ORDER BY %s ", this.table,this.pk);
	}
	
	
	
}

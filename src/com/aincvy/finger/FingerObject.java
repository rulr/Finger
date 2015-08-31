package com.aincvy.finger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aincvy.finger.cache.FingerCache;
import com.aincvy.finger.cache.FingerCacheClass;
import com.aincvy.finger.inf.IFingerEntity;
import com.aincvy.finger.inf.IFingerObject;

/**
 * Finger对象   <p>
 * 使用Finger的DAO对象都应该继承自本类 <p>
 * @author World
 * @version alpha 0.0.7
 * @since JDK 1.7
 */
public abstract class FingerObject implements IFingerObject{
	
	protected String table;
	//类型的属性名
	protected List<String> fields;
	//数据库里的字段名
	protected List<String> dataFields;
	protected String pk;

	protected FingerObject() {
		fields = new ArrayList<>();
		dataFields = new ArrayList<>();
	}
	/**
	 * 如果你的数据库字段名和 属性名不一样 ，可以使用  field => dataField 的方式赋值
	 * field 表示类的属性名，  dataField 则表示数据库的字段名
	 * @param str
	 * @return
	 */
	public List<String> setFieldList(String str){
		this.fields.clear();
		this.dataFields.clear();
		String []array = str.split(",");
		for (String string : array) {
			if (string.contains("=>")) {
				String[] tmp = string.split("=>");
				if (tmp.length == 2 && !tmp[0].equals("") && !tmp[1].equals("")) {
					fields.add(tmp[0].trim());
					dataFields.add(tmp[1].trim());
					continue;
				}
			}
			this.fields.add(string);
			this.dataFields.add(string);
		}
		this.fields = Arrays.asList(str.split(","));
		return this.fields;
	}

	@Override
	public int insert(IFingerEntity entity) {
		return insert(FingerBus.newConnection(), entity);
	}
	
	@Override
	public int insert(IFingerEntity entity, boolean insertPK) {
		return insert(FingerBus.newConnection(), entity, insertPK);
	}
	
	public int insert(Connection con,IFingerEntity entity) {
		return insert(con, entity, false);
	}
	
	@Override
	public int insert(Connection con, IFingerEntity entity, boolean insertPK) {
		Object []param = new Object[this.fields.size()];
		StringBuffer qString = new StringBuffer();
		StringBuffer fieldList = new StringBuffer();
		FingerCacheClass fcs = FingerCache.getCacheClass(entity.getClass());
		try {
			for (int i = 0; i < this.fields.size(); i++) {
				if (insertPK) {
					if (this.pk != null) {
						if (this.pk.equals(this.fields.get(i))) {
							continue;
						}
					}else{
						FingerUtils.debug("表" + this.table + "并没有设置主键，所以无法插入主键");
					}
				}
				param[i] = fcs.getGetMethod(this.fields.get(i)).invoke(entity);
				qString.append("?,");
				fieldList.append(this.dataFields.get(i) + ",");
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if (qString.length() > 0) {
			qString.setLength(qString.length()-1);
		}
		if (fieldList.length() > 0) {
			fieldList.setLength(fieldList.length() -1 );
		}
		String sql = String.format("INSERT INTO `%s`(%s) VALUES(%s)", this.table, fieldList,qString);
		return queryUpdate(con,sql, param);
	}

	@Override
	public int update(Object id, IFingerEntity entity) {
		return update(FingerBus.newConnection(), id, entity,true);
	}
	
	@Override
	public int update(IFingerEntity entity) {
		return update(pkValue(entity), entity);
	}
	
	@Override
	public int update(Connection con, Object id, IFingerEntity entity, boolean flag) {
		StringBuffer sqlBuffer = new StringBuffer("UPDATE `" + this.table + "` SET ");
		Object[] param = new Object[this.fields.size() + 1];
		FingerCacheClass fcs = FingerCache.getCacheClass(entity.getClass());
		try {
			for (int i = 0; i < param.length-1; i++) {
				param[i] = fcs.getGetMethod(fields.get(i)).invoke(entity);
				sqlBuffer.append(String.format("`%s`=?,", dataFields.get(i)));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (this.fields.size() > 0) {
			sqlBuffer.setLength(sqlBuffer.length() - 1);
		}
		sqlBuffer.append(String.format(" WHERE %s=?", this.pk));
		param[this.fields.size()] = id;
//		System.out.println(sqlBuffer);
//		System.out.println(Arrays.toString(param));
		return queryUpdate(con,sqlBuffer.toString(),flag, param);
	}

	@Override
	public int delete(Object id) {
		return delete(FingerBus.newConnection(), id);
	}

	@Override
	public int delete(Connection con, Object id) {
		return queryUpdate(con,String.format("DELETE FROM `%s` WHERE `%s`=?", this.table,this.pk), id);
	}

	@Override
	public <T> T fetchFirst() {
		return fetchFirst(null);
	}

	/**
	 * 查询， 结果返还一个 List<Map<String,Object>> 类型  <br/>
	 * Map<String,Object> 代表一条数据行， String 为列名，  Object为值　<br/>
	 * List 表示好多个数据行
	 * @param sql SQL 语句
	 * @param param
	 * @return
	 */
	public final List<Map<String, Object>> query(String sql,Object ...param){
		FingerUtils.debug("Exec Sql: " + sql);
		Connection con = FingerBus.newConnection();
		PreparedStatement pstat = null;
		ResultSet rSet = null;
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			pstat = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				pstat.setObject(i+1, param[i]);
			}
			rSet = pstat.executeQuery();
			List<String> columns = new ArrayList<>();
			for (int i = 1; i <= rSet.getMetaData().getColumnCount(); i++) {
				columns.add(rSet.getMetaData().getColumnLabel(i));
			}
			//FingerUtils.debug("resultColumns: " + columns);
			//System.out.println("resultColumns: "+ columns);
			while(rSet.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				//改变算法
				for (String item : columns) {
					map.put(item, rSet.getObject(item));
				}
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (rSet != null && !rSet.isClosed()) {
					rSet.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstat != null && !pstat.isClosed()) {
					pstat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null && !con.isClosed()) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
		}
		return list;
	}
	
	
	
	public int queryUpdate(String sql,Object ...param){
		return queryUpdate(FingerBus.newConnection(), sql, param);
	}
	
	public int queryUpdate(Connection con,String sql,Object ...param){
		return queryUpdate(con, sql, true, param);
	}
	
	/**
	 * 
	 * @param con
	 * @param sql 
	 * @param flag 执行完sql 语句，是否关闭连接  true: 关闭， false: 不关闭 <br/>
	 *   注，此处只是针对链接
	 * @param param
	 * @return
	 */
	public int queryUpdate(Connection con,String sql, boolean flag,Object ...param){
		FingerUtils.debug("Exec Sql: " + sql);
		
		PreparedStatement pstat = null;
		try {
			pstat = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				pstat.setObject(i+1, param[i]);
			}
			return pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (pstat != null && !pstat.isClosed()) {
					pstat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (flag) {
					if (con != null && !con.isClosed()) {
						con.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return 0;
	}

	
	protected Object pkValue(IFingerEntity entity) {
		Class<? extends IFingerEntity> claxx = entity.getClass();
		try {
			return FingerCache.getCacheClass(claxx).getGetMethod(this.pk).invoke(entity);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	@Override
	public <T> T fetch(Object id) {
		String sql = String.format("SELECT * FROM `%s` WHERE `%s`=?", this.table,this.pk);
		List<Map<String, Object>> list = query(sql,id);
		if (list.size() <= 0) {
			return null;
		}
		Map<String, Object> map = list.get(0);
		Class<?> claxx = FingerBus.getEntityClass(this.getClass());
		FingerCacheClass fcs = FingerCache.getCacheClass(claxx);
		T entity = null;
		try {
			entity = fcs.newInstance();
			for (String item : this.fields) {
				fcs.getSetMethod(item).invoke(entity, map.get(item));
//				FingerUtils.getSetMethod(claxx, FingerUtils.setMethodName(item)).invoke(entity, map.get(item));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public <T> List<T> fetchTable() {
		return executeQuery(String.format("SELECT * FROM `%s`", this.table));
	}
	
	

	@Override
	public <T> T fetchFirst(String condition) {
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("SELECT * FROM `");
		sqlBuilder.append(this.table);
		sqlBuilder.append("`");
		if (condition != null) {
			sqlBuilder.append(" WHERE ");
			sqlBuilder.append(condition);
		}
		sqlBuilder.append(" LIMIT 1");
		List<T> list = executeQuery(sqlBuilder.toString());
		if (list == null || list.size() <= 0) {
			return null;
		}
		
		return list.get(0);
	}

	@Override
	public <T> List<T> executeQuery(String sql) {
		FingerUtils.debug("Exec Sql: " + sql);
		List<Map<String, Object>> list = query(sql);
		if (list.size() <= 0) {
			return null;
		}
		//FingerUtils.debug(list.toString());
		List<T> ret = new ArrayList<>();
		for (Map<String, Object> map : list) {
			Class<?> claxx = FingerBus.getEntityClass(this.getClass());
			FingerCacheClass fcs = FingerCache.getCacheClass(claxx);
			T entity = null;
			try {
				entity = fcs.newInstance();
				for (String item : this.fields) {
					fcs.getSetMethod(item).invoke(entity, map.get(item));
//					FingerUtils.getSetMethod(claxx, FingerUtils.setMethodName(item)).invoke(entity, map.get(item));
				}
				ret.add( entity);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	@Override
	public int count() {
		List<Map<String, Object>> list = query(String.format("SELECT count(1) as count FROM `%s`", this.table));
		if (list == null || list.size() <= 0) {
			return 0;
		}
		return new Integer(String.valueOf(list.get(0).get("count")));
	}

	
	
}

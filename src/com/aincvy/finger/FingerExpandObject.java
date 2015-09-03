package com.aincvy.finger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.aincvy.finger.cache.FingerCache;
import com.aincvy.finger.cache.FingerCacheClass;
import com.aincvy.finger.inf.IFingerDataRow;
import com.aincvy.finger.inf.IFingerDataTable;
import com.aincvy.finger.inf.IFingerEntity;
import com.aincvy.finger.inf.IFingerExpandObject;

/**
 * 可拓展 FingerObject
 * @author World
 * @version alpha 0.0.6
 * @since JDK 1.7
 *
 */
public class FingerExpandObject<T extends IFingerEntity> extends FingerBatchObject<T> implements IFingerExpandObject<T>{

	@Override
	public IFingerDataTable exQuery(String sql, Object... params) {
		FingerUtils.debug("Exec Sql: " + sql);
		Connection con = FingerBus.newConnection();
		PreparedStatement pstat = null;
		ResultSet rSet = null;
		IFingerDataTable t = new FingerDataTable();
		try {
			pstat = con.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstat.setObject(i+1, params[i]);
			}
			rSet = pstat.executeQuery();
			ResultSetMetaData rsmd = rSet.getMetaData();
			int columnCount = rsmd.getColumnCount();
			if (columnCount <= 0) {
				return t;
			}

			List<String> columns = new ArrayList<>();
			for (int i = 1; i <= rSet.getMetaData().getColumnCount(); i++) {
				//t.addColumn(rsmd.getColumnLabel(i));
				columns.add(rsmd.getColumnLabel(i));
			}
			t.setTableName(rsmd.getTableName(1));
			t.setColumns(columns);
			
			while(rSet.next()){
				IFingerDataRow row = new FingerDataRow(t);
				for (String item : columns) {
					row.add(rSet.getObject(item));
				}
				t.add(row);
			}
			return t;
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
		return t;
	}

	@Override
	public List<T> exQuery(Class<T> claxx, String rule, String sql,
			Object... params) {
		List<String> fields = new ArrayList<>();
		List<String> dataFields = new ArrayList<>();
		parseRule(rule, fields, dataFields);
		
		Connection con = FingerBus.newConnection();
		PreparedStatement pstat = null;
		ResultSet rSet = null;
		
		List<T> list = new ArrayList<>();
		FingerCacheClass fcc = FingerCache.getCacheClass(claxx);
		try {
			pstat = con.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				pstat.setObject(i+1, params[i]);
			}
			rSet = pstat.executeQuery();
			while(rSet.next()){
				T t = claxx.newInstance();
				for (int i = 0; i < fields.size(); i++) {
					String tmp = fields.get(i);
					Method method = fcc.getSetMethod(tmp);
					Object value = rSet.getObject(dataFields.get(i));
					if (method == null) {
						FingerUtils.debug(String.format("不能为类%s的属性%s设置值为： %s", fcc.getCachedClass().getName(),tmp,value));
						continue;
					}
					method.invoke(t, value);
				}
				list.add(t);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
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
		
		return null;
	}

	@Override
	public int exInsert(String rule, T entity, int returnBack) {
		
		return 0;
	}

	@Override
	public int exTransactionUpdate(int tid, T entity, String rule) {
		
		return 0;
	}

	// 规则使用
	
	
	
}

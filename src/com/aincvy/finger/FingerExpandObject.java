package com.aincvy.finger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
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
 * @version alpha 0.0.9
 * @since JDK 1.7
 *
 */
public class FingerExpandObject<T extends IFingerEntity> extends FingerBatchObject<T> implements IFingerExpandObject<T>{

	// 规则使用
	
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
		if (rule != null) {
			parseRule(rule, fields, dataFields);
		}else{
			fields = this.fields;
			dataFields = this.dataFields;
		}
		
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

	@SuppressWarnings("resource")
	@Override
	public int exInsert(String rule, T entity, int returnBack) {
		List<String> fields = new ArrayList<>();
		List<String> dataFields = new ArrayList<>();
		if (rule != null) {
			parseRule(rule, fields, dataFields);
		}else{
			fields = this.fields;
			dataFields = this.dataFields;
		}
		
		
		Connection con = FingerBus.newConnection();
		PreparedStatement pstat = null;
		
		Object []param = new Object[this.fields.size()];
		StringBuffer qString = new StringBuffer();
		StringBuffer fieldList = new StringBuffer();
		FingerCacheClass fcs = FingerCache.getCacheClass(entity.getClass());
		try {
			for (int i = 0; i < fields.size(); i++) {
				String fieldString = fields.get(i);
				Method method = fcs.getGetMethod(fieldString);
				if (method == null) {
					method = fcs.getIsMethod(fieldString);
					if (method == null) {
						FingerUtils.debug(String.format("并不能获取到类%s的属性%s，因为该属性的getter并没有找到，这可能会产生一个错误", fcs.getCachedClass().getName(),fieldString));
						continue;
					}
				}
				
				param[i] = method.invoke(entity);
				qString.append("?,");
				fieldList.append(dataFields.get(i) + ",");
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
		
		//执行查询
		int ret = 0;
		try {
			if (returnBack == FingerBus.RESULT_RETURN_INSERT_ID) {
				pstat = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			}else if (returnBack == FingerBus.RESULT_RETURN_UPDATE_NUMS) {
				pstat = con.prepareStatement(sql);
			}
			
			
			for (int i = 0; i < param.length; i++) {
				pstat.setObject(i+1, param[i]);
			}
			ret = pstat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (returnBack == FingerBus.RESULT_RETURN_INSERT_ID) {
			try {
				ResultSet rSet = pstat.getGeneratedKeys();
				if (rSet.next()) {
					ret = rSet.getInt(1);
				}
				rSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		try {
			pstat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public int exTransactionUpdate(int tid, T entity, String rule) {
		if (rule == null) {
			throw new FingerRuntimeException("规则说明不能为null,如果不想使用规则说明，请使用transactionUpdate 方法 ");
		}
		
		List<String> fields = new ArrayList<>();
		List<String> dataFields = new ArrayList<>();
		parseRule(rule, fields, dataFields);
		
		StringBuffer sqlBuffer = new StringBuffer("UPDATE `" + this.table + "` SET ");
		Object[] param = new Object[fields.size() + 1];
		FingerCacheClass fcs = FingerCache.getCacheClass(entity.getClass());
		try {
			for (int i = 0; i < param.length-1; i++) {
				String fieldString = fields.get(i);
				Method method = fcs.getGetMethod(fieldString);
				if (method == null) {
					method = fcs.getIsMethod(fieldString);
					if (method == null) {
						FingerUtils.debug(String.format("并不能获取到类%s的属性%s，因为该属性的getter并没有找到，这可能会产生一个错误", fcs.getCachedClass().getName(),fieldString));
						continue;
					}
				}
				param[i] = method.invoke(entity);
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
		param[fields.size()] = pkValue(entity);

		Connection con = null;
		if (tid == -1) {
			con = FingerBus.newConnection();
		}else{
			con = getConnection(tid);
		}
		int ret = 0;
		try {
			String sql = sqlBuffer.toString();
			FingerUtils.debug("exTransactionUpdate Sql: " + sql);
			PreparedStatement pstat = con.prepareStatement(sql);
			
			for (int i = 0; i < param.length; i++) {
				pstat.setObject(i+1, param[i]);
			}
			
			ret = pstat.executeUpdate();
			
			pstat.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (tid == -1) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}

	
	
}

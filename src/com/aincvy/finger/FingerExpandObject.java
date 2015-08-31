package com.aincvy.finger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.aincvy.finger.inf.IFingerDataTable;
import com.aincvy.finger.inf.IFingerEntity;
import com.aincvy.finger.inf.IFingerExpandObject;

/**
 * 可拓展 FingerObject
 * @author World
 * @version alpha 0.0.2
 * @since JDK 1.7
 *
 */
public class FingerExpandObject extends FingerBatchObject implements IFingerExpandObject{

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
			if (rSet.getMetaData().getColumnCount() <= 0) {
				return t;
			}

			t.setTableName(rSet.getMetaData().getTableName(1));
			
			while(rSet.next()){
				
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
	public <T> List<T> exQuery(Class<T> claxx, String rule, String sql,
			Object... params) {
		return null;
	}

	@Override
	public int exInsert(String rule, IFingerEntity entity, int returnBack) {
		return 0;
	}

	// 规则使用
	
	
	
}

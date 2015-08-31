package com.aincvy.finger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.aincvy.finger.inf.IFingerEntity;
import com.aincvy.finger.inf.IFingerTransactionObject;

/**
 * 具有事务能力的FingerObject <p>
 * @author World
 * @version alpha 0.0.3
 * @since JDK 1.7
 */
public class FingerTransactionObject extends FingerObject implements IFingerTransactionObject{

	private Map<Integer, Connection> connectionMap = null;
	private int lastId = 0;
	
	public FingerTransactionObject() {
		connectionMap = Collections.synchronizedMap(new HashMap<Integer, Connection>());
	}
	
	@Override
	public int beginTransaction() {
		Connection con = FingerBus.newConnection();
		int tid = lastId++;
		try {
			con.setAutoCommit( false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connectionMap.put(tid, con);
		return tid;
	}

	@Override
	public void commit(int tid) {
		if (connectionMap.containsKey(tid)) {
			try {
				Connection con = connectionMap.remove(tid);
				if (con == null) {
					throw new FingerRuntimeException("无法提交事务，因为给定的事务id(tid) 不存在");
				}
				con.commit();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	@Override
	public void rollback(int tid) {
		if (connectionMap.containsKey(tid)) {
			try {
				Connection con = connectionMap.remove(tid);
				if (con == null) {
					throw new FingerRuntimeException("无法对事务进行回滚，因为给定的事务id(tid)不存在");
				}
				con.rollback();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private Connection getConnection(int tid) {
		if (!connectionMap.containsKey(tid)) {
			throw new FingerRuntimeException("给定的事务id有误，请检查");
		}
		return connectionMap.get(tid);
	}
	
	@Override
	public int transactionInsert(int tid, IFingerEntity entity) {
		return insert(getConnection(tid), entity);
	}

	@Override
	public int transactionUpdate(int tid, IFingerEntity entity) {
		return update(getConnection(tid), pkValue(entity), entity,false);
	}

	@Override
	public int transactionDelete(int tid, Object id) {
		return delete(getConnection(tid), id);
	}
	
	
	
}

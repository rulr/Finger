package com.aincvy.finger;

import java.util.Arrays;
import java.util.List;

import com.aincvy.finger.inf.IFingerBatchObject;
import com.aincvy.finger.inf.IFingerEntity;

/**
 * 批处理 Finger 对象  <p>
 * @author World
 * @version alpha 0.0.4
 * @since JDK 1.7
 */
public abstract class FingerBatchObject<T extends IFingerEntity> extends FingerTransactionObject<T> implements IFingerBatchObject<T>{

	@Override
	public int batchInsert(List<T> entities) {
		int ret = 0;
		for (T entity : entities) {
			ret += insert(entity);
		}
		return ret;
	}
	
	@Override
	public int batchDelete(List<Object> ids) {
		int ret = 0;
		for (Object id : ids) {
			ret += delete(id);
		}
		return ret;
	}

	@Override
	public int batchUpdate(List<T> entities) {
		int ret =  0;
		for (T entity : entities) {
			ret += update(entity);
		}
		return ret;
	}

	@Override
	public int batchInsert(T[] entities) {
		return batchInsert(Arrays.asList(entities));
	}

	@Override
	public int batchDelete(Object[] ids) {
		return batchDelete(Arrays.asList(ids));
	}

	@Override
	public int batchUpdate(T[] entities) {
		return batchUpdate(Arrays.asList(entities));
	}

	
}

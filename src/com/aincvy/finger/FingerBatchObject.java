package com.aincvy.finger;

import java.util.Arrays;
import java.util.List;

import com.aincvy.finger.inf.IFingerBatchObject;
import com.aincvy.finger.inf.IFingerEntity;

/**
 * 批处理 Finger 对象  <p>
 * @author World
 * @version alpha 0.0.2
 * @since JDK 1.7
 */
public class FingerBatchObject extends FingerTransactionObject implements IFingerBatchObject{

	@Override
	public int batchInsert(List<? extends IFingerEntity> entities) {
		int ret = 0;
		for (IFingerEntity entity : entities) {
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
	public int batchUpdate(List<? extends IFingerEntity> entities) {
		int ret =  0;
		for (IFingerEntity entity : entities) {
			ret += update(entity);
		}
		return ret;
	}

	@Override
	public int batchInsert(IFingerEntity[] entities) {
		return batchInsert(Arrays.asList(entities));
	}

	@Override
	public int batchDelete(Object[] ids) {
		return batchDelete(Arrays.asList(ids));
	}

	@Override
	public int batchUpdate(IFingerEntity[] entities) {
		return batchUpdate(Arrays.asList(entities));
	}

	
}

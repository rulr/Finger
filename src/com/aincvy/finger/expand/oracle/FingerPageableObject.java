package com.aincvy.finger.expand.oracle;

import java.util.List;

import com.aincvy.finger.FingerExpandObject;
import com.aincvy.finger.FingerRuntimeException;
import com.aincvy.finger.inf.IFingerEntity;
import com.aincvy.finger.inf.IFingerPageableObject;

/**
 * 带有排序功能的 <p>
 * 针对于Oracle进行实现
 * @author World
 * @version alpha 0.0.3
 * @since JDK 1.7
 */
public class FingerPageableObject<T extends IFingerEntity> extends FingerExpandObject<T> implements IFingerPageableObject<T>{

	@Override
	public Object previousId(Object id) {
		throw new FingerRuntimeException("本方法未实现");
		//return null;
	}

	@Override
	public Object nextId(Object id) {
		throw new FingerRuntimeException("本方法未实现");
		//return null;
	}

	@Override
	public void setPageSize(int pageSize) {
		throw new FingerRuntimeException("本方法未实现");
	}

	@Override
	public List<T> page(int pageNum, int pageSize) {
		throw new FingerRuntimeException("本方法未实现");
		//return null;
	}

	@Override
	public List<T> page(int pageNum) {
		throw new FingerRuntimeException("本方法未实现");
		//return null;
	}

	@Override
	public int totalPageNum() {
		throw new FingerRuntimeException("本方法未实现");
		//return 0;
	}

	@Override
	public int totalPageNum(int pageSize) {
		throw new FingerRuntimeException("本方法未实现");
		//return 0;
	}

	@Override
	public void setPageSql(String sql) {
		throw new FingerRuntimeException("本方法未实现");
	}

}

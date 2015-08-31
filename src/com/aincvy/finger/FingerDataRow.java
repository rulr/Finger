package com.aincvy.finger;

import java.util.ArrayList;

import com.aincvy.finger.inf.IFingerDataRow;
import com.aincvy.finger.inf.IFingerDataTable;

/**
 * Finger 数据行
 * @author World
 * @version alpha 0.0.2
 * @since JDK 1.7
 */
public class FingerDataRow extends ArrayList<Object> implements IFingerDataRow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object get(String column) {
		return null;
	}

	@Override
	public IFingerDataTable getTable() {
		return null;
	}

	@Override
	public int getRowIndex() {
		return 0;
	}
	
}

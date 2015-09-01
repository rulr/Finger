package com.aincvy.finger;

import java.util.ArrayList;

import com.aincvy.finger.inf.IFingerDataRow;
import com.aincvy.finger.inf.IFingerDataTable;

/**
 * Finger 数据行
 * @author World
 * @version alpha 0.0.3
 * @since JDK 1.7
 */
public class FingerDataRow extends ArrayList<Object> implements IFingerDataRow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IFingerDataTable table;
	//private int rowIndex;
	
	public FingerDataRow(IFingerDataTable table) {
		this.table = table;
		//this.rowIndex = rowIndex;
	}

	@Override
	public Object get(String column) {
		int i = table.getColumns().indexOf(column);
		if (i == -1) {
			return null;
		}
		return get(i);
	}

	@Override
	public IFingerDataTable getTable() {
		return this.table;
	}

	@Override
	public int getRowIndex() {
		//return this.rowIndex;
		return this.table.indexOf(this);
	}
	
}

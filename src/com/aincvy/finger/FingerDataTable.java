package com.aincvy.finger;

import java.util.ArrayList;
import java.util.List;

import com.aincvy.finger.inf.IFingerDataRow;
import com.aincvy.finger.inf.IFingerDataTable;

/**
 * Finger 数据表  <p>
 * 
 * @author World
 * @version alpha 0.0.3
 * @since JDK 1.7
 * 
 */
public class FingerDataTable extends ArrayList<IFingerDataRow> implements IFingerDataTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tableName;
	private List<String> columns;
	

	@Override
	public String getTableName() {
		return tableName;
	}
	
	
	public void setTableName(String name){
		this.tableName = name;
	}


	@Override
	public List<String> getColumns() {
		return columns;
	}


	@Override
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}


	@Override
	public String addColumn(String name) {
		if (this.columns.add(name)) {
			return name;
		}
		return null;
	}


	@Override
	public int getColumnNum() {
		return columns.size();
	}

	@Override
	public String getColumnName(int index) {
		return columns.get(index);
	}


	@Override
	public int getColumnIndex(String name) {
		return columns.indexOf(name);
	}
	

}

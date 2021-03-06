package dataBase;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Represents a basic table that can sort by values 
 * We can get any data and save them as table
 */
public class TableModel extends AbstractTableModel {
	private static final long serialVersionUID = -2591792764959317565L;

	private String column[] = {"First ID","Second ID","Third ID","Log Time","Points","Map Name"};
	private ArrayList<Object[]> data;  // Data to present

	public TableModel(ArrayList<Object[]> data) {
		this.data = data;
	}

	@Override
	public String getColumnName(int column) {
		return this.column[column];
	}

	@Override
	public int getColumnCount() {
		return column.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data.get(row)[col];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

}
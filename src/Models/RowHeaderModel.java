package Models;

import javax.swing.table.AbstractTableModel;

public class RowHeaderModel extends AbstractTableModel {
    private final int rowCount;

    public RowHeaderModel(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return 1; // Only one column for row numbers
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowIndex + 1; // Display row index starting from 1
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // Row header cells are not editable
    }
}

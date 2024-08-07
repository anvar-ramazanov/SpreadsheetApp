package Models;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Map;

public class SpreadsheetModel extends AbstractTableModel {
    private final int rowCount;
    private final int columnCount;
    // private final Object[][] data;
    private final Map<String, CellModel> cells;

    public SpreadsheetModel(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        //this.data = new Object[rowCount][columnCount];
        this.cells = new HashMap<>();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    private String getCellName(int rowIndex, int columnIndex) {
        return String.format("%s%d", (char)('A' + columnIndex), rowIndex+1);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var cellName = getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName))  {
            return null;
        }
        return cells.get(cellName).ShowValue;
    }

    public void setShowValueAt(Object value, int rowIndex, int columnIndex)  {
        var cellName = getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName))
        {
            var cell = new CellModel();
            cell.ShowValue = (String)value;
            cells.put(cellName, cell);
        } else {
            var cell = cells.get(cellName);
            cell.ShowValue = value.toString();
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        var cellName = getCellName(rowIndex, columnIndex);
        System.out.println("changing cell: " + cellName);
        if (!cells.containsKey(cellName))
        {
            var cell = new CellModel();
            cell.Value = value;
            cell.ShowValue = (String)value;
            cells.put(cellName, cell);
        } else {
            var cell = cells.get(cellName);
            cell.Value = value;
            cell.ShowValue = value.toString();
        }

        //data[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public HashMap<String, Object> getVariables() {
        //
        var model = new HashMap<String, Object>();
        cells.forEach((s, cellModel) -> {
            model.put(s, cellModel.Value);
        });
        return model;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true; // All cells are editable
    }
}

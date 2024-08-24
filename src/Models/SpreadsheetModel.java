package Models;

import Helpers.CellHelpers;
import Models.Cell.CellModel;
import Models.Cell.ExpressionCell;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Map;

public class SpreadsheetModel extends AbstractTableModel {
    private final int rowCount;
    private final int columnCount;

    private final Map<String, CellModel> cells;

    public SpreadsheetModel(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @SuppressWarnings("unchecked")
    public Map<String, ExpressionCell> getExpressionCells() {
        return (Map<String, ExpressionCell>)(Map<?, ?>)cells;
    }

    public CellModel getCell(String cellName) {
        if (!cells.containsKey(cellName)) {
            return null;
        }
        return cells.get(cellName);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var cellName = CellHelpers.getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName)) {
            return null;
        }
        return cells.get(cellName).showValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        var cellName = CellHelpers.getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName))
        {
            var cell = new CellModel();
            cell.value = value;
            cell.showValue = (String)value;
            cells.put(cellName, cell);
        } else {
            var cell = cells.get(cellName);
            cell.value = value;
            cell.showValue = value.toString();
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public String getErrorAt(int rowIndex, int columnIndex) {
        var cellName = CellHelpers.getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName))  {
            return null;
        }
        return cells.get(cellName).errorText;
    }
}

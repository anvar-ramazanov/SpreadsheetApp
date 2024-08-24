package Models;

import Formulas.Expressions.ExpressionNode;
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var cellName = CellHelpers.getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName)) {
            return null;
        }
        return cells.get(cellName).showValue;
    }

    public CellModel getCell(String cellName) {
        if (!cells.containsKey(cellName)) {
            return null;
        }
        return cells.get(cellName);
    }

    public String getRealValueAt(int rowIndex, int columnIndex) {
        var cellName = CellHelpers.getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName))  {
            return null;
        }
        return cells.get(cellName).value.toString();
    }

    public String getErrorAt(int rowIndex, int columnIndex) {
        var cellName = CellHelpers.getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName))  {
            return null;
        }
        return cells.get(cellName).errorText;
    }

    public void setErrorTextTo(String cellName, String errorText) {
        if (!cells.containsKey(cellName))  {
            return;
        }
        var cell = cells.get(cellName);
        cell.errorText = errorText;
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

    public void setCell(String cellName, ExpressionNode expression, Object showValue)
    {
        updateCellShowValue(cellName, showValue);
        if (expression != null) {
            if (cells.containsKey(cellName)) {
                var cell = cells.get(cellName);
                cell.setExpression(expression);
            }
        }
    }

    public void updateCellShowValue(String cellName, Object showValue) {
        if (!cells.containsKey(cellName))
        {
            var cell = new CellModel();
            cell.showValue = (String)showValue;
            cells.put(cellName, cell);
        } else {
            var cell = cells.get(cellName);
            cell.showValue = showValue.toString();
        }
    }
}

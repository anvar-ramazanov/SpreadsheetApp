package Models;

import Formulas.Expressions.ExpressionNode;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SpreadsheetModel extends AbstractTableModel {
    private final int rowCount;
    private final int columnCount;

    private final Map<String, CellModel> cells;
    private final Map<String, ExpressionNode> expressionNodeMap;
    private final Map<String, HashSet<String>> nodeRelations;

    public SpreadsheetModel(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.cells = new HashMap<>();
        this.expressionNodeMap = new HashMap<>();
        this.nodeRelations = new HashMap<>();
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    public String getCellName(int rowIndex, int columnIndex) {
        return String.format("%s%d", (char)('A' + columnIndex), rowIndex + 1);
    }

    public Map<String, ExpressionNode> getExpressionNodeMap() {
        return this.expressionNodeMap;
    }

    public void setChildNode(String parentNode, String childNode) {
        if (!this.nodeRelations.containsKey(parentNode)) {
            this.nodeRelations.put(parentNode, new HashSet<>());

        }
        this.nodeRelations.get(parentNode).add(childNode);
    }

    public HashSet<String> getChildNodes(String node) {
        if (this.nodeRelations.containsKey(node)) {
            return this.nodeRelations.get(node);
        }
        return null;
    }

    public void removeChildNode(String parentNode, String childNode) {
        if (this.nodeRelations.containsKey(parentNode)) {
            this.nodeRelations.get(parentNode).remove(childNode);
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        var cellName = getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName))  {
            return null;
        }
        return cells.get(cellName).ShowValue;
    }

    public String getRealValueAt(int rowIndex, int columnIndex) {
        var cellName = getCellName(rowIndex, columnIndex);
        if (!cells.containsKey(cellName))  {
            return null;
        }
        return cells.get(cellName).Value.toString();
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

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void setCell(String cellName, ExpressionNode expression, Object showValue)
    {
        updateCellShowValue(cellName, showValue);
        this.expressionNodeMap.put(cellName, expression);
    }

    public void updateCellShowValue(String cellName, Object showValue) {
        if (!cells.containsKey(cellName))
        {
            var cell = new CellModel();
            cell.ShowValue = (String)showValue;
            cells.put(cellName, cell);
        } else {
            var cell = cells.get(cellName);
            cell.ShowValue = showValue.toString();
        }
    }



    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}

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
    private final Map<String, HashSet<String>> nodeRaltions;

    public SpreadsheetModel(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.cells = new HashMap<>();
        this.expressionNodeMap = new HashMap<>();
        this.nodeRaltions = new HashMap<>();
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
        return String.format("%s%d", (char)('A' + columnIndex), rowIndex+1);
    }

    public Map<String, ExpressionNode> getExpressionNodeMap() {
        return this.expressionNodeMap;
    }

    public void setExpressionNode(String name, ExpressionNode node) {
        this.expressionNodeMap.put(name, node);
    }

    public void setChildNode(String parentNode, String childNode) {
        if (!this.nodeRaltions.containsKey(parentNode)) {
            this.nodeRaltions.put(parentNode, new HashSet<>());

        }
        this.nodeRaltions.get(parentNode).add(childNode);
    }

    public HashSet<String> getChildNodes(String node) {
        if (this.nodeRaltions.containsKey(node)) {
            return this.nodeRaltions.get(node);
        }
        return null;
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
            var cell = new CellModel(); // fixme excessive allocation
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

    public int[] getCellAddress(String cellName) {
        // Extract the column part (first character)
        char columnChar = cellName.charAt(0);

        // Extract the row part (remaining substring after the first character)
        String rowString = cellName.substring(1);

        // Convert column character to index
        int columnIndex = columnChar - 'A';

        // Convert row string to index (and subtract 1 to make it zero-based)
        int rowIndex = Integer.parseInt(rowString) - 1;

        return new int[] { rowIndex,  columnIndex};
    }



    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}

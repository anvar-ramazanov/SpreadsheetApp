package Views;

import Models.RowHeaderModel;
import Models.SpreadsheetModel;
import Models.SpreadsheetModelFabric;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class SpreadsheetView {
    private final JFrame frame;
    private final JTable table;

    private final SpreadsheetModel model;

    public SpreadsheetView(SpreadsheetModelFabric spreadsheetModelFabric) {
        this.model = spreadsheetModelFabric.getSpreadsheetModel();

        this.table = initMainTable();
        var rowHeaderTable = initRowHeaderTable();

        // Add both tables to a scroll pane
        JScrollPane scrollPane = new JScrollPane(this.table);
        scrollPane.setRowHeaderView(rowHeaderTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeaderTable.getTableHeader());

        // Create and set up the window
        var frame = new JFrame("TableApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);

        this.frame = frame;
    }

    private JTable initMainTable() {
        var table = new JTable(model) {
            // It would be better to have that code in controller, but I don't know how to do it
            public String getToolTipText(MouseEvent e) {
                String tooltipText = null;
                java.awt.Point p = e.getPoint();
                int rowIndex = rowAtPoint(p);
                int columnIndex = columnAtPoint(p);

                try {
                    tooltipText = model.getErrorAt(rowIndex, columnIndex);
                } catch (RuntimeException exception) {
                    //catch null pointer exception if mouse is over an empty line
                }
                return tooltipText;
            }
        };

        // Set custom renderer for main table
        table.setDefaultRenderer(Object.class, new CustomCellRenderer());
        table.getTableHeader().setReorderingAllowed(false);

        for (int i = 0; i < table.getColumnCount(); i++) {
            var column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(50);
            column.setMinWidth(50);
            column.setResizable(true);
        }

        return table;
    }

    private JTable initRowHeaderTable() {
        RowHeaderModel rowHeaderModel = new RowHeaderModel(this.model.getRowCount());
        JTable rowHeaderTable = new JTable(rowHeaderModel);

        // Customize row header table
        rowHeaderTable.setPreferredScrollableViewportSize(new Dimension(40, 0));
        rowHeaderTable.setRowHeight(table.getRowHeight());
        rowHeaderTable.setEnabled(false);
        var rowHeaderTableCellRenderer = new CustomCellRenderer();
        rowHeaderTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        rowHeaderTableCellRenderer.setBorder(CellBorders.getDefaultBorder());
        rowHeaderTable.setDefaultRenderer(Object.class, rowHeaderTableCellRenderer);
        rowHeaderTable.getColumnModel().getColumn(0).setHeaderValue("");

        return rowHeaderTable;
    }

    public void show() {
        this.frame.setVisible(true);
    }

    public JTable getTable() {
        return this.table;
    }
}



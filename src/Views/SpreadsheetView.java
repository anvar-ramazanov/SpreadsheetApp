package Views;

import Models.RowHeaderModel;
import Models.SpreadsheetModel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.EventObject;

public class SpreadsheetView {
    private final JFrame frame;
    private final JTable table;

    public SpreadsheetView(SpreadsheetModel model) {

        this.table = new JTable(model);

        // Set custom renderer for main table
        table.setDefaultRenderer(Object.class, new CustomCellRenderer());
        table.getTableHeader().setReorderingAllowed(false);


        for (int i = 0; i < table.getColumnCount(); i++) {
            var column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(50);
            column.setMinWidth(50);
            column.setMaxWidth(50);
            column.setResizable(true);
        }

        RowHeaderModel rowHeaderModel = new RowHeaderModel(model.getRowCount());
        JTable rowHeaderTable = new JTable(rowHeaderModel);

        // Customize row header table
        var border = BorderFactory.createMatteBorder(0, 0, 1, 1,  Color.LIGHT_GRAY);

        rowHeaderTable.setPreferredScrollableViewportSize(new Dimension(40, 0));
        rowHeaderTable.setRowHeight(table.getRowHeight());
        rowHeaderTable.setEnabled(false);
        var rowHeaderTableCellRenderer = new CustomCellRenderer();
        rowHeaderTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        rowHeaderTableCellRenderer.setBorder(border);
        rowHeaderTable.setDefaultRenderer(Object.class, rowHeaderTableCellRenderer);
        rowHeaderTable.getColumnModel().getColumn(0).setHeaderValue("");

        // Ensure row selection is synchronized
        ListSelectionModel selectionModel = table.getSelectionModel();
        rowHeaderTable.setSelectionModel(selectionModel);

        // Add both tables to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setRowHeaderView(rowHeaderTable);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowHeaderTable.getTableHeader());

        // Create and set up the window
        frame = new JFrame("TableApp");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }

    public JTable getTable() {
        return table;
    }
}



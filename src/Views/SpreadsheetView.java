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

//        table.getSelectionModel().addListSelectionListener(e -> {
//            int row = table.getSelectedRow();
//            int col = table.getSelectedColumn();
//            System.out.println("Editor started at row " + row + ", column " + col);
//        });

//        table.getDefaultEditor(String.class).addCellEditorListener(new javax.swing.event.CellEditorListener() {
//            @Override
//            public void editingStopped(javax.swing.event.ChangeEvent e) {
//                int row = table.getSelectedRow();
//                int col = table.getSelectedColumn();
//                if (row >= 0 && col >= 0) {
//                    System.out.println("Editing stopped at row " + row + ", column " + col);
//                }
//            }
//
//            @Override
//            public void editingCanceled(javax.swing.event.ChangeEvent e) {
//                // Handle editing canceled
//            }
//        });



        for (int i = 0; i < table.getColumnCount(); i++) {
            var column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(50);
            column.setMinWidth(50);
            column.setMaxWidth(50);
        }

        RowHeaderModel rowHeaderModel = new RowHeaderModel(model.getRowCount());
        JTable rowHeaderTable = new JTable(rowHeaderModel);

        // Customize row header table
        rowHeaderTable.setPreferredScrollableViewportSize(new Dimension(40, 0));
        rowHeaderTable.setRowHeight(table.getRowHeight());
        rowHeaderTable.setEnabled(false);
        var rowHeaderTableCellRenderer = new CustomCellRenderer();
        rowHeaderTableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        rowHeaderTable.setDefaultRenderer(Object.class, rowHeaderTableCellRenderer);
        rowHeaderTable.getColumnModel().getColumn(0).setHeaderValue(""); // FIXME


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



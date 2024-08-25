package Views;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (isSelected) {
            c.setBackground(table.getBackground());
            c.setForeground(table.getForeground());
        } else {
            c.setBackground(Color.WHITE);
            c.setForeground(Color.BLACK);
        }
        if (hasFocus) {
            ((JComponent) c).setBorder(CellBorders.getFocusBorder());
        } else {
            setBorder(CellBorders.getDefaultBorder());
        }

        setHorizontalAlignment(SwingConstants.CENTER);

        return c;
    }
}

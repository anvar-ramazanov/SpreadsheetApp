package Views;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    private final Border border = BorderFactory.createMatteBorder(0, 0, 1, 1,  Color.LIGHT_GRAY);
    private final Border focusBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE);

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
            ((JComponent) c).setBorder(focusBorder);
        } else {
            setBorder(border);
        }

        return c;
    }
}

package Views;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    private final Border border = BorderFactory.createMatteBorder(0, 0, 1, 1,  Color.LIGHT_GRAY);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        ((JComponent) c).setBorder(border);

        return c;
    }
}

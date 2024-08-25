package Views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CellBorders {
    private final static Border defaultBorder = BorderFactory.createMatteBorder(0, 0, 1, 1,  Color.LIGHT_GRAY);
    private final static Border focusBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE);
    private final static Border editorBorder = BorderFactory.createLineBorder(Color.BLUE, 2);

    public static Border getDefaultBorder() {
        return defaultBorder;
    }

    public static Border getFocusBorder() {
        return focusBorder;
    }

    public static Border getEditorBorder() {
        return editorBorder;
    }
}

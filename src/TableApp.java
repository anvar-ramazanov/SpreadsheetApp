import Controller.SpreadsheetController;
import Models.SpreadsheetModel;
import Views.SpreadsheetView;

import javax.swing.*;

public class TableApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpreadsheetModel model = new SpreadsheetModel(50, 26);
            SpreadsheetView view = new SpreadsheetView(model);
            new SpreadsheetController(model, view);
            view.show();
        });
    }
}

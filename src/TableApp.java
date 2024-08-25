import Controller.SpreadsheetController;
import Views.SpreadsheetView;
import org.picocontainer.PicoContainer;

import javax.swing.*;

public class TableApp {

    public static void main(String[] args) {
        final PicoContainer pico = TableAppModule.newContainer();

        SwingUtilities.invokeLater(() -> {
            SpreadsheetController controller = pico.getComponent(SpreadsheetController.class);
            SpreadsheetView view = controller.getView();
            view.show();
        });
    }
}

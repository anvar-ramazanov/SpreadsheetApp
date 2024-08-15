package Controller;

import Formulas.Expressions.ExpressionTreeParser;
import Formulas.Tokens.Tokenizer;
import Models.SpreadsheetModel;
import Views.SpreadsheetView;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class SpreadsheetController {
    private final SpreadsheetModel model;
    private final SpreadsheetView view;

    public SpreadsheetController(SpreadsheetModel model, SpreadsheetView view) {
        this.model = model;
        this.view = view;

        // Add listeners to handle user actions
        view.getTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (e.getType() == TableModelEvent.UPDATE) {
                    Object newData = model.getValueAt(row, column);
                    System.out.println("Cell at (" + row + ", " + column + ") changed to: " + newData);
                    // Add additional logic if needed

                    if (newData == null) {
                        return;
                    }
                    var newDataStr = newData.toString();
                    if (newDataStr.charAt(0) == '=')  {
                        var tokenizer = new Tokenizer();
                        var tokens = tokenizer.tokenize(newDataStr.substring(1));
                        var formulaAst = new ExpressionTreeParser(tokens);
                        var node = formulaAst.parse();
                        var val = node.evaluate(model.getVariables());
                        model.setShowValueAt(val, row, column);
                    }

                }
            }
        });
    }
}

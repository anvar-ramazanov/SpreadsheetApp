package Controller;

import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionTreeEvaluator;
import Formulas.Expressions.ExpressionTreeParser;
import Formulas.Tokens.Tokenizer;
import Models.SpreadsheetModel;
import Views.SpreadsheetView;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.text.DecimalFormat;

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
                    if (newDataStr.equals("")) {
                        return;
                    }

                    if (newDataStr.charAt(0) == '=')  {
                        newDataStr = newDataStr.substring(1);
                    }
                    var tokenizer = new Tokenizer();
                    var tokens = tokenizer.tokenize(newDataStr);
                    var expression = new ExpressionTreeParser();
                    var node = expression.parse(tokens);
                    var cellName = model.getCellName(row, column);
                    model.setExpressionNode(cellName, node);
                    var context = model.getExpressionNodeMap();

                    var expressionAnalyzer = new ExpressionTreeAnalyzer();
                    try {
                        expressionAnalyzer.AnalyzeExpressionTree(cellName, context);
                        var expressionEvaluator = new ExpressionTreeEvaluator();
                        var val = expressionEvaluator.EvaluateExpressionTree(cellName, context);
                        if (val instanceof Double doubleValue) {
                            DecimalFormat decimalFormat = new DecimalFormat("#.#");
                            String formattedValue = decimalFormat.format(doubleValue);
                            model.setShowValueAt(formattedValue, row, column);
                        } else {
                            model.setShowValueAt(val.toString(), row, column);
                        }
                    }
                    catch (RuntimeException exception) {
                        model.setShowValueAt("ERROR", row, column);
                    }
                }
            }
        });
    }
}

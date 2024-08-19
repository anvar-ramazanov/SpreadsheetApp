package Controller;

import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionNodes.StringNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionTreeEvaluator;
import Formulas.Expressions.ExpressionTreeParser;
import Formulas.Expressions.ExpressionTreeWalker;
import Formulas.Tokens.Tokenizer;
import Formulas.Tokens.TokenizerHelpers;
import Models.SpreadsheetModel;
import Views.SpreadsheetView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.beans.PropertyChangeEvent;
import java.text.DecimalFormat;
import java.util.List;

public class SpreadsheetController {
    private final SpreadsheetModel model;
    private final SpreadsheetView view;

    public SpreadsheetController(SpreadsheetModel model, SpreadsheetView view) {
        this.model = model;
        this.view = view;

        var table = view.getTable();
        table.getModel().addTableModelListener(this::onTableChanged);
        table.addPropertyChangeListener("tableCellEditor", this::onShowTableCellEditor);
    }

    private void onShowTableCellEditor(PropertyChangeEvent propertyChangeEvent) {
        var table = view.getTable();
        if (table.isEditing()) {
            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();
            System.out.println("Editor started at row " + row + ", column " + column);
            var realValue = this.model.getRealValueAt(row, column);
            if (realValue != null && !realValue.isEmpty()){
                if (realValue.charAt(0) == '=') {
                    var source = (DefaultCellEditor) propertyChangeEvent.getNewValue();
                    System.out.println(source.getClass());
                    var component = (JTextField)source.getComponent();
                    component.setText(realValue);
                }
            }
        }

    }

    public void onTableChanged(TableModelEvent tableModelEvent) {
        int row = tableModelEvent.getFirstRow();
        int column = tableModelEvent.getColumn();
        var tabelModelEvent = tableModelEvent.getType();
        if (tabelModelEvent == TableModelEvent.UPDATE) {
            Object newData = this.model.getValueAt(row, column);
            updateCell(row, column, newData);
        }
    }

    private void updateCell(int row, int column, Object newData) {
        if (newData == null) {
            return;
        }
        var newDataStr = newData.toString();
        if (newDataStr.isEmpty()) {
            return;
        }

        System.out.println("Start updating cell at (" + row + ", " + column + ") to have value: " + newDataStr);

        var cellName = model.getCellName(row, column);

        if (newDataStr.charAt(0) == '=') {
            newDataStr = newDataStr.substring(1);
            var tokenizer = new Tokenizer();
            var tokens = tokenizer.tokenize(newDataStr);
            var expressionTreeParser = new ExpressionTreeParser();
            var node = expressionTreeParser.parse(tokens);
            model.setExpressionNode(cellName, node);

            var context = model.getExpressionNodeMap();

            var expressionTreeAnalyzer = new ExpressionTreeAnalyzer();

            var expressionTreeWalker = new ExpressionTreeWalker();
            var dependedNodes = expressionTreeWalker.GetDependedNodes(cellName, context);

            for (var dependedNode:dependedNodes) {
                model.setChildNode(dependedNode, cellName);
            }

            try {
                expressionTreeAnalyzer.AnalyzeExpressionTree(cellName, context);
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
                System.out.println("Cell at (" + row + ", " + column + ") has formula with error: " + exception.getMessage());
                model.setShowValueAt("ERROR", row, column);
            }

        } else if (TokenizerHelpers.isNumeric(newDataStr)) {
            var doubleValue = Double.parseDouble(newDataStr);
            var numericNode = new NumberNode(doubleValue);
            model.setExpressionNode(cellName, numericNode);

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String formattedValue = decimalFormat.format(doubleValue);
            model.setShowValueAt(formattedValue, row, column);

        } else if(TokenizerHelpers.isBoolean(newDataStr)) {

            var booleanValue = Boolean.parseBoolean(newDataStr);
            var booleanNode = new BooleanNode(booleanValue);
            model.setExpressionNode(cellName, booleanNode);
            model.setShowValueAt(booleanValue, row, column);

        } else {
            var stringNode = new StringNode(newDataStr);
            model.setExpressionNode(cellName, stringNode);
            model.setShowValueAt(newDataStr, row, column);
        }

        var childNodes = model.getChildNodes(cellName);
        if (childNodes != null) {
            for (var childNode:childNodes) {
                var adress = model.getCellAddress(childNode);
                updateCell(adress[0], adress[1], model.getRealValueAt(adress[0], adress[1]));
                model.fireTableCellUpdated(adress[0], adress[1]);
            }
        }
    }
}

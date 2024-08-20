package Controller;

import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionNodes.StringNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionTreeEvaluator;
import Formulas.Expressions.ExpressionTreeParser;
import Formulas.Tokens.ITokenizer;
import Formulas.Tokens.Tokenizer;
import Formulas.Tokens.TokenizerHelpers;
import Helpers.StringHelpers;
import Models.SpreadsheetModel;
import Views.SpreadsheetView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.beans.PropertyChangeEvent;
import java.text.DecimalFormat;
import java.util.HashSet;

public class SpreadsheetController {
    private final SpreadsheetModel model;
    private final SpreadsheetView view;

    private final ITokenizer tokenizer;
    private final ExpressionTreeParser expressionTreeParser;
    private final ExpressionTreeAnalyzer expressionTreeAnalyzer;
    private final ExpressionTreeEvaluator expressionTreeEvaluator;

    public SpreadsheetController(SpreadsheetModel model, SpreadsheetView view) {
        this.model = model;
        this.view = view;

        this.tokenizer = new Tokenizer();
        this.expressionTreeParser = new ExpressionTreeParser();
        this.expressionTreeAnalyzer = new ExpressionTreeAnalyzer();
        this.expressionTreeEvaluator = new ExpressionTreeEvaluator();

        var table = view.getTable();
        table.getModel().addTableModelListener(this::onTableChanged);
        table.addPropertyChangeListener("tableCellEditor", this::onShowTableCellEditor);
    }

    private void onShowTableCellEditor(PropertyChangeEvent propertyChangeEvent) {
        var table = view.getTable();
        if (table.isEditing()) {
            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();
            var realValue = this.model.getRealValueAt(row, column);
            if (realValue != null && !realValue.isEmpty()){
                if (realValue.charAt(0) == '=') {
                    var source = (DefaultCellEditor) propertyChangeEvent.getNewValue();
                    var component = (JTextField)source.getComponent();
                    component.setText(realValue);
                }
            }
        }

    }

    public void onTableChanged(TableModelEvent tableModelEvent) {
        int row = tableModelEvent.getFirstRow();
        int column = tableModelEvent.getColumn();
        var tableModelEventType = tableModelEvent.getType();
        if (tableModelEventType == TableModelEvent.UPDATE) {
            Object newValue = this.model.getValueAt(row, column);
            updateCell(row, column, newValue);
        }
    }

    private void updateCell(int row, int column, Object newValue) {
        if (newValue == null) {
            return;
        }
        var newValueStr = newValue.toString();
        if (newValueStr.isEmpty()) {
            return;
        }

        System.out.println("Updating cell at (" + row + ", " + column + ") to have value: " + newValueStr);

        var cellName = model.getCellName(row, column);

        if (newValueStr.charAt(0) == '=') {

            HashSet<String> oldDependencies = null;
            if (model.getExpressionNodeMap().containsKey(cellName)){
                oldDependencies = model.getExpressionNodeMap().get(cellName).getDependencies();
            }

            newValueStr = newValueStr.substring(1);
            var tokens = this.tokenizer.tokenize(newValueStr);
            var node = this.expressionTreeParser.parse(tokens);

            var context = model.getExpressionNodeMap();

            try {
                expressionTreeAnalyzer.AnalyzeExpressionTree(cellName, context);
                var newShowValue = this.expressionTreeEvaluator.EvaluateExpressionTree(cellName, context);
                if (newShowValue instanceof Double doubleValue) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    String formattedValue = decimalFormat.format(doubleValue);
                    model.setExpressionNode(cellName, node);
                    model.setShowValueAt(formattedValue, row, column);
                } else {
                    model.setExpressionNode(cellName, node);
                    model.setShowValueAt(newShowValue.toString(), row, column);
                }
            }
            catch (RuntimeException exception) {
                System.out.println("Cell at (" + row + ", " + column + ") has formula with error: " + exception.getMessage());
                model.setShowValueAt("ERROR", row, column);
            }

            if (oldDependencies != null) {
                for (var oldDependency: oldDependencies) {
                    if (!node.getDependencies().contains(oldDependency)) {
                        model.removeChildNode(oldDependency, cellName);
                    }
                }
            }
            var newDependencies = node.getDependencies();
            for (var dependedNode: newDependencies) {
                if (!dependedNode.equals(cellName)) {
                    model.setChildNode(dependedNode, cellName);
                }
            }

        } else if (StringHelpers.isNumeric(newValueStr)) {
            var doubleValue = Double.parseDouble(newValueStr);
            var numericNode = new NumberNode(doubleValue);

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String formattedValue = decimalFormat.format(doubleValue);
            model.setShowValueAt(formattedValue, row, column);
            model.setExpressionNode(cellName, numericNode);

        } else if(StringHelpers.isBoolean(newValueStr)) {

            var booleanValue = Boolean.parseBoolean(newValueStr);
            var booleanNode = new BooleanNode(booleanValue);
            model.setShowValueAt(booleanValue, row, column);
            model.setExpressionNode(cellName, booleanNode);

        } else {
            var stringNode = new StringNode(newValueStr);
            model.setShowValueAt(newValueStr, row, column);
            model.setExpressionNode(cellName, stringNode);
        }

        var childNodes = model.getChildNodes(cellName);
        if (childNodes != null) {
            for (var childNode:childNodes) {
                recalculateCell(childNode);
            }
        }
        model.fireTableDataChanged();
    }

    private void recalculateCell(String cellName) {

        System.out.println("Recalculating cell " + cellName);

        var address = model.getCellAddress(cellName);
        var row = address[0];
        var column = address[1];

        var context = model.getExpressionNodeMap();

        try {
            expressionTreeAnalyzer.AnalyzeExpressionTree(cellName, context);
            var newShowValue = this.expressionTreeEvaluator.EvaluateExpressionTree(cellName, context);
            if (newShowValue instanceof Double doubleValue) {
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                String formattedValue = decimalFormat.format(doubleValue);
                model.setShowValueAt(formattedValue, row, column);
            } else {
                model.setShowValueAt(newShowValue.toString(), row, column);
            }
        }
        catch (RuntimeException exception) {
            System.out.println("Cell at (" + row + ", " + column + ") has formula with error: " + exception.getMessage());
            model.setShowValueAt("ERROR", row, column);
        }

        var childNodes = model.getChildNodes(cellName);
        if (childNodes != null) {
            for (var childNode:childNodes) {
                recalculateCell(childNode);
            }
        }
    }
}

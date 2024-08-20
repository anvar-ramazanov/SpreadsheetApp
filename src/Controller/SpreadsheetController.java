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

        System.out.println("Start updating cell at (" + row + ", " + column + ") to have value: " + newValueStr);

        var cellName = model.getCellName(row, column);

        if (newValueStr.charAt(0) == '=') {

            HashSet<String> oldDependencies = null;
            if (model.getExpressionNodeMap().containsKey(cellName)){
                oldDependencies = model.getExpressionNodeMap().get(cellName).getDependencies();
            }

            newValueStr = newValueStr.substring(1);
            var tokens = this.tokenizer.tokenize(newValueStr);
            var node = this.expressionTreeParser.parse(tokens);
            model.setExpressionNode(cellName, node);

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

            if (oldDependencies != null) {
                for (var oldDependency: oldDependencies) {
                    if (!node.getDependencies().contains(oldDependency)) {
                        model.removeChildNode(oldDependency, cellName);
                    }
                }
            }
            for (var dependedNode: node.getDependencies()) {
                if (!dependedNode.equals(cellName)) {
                    model.setChildNode(dependedNode, cellName);
                }
            }

        } else if (TokenizerHelpers.isNumeric(newValueStr)) {
            var doubleValue = Double.parseDouble(newValueStr);
            var numericNode = new NumberNode(doubleValue);
            model.setExpressionNode(cellName, numericNode);

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String formattedValue = decimalFormat.format(doubleValue);
            model.setShowValueAt(formattedValue, row, column);

        } else if(TokenizerHelpers.isBoolean(newValueStr)) {

            var booleanValue = Boolean.parseBoolean(newValueStr);
            var booleanNode = new BooleanNode(booleanValue);
            model.setExpressionNode(cellName, booleanNode);
            model.setShowValueAt(booleanValue, row, column);

        } else {
            var stringNode = new StringNode(newValueStr);
            model.setExpressionNode(cellName, stringNode);
            model.setShowValueAt(newValueStr, row, column);
        }

        var childNodes = model.getChildNodes(cellName);
        if (childNodes != null) {
            for (var childNode:childNodes) {
                var adress = model.getCellAddress(childNode);
                updateCell(adress[0], adress[1], model.getRealValueAt(adress[0], adress[1])); // fixme should be only analyze and recalc - no parse
                model.fireTableCellUpdated(adress[0], adress[1]); // fixme prerhaps a bug because we updating one cell twice
            }
        }
    }
}

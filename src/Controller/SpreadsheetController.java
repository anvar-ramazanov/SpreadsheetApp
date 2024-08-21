package Controller;

import Formulas.Exceptions.Evaluators.ExpressionTreeEvaluatorException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.ExpressionTreeAnalyzerException;
import Formulas.Exceptions.Expressions.TreeParser.ExpressionTreeParserException;
import Formulas.Exceptions.Tokenizer.TokenizerException;
import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionNodes.StringNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionTreeEvaluator;
import Formulas.Expressions.ExpressionTreeParser;
import Formulas.Tokens.ITokenizer;
import Formulas.Tokens.Tokenizer;
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
            onUpdateCell(row, column, newValue);
        }
    }

    private void onUpdateCell(int row, int column, Object newValue) {
        if (newValue == null) {
            return;
        }
        var newValueStr = newValue.toString();

        var cellName = model.getCellName(row, column);

        System.out.println("Updating cell at " + cellName + " to have value: " + newValueStr);

        if (!newValueStr.isEmpty() &&  newValueStr.charAt(0) == '=') {

            HashSet<String> oldDependencies = null;
            if (model.getExpressionNodeMap().containsKey(cellName)){
                oldDependencies = model.getExpressionNodeMap().get(cellName).getDependencies();
            }

            newValueStr = newValueStr.substring(1);
            Object newShowValue;
            ExpressionNode node = null;

            try {
                var tokens = this.tokenizer.tokenize(newValueStr);
                node = this.expressionTreeParser.parse(tokens);

                var context = model.getExpressionNodeMap();

                expressionTreeAnalyzer.AnalyzeExpressionTree(node, cellName, context);

                newShowValue = this.expressionTreeEvaluator.EvaluateExpressionTree(node, context);
                if (newShowValue instanceof Double doubleValue) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    newShowValue = decimalFormat.format(doubleValue);
                }
            }
            catch (TokenizerException exception) {
                var errorText = "Error during parsing formula: " + exception.getMessage(); // even if it tokenizer to customer we will say it was parsing
                model.setErrorTextTo(cellName, errorText);
                System.out.println("Cell " + cellName + " has error during tokenizing: " + exception.getMessage());
                newShowValue = "ERROR";
            }
            catch (ExpressionTreeParserException exception){
                var errorText = "Error during parsing formula: " + exception.getMessage();
                model.setErrorTextTo(cellName, errorText);
                System.out.println("Cell " + cellName + " has error during parsing: " + exception.getMessage());
                newShowValue = "ERROR";
            }
            catch (ExpressionTreeAnalyzerException exception) {
                var errorText = "Problem with formula: " + exception.getMessage();
                model.setErrorTextTo(cellName, errorText);
                System.out.println("Cell " + cellName + " has error during analyzing: " + exception.getMessage());
                newShowValue = "REF!";
            }
            catch (ExpressionTreeEvaluatorException exception) {
                var errorText = "Problem with formula: " + exception.getMessage();
                model.setErrorTextTo(cellName, errorText);
                System.out.println("Cell " + cellName + " has error during evaluation: " + exception.getMessage());
                newShowValue = "REF!";
            }
            model.setCell(cellName, node, newShowValue.toString());

            if (node != null) {
                if (oldDependencies != null) {
                    for (var oldDependency : oldDependencies) {
                        if (!node.getDependencies().contains(oldDependency)) {
                            model.removeChildNode(oldDependency, cellName);
                        }
                    }
                }
                var newDependencies = node.getDependencies();
                for (var dependedNode : newDependencies) {
                    if (!dependedNode.equals(cellName)) {
                        model.setChildNode(dependedNode, cellName);
                    }
                }
            }

        } else if (StringHelpers.isNumeric(newValueStr)) {
            var doubleValue = Double.parseDouble(newValueStr);
            var numericNode = new NumberNode(doubleValue);

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String formattedValue = decimalFormat.format(doubleValue);
            model.setCell(cellName, numericNode, formattedValue);

        } else if(StringHelpers.isBoolean(newValueStr)) {
            var booleanValue = Boolean.parseBoolean(newValueStr);
            var booleanNode = new BooleanNode(booleanValue);
            model.setCell(cellName, booleanNode, booleanValue);

        } else {
            var stringNode = new StringNode(newValueStr);
            model.setCell(cellName, stringNode, newValueStr);
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

        var expressionNode  = model.getExpressionNodeMap().get(cellName);
        var context = model.getExpressionNodeMap();

        try {
            expressionTreeAnalyzer.AnalyzeExpressionTree(expressionNode, cellName, context);
            var newShowValue = this.expressionTreeEvaluator.EvaluateExpressionTree(expressionNode, context);
            if (newShowValue instanceof Double doubleValue) {
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                String formattedValue = decimalFormat.format(doubleValue);
                model.updateCellShowValue(cellName, formattedValue);
            } else {
                model.updateCellShowValue(cellName, newShowValue.toString());
            }
        }
        catch (ExpressionTreeAnalyzerException exception) {
            var errorText = "Problem with formula: " + exception.getMessage();
            model.setErrorTextTo(cellName, errorText);
            System.out.println("Cell " + cellName + " has error during analyzing: " + exception.getMessage());
            model.updateCellShowValue(cellName, "REF!");
        }
        catch (ExpressionTreeEvaluatorException exception) {
            var errorText = "Problem with formula: " + exception.getMessage();
            model.setErrorTextTo(cellName, errorText);
            System.out.println("Cell " + cellName + " has error during evaluation: " + exception.getMessage());
            model.updateCellShowValue(cellName, "REF!");
        }

        var childNodes = model.getChildNodes(cellName);
        if (childNodes != null) {
            for (var childNode:childNodes) {
                recalculateCell(childNode);
            }
        }
    }
}

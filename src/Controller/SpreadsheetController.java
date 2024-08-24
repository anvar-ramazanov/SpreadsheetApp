package Controller;

import Formulas.Exceptions.Evaluators.ExpressionTreeEvaluatorException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.ExpressionTreeAnalyzerException;
import Formulas.Exceptions.Expressions.TreeParser.ExpressionTreeParserException;
import Formulas.Exceptions.Tokenizer.TokenizerException;
import Formulas.Expressions.*;
import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionNodes.StringNode;
import Formulas.Tokens.Tokenizer;
import Formulas.Tokens.TokenizerImpl;
import Helpers.StringHelpers;
import Models.SpreadsheetModel;
import Views.SpreadsheetView;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import java.beans.PropertyChangeEvent;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.logging.Logger;

public class SpreadsheetController {
    private final SpreadsheetModel model;
    private final SpreadsheetView view;

    private final Tokenizer tokenizer;
    private final ExpressionTreeParser expressionTreeParser;
    private final ExpressionTreeAnalyzer expressionTreeAnalyzer;
    private final ExpressionTreeEvaluator expressionTreeEvaluator;

    private final Logger logger;

    private final static String ErrorFormulaParsingText = "ERROR";
    private final static String ErrorFormulaText = "REF!";

    public SpreadsheetController(SpreadsheetModel model, SpreadsheetView view) {
        this.model = model;
        this.view = view;

        this.tokenizer = new TokenizerImpl();
        this.expressionTreeParser = new ExpressionTreeParserImpl();
        this.expressionTreeAnalyzer = new ExpressionTreeAnalyzerImpl();
        this.expressionTreeEvaluator = new ExpressionTreeEvaluatorImpl();

        this.logger = Logger.getLogger(SpreadsheetController.class.getName());

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
        var cell = model.getCell(cellName);

        logger.info("Updating cell " + cellName + " to have value: " + newValueStr);

        if (!newValueStr.isEmpty() &&  newValueStr.charAt(0) == '=') {

            var context = model.getExpressionCells();

            HashSet<String> oldChildCells = cell.getChildCells();

            newValueStr = newValueStr.substring(1);
            Object newShowValue;
            ExpressionNode node = null;

            try {
                var tokens = this.tokenizer.tokenize(newValueStr);
                node = this.expressionTreeParser.parse(tokens);

                expressionTreeAnalyzer.AnalyzeExpressionTree(node, cellName, context);

                newShowValue = this.expressionTreeEvaluator.EvaluateExpressionTree(node, context);
                if (newShowValue instanceof Double doubleValue) {
                    DecimalFormat decimalFormat = new DecimalFormat("#.#");
                    newShowValue = decimalFormat.format(doubleValue);
                }
            }
            catch (TokenizerException exception) {
                logger.severe("Cell " + cellName + " has error during tokenizing: " + exception.getMessage());
                var errorText = "Error during parsing formula: " + exception.getMessage(); // even if it tokenizer to customer we will say it was parsing
                model.setErrorTextTo(cellName, errorText);
                newShowValue = ErrorFormulaParsingText;
            }
            catch (ExpressionTreeParserException exception){
                var errorText = "Error during parsing formula: " + exception.getMessage();
                logger.severe("Cell " + cellName + " has error during parsing: " + exception.getMessage());
                model.setErrorTextTo(cellName, errorText);
                newShowValue = ErrorFormulaParsingText;
            }
            catch (ExpressionTreeAnalyzerException exception) {
                var errorText = "Problem with formula: " + exception.getMessage();
                model.setErrorTextTo(cellName, errorText);
                logger.severe("Cell " + cellName + " has error during analyzing: " + exception.getMessage());
                newShowValue = ErrorFormulaText;
            }
            catch (ExpressionTreeEvaluatorException exception) {
                var errorText = "Problem with formula: " + exception.getMessage();
                model.setErrorTextTo(cellName, errorText);
                logger.severe("Cell " + cellName + " has error during evaluation: " + exception.getMessage());
                newShowValue = ErrorFormulaText;
            }
            model.setCell(cellName, node, newShowValue.toString());

            if (node != null) {
                if (oldChildCells != null) {
                    for (var oldChildCell : oldChildCells) {
                        if (!node.getDependencies().contains(oldChildCell)) {
                            cell.removeChildCell(oldChildCell);
                        }
                    }
                }
                var newDependencies = node.getDependencies();
                for (var dependedNode : newDependencies) {
                    if (model.getCell(dependedNode) != null) {
                        // reversing logic
                        model.getCell(dependedNode).setChildCell(cellName);
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

        var childNodes = cell.getChildCells();
        if (childNodes != null) {
            for (var childNode:childNodes) {
                recalculateCell(childNode);
            }
        }
        model.fireTableDataChanged();
    }

    private void recalculateCell(String cellName) {

        logger.info("Recalculating cell " + cellName);

        var cell = model.getCell(cellName);

        var context = model.getExpressionCells();
        var expression = context.get(cellName).getExpression();

        try {
            expressionTreeAnalyzer.AnalyzeExpressionTree(expression, cellName, context);
            var newShowValue = this.expressionTreeEvaluator.EvaluateExpressionTree(expression, context);
            if (newShowValue instanceof Double doubleValue) {
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                String formattedValue = decimalFormat.format(doubleValue);
                model.updateCellShowValue(cellName, formattedValue);
            } else {
                model.updateCellShowValue(cellName, newShowValue.toString());
            }

            var childNodes = cell.getChildCells();
            if (childNodes != null) {
                for (var childNode:childNodes) {
                    recalculateCell(childNode);
                }
            }
        }
        catch (ExpressionTreeAnalyzerException exception) {
            var errorText = "Problem with formula: " + exception.getMessage();
            model.setErrorTextTo(cellName, errorText);
            model.updateCellShowValue(cellName, ErrorFormulaText);
            logger.severe("Cell " + cellName + " has error during analyzing: " + exception.getMessage());
        }
        catch (ExpressionTreeEvaluatorException exception) {
            var errorText = "Problem with formula: " + exception.getMessage();
            model.setErrorTextTo(cellName, errorText);
            model.updateCellShowValue(cellName, ErrorFormulaText);
            logger.severe("Cell " + cellName + " has error during evaluation: " + exception.getMessage());
        }
    }
}

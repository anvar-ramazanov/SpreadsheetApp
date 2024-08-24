package Controller;

import Formulas.Exceptions.Evaluators.ExpressionTreeEvaluatorException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.CircularDependencyException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.ExpressionTreeAnalyzerException;
import Formulas.Exceptions.Expressions.TreeParser.ExpressionTreeParserException;
import Formulas.Exceptions.Tokenizer.TokenizerException;
import Formulas.Expressions.*;
import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionNodes.StringNode;
import Formulas.Tokens.Tokenizer;
import Formulas.Tokens.TokenizerImpl;
import Helpers.CellHelpers;
import Helpers.StringHelpers;
import Models.Cell.CellModel;
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

    private final DecimalFormat decimalFormat;

    public SpreadsheetController(SpreadsheetModel model, SpreadsheetView view) {
        this.model = model;
        this.view = view;

        this.tokenizer = new TokenizerImpl();
        this.expressionTreeParser = new ExpressionTreeParserImpl();
        this.expressionTreeAnalyzer = new ExpressionTreeAnalyzerImpl();
        this.expressionTreeEvaluator = new ExpressionTreeEvaluatorImpl();

        this.logger = Logger.getLogger(SpreadsheetController.class.getName());

        this.decimalFormat = new DecimalFormat("#.#");

        var table = view.getTable();
        table.getModel().addTableModelListener(this::onTableChanged);
        table.addPropertyChangeListener("tableCellEditor", this::onShowTableCellEditor);
    }

    private void onShowTableCellEditor(PropertyChangeEvent propertyChangeEvent) {
        var table = view.getTable();
        if (table.isEditing()) {
            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();
            var cellName = CellHelpers.getCellName(row, column);
            var cell = this.model.getCell(cellName);
            if (cell != null && cell.value instanceof String realValue) {
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
            if (newValue == null) {
                return;
            }
            fullUpdateCell(row, column, newValue.toString());
        }
    }

    private void fullUpdateCell(int row, int column, String newValue) {
        var cellName = CellHelpers.getCellName(row, column);
        var cell = model.getCell(cellName);

        logger.info("Updating cell " + cellName + " to have value: " + newValue);

        if (!newValue.isEmpty() && newValue.charAt(0) == '=') {
            updateCellWithFormula(cellName, cell, newValue.substring(1));
        } else if (StringHelpers.isNumeric(newValue)) {
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            var doubleValue = Double.parseDouble(newValue);
            cell.setExpression(new NumberNode(doubleValue));
            cell.showValue = decimalFormat.format(doubleValue);
        } else if(StringHelpers.isBoolean(newValue)) {
            cell.showValue = newValue;
            var booleanValue = Boolean.parseBoolean(newValue);
            cell.setExpression(new BooleanNode(booleanValue));
        } else {
            cell.showValue = newValue;
            cell.setExpression(new StringNode(newValue));
        }

        var childCells = cell.getChildCells();
        if (childCells != null) {
            for (var childCell : childCells) {
                recalculateCell(childCell);
            }
        }

        model.fireTableDataChanged();
    }

    private void updateCellWithFormula(String cellName, CellModel cell, String newExpressionText) {
        HashSet<String> oldChildCells = cell.getExpression().getParentCells();

        ExpressionNode expression = null;

        try {
            var tokens = this.tokenizer.tokenize(newExpressionText);

            expression = this.expressionTreeParser.parse(tokens);

            var context = model.getExpressionCells();

            expressionTreeAnalyzer.AnalyzeExpressionTree(expression, cellName, context);

            cell.setExpression(expression);

            var newShowValue = this.expressionTreeEvaluator.EvaluateExpressionTree(expression, context);
            if (newShowValue instanceof Double doubleValue) {
                cell.showValue = this.decimalFormat.format(doubleValue);
            } else {
                cell.showValue = newShowValue.toString();
            }

            if (oldChildCells != null) {
                for (var oldChildCell : oldChildCells) {
                    if (!expression.getParentCells().contains(oldChildCell)) {
                        model.getCell(oldChildCell).removeChildCell(cellName);
                    }
                }
            }
            var newDependencies = expression.getParentCells();
            for (var dependedNode : newDependencies) {
                if (model.getCell(dependedNode) != null) {
                    model.getCell(dependedNode).setChildCell(cellName);
                }
            }
        }
        catch (TokenizerException exception) {
            logger.severe("Cell " + cellName + " has error during tokenizing: " + exception.getMessage());
            cell.showValue  = ErrorFormulaParsingText;
            cell.errorText = "Error during parsing formula: " + exception.getMessage(); // even if it tokenizer to customer we will say it was parsing
        }
        catch (ExpressionTreeParserException exception){
            logger.severe("Cell " + cellName + " has error during parsing: " + exception.getMessage());
            cell.showValue = ErrorFormulaParsingText;
            cell.errorText = "Error during parsing formula: " + exception.getMessage();
        }
        catch (ExpressionTreeAnalyzerException exception) {
            logger.severe("Cell " + cellName + " has error during analyzing: " + exception.getMessage());
            cell.showValue = ErrorFormulaText;
            cell.errorText = "Problem with formula: " + exception.getMessage();
            if (exception instanceof CircularDependencyException circularDependencyException) {
                var visitedCells = circularDependencyException.getVisitedCells();
                if (visitedCells != null) {
                    for (var visitedCell : visitedCells) {
                        var c = model.getCell(visitedCell); // fixme naming!
                        c.showValue = ErrorFormulaText;
                        c.errorText = "Problem with formula: " + exception.getMessage();
                    }
                }
            }
        }
        catch (ExpressionTreeEvaluatorException exception) {
            logger.severe("Cell " + cellName + " has error during evaluation: " + exception.getMessage());
            cell.showValue = ErrorFormulaText;
            cell.errorText = "Problem with formula: " + exception.getMessage();
        }
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
                cell.showValue = this.decimalFormat.format(doubleValue);
            } else {
                cell.showValue = newShowValue.toString();
            }

            var childCells = cell.getChildCells();
            if (childCells != null) {
                for (var childCell : childCells) {
                    recalculateCell(childCell);
                }
            }
        }
        catch (ExpressionTreeAnalyzerException exception) {
            cell.errorText = "Problem with formula: " + exception.getMessage();
            cell.showValue = ErrorFormulaText;
            logger.severe("Cell " + cellName + " has error during analyzing: " + exception.getMessage());
            if (exception instanceof CircularDependencyException circularDependencyException) {
                var visitedCells = circularDependencyException.getVisitedCells();
                if (visitedCells != null) {
                    for (var visitedCell : visitedCells) {
                        var c = model.getCell(visitedCell); // fixme naming!
                        c.showValue = ErrorFormulaText;
                        c.errorText = "Problem with formula: " + exception.getMessage();
                    }
                }
            }
        }
        catch (ExpressionTreeEvaluatorException exception) {
            cell.errorText = "Problem with formula: " + exception.getMessage();
            cell.showValue = ErrorFormulaText;
            logger.severe("Cell " + cellName + " has error during evaluation: " + exception.getMessage());
        }
    }
}

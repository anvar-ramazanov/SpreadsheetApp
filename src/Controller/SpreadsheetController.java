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
import Helpers.CellHelpers;
import Helpers.StringHelpers;
import Models.Cell.CellModel;
import Models.SpreadsheetModel;
import Models.SpreadsheetModelFabric;
import Views.CellBorders;
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

    public SpreadsheetController(
            SpreadsheetView view,
            SpreadsheetModelFabric spreadsheetModelFabric,
            Tokenizer tokenizer,
            ExpressionTreeParser expressionTreeParser,
            ExpressionTreeAnalyzer expressionTreeAnalyzer,
            ExpressionTreeEvaluator expressionTreeEvaluator) {

        this.model = spreadsheetModelFabric.getSpreadsheetModel();
        this.view = view;

        this.tokenizer = tokenizer;
        this.expressionTreeParser = expressionTreeParser;
        this.expressionTreeAnalyzer = expressionTreeAnalyzer;
        this.expressionTreeEvaluator = expressionTreeEvaluator;

        this.logger = Logger.getLogger(SpreadsheetController.class.getName());

        this.decimalFormat = new DecimalFormat("#.#");

        bindEvents();
    }

    public SpreadsheetView getView() {
        return this.view;
    }

    private void bindEvents() {
        var table = this.view.getTable();
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
            var source = (DefaultCellEditor) propertyChangeEvent.getNewValue();
            var component = (JTextField) source.getComponent();
            component.setBorder(CellBorders.getEditorBorder());
            if (cell != null && cell.value instanceof String realValue) {
                if (!realValue.isEmpty() && realValue.charAt(0) == '=') {
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
        if (cell == null) {
            logger.severe("Cell " + cellName + " is null");
            return;
        }

        logger.info("Updating cell " + cellName + " to have value: " + newValue);

        cell.errorText = null;

        boolean needToRecalcChilds = true;
        if (!newValue.isEmpty() && newValue.charAt(0) == '=') {
            needToRecalcChilds = updateCellWithFormula(cellName, cell, newValue.substring(1));
        } else if (StringHelpers.isNumeric(newValue)) {
            var doubleValue = Double.parseDouble(newValue);
            cell.setExpression(new NumberNode(doubleValue));
            cell.showValue = decimalFormat.format(doubleValue);
        } else if (StringHelpers.isBoolean(newValue)) {
            cell.showValue = newValue;
            var booleanValue = Boolean.parseBoolean(newValue);
            cell.setExpression(new BooleanNode(booleanValue));
        } else {
            cell.showValue = newValue;
            cell.setExpression(new StringNode(newValue));
        }

        if ( needToRecalcChilds) {
            var childCellNames = cell.getChildCells();
            if (childCellNames != null) {
                for (var childCellName : childCellNames) {
                    recalculateCell(childCellName);
                }
            }
        }

        model.fireTableDataChanged();
    }

    private boolean updateCellWithFormula(String cellName, CellModel cell, String newExpressionText) {
        try {
            HashSet<String> oldParentCells = cell.getExpression().getParentCells();

            var tokens = this.tokenizer.tokenize(newExpressionText);

            var expression = this.expressionTreeParser.parse(tokens);

            var context = this.model.getExpressionCells();

            expressionTreeAnalyzer.AnalyzeExpressionTree(expression, cellName, context);

            cell.setExpression(expression);

            var newShowValue = this.expressionTreeEvaluator.EvaluateExpressionTree(expression, context);
            if (newShowValue instanceof Double doubleValue) {
                cell.showValue = this.decimalFormat.format(doubleValue);
            } else {
                cell.showValue = newShowValue.toString();
            }

            var newParentCells = expression.getParentCells();
            if (oldParentCells != null) {
                for (var oldParentCell : oldParentCells) {
                    if (!newParentCells.contains(oldParentCell)) {
                        if (this.model.getCell(oldParentCell) != null) {
                            this.model.getCell(oldParentCell).removeChildCell(cellName);
                        }
                    }
                }
            }
            for (var parentCell : newParentCells) {
                if (this.model.getCell(parentCell) != null) {
                    this.model.getCell(parentCell).setChildCell(cellName);
                }
            }
            return true;
        } catch (TokenizerException exception) {
            handleTokenizerException(cellName, cell, exception);
            return false;
        } catch (ExpressionTreeParserException exception) {
            handleExpressionTreeParserException(cellName, cell, exception);
            return false;
        } catch (CircularDependencyException exception) {
            handleCircularDependencyException(cellName, cell, exception);
            return false;
        } catch (ExpressionTreeAnalyzerException exception) {
            handleExpressionTreeAnalyzerException(cellName, cell, exception);
            return false;
        } catch (ExpressionTreeEvaluatorException exception) {
            handleExpressionTreeEvaluatorException(cellName, cell, exception);
            return false;
        } catch (Exception exception) {
            handleGeneralException(cellName, cell, exception);
            return false;
        }
    }

    private void recalculateCell(String cellName) {
        var cell = this.model.getCell(cellName);
        if (cell == null) {
            logger.severe("Cell " + cellName + " is null");
            return;
        }
        try {
            logger.info("Recalculating cell " + cellName);

            cell.errorText = null;

            var context = this.model.getExpressionCells();
            var expression = context.get(cellName).getExpression();

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
        } catch (CircularDependencyException exception) {
            handleCircularDependencyException(cellName, cell, exception);
        } catch (ExpressionTreeAnalyzerException exception) {
            handleExpressionTreeAnalyzerException(cellName, cell, exception);
        } catch (ExpressionTreeEvaluatorException exception) {
            handleExpressionTreeEvaluatorException(cellName, cell, exception);
        } catch (Exception exception) {
            handleGeneralException(cellName, cell, exception);
        }
    }

    private void handleTokenizerException(String cellName, CellModel cell, TokenizerException exception) {
        logger.severe("Cell " + cellName + " has error during tokenizing: " + exception.getMessage());
        cell.showValue = ErrorFormulaParsingText;
        cell.errorText = "Error during parsing formula: " + exception.getMessage(); // even if it tokenizer to customer we will say it was parsing
    }

    private void handleExpressionTreeParserException(String cellName, CellModel cell, ExpressionTreeParserException exception) {
        logger.severe("Cell " + cellName + " has error during parsing: " + exception.getMessage());
        cell.showValue = ErrorFormulaParsingText;
        cell.errorText = "Error during parsing formula: " + exception.getMessage();
    }

    private void handleCircularDependencyException(String cellName, CellModel cell, CircularDependencyException exception) {
        logger.severe("Cell " + cellName + " has error during analyzing: " + exception.getMessage());
        cell.showValue = ErrorFormulaText;

        var visitedCellsNames = exception.getVisitedCells();
        var circularPath = String.join("->", visitedCellsNames);
        circularPath = circularPath + "->" + cellName;
        for (var visitedCellName : visitedCellsNames) {
            var visitedCell = model.getCell(visitedCellName);
            visitedCell.showValue = ErrorFormulaText;
            visitedCell.errorText = "Problem with formula: " + exception.getMessage() + " Path: " + circularPath;
        }
    }

    private void handleExpressionTreeAnalyzerException(String cellName, CellModel cell, ExpressionTreeAnalyzerException exception) {
        logger.severe("Cell " + cellName + " has error during analyzing: " + exception.getMessage());
        cell.showValue = ErrorFormulaText;
        cell.errorText = "Problem with formula: " + exception.getMessage();
    }

    private void handleExpressionTreeEvaluatorException(String cellName, CellModel cell, ExpressionTreeEvaluatorException exception) {
        logger.severe("Cell " + cellName + " has error during evaluation: " + exception.getMessage());
        cell.showValue = ErrorFormulaText;
        cell.errorText = "Problem with formula: " + exception.getMessage();
    }

    private void handleGeneralException(String cellName, CellModel cell, Exception exception) {
        logger.severe("Cell " + cellName + " has error: " + exception.getMessage());
        cell.showValue = ErrorFormulaText;
        cell.errorText = "Unknown problem with cell";
    }
}

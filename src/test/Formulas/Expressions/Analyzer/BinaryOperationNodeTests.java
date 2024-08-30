package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.OperandTypeMismatchException;
import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeAnalyzerImpl;
import Formulas.Expressions.ExpressionNodes.BinaryOperationNode;
import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Models.Cell.ExpressionCell;
import org.junit.Test;

import java.util.Map;

public class BinaryOperationNodeTests {

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_BinaryOperation_ValidOperator() {
        var expression = new BinaryOperationNode("+", new NumberNode(2), new NumberNode(2));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression, "A1", context, false);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_BinaryOperation_InvalidLeftOperandType() {
        var expression = new BinaryOperationNode("+", new BooleanNode(false), new NumberNode(2));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context, false);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_BinaryOperation_InvalidRightOperandType() {
        var expression = new BinaryOperationNode( "+", new NumberNode(2), new BooleanNode(false));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context, false);
    }
}

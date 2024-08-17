package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.OperandTypeMismatchException;
import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionNodes.BinaryOperationNode;
import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import org.junit.Test;

import java.util.Map;

public class BinaryOperationNodeTests {

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_BinaryOperation_ValidOperator() {
        var node = new BinaryOperationNode("+", new NumberNode(2), new NumberNode(2));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_BinaryOperation_InvalidLeftOperandType() {
        var node = new BinaryOperationNode("+", new BooleanNode(false), new NumberNode(2));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_BinaryOperation_InvalidRightOperandType() {
        var node = new BinaryOperationNode( "+", new NumberNode(2), new BooleanNode(false));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }
}

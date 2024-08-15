package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.OperandTypeMismatchException;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.Nodes.BinaryOperationNode;
import Formulas.Expressions.Nodes.BooleanNode;
import Formulas.Expressions.Nodes.NumberNode;
import Formulas.NodeType;
import org.junit.Test;

public class BinaryOperationNodeTests {

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_BinaryOperation_ValidOperator() {
        var node = new BinaryOperationNode("+", new NumberNode(2), new NumberNode(2), NodeType.NUMBER);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(node);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_BinaryOperation_InvalidLeftOperandType() {
        var node = new BinaryOperationNode("+", new BooleanNode(false), new NumberNode(2), NodeType.NUMBER);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(node);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_BinaryOperation_InvalidRightOperandType() {
        var node = new BinaryOperationNode( "+", new NumberNode(2), new BooleanNode(false), NodeType.NUMBER);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(node);
    }
}

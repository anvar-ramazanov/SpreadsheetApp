package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.OperandTypeMismatchException;
import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionNodes.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class UnaryOperationTests {

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_ValidOperator() {
        var node = new UnaryOperationNode("-", new NumberNode(2));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }


    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidOperandType() {
        var node = new UnaryOperationNode("-", new BooleanNode(false));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_NestedFunctionOperand() {
        var node = new UnaryOperationNode("-", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidNestedFunctionOperand() {
        var node = new UnaryOperationNode("!", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidNestedBinaryOperation() {
        var node = new UnaryOperationNode("!",  new BinaryOperationNode("+", new NumberNode(2), new NumberNode(3)));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_ValidNestedBinaryOperation() {
        var node = new UnaryOperationNode("!",  new BinaryOperationNode(">", new NumberNode(2), new NumberNode(3)));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }


    @Test
    public void ASTParser_ParseUnaryOperator_VariableOperand() {
        var node1 = new UnaryOperationNode("-",  new ReferencesNode("A2"));
        var node2 = new NumberNode(5);
        var nodes = Map.of(
                "A1", node1,
                "A2", node2
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }
}

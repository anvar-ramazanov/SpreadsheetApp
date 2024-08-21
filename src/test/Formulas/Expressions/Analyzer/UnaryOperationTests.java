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
        var a1 = new UnaryOperationNode("-", new NumberNode(2));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1,"A1", nodes);
    }


    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidOperandType() {
        var a1 = new UnaryOperationNode("-", new BooleanNode(false));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1,"A1", nodes);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_NestedFunctionOperand() {
        var a1 = new UnaryOperationNode("-", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1,"A1", nodes);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidNestedFunctionOperand() {
        var a1 = new UnaryOperationNode("!", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1,"A1", nodes);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidNestedBinaryOperation() {
        var a1 = new UnaryOperationNode("!",  new BinaryOperationNode("+", new NumberNode(2), new NumberNode(3)));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1,"A1", nodes);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_ValidNestedBinaryOperation() {
        var a1 = new UnaryOperationNode("!",  new BinaryOperationNode(">", new NumberNode(2), new NumberNode(3)));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1,"A1", nodes);
    }


    @Test
    public void ASTParser_ParseUnaryOperator_VariableOperand() {
        var a1 = new UnaryOperationNode("-",  new ReferencesNode("A2"));
        var a2 = new NumberNode(5);
        var nodes = Map.of(
                "A1", a1,
                "A2", a2
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1,"A1", nodes);
    }
}

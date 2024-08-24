package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.OperandTypeMismatchException;
import Formulas.Expressions.ExpressionTreeAnalyzerImpl;
import Formulas.Expressions.ExpressionNodes.*;
import Models.Cell.ExpressionCell;
import org.junit.Test;
import test.Formulas.Expressions.TestExpressionCell;

import java.util.List;
import java.util.Map;

public class UnaryOperationTests {

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_ValidOperator() {
        var expression = new UnaryOperationNode("-", new NumberNode(2));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context);
    }


    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidOperandType() {
        var expression = new UnaryOperationNode("-", new BooleanNode(false));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_NestedFunctionOperand() {
        var expression = new UnaryOperationNode("-", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidNestedFunctionOperand() {
        var expression = new UnaryOperationNode("!", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidNestedBinaryOperation() {
        var expression = new UnaryOperationNode("!",  new BinaryOperationNode("+", new NumberNode(2), new NumberNode(3)));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_ValidNestedBinaryOperation() {
        var expression = new UnaryOperationNode("!",  new BinaryOperationNode(">", new NumberNode(2), new NumberNode(3)));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context);
    }


    @Test
    public void ASTParser_ParseUnaryOperator_VariableOperand() {
        var expression = new UnaryOperationNode("-",  new ReferencesNode("A2"));
        var a2 = new TestExpressionCell(new NumberNode(5));

        Map<String, ExpressionCell> context = Map.of(
                "A2", a2
        );

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context);
    }
}

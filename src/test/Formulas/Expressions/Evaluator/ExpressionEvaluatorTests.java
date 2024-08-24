package test.Formulas.Expressions.Evaluator;

import Formulas.Expressions.ExpressionNodes.*;
import Formulas.Expressions.ExpressionTreeEvaluatorImpl;
import Models.Cell.ExpressionCell;
import test.Formulas.Expressions.TestExpressionCell;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExpressionEvaluatorTests {
    @Test
    public void ExpressionEvaluator_EvaluateBinaryOperation() {
        var expression = new BinaryOperationNode("+", new NumberNode(2), new NumberNode(2));
        var a1 = new TestExpressionCell(expression);
        Map<String, ExpressionCell> nodes = Map.of("A1", a1);

        var evaluator = new ExpressionTreeEvaluatorImpl();

        var result =  evaluator.EvaluateExpressionTree(expression, nodes);

        assertEquals(4.0, result);
    }

    @Test
    public void ExpressionEvaluator_EvaluateBinaryOperationWithRefs() {
        var expression = new BinaryOperationNode("+", new ReferencesNode("A2"), new ReferencesNode("A3"));
        var a1 = new TestExpressionCell(expression);
        var a2 = new TestExpressionCell(new NumberNode(1));
        var a3 = new TestExpressionCell(new NumberNode(2));
        Map<String, ExpressionCell> nodes = Map.of(
            "A1", a1,
            "A2", a2,
            "A3", a3);

        var evaluator = new ExpressionTreeEvaluatorImpl();

        var result =  evaluator.EvaluateExpressionTree(expression, nodes);

        assertEquals(3.0, result);
    }

    @Test
    public void ExpressionEvaluator_EvaluateUnaryOperation() {
        var expression = new UnaryOperationNode("-", new NumberNode(2));
        var a1 =  new TestExpressionCell(expression);
        Map<String, ExpressionCell> nodes = Map.of("A1", a1);

        var evaluator = new ExpressionTreeEvaluatorImpl();

        var result =  evaluator.EvaluateExpressionTree(expression, nodes);

        assertEquals(-2.0, result);
    }

    @Test
    public void ExpressionEvaluator_EvaluateFunctionOperation() {
        var expression = new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2)));
        var a1 = new TestExpressionCell(expression);
        Map<String, ExpressionCell> nodes = Map.of("A1", a1);

        var evaluator = new ExpressionTreeEvaluatorImpl();

        var result =  evaluator.EvaluateExpressionTree(expression, nodes);

        assertEquals(1.0, result);
    }

    @Test
    public void ExpressionEvaluator_EvaluateFunctionWithNestedUnaryOperation() {
        var expression = new FunctionNode("MIN", List.of(new UnaryOperationNode("-", new NumberNode(1)), new NumberNode(2)));
        var a1 = new TestExpressionCell(expression);
        Map<String, ExpressionCell> nodes = Map.of("A1", a1);

        var evaluator = new ExpressionTreeEvaluatorImpl();

        var result =  evaluator.EvaluateExpressionTree(expression, nodes);

        assertEquals(-1.0, result);
    }
}

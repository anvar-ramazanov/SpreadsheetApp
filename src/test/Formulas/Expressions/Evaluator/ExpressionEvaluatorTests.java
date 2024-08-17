package test.Formulas.Expressions.Evaluator;

import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionNodes.*;
import Formulas.Expressions.ExpressionTreeEvaluator;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ExpressionEvaluatorTests {
    @Test
    public void ExpressionEvaluator_EvaluateBinaryOperation() {
        var node = new BinaryOperationNode("+", new NumberNode(2), new NumberNode(2));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(4.0, result);
    }

    @Test
    public void ExpressionEvaluator_EvaluateBinaryOperationWithRefs() {
        var a1 = new BinaryOperationNode("+", new ReferencesNode("A2"), new ReferencesNode("A3"));
        var a2 = new NumberNode(1);
        var a3 = new NumberNode(2);
        var nodes = Map.of(
            "A1", a1,
            "A2", a2,
            "A3", a3);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(3.0, result);
    }

    @Test
    public void ExpressionEvaluator_EvaluateUnaryOperation() {
        var node = new UnaryOperationNode("-", new NumberNode(2));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(-2.0, result);
    }

    @Test
    public void ExpressionEvaluator_EvaluateFunctionOperation() {
        var node = new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2)));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(1.0, result);
    }

    @Test
    public void ExpressionEvaluator_EvaluateFunctionWithNestedUnaryOperation() {
        var node = new FunctionNode("MIN", List.of(new UnaryOperationNode("-", new NumberNode(1)), new NumberNode(2)));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(-1.0, result);
    }
}

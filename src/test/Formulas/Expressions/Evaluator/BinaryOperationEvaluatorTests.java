package test.Formulas.Expressions.Evaluator;

import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeEvaluator;
import Formulas.Expressions.Nodes.BinaryOperationNode;
import Formulas.Expressions.Nodes.NumberNode;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BinaryOperationEvaluatorTests {
    @Test
    public void ExpressionEvaluator_BinaryOperation_EvaluateBinaryPlus() {
        var node = new BinaryOperationNode("+", new NumberNode(2), new NumberNode(2));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(4.0, result);
    }
}

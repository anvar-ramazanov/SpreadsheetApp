package test.Formulas.Expressions.Evaluator;

import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeEvaluator;
import Formulas.Expressions.Nodes.FunctionNode;
import Formulas.Expressions.Nodes.NumberNode;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FunctionEvaluatorTests {
    @Test
    public void ExpressionEvaluator_UnaryOperation_EvaluateMinus() {
        var node = new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2)));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(1.0, result);
    }
}

package test.Formulas.Expressions.Evaluator;

import Formulas.Expressions.ExpressionTreeEvaluator;
import Formulas.Expressions.ExpressionNodes.BinaryOperationNode;
import Formulas.Expressions.ExpressionNodes.FunctionNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionNodes.ReferencesNode;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RefEvaluatorTests {
    @Test
    public void ExpressionEvaluator_UnaryOperation_EvaluateMinus() { // FIX NAME
        var node1 = new ReferencesNode("A2");
        var node2 = new NumberNode(4);

        var context = Map.of(
                "A1", node1,
                "A2", node2
        );

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(context, "A1");

        assertEquals(4.0, result);
    }

    @Test
    public void ExpressionEvaluator_UnaryOperation_EvaluateMinus2() {
        var node1 = new BinaryOperationNode("+", new ReferencesNode("A2"),  new NumberNode(1));
        var node2 = new FunctionNode("MIN", List.of(new NumberNode(8), new NumberNode(3)));

        var context = Map.of(
                "A1", node1,
                "A2", node2
        );

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(context, "A1");

        assertEquals(4.0, result);
    }
}

package test.Formulas.Expressions.Evaluator;

import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeEvaluator;
import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionNodes.UnaryOperationNode;
import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;

public class UnaryOperationEvaluatorTests {
    @Test
    public void ExpressionEvaluator_UnaryOperation_EvaluateMinus() {
        var node = new UnaryOperationNode("-", new NumberNode(2));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(-2.0, result);
    }

    @Test
    public void ExpressionEvaluator_UnaryOperation_EvaluateMinusDeep() {
        var node = new UnaryOperationNode("-", new UnaryOperationNode("-", new NumberNode(2)));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(2.0, result);
    }

    @Test
    public void ExpressionEvaluator_UnaryOperation_EvaluateNot() {
        var node = new UnaryOperationNode("!", new BooleanNode(false));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var evaluator = new ExpressionTreeEvaluator();

        var result =  evaluator.EvaluateExpressionTree(nodes, "A1");

        assertEquals(true, result);
    }

}

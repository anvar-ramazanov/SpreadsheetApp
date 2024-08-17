package test.Formulas.Expressions.Evaluator.Evaluators.UnaryEvaluatorsTests;

import Formulas.Expressions.Evaluators.UnaryOperatorsEvaluators.UnaryMinusEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnaryMinusEvaluatorTests {
    @Test
    public void UnaryMinusEvaluator_Evaluate() {

        var evaluator = new UnaryMinusEvaluator();

        var result = evaluator.evaluate(8);

        assertEquals(-8.0, result);
    }

    @Test
    public void UnaryMinusEvaluator_Evaluate_NegativeNumber() {

        var evaluator = new UnaryMinusEvaluator();

        var result = evaluator.evaluate(-8);

        assertEquals(8.0, result);
    }
}

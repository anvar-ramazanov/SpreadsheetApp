package test.Formulas.Expressions.Evaluator.Evaluators.UnaryEvaluatorsTests;

import Formulas.Expressions.Evaluators.UnaryOperatorsEvaluators.UnaryNotEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UnaryNotOperatorMinusTests {
    @Test
    public void UnaryNotEvaluator_Evaluate_False() {

        var evaluator = new UnaryNotEvaluator();

        var result = evaluator.evaluate(false);

        assertEquals(true, result);
    }

    @Test
    public void UnaryNotEvaluator_Evaluate_True() {

        var evaluator = new UnaryNotEvaluator();

        var result = evaluator.evaluate(true);

        assertEquals(false, result);
    }
}

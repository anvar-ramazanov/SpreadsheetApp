package test.Formulas.Expressions.Evaluator.Evaluators.BinaryEvaluatorsTests;

import Formulas.Expressions.Evaluators.BinaryOperationsEvaluators.BinaryGreatEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryGreatEvaluatorTests {
    @Test
    public void BinaryGreatEvaluator_Evaluate_False() {

        var evaluator = new BinaryGreatEvaluator();

        var result =  evaluator.evaluate(1, 2);

        assertEquals(false, result);
    }

    @Test
    public void BinaryGreatEvaluator_Evaluate_True() {

        var evaluator = new BinaryGreatEvaluator();

        var result =  evaluator.evaluate(2, 1);

        assertEquals(true, result);
    }
}

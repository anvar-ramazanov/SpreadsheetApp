package test.Formulas.Expressions.Evaluator.Evaluators.BinaryEvaluatorsTests;

import Formulas.Expressions.Evaluators.BinaryOperationsEvaluators.BinaryLessEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryLessEvaluatorTests {
    @Test
    public void BinaryLessEvaluatorTests_Evaluate_False() {

        var evaluator = new BinaryLessEvaluator();

        var result =  evaluator.evaluate(3, 2);

        assertEquals(false, result);
    }

    @Test
    public void BinaryLessEvaluatorTests_Evaluate_True() {

        var evaluator = new BinaryLessEvaluator();

        var result =  evaluator.evaluate(2, 3);

        assertEquals(true, result);
    }
}

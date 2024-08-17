package test.Formulas.Expressions.Evaluator.Evaluators.BinaryEvaluatorsTests;

import Formulas.Expressions.Evaluators.BinaryOperationsEvaluators.BinaryMinusEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryMinusEvaluatorTests {
    @Test
    public void BinaryMinusEvaluator_Evaluate() {

        var evaluator = new BinaryMinusEvaluator();

        var result =  evaluator.evaluate(6, 2);

        assertEquals(4.0, result);
    }
}

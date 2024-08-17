package test.Formulas.Expressions.Evaluator.Evaluators.BinaryEvaluatorsTests;

import Formulas.Expressions.Evaluators.BinaryOperationsEvaluators.BinaryPlusEvaluator;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class BinaryPlusEvaluatorTests {
    @Test
    public void BinaryPlusEvaluator_Evaluate() {

        var evaluator = new BinaryPlusEvaluator();

        var result =  evaluator.evaluate(2, 3);

        assertEquals(5.0, result);
    }
}

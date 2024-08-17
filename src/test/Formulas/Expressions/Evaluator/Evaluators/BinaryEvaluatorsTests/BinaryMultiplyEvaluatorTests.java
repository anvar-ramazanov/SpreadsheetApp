package test.Formulas.Expressions.Evaluator.Evaluators.BinaryEvaluatorsTests;

import Formulas.Expressions.Evaluators.BinaryOperationsEvaluators.BinaryMultiplyEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryMultiplyEvaluatorTests {
    @Test
    public void BinaryMultiplyEvaluator_Evaluate() {

        var evaluator = new BinaryMultiplyEvaluator();

        var result =  evaluator.evaluate(3, 2);

        assertEquals(6.0, result);
    }
}

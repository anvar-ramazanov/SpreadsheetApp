package test.Formulas.Expressions.Evaluator.Evaluators.BinaryEvaluatorsTests;

import Formulas.Exceptions.Evaluators.DivideByZeroException;
import Formulas.Expressions.Evaluators.BinaryOperationsEvaluators.BinaryDivideEvaluator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryDivideEvaluatorTest {
    @Test
    public void BinaryDivideEvaluator_Evaluate() {

        var evaluator = new BinaryDivideEvaluator();

        var result = evaluator.evaluate(14, 2);

        assertEquals(7.0, result);
    }

    @Test(expected = DivideByZeroException.class)
    public void BinaryDivideEvaluator_Evaluate_DivideByZero() {

        var evaluator = new BinaryDivideEvaluator();

        evaluator.evaluate(14, 0);
    }

    @Test
    public void BinaryDivideEvaluator_Evaluate_DivideZero() {

        var evaluator = new BinaryDivideEvaluator();

        var result =  evaluator.evaluate(0, 14);

        assertEquals(0.0, result);
    }
}

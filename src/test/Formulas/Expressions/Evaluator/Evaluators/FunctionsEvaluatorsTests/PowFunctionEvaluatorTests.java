package test.Formulas.Expressions.Evaluator.Evaluators.FunctionsEvaluatorsTests;

import Formulas.Expressions.Evaluators.FunctionsEvaluators.PowFunctionEvaluator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PowFunctionEvaluatorTests {
    @Test
    public void PowFunctionEvaluator_Evaluate() {

        var evaluator = new PowFunctionEvaluator();

        var result = evaluator.evaluate(List.of(2, 3));

        assertEquals(8.0, result);
    }
}

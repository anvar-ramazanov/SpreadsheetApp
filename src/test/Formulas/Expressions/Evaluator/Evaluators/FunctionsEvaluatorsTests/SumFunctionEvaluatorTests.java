package test.Formulas.Expressions.Evaluator.Evaluators.FunctionsEvaluatorsTests;

import Formulas.Expressions.Evaluators.FunctionsEvaluators.SumFunctionEvaluator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SumFunctionEvaluatorTests {
    @Test
    public void SumFunctionEvaluator_Evaluate() {

        var evaluator = new SumFunctionEvaluator();

        var result = evaluator.evaluate(List.of(3, 4));

        assertEquals(7.0, result);
    }
}

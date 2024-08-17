package test.Formulas.Expressions.Evaluator.Evaluators.FunctionsEvaluatorsTests;

import Formulas.Expressions.Evaluators.FunctionsEvaluators.MinFunctionEvaluator;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MinFunctionEvaluatorTests {
    @Test
    public void MinFunctionEvaluator_Evaluate() {

        var evaluator = new MinFunctionEvaluator();

        var result = evaluator.evaluate(List.of(3, -1));

        assertEquals(-1.0, result);
    }
}

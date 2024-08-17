package Formulas.Expressions.Evaluators;

import java.util.List;

public interface FunctionEvaluator extends Evaluator {
    Object evaluate(List<Object> arguments);
}

package Formulas.Expressions.Evaluators.UnaryOperatorsEvaluators;

import Formulas.Expressions.Evaluators.UnaryOperationEvaluator;

public class UnaryNotEvaluator implements UnaryOperationEvaluator {
    @Override
    public Object evaluate(Object operand) {
        var value = Boolean.parseBoolean(operand.toString());
        return !value;
    }
}

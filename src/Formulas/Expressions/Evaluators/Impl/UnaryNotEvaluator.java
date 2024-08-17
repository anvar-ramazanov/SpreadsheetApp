package Formulas.Expressions.Evaluators.Impl;

import Formulas.Expressions.Evaluators.UnaryEvaluator;

public class UnaryNotEvaluator implements UnaryEvaluator {
    @Override
    public Object evaluate(Object operand) {
        var value = Boolean.parseBoolean(operand.toString());
        return !value;
    }
}

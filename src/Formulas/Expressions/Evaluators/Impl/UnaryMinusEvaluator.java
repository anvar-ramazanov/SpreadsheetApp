package Formulas.Expressions.Evaluators.Impl;

import Formulas.Expressions.Evaluators.UnaryEvaluator;

public class UnaryMinusEvaluator implements UnaryEvaluator {
    @Override
    public Object evaluate(Object operand) {
        var operandValue = Double.parseDouble(operand.toString());
        return (-1) * operandValue;
    }
}

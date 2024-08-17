package Formulas.Expressions.Evaluators.Impl;

import Formulas.Expressions.Evaluators.BinaryEvaluator;

public class BinaryPlusEvaluator implements BinaryEvaluator {
    @Override
    public Object evaluate(Object leftOperand, Object rightOperand) {
        var leftOperandValue = Double.parseDouble(leftOperand.toString());
        var rightOperandValue = Double.parseDouble(rightOperand.toString());
        return leftOperandValue + rightOperandValue;
    }
}

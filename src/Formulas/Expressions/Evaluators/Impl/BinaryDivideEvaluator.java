package Formulas.Expressions.Evaluators.Impl;

import Formulas.Exceptions.Evaluators.DivideByZeroException;
import Formulas.Expressions.Evaluators.BinaryEvaluator;

public class BinaryDivideEvaluator implements BinaryEvaluator {
    @Override
    public Object evaluate(Object leftOperand, Object rightOperand) {
        var leftOperandValue = Double.parseDouble(leftOperand.toString());
        var rightOperandValue = Double.parseDouble(rightOperand.toString());
        if (rightOperandValue == 0) {
            throw new DivideByZeroException();
        }
        return leftOperandValue / rightOperandValue;
    }
}

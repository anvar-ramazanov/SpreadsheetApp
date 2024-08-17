package Formulas.Expressions.Evaluators.BinaryOperationsEvaluators;

import Formulas.Exceptions.Evaluators.DivideByZeroException;
import Formulas.Expressions.Evaluators.BinaryOperationEvaluator;

public class BinaryDivideEvaluator implements BinaryOperationEvaluator {
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

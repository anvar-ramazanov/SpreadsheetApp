package Formulas.Expressions.Evaluators.BinaryOperationsEvaluators;

import Formulas.Expressions.Evaluators.BinaryOperationEvaluator;

public class BinaryPlusEvaluator implements BinaryOperationEvaluator {
    @Override
    public Object evaluate(Object leftOperand, Object rightOperand) {
        var leftOperandValue = Double.parseDouble(leftOperand.toString());
        var rightOperandValue = Double.parseDouble(rightOperand.toString());
        return leftOperandValue + rightOperandValue;
    }
}

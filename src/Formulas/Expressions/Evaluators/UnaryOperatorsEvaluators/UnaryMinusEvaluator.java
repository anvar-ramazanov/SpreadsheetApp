package Formulas.Expressions.Evaluators.UnaryOperatorsEvaluators;

import Formulas.Expressions.Evaluators.UnaryOperationEvaluator;

public class UnaryMinusEvaluator implements UnaryOperationEvaluator {
    @Override
    public Object evaluate(Object operand) {
        var operandValue = Double.parseDouble(operand.toString());
        return (-1) * operandValue;
    }
}

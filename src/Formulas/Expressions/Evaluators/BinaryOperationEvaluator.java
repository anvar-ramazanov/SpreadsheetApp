package Formulas.Expressions.Evaluators;

public interface BinaryOperationEvaluator extends Evaluator {
    Object evaluate(Object leftOperand, Object rightOperand);
}
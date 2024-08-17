package Formulas.Expressions.Evaluators;

public interface BinaryEvaluator extends Evaluator {
    Object evaluate(Object leftOperand, Object rightOperand);
}
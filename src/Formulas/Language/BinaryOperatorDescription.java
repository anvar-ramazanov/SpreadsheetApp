package Formulas.Language;

import Formulas.Expressions.Evaluators.Evaluator;

public record BinaryOperatorDescription(DataType leftOperandType, DataType rightOperandType, DataType resultType, OperatorPrecedence precedence, Evaluator evaluator) {
}

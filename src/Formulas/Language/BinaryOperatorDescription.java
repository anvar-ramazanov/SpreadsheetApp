package Formulas.Language;

import Formulas.Expressions.Evaluators.Evaluator;

public record BinaryOperatorDescription(DataType leftOperandType, DataType rightOperandType, DataType resultType, Evaluator evaluator) {
}

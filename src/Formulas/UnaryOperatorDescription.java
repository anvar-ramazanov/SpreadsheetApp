package Formulas;

import Formulas.Expressions.Evaluators.Evaluator;

public record UnaryOperatorDescription(DataType operandType, DataType resultType, String operator, Evaluator evaluator) {

}

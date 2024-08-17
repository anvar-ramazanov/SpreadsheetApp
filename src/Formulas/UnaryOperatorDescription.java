package Formulas;

import Formulas.Expressions.Evaluators.Evaluator;

public record UnaryOperatorDescription(NodeType operandType, NodeType resultType, String operator, Evaluator evaluator) {

}

package Formulas;

import Formulas.Expressions.Evaluators.Evaluator;

public record BinaryOperatorDescription(NodeType leftOperandType, NodeType rightOperandType, NodeType resultType, Evaluator evaluator) {
}

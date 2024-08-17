package Formulas;

import Formulas.Expressions.Evaluators.Evaluator;

import java.util.List;
import java.util.Set;

public record FunctionDescription(List<NodeType> arguments, NodeType resultType, String functionName, Evaluator evaluator) {
}


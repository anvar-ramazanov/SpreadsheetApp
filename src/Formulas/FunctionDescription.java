package Formulas;

import Formulas.Expressions.Evaluators.Evaluator;

import java.util.List;

public record FunctionDescription(List<DataType> arguments, DataType resultType, String functionName, Evaluator evaluator) {
}


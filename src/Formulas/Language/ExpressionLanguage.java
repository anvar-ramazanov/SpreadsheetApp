package Formulas.Language;

import Formulas.Expressions.Evaluators.BinaryOperationsEvaluators.*;
import Formulas.Expressions.Evaluators.FunctionsEvaluators.*;
import Formulas.Expressions.Evaluators.UnaryOperatorsEvaluators.UnaryMinusEvaluator;
import Formulas.Expressions.Evaluators.UnaryOperatorsEvaluators.UnaryNotEvaluator;

import java.util.*;


public class ExpressionLanguage {

    public static final Map<String, FunctionDescription> FunctionsDescription = Map.of(
        "MIN", new FunctionDescription(List.of(DataType.NUMBER, DataType.NUMBER), DataType.NUMBER, "MIN", new MinFunctionEvaluator()),
        "SUM", new FunctionDescription(List.of(DataType.NUMBER, DataType.NUMBER), DataType.NUMBER, "SUM", new SumFunctionEvaluator()),
        "POW", new FunctionDescription(List.of(DataType.NUMBER, DataType.NUMBER), DataType.NUMBER, "SUM", new PowFunctionEvaluator())
    );

    public static final Map<String, UnaryOperatorDescription> UnaryOperations = Map.of(
            "-", new UnaryOperatorDescription(DataType.NUMBER, DataType.NUMBER, "-", new UnaryMinusEvaluator()),
            "!", new UnaryOperatorDescription(DataType.BOOLEAN, DataType.BOOLEAN, "!", new UnaryNotEvaluator())
    );

    public static final Map<String, BinaryOperatorDescription> BinaryOperations = Map.of(
            "+", new BinaryOperatorDescription(DataType.NUMBER, DataType.NUMBER, DataType.NUMBER, new BinaryPlusEvaluator()),
            "-", new BinaryOperatorDescription(DataType.NUMBER, DataType.NUMBER, DataType.NUMBER, new BinaryMinusEvaluator()),
            "*", new BinaryOperatorDescription(DataType.NUMBER, DataType.NUMBER, DataType.NUMBER, new BinaryMultiplyEvaluator()),
            "/", new BinaryOperatorDescription(DataType.NUMBER, DataType.NUMBER, DataType.NUMBER, new BinaryDivideEvaluator()),
            ">", new BinaryOperatorDescription(DataType.NUMBER, DataType.NUMBER, DataType.BOOLEAN, new BinaryGreatEvaluator() ),
            "<", new BinaryOperatorDescription(DataType.NUMBER, DataType.NUMBER, DataType.BOOLEAN, new BinaryLessEvaluator())

    );

}



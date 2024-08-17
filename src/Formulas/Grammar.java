package Formulas;

import Formulas.Expressions.Evaluators.BinaryOperationsEvaluators.*;
import Formulas.Expressions.Evaluators.FunctionsEvaluators.*;
import Formulas.Expressions.Evaluators.UnaryOperatorsEvaluators.UnaryMinusEvaluator;
import Formulas.Expressions.Evaluators.UnaryOperatorsEvaluators.UnaryNotEvaluator;

import java.util.*;


public class Grammar {

    public static final Map<String, FunctionDescription> FunctionsDescription = Map.of(
        "MIN", new FunctionDescription(List.of(NodeType.NUMBER, NodeType.NUMBER), NodeType.NUMBER, "MIN", new MinFunctionEvaluator()),
        "SUM", new FunctionDescription(List.of(NodeType.NUMBER, NodeType.NUMBER), NodeType.NUMBER, "SUM", new SumFunctionEvaluator()),
        "POW", new FunctionDescription(List.of(NodeType.NUMBER, NodeType.NUMBER), NodeType.NUMBER, "SUM", new PowFunctionEvaluator())
    );

    public static final Map<String, UnaryOperatorDescription> UnaryOperations = Map.of(
            "-", new UnaryOperatorDescription(NodeType.NUMBER, NodeType.NUMBER, "-", new UnaryMinusEvaluator()),
            "!", new UnaryOperatorDescription(NodeType.BOOLEAN, NodeType.BOOLEAN, "!", new UnaryNotEvaluator())
    );

    public static final Map<String, BinaryOperatorDescription> BinaryOperations = Map.of(
            "+", new BinaryOperatorDescription(NodeType.NUMBER, NodeType.NUMBER, NodeType.NUMBER, new BinaryPlusEvaluator()),
            "-", new BinaryOperatorDescription(NodeType.NUMBER, NodeType.NUMBER, NodeType.NUMBER, new BinaryMinusEvaluator()),
            "*", new BinaryOperatorDescription(NodeType.NUMBER, NodeType.NUMBER, NodeType.NUMBER, new BinaryMultiplyEvaluator()),
            "/", new BinaryOperatorDescription(NodeType.NUMBER, NodeType.NUMBER, NodeType.NUMBER, new BinaryDivideEvaluator()),
            ">", new BinaryOperatorDescription(NodeType.NUMBER, NodeType.NUMBER, NodeType.BOOLEAN, new BinaryGreatEvaluator() ),
            "<", new BinaryOperatorDescription(NodeType.NUMBER, NodeType.NUMBER, NodeType.BOOLEAN, new BinaryLessEvaluator())

    );

}



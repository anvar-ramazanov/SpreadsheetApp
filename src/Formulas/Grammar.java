package Formulas;

import Formulas.Expressions.Evaluators.Impl.*;

import java.util.*;


public class Grammar {
    private static final Set<NodeType> numberOrDerives = Set.of(NodeType.NUMBER);
    private static final Set<NodeType> boolOrDerives = Set.of(NodeType.BOOLEAN);

    public static final Map<String, FunctionDescription> FunctionsDescription = Map.of(
        "MIN", new FunctionDescription(List.of(numberOrDerives, numberOrDerives), NodeType.NUMBER, "MIN"),
        "SUM", new FunctionDescription(List.of(numberOrDerives, numberOrDerives), NodeType.NUMBER, "SUM")
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



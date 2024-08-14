package Formulas;

import java.util.*;


public class Grammar {
    private static final Set<NodeType> numberOrDerives = Set.of(NodeType.NUMBER);
    private static final Set<NodeType> boolOrDerives = Set.of(NodeType.BOOLEAN);

    public static final Map<String, FunctionDescription> FunctionsDescription = Map.of(
        "MIN", new FunctionDescription(List.of(numberOrDerives, numberOrDerives), NodeType.NUMBER, "MIN"),
        "SUM", new FunctionDescription(List.of(numberOrDerives, numberOrDerives), NodeType.NUMBER, "SUM")
    );

    public static final Map<String, UnaryOperatorDescription> UnaryOperations = Map.of(
            "-", new UnaryOperatorDescription(NodeType.NUMBER, NodeType.NUMBER, "-"),
            "!", new UnaryOperatorDescription(NodeType.BOOLEAN, NodeType.BOOLEAN, "!")
    );

    public static final Map<String, BinaryOperatorDescription> BinaryOperations = Map.of(
            "+", new BinaryOperatorDescription(numberOrDerives, numberOrDerives),
            "-", new BinaryOperatorDescription(numberOrDerives, numberOrDerives),
            "*", new BinaryOperatorDescription(numberOrDerives, numberOrDerives),
            "/", new BinaryOperatorDescription(numberOrDerives, numberOrDerives),
            ">", new BinaryOperatorDescription(numberOrDerives, numberOrDerives),
            "<", new BinaryOperatorDescription(numberOrDerives, numberOrDerives)

    );

}



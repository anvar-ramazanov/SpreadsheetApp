package Formulas;

import java.util.*;

public class Grammar {
    private static final Set<String> numberOrDerives = Set.of("NUMBER", "FUNCTION", "VARIABLE", "UNARY_OPERATION", "BINARY_OPERATION");
    private static final Set<String> boolOrDerives = Set.of("BOOL", "FUNCTION", "VARIABLE", "UNARY_OPERATION", "BINARY_OPERATION");

    public static final Map<String, FunctionDescription> FunctionsDescription = Map.of(
        "MIN", new FunctionDescription(List.of(numberOrDerives, numberOrDerives)),
        "SUM", new FunctionDescription(List.of(numberOrDerives, numberOrDerives))
    );

    public static final Map<String, UnaryOperatorDescription> UnaryOperations = Map.of(
            "-", new UnaryOperatorDescription(numberOrDerives),
            "!", new UnaryOperatorDescription(boolOrDerives));

    // public static final Set<String> BinaryOperations = Set.of("+", "-", "/", "*", ">", "<");

    public static final Map<String, BinaryOperatorDescription> BinaryOperations = Map.of(
            "+", new BinaryOperatorDescription(numberOrDerives, numberOrDerives),
            "-", new BinaryOperatorDescription(numberOrDerives, numberOrDerives)
    );

}



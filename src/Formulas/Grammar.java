package Formulas;

import java.util.*;

public class Grammar {
    private static final Set<String> numberOrDerives = Set.of("NUMBER", "FUNCTION", "VARIABLE", "UNARY_OPERATION", "BINARY_OPERATION");
    private static final Set<String> boolOrDerives = Set.of("BOOL", "FUNCTION", "VARIABLE", "UNARY_OPERATION", "BINARY_OPERATION");

    public static final Map<String, FunctionDescription> FunctionsDescription = Map.of(
        "MIN", new FunctionDescription(List.of(numberOrDerives, numberOrDerives)),
        "SUM", new FunctionDescription(List.of(numberOrDerives, numberOrDerives))
    );

    public static final Map<String, FunctionDescription> UnaryOperations = Map.of(
            "-",  new FunctionDescription(List.of(numberOrDerives)),
            "!", new FunctionDescription(List.of(boolOrDerives)));

    public static final Set<String> BinaryOperations = Set.of("+", "-", "/", "*", ">", "<");
}



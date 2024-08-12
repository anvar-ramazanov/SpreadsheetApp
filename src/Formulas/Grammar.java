package Formulas;

import java.util.*;

public class Grammar {

    public static final Map<String, FunctionDescription> FunctionsDescription = Map.of(
        "MIN", new FunctionDescription(List.of(Set.of("NUMBER", "DOUBLE"), Set.of("NUMBER", "DOUBLE"))),
        "SUM", new FunctionDescription(List.of(Set.of("NUMBER", "DOUBLE"), Set.of("NUMBER", "DOUBLE")))
    );

    public static final Set UnaryOperations = Set.of("-", "!");

    public static final Set BinaryOperations = Set.of("");
}



package Formulas;

import Formulas.AST.FunctionDescription;

import java.util.*;

public class Functions {

    public static final Map<String, FunctionDescription> FunctionsDescription = Map.of(
        "MIN", new FunctionDescription(List.of(Set.of("NUMBER", "DOUBLE"), Set.of("NUMBER", "DOUBLE"))),
        "SUM", new FunctionDescription(List.of(Set.of("NUMBER", "DOUBLE"), Set.of("NUMBER", "DOUBLE")))
    );

}



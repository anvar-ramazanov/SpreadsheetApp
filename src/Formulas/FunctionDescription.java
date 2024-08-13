package Formulas;

import java.util.List;
import java.util.Set;

public record FunctionDescription(List<Set<NodeType>> arguments, NodeType resultType, String functionName) {
}


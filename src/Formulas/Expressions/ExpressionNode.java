package Formulas.Expressions;

import Formulas.NodeType;

import java.util.Map;

public abstract class ExpressionNode {
    public abstract Object evaluate(Map<String, Object> variables);
    public abstract NodeType getType();
}
package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

import java.util.Map;

public class BooleanNode extends ExpressionNode {
    boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        return value;
    }

    @Override
    public NodeType getType() { return NodeType.BOOLEAN; }
}

package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

import java.util.Map;

public class BooleanNode extends ExpressionNode {
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public NodeType getType() {
        return NodeType.BOOLEAN;
    }

    public Boolean getValue() {
        return value;
    }
}

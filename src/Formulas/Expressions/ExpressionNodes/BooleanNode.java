package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

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

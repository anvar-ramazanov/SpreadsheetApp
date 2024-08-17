package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

public class NumberNode extends ExpressionNode {
    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public NodeType getType() {
        return NodeType.NUMBER;
    }

    public Double getValue() {
        return this.value;
    }
}

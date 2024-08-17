package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

public class StringNode extends ExpressionNode {
    private final String value;

    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public NodeType getType() {
        return NodeType.STRING;
    }

    public String getValue() {
        return this.value;
    }
}

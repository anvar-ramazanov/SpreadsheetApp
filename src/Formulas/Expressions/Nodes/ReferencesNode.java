package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

public class ReferencesNode extends ExpressionNode {
    private final String name;
    private NodeType nodeType;

    public ReferencesNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public NodeType getType() {
        return this.nodeType;
    }

    public void setType(NodeType nodeType) {
        this.nodeType = nodeType;
    }
}


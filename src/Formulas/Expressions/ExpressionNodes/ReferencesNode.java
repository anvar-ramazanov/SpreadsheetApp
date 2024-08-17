package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

public class ReferencesNode extends ExpressionNode {
    private final String references;
    private NodeType referenceType;

    public ReferencesNode(String references) {
        this.references = references;
    }

    @Override
    public NodeType getType() {
        return this.referenceType;
    }

    public String getReferences() {
        return references;
    }

    public void setReferenceType(NodeType nodeType) {
        this.referenceType = nodeType;
    }
}


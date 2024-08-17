package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.Language.DataType;

public class ReferencesNode extends ExpressionNode {
    private final String references;
    private DataType referenceType;

    public ReferencesNode(String references) {
        this.references = references;
    }

    @Override
    public DataType getType() {
        return this.referenceType;
    }

    public String getReferences() {
        return references;
    }

    public void setReferenceType(DataType nodeType) {
        this.referenceType = nodeType;
    }
}


package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.DataType;

public class StringNode extends ExpressionNode {
    private final String value;

    public StringNode(String value) {
        this.value = value;
    }

    @Override
    public DataType getType() {
        return DataType.STRING;
    }

    public String getValue() {
        return this.value;
    }
}

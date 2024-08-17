package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.Language.DataType;

public class BooleanNode extends ExpressionNode {
    private final boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public DataType getType() {
        return DataType.BOOLEAN;
    }

    public Boolean getValue() {
        return value;
    }
}

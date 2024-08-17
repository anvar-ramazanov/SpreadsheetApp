package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.DataType;

public class NumberNode extends ExpressionNode {
    private final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public DataType getType() {
        return DataType.NUMBER;
    }

    public Double getValue() {
        return this.value;
    }
}

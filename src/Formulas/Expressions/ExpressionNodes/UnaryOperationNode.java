package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.DataType;

public class UnaryOperationNode extends ExpressionNode {
    private final String operator;
    private final ExpressionNode operand;

    public UnaryOperationNode(String operator, ExpressionNode operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public DataType getType() {
        return operand.getType();
    }

    public ExpressionNode getOperand() {
        return operand;
    }

    public String getOperator() {
        return operator;
    }
}

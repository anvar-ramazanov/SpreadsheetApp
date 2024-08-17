package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

public class UnaryOperationNode extends ExpressionNode {
    private final String operator;
    private final ExpressionNode operand;

    public UnaryOperationNode(String operator, ExpressionNode operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public ExpressionNode getOperand() {
        return operand;
    }

    public String getOperator() {
        return operator;
    }

    @Override
    public NodeType getType() {
        return operand.getType();
    }
}

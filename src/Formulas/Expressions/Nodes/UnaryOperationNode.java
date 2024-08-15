package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

import java.util.Map;

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
    public Object evaluate(Map<String, Object> variables) {
        Object operandValue = operand.evaluate(variables);

        switch (operator) {
            case "-":
                return -(double) operandValue;
            case "!":
                return !(boolean) operandValue;
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }

    @Override
    public NodeType getType() {
        return operand.getType();
    }
}

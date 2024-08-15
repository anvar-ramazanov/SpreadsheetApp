package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

import java.util.Map;

public class BinaryOperationNode extends ExpressionNode {
    private final ExpressionNode leftOperand;
    private final String operator;
    private final ExpressionNode rightOperand;
    private final NodeType resultType;

    public BinaryOperationNode(String operator, ExpressionNode leftOperand,  ExpressionNode rightOperand, NodeType resultType) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.resultType = resultType;
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getLeftOperand() {
        return leftOperand;
    }

    public ExpressionNode getRightOperand() {
        return rightOperand;
    }

    @Override
    public NodeType getType() {
        return resultType;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        Object leftValue = leftOperand.evaluate(variables);
        Object rightValue = rightOperand.evaluate(variables);

        switch (operator) {
            case "+": {
                var l = (leftValue instanceof Double) ?  (double) leftValue : Double.parseDouble((String)leftValue);
                var r =  (rightValue instanceof Double) ?  (double) rightValue : Double.parseDouble((String)rightValue);
                return  l + r;
            }
            case "-":
                return (double) leftValue - (double) rightValue;
            case "*":
                return (double) leftValue * (double) rightValue;
            case "/":
                return (double) leftValue / (double) rightValue;
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }
}
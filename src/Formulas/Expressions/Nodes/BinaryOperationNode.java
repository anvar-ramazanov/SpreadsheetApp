package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

import java.util.Map;

public class BinaryOperationNode extends ExpressionNode {
    private final ExpressionNode left;
    private final String operator;
    private final ExpressionNode right;

    public BinaryOperationNode(ExpressionNode left, String operator, ExpressionNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public ExpressionNode getLeft() {
        return left;
    }

    public ExpressionNode getRight() {
        return right;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        Object leftValue = left.evaluate(variables);
        Object rightValue = right.evaluate(variables);

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

    @Override
    public NodeType getType() {
        return NodeType.NUMBER; // FIXME
    }
}
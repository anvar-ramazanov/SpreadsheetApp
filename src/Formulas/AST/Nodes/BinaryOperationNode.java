package Formulas.AST.Nodes;

import Formulas.AST.ASTNode;

import java.util.Map;

public class BinaryOperationNode extends ASTNode {
    ASTNode left;
    String operator;
    ASTNode right;

    public BinaryOperationNode(ASTNode left, String operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
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
}
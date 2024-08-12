package Formulas.AST.Nodes;

import Formulas.AST.ASTNode;

import java.util.Map;

public class UnaryOperationNode extends ASTNode {
    private final String operator;
    private final ASTNode operand;

    public UnaryOperationNode(String operator, ASTNode operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public ASTNode getOperand() {
        return operand;
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
}

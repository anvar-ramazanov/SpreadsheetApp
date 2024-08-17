package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.Grammar;
import Formulas.DataType;

public class BinaryOperationNode extends ExpressionNode {
    private final String operator;
    private final ExpressionNode leftOperand;
    private final ExpressionNode rightOperand;
    private final DataType resultType;

    public BinaryOperationNode(String operator, ExpressionNode leftOperand,  ExpressionNode rightOperand) {
        this.operator = operator;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.resultType = Grammar.BinaryOperations.get(this.operator).resultType();
    }

    @Override
    public DataType getType() {
        return resultType;
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
}
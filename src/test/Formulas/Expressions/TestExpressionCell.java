package test.Formulas.Expressions;

import Formulas.Expressions.ExpressionNode;
import Models.Cell.ExpressionCell;

public class TestExpressionCell implements ExpressionCell {
    private final ExpressionNode expression;

    public TestExpressionCell(ExpressionNode expression){
        this.expression = expression;
    }

    @Override
    public ExpressionNode getExpression() {
        return expression;
    }
}

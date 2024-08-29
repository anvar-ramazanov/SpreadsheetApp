package test.Formulas.Expressions;

import Formulas.Expressions.ExpressionNode;
import Models.Cell.ExpressionCell;

public class TestExpressionCell implements ExpressionCell {
    private final ExpressionNode expression;
    private final String showValue;

    public TestExpressionCell(ExpressionNode expression){
        showValue = null;
        this.expression = expression;
    }

    public TestExpressionCell(ExpressionNode expression, String showValue){
        this.showValue = showValue;
        this.expression = expression;
    }

    @Override
    public ExpressionNode getExpression() {
        return expression;
    }

    public String getShowValue() {
        return showValue;
    }
}

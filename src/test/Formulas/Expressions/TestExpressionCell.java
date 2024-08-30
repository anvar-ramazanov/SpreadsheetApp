package test.Formulas.Expressions;

import Formulas.Expressions.ExpressionNode;
import Models.Cell.ExpressionCell;

public class TestExpressionCell implements ExpressionCell {
    private final ExpressionNode expression;
    private final String showValue;
    private final boolean hasError;

    public TestExpressionCell(ExpressionNode expression){
        this.showValue = null;
        this.hasError = false;
        this.expression = expression;
    }

    public TestExpressionCell(ExpressionNode expression, boolean hasError) {
        this.hasError = hasError;
        this.showValue = null;
        this.expression = expression;
    }

    public TestExpressionCell(ExpressionNode expression, String showValue){
        this.showValue = showValue;
        this.hasError = false;
        this.expression = expression;
    }

    @Override
    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public String getShowValue() {
        return showValue;
    }

    @Override
    public boolean hasError() {
        return hasError;
    }

}

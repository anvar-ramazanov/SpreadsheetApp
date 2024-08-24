package Models.Cell;

import Formulas.Expressions.ExpressionNode;

public class CellModel implements ExpressionCell {
    public Object Value;
    public String ShowValue;
    public String ErrorText;

    private ExpressionNode expression;

    public ExpressionNode getExpression() {
        return this.expression;
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }
}

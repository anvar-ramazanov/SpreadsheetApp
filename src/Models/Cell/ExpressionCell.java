package Models.Cell;

import Formulas.Expressions.ExpressionNode;

public interface ExpressionCell {
    ExpressionNode getExpression();
    String getShowValue();
}

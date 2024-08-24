package Models.Cell;

import Formulas.Expressions.ExpressionNode;

import java.util.HashSet;

public class CellModel implements ExpressionCell {
    public Object value;
    public String showValue;
    public String errorText;

    public HashSet<String> childCells;

    private ExpressionNode expression;

    public ExpressionNode getExpression() {
        return this.expression;
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    public void setChildCell(String childeCell) {
        if (this.childCells == null) {
            this.childCells = new HashSet<>();
        }
        this.childCells.add(childeCell);
    }

    public HashSet<String> getChildCells() {
        return childCells;
    }

    public void removeChildCell(String childCell) {
        this.childCells.remove(childCell);
    }
}

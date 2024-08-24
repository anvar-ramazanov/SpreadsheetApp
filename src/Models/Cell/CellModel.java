package Models.Cell;

import Formulas.Expressions.ExpressionNode;

import java.util.HashSet;

public class CellModel implements ExpressionCell {
    public Object Value;
    public String ShowValue;
    public String ErrorText;
    public HashSet<String> dependedCells;

    private ExpressionNode expression;

    public ExpressionNode getExpression() {
        return this.expression;
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    public void setChildCell(String childeCell) {
        if (dependedCells == null) {
            dependedCells = new HashSet<>();
        }
        this.dependedCells.add(childeCell);
    }

    public HashSet<String> getChildCells() {
        return dependedCells;
    }

    public void removeChildCell(String childCell) {
        this.dependedCells.remove(childCell);
    }
}

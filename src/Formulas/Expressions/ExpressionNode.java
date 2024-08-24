package Formulas.Expressions;

import Formulas.Language.DataType;

import java.util.HashSet;

public abstract class ExpressionNode {
    public abstract DataType getType();

    protected HashSet<String> parentCells;

    public HashSet<String> getParentCells() {
        return parentCells;
    }

    public void setParentCells(HashSet<String> parents) {
        this.parentCells = parents;
    }
}
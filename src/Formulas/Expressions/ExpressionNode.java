package Formulas.Expressions;

import Formulas.Language.DataType;

import java.util.HashSet;

public abstract class ExpressionNode {
    public abstract DataType getType();
    protected HashSet<String> dependencies;

    public HashSet<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(HashSet<String> dependencies) {
        this.dependencies = dependencies;
    }
}
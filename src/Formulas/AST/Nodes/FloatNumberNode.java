package Formulas.AST.Nodes;

import Formulas.AST.ASTNode;

import java.util.Map;

public class FloatNumberNode extends ASTNode {
    double value;

    public FloatNumberNode(double value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        return value;
    }
}

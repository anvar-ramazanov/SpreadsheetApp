package Formulas.AST.Nodes;

import Formulas.AST.ASTNode;

import java.util.Map;

public class BooleanNode extends ASTNode {
    boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        return value;
    }

    @Override
    public String getType() {
        return "BOOLEAN";
    }
}

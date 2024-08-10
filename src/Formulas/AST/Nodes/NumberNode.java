package Formulas.AST.Nodes;

import Formulas.AST.ASTNode;

import java.util.Map;

public class NumberNode extends ASTNode {
    int value;

    public NumberNode(int value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        return value;
    }

    @Override
    public String getType() {
        return "NUMBER";
    }
}

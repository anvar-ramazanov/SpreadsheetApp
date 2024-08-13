package Formulas.AST.Nodes;

import Formulas.AST.ASTNode;
import Formulas.NodeType;

import java.util.Map;

public class NumberNode extends ASTNode {
    double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        return value;
    }

    @Override
    public NodeType getType() {
        return NodeType.NUMBER;
    }
}

package Formulas.AST.Nodes;

import Formulas.AST.ASTNode;
import Formulas.NodeType;

import java.util.Map;

public class VariableNode extends ASTNode {
    String name;

    public VariableNode(String name) {
        this.name = name;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new RuntimeException("Variable not found: " + name);
    }

    @Override
    public NodeType getType() {
        return NodeType.VARIABLE;
    }
}


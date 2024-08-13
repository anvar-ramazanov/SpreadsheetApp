package Formulas.AST.Nodes;

import Formulas.AST.ASTNode;
import Formulas.NodeType;

import java.util.Map;

public class VariableNode extends ASTNode {
    private final String name;
    private final NodeType nodeType;

    public VariableNode(String name) {
        this.name = name;
        this.nodeType = NodeType.VARIABLE;
    }

    public VariableNode(String name, NodeType nodeType) {
        this.name = name;
        this.nodeType = nodeType;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new RuntimeException("Variable not found: " + name);
    }

    public String getName() {
        return name;
    }

    @Override
    public NodeType getType() {
        return this.nodeType;
    }
}


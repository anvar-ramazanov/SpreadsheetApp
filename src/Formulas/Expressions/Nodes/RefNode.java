package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.NodeType;

import java.util.Map;

public class RefNode extends ExpressionNode {
    private final String name;
    private final NodeType nodeType;

    public RefNode(String name) {
        this.name = name;
        this.nodeType = NodeType.NUMBER;
    }

    public RefNode(String name, NodeType nodeType) {
        this.name = name;
        this.nodeType = nodeType;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new RuntimeException("Variable not found: " + name); // FIXME
    }

    public String getName() {
        return name;
    }

    @Override
    public NodeType getType() {
        return this.nodeType;
    }
}


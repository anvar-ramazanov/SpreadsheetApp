package Formulas.AST;

import java.util.Map;

public abstract class ASTNode {
    public abstract Object evaluate(Map<String, Object> variables);
    public abstract String getType();
}
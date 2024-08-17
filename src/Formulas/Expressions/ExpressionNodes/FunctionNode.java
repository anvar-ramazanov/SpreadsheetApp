package Formulas.Expressions.ExpressionNodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.Grammar;
import Formulas.DataType;

import java.util.List;

public class FunctionNode extends ExpressionNode {
    private final String functionName;
    private final List<ExpressionNode> arguments;

    public FunctionNode(String functionName, List<ExpressionNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public DataType getType() {
        return Grammar.FunctionsDescription.get(this.functionName).resultType();
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }
}
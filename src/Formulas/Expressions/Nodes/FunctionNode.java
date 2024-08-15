package Formulas.Expressions.Nodes;

import Formulas.Expressions.ExpressionNode;
import Formulas.Grammar;
import Formulas.NodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionNode extends ExpressionNode {
    private final String functionName;
    private final List<ExpressionNode> arguments;

    public FunctionNode(String functionName, List<ExpressionNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        List<Object> evaluatedArgs = new ArrayList<>();
        for (ExpressionNode arg : arguments) {
            evaluatedArgs.add(arg.evaluate(variables));
        }

        switch (functionName) {
            case "SUM":
                double sum = 0;
                for (Object arg : evaluatedArgs) {
                    sum += (double) arg;
                }
                return sum;
            case "MIN":
                double min = Double.MAX_VALUE;
                for (Object arg : evaluatedArgs) {
                    min = Math.min(min, (double) arg);
                }
                return min;
            // Add more functions as needed
            default:
                throw new RuntimeException("Unknown function: " + functionName);
        }
    }

    @Override
    public NodeType getType() {
        return Grammar.FunctionsDescription.get(this.functionName).resultType();
    }
}
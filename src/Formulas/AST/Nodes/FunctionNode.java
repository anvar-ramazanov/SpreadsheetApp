package Formulas.AST.Nodes;

import Formulas.AST.ASTNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionNode extends ASTNode {
    String functionName;
    List<ASTNode> arguments;

    public FunctionNode(String functionName, List<ASTNode> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        List<Object> evaluatedArgs = new ArrayList<>();
        for (ASTNode arg : arguments) {
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
    public String getType() {
        return "FUNCTION";
    }
}
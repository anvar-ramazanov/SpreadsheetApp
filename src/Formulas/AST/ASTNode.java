package Formulas.AST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class ASTNode {
    public abstract Object evaluate(Map<String, Object> variables);
}

class NumberNode extends ASTNode {
    double value;

    NumberNode(double value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        return value;
    }
}

class BooleanNode extends ASTNode {
    boolean value;

    BooleanNode(boolean value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        return value;
    }
}

class VariableNode extends ASTNode {
    String name;

    VariableNode(String name) {
        this.name = name;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        }
        throw new RuntimeException("Variable not found: " + name);
    }
}

class FunctionNode extends ASTNode {
    String functionName;
    List<ASTNode> arguments;

    FunctionNode(String functionName, List<ASTNode> arguments) {
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
}

class BinaryOperationNode extends ASTNode {
    ASTNode left;
    String operator;
    ASTNode right;

    BinaryOperationNode(ASTNode left, String operator, ASTNode right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        Object leftValue = left.evaluate(variables);
        Object rightValue = right.evaluate(variables);

        switch (operator) {
            case "+": {
                var l = (leftValue instanceof Double) ?  (double) leftValue : Double.parseDouble((String)leftValue);
                var r =  (rightValue instanceof Double) ?  (double) rightValue : Double.parseDouble((String)rightValue);
                return  l + r;
            }
            case "-":
                return (double) leftValue - (double) rightValue;
            case "*":
                return (double) leftValue * (double) rightValue;
            case "/":
                return (double) leftValue / (double) rightValue;
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }
}

class UnaryOperationNode extends ASTNode {
    String operator;
    ASTNode operand;

    UnaryOperationNode(String operator, ASTNode operand) {
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    public Object evaluate(Map<String, Object> variables) {
        Object operandValue = operand.evaluate(variables);

        switch (operator) {
            case "-":
                return -(double) operandValue;
            case "!":
                return !(boolean) operandValue;
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }
}
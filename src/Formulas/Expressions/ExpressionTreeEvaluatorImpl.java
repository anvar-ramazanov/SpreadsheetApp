package Formulas.Expressions;

import Formulas.Exceptions.Evaluators.UnknownTypeOfNodeException;
import Formulas.Expressions.Evaluators.*;
import Formulas.Expressions.ExpressionNodes.*;
import Formulas.Language.ExpressionLanguage;
import Models.Cell.ExpressionCell;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpressionTreeEvaluatorImpl implements ExpressionTreeEvaluator {

    private final Map<ExpressionNode, Object> cache = new HashMap<>();;

    public Object EvaluateExpressionTree(ExpressionNode node, Map<String, ExpressionCell> context) {
        cache.clear();
        return EvaluateNode(node, context);
    }

    private Object EvaluateNode(ExpressionNode node, Map<String, ExpressionCell> context) {
        // Check if the node is already evaluated and cached
        if (cache.containsKey(node)) {
            return cache.get(node);
        }

        Object result;

        if (node instanceof UnaryOperationNode unaryOperationNode) {
            result = EvaluateUnaryOperation(unaryOperationNode, context);
        } else if (node instanceof BinaryOperationNode binaryOperationNode) {
            result = EvaluateBinaryOperation(binaryOperationNode, context);
        } else if (node instanceof FunctionNode functionNode) {
            result = EvaluateFunction(functionNode, context);
        } else if (node instanceof ReferencesNode refNode) {
            result = EvaluateReferences(refNode, context);
        } else if (node instanceof NumberNode numberNode)  {
            result = numberNode.getValue();
        } else if (node instanceof BooleanNode booleanNode) {
            result = booleanNode.getValue();
        } else if (node instanceof StringNode stringNode) {
            result = stringNode.getValue();
        } else {
            if (node != null) {
                throw new UnknownTypeOfNodeException("Unknown type of node: " + node.getType());
            }
            throw new UnknownTypeOfNodeException("Node is null");
        }

        // Store the result in the cache
        cache.put(node, result);
        return result;
    }


    private Object EvaluateReferences(ReferencesNode refNode, Map<String, ExpressionCell> context) {
        var nextNodeName = refNode.getReferences();
        var nextNode = context.get(nextNodeName).getExpression();
        return EvaluateNode(nextNode, context);
    }

    private Object EvaluateFunction(FunctionNode functionNode, Map<String, ExpressionCell> context) {
        var functionName = functionNode.getFunctionName();
        var evaluator = (FunctionEvaluator) ExpressionLanguage.FunctionsDescription.get(functionName).evaluator();
        var arguments = functionNode.getArguments();
        return evaluator.evaluate(arguments
                .stream()
                .map(n -> EvaluateNode(n, context))
                .collect(Collectors.toList())
        );
    }

    private Object EvaluateBinaryOperation(BinaryOperationNode binaryOperationNode, Map<String, ExpressionCell> context) {
        var operator = binaryOperationNode.getOperator();
        var leftOperand = binaryOperationNode.getLeftOperand();
        var rightOperand = binaryOperationNode.getRightOperand();
        var evaluator = (BinaryOperationEvaluator) ExpressionLanguage.BinaryOperations.get(operator).evaluator();
        return evaluator.evaluate(EvaluateNode(leftOperand, context), EvaluateNode(rightOperand, context));
    }

    private Object EvaluateUnaryOperation(UnaryOperationNode node, Map<String, ExpressionCell> context) {
        var operator = node.getOperator();
        var operand = node.getOperand();
        var evaluator = (UnaryOperationEvaluator) ExpressionLanguage.UnaryOperations.get(operator).evaluator();
        return evaluator.evaluate(EvaluateNode(operand, context));
    }
}

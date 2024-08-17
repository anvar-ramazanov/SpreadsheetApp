package Formulas.Expressions;

import Formulas.Exceptions.Evaluators.UnknownTypeOfNodeException;
import Formulas.Expressions.Evaluators.*;
import Formulas.Expressions.ExpressionNodes.*;
import Formulas.Language.ExpressionLanguage;

import java.util.Map;
import java.util.stream.Collectors;

public class ExpressionTreeEvaluator {

    public Object EvaluateExpressionTree(String nodeToStart, Map<String, ExpressionNode> context) {
        return EvaluateNode(context.get(nodeToStart), context);
    }

    private Object EvaluateNode(ExpressionNode node, Map<String, ExpressionNode> context) {
        if (node instanceof UnaryOperationNode unaryOperationNode) {
            return EvaluateUnaryOperation(unaryOperationNode, context);
        } else if (node instanceof BinaryOperationNode binaryOperationNode) {
            return EvaluateBinaryOperation(binaryOperationNode, context);
        } else if (node instanceof FunctionNode functionNode) {
            return EvaluateFunction(functionNode, context);
        } else if (node instanceof ReferencesNode refNode) {
            return EvaluateReferences(refNode, context);
        } else if (node instanceof NumberNode numberNode)  {
            return numberNode.getValue();
        } else if (node instanceof BooleanNode booleanNode) {
            return booleanNode.getValue();
        } else if (node instanceof StringNode stringNode) {
            return stringNode.getValue();
        }
        throw new UnknownTypeOfNodeException("Unknow type of node: " + node.getType());
    }

    private Object EvaluateReferences(ReferencesNode refNode, Map<String, ExpressionNode> context) {
        var nextNodeName = refNode.getReferences();
        var nextNode = context.get(nextNodeName);
        return EvaluateNode(nextNode, context);
    }

    private Object EvaluateFunction(FunctionNode functionNode, Map<String, ExpressionNode> context) {
        var functionName = functionNode.getFunctionName();
        var evaluator = (FunctionEvaluator) ExpressionLanguage.FunctionsDescription.get(functionName).evaluator();
        var arguments = functionNode.getArguments();
        return evaluator.evaluate(arguments
                .stream()
                .map(n -> EvaluateNode(n, context))
                .collect(Collectors.toList())
        );
    }

    private Object EvaluateBinaryOperation(BinaryOperationNode binaryOperationNode, Map<String, ExpressionNode> context) {
        var operator = binaryOperationNode.getOperator();
        var leftOperand = binaryOperationNode.getLeftOperand();
        var rightOperand = binaryOperationNode.getRightOperand();
        var evaluator = (BinaryOperationEvaluator) ExpressionLanguage.BinaryOperations.get(operator).evaluator();
        return evaluator.evaluate(EvaluateNode(leftOperand, context), EvaluateNode(rightOperand, context));
    }

    private Object EvaluateUnaryOperation(UnaryOperationNode node, Map<String, ExpressionNode> context) {
        var operator = node.getOperator();
        var operand = node.getOperand();
        var evaluator = (UnaryOperationEvaluator) ExpressionLanguage.UnaryOperations.get(operator).evaluator();
        return evaluator.evaluate(EvaluateNode(operand, context));
    }
}

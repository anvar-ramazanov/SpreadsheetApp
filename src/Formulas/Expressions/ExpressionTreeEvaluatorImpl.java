package Formulas.Expressions;

import Formulas.Exceptions.Evaluators.UnknownTypeOfNodeException;
import Formulas.Expressions.Evaluators.*;
import Formulas.Expressions.ExpressionNodes.*;
import Formulas.Language.ExpressionLanguage;
import Models.Cell.ExpressionCell;

import java.util.Map;
import java.util.stream.Collectors;

public class ExpressionTreeEvaluatorImpl implements ExpressionTreeEvaluator {

    public Object EvaluateExpressionTree(ExpressionNode node, Map<String, ExpressionCell> context) {
        return EvaluateNode(node, context);
    }

    private Object EvaluateNode(ExpressionNode node, Map<String, ExpressionCell> context) {
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
        if (node != null) {
            throw new UnknownTypeOfNodeException("Unknown type of node: " + node.getType());
        }
        throw new UnknownTypeOfNodeException("Node is null");
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

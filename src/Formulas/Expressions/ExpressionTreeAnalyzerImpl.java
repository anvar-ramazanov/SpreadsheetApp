package Formulas.Expressions;

import Formulas.Exceptions.Expressions.TreeAnalyzer.*;
import Formulas.Expressions.ExpressionNodes.*;
import Formulas.Language.ExpressionLanguage;
import Formulas.Language.DataType;
import Models.Cell.ExpressionCell;

import java.util.LinkedHashSet;
import java.util.Map;

public class ExpressionTreeAnalyzerImpl implements ExpressionTreeAnalyzer {

    public void AnalyzeExpressionTree(ExpressionNode expressionNode, String nodeName, Map<String, ExpressionCell> context, boolean reuseComputedTypes) {
        var visitedNodes = new LinkedHashSet<String>();
        AnalyzeNode(expressionNode, nodeName, context, visitedNodes, reuseComputedTypes);
    }

    private DataType AnalyzeNode(ExpressionNode node, String nodeName, Map<String, ExpressionCell> context, LinkedHashSet<String> visitedNodes, boolean reuseComputedTypes) {
        DataType nodeType = null;
        visitedNodes.add(nodeName);
        if (node instanceof UnaryOperationNode unaryOperationNode) {
            nodeType = AnalyzeUnaryOperationNode(unaryOperationNode, nodeName, context, visitedNodes, reuseComputedTypes);
        } else if (node instanceof BinaryOperationNode binaryOperationNode) {
            nodeType = AnalyzeBinaryOperationNode(binaryOperationNode, nodeName, context, visitedNodes, reuseComputedTypes);
        } else if (node instanceof FunctionNode functionNode) {
            nodeType = AnalyzeFunctionNode(functionNode, nodeName, context, visitedNodes, reuseComputedTypes);
        } else if (node instanceof ReferencesNode refNode) {
            nodeType = AnalyzeRefNode(refNode, nodeName, context, visitedNodes, reuseComputedTypes);
        } else if (node instanceof NumberNode) {
            nodeType = DataType.NUMBER;
        } else if (node instanceof BooleanNode) {
            nodeType = DataType.BOOLEAN;
        } else if (node instanceof StringNode) {
            nodeType = DataType.STRING;
        }
        return nodeType;
    }

    private DataType AnalyzeUnaryOperationNode(UnaryOperationNode node, String nodeName, Map<String, ExpressionCell> context, LinkedHashSet<String> visitedNodes, boolean reuseComputedTypes) {
        var operator = node.getOperator();
        var operand = node.getOperand();

        AnalyzeNode(operand, nodeName, context, visitedNodes, reuseComputedTypes);

        if (ExpressionLanguage.UnaryOperations.get(node.getOperator()).operandType() != node.getOperand().getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " operand type " + node.getOperand().getType());
        }

        return ExpressionLanguage.UnaryOperations.get(node.getOperator()).resultType();
    }

    private DataType AnalyzeBinaryOperationNode(BinaryOperationNode node, String nodeName, Map<String, ExpressionCell> context, LinkedHashSet<String> visitedNodes, boolean reuseComputedTypes) {
        var operator = node.getOperator();
        var leftOperand = node.getLeftOperand();
        var rightOperand = node.getRightOperand();

        AnalyzeNode(leftOperand, nodeName, context, visitedNodes, reuseComputedTypes);
        AnalyzeNode(rightOperand, nodeName, context, visitedNodes, reuseComputedTypes);

        if (ExpressionLanguage.BinaryOperations.get(operator).leftOperandType() != leftOperand.getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " left operand type " + node.getLeftOperand().getType());
        }

        if (ExpressionLanguage.BinaryOperations.get(operator).rightOperandType() != rightOperand.getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " right operand type " + node.getLeftOperand().getType());
        }

        return ExpressionLanguage.BinaryOperations.get(operator).resultType();
    }

    private DataType AnalyzeFunctionNode(FunctionNode node, String nodeName, Map<String, ExpressionCell> context, LinkedHashSet<String> visitedNodes, boolean reuseComputedTypes) {
        var functionName = node.getFunctionName();
        var arguments = node.getArguments();
        var description = ExpressionLanguage.FunctionsDescription.get(functionName);
        if (description.arguments().size() != arguments.size()) {
            throw new ArgumentsNumberMismatchException("Arguments number mismatch for function " + functionName + " Awaited = " + description.arguments().size() + " but has " + arguments.size());
        }
        for (int i = 0; i < arguments.size(); ++i) {
            var allowedType = description.arguments().get(i);
            var argument = arguments.get(i);

            AnalyzeNode(argument, nodeName, context, visitedNodes, reuseComputedTypes);

            var currentType = arguments.get(i).getType();

            if (allowedType != currentType) {
                throw new ArgumentTypeMismatchException("Argument type mismatch for function " + functionName + " Awaited = " + allowedType + " but has " + currentType);
            }
        }
        return ExpressionLanguage.FunctionsDescription.get(functionName).resultType();
    }

    private DataType AnalyzeRefNode(ReferencesNode node, String nodeName, Map<String, ExpressionCell> context, LinkedHashSet<String> visitedNodes, boolean reuseComputedTypes) {
        var nextNodeName = node.getReferences();
        if (nextNodeName.equals(nodeName)) {
            throw new CircularDependencyException("Cell contains circular dependency", visitedNodes);
        }
        if (visitedNodes.contains(nextNodeName)) {
            throw new CircularDependencyException("Cell contains circular dependency", visitedNodes);
        }
        if (!context.containsKey(nextNodeName)) {
            throw new InvalidReferenceException("Cell " + nextNodeName + " doesn't exist");
        }
        var nextNode = context.get(nextNodeName);

        var nextNodeType = reuseComputedTypes
                ? nextNode.getExpression().getType()
                : AnalyzeNode(nextNode.getExpression(), nextNodeName, context, visitedNodes, reuseComputedTypes);

        if (nextNode.hasError()) {
            throw new InvalidReferenceException("Cell " + nextNodeName + " has error");
        }

        if (nextNodeType == null) {
            throw new InvalidReferenceException("Cell " + nextNodeName + "has unknown type of cell");
        }

        // After the recursive call, remove the node from visited set so it doesn't falsely trigger circular dependencies in parallel branches.
        visitedNodes.remove(nextNodeName);

        node.setReferenceType(nextNodeType);

        return nextNodeType;
    }
}

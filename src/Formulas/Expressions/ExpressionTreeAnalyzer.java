package Formulas.Expressions;

import Formulas.Exceptions.Expressions.TreeAnalyzer.*;
import Formulas.Expressions.Nodes.*;
import Formulas.Grammar;
import Formulas.NodeType;

import java.util.HashSet;
import java.util.Map;

public class ExpressionTreeAnalyzer {

    public void AnalyzeExpressionTree(Map<String, ExpressionNode> nodes, String nodeToStart) {
        var visitedNodes = new HashSet<ExpressionNode>();
        visitedNodes.add(nodes.get(nodeToStart));
        AnalyzeNode(nodes.get(nodeToStart), nodes, visitedNodes);
    }

    private NodeType AnalyzeNode(ExpressionNode node, Map<String, ExpressionNode> nodes, HashSet<ExpressionNode> visitedNodes) {
        NodeType nodeType = null;
        if (node instanceof UnaryOperationNode unaryOperationNode) {
            nodeType = AnalyzeUnaryOperationNode(unaryOperationNode, nodes, visitedNodes);
        } else if (node instanceof BinaryOperationNode binaryOperationNode) {
            nodeType = AnalyzeBinaryOperationNode(binaryOperationNode, nodes, visitedNodes);
        } else if (node instanceof FunctionNode functionNode) {
            nodeType = AnalyzeFunctionNode(functionNode, nodes, visitedNodes);
        } else if (node instanceof ReferencesNode refNode) {
            nodeType = AnalyzeRefNode(refNode, nodes, visitedNodes);
        } else if (node instanceof NumberNode)  {
            nodeType = NodeType.NUMBER;
        } else if (node instanceof BooleanNode) {
            nodeType = NodeType.BOOLEAN;
        } else if (node instanceof StringNode) {
            nodeType = NodeType.STRING;
        }
        return nodeType;
    }

    private NodeType AnalyzeUnaryOperationNode(UnaryOperationNode node, Map<String, ExpressionNode> nodes, HashSet<ExpressionNode> visitedNodes) {
        var operator = node.getOperator();
        var operand = node.getOperand();

        AnalyzeNode(operand, nodes, visitedNodes);

        if (Grammar.UnaryOperations.get(node.getOperator()).operandType() != node.getOperand().getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " operand type " + node.getOperand().getType());
        }

        return Grammar.UnaryOperations.get(node.getOperator()).resultType();
    }

    private NodeType AnalyzeBinaryOperationNode(BinaryOperationNode node, Map<String, ExpressionNode> nodes, HashSet<ExpressionNode> visitedNodes) {
        var operator = node.getOperator();
        var leftOperand = node.getLeftOperand();
        var rightOperand = node.getRightOperand();

        AnalyzeNode(leftOperand, nodes, visitedNodes);
        AnalyzeNode(rightOperand, nodes, visitedNodes);

        if (Grammar.BinaryOperations.get(operator).leftOperandType() != leftOperand.getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " left operand type " + node.getLeftOperand().getType());
        }

        if (Grammar.BinaryOperations.get(operator).rightOperandType() != rightOperand.getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " right operand type " + node.getLeftOperand().getType());
        }

        return Grammar.BinaryOperations.get(operator).resultType();
    }

    private NodeType AnalyzeFunctionNode(FunctionNode node, Map<String, ExpressionNode> nodes, HashSet<ExpressionNode> visitedNodes) {
        var functionName = node.getFunctionName();
        var arguments = node.getArguments();
        var description = Grammar.FunctionsDescription.get(functionName);
        if (description.arguments().size() != arguments.size()) {
            throw new ArgumentsNumberMismatchException("Arguments number mismatch for function " + functionName + " Awaited = " + description.arguments().size() + " but has " + arguments.size());
        }
        for (int i = 0; i < arguments.size(); ++i) {
            var allowedType = description.arguments().get(i);
            var argument = arguments.get(i);

            AnalyzeNode(argument, nodes, visitedNodes);

            var currentType = arguments.get(i).getType();

            if (allowedType != currentType) {
                throw new ArgumentTypeMismatchException("Argument type mismatch for function " + functionName + " Awaited = " + allowedType + " but has " + currentType);
            }
        }
        return Grammar.FunctionsDescription.get(functionName).resultType();
    }

    private NodeType AnalyzeRefNode(ReferencesNode node, Map<String, ExpressionNode> nodes, HashSet<ExpressionNode> visitedNodes) {
        var nextNodeName = node.getReferences();
        if (!nodes.containsKey(nextNodeName)) {
            throw new InvalidReferenceException("Node " + nextNodeName + " doesn't exist");
        }
        var nextNode = nodes.get(nextNodeName);
        if (visitedNodes.contains(nextNode)) {
            throw new CircularDependencyException();
        }

        var nextNodeType = AnalyzeNode(nextNode, nodes, visitedNodes);

        node.setReferenceType(nextNodeType);

        return nextNodeType;
    }
}

package Formulas.Expressions;

import Formulas.Exceptions.Expressions.TreeAnalyzer.ArgumentTypeMismatchException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.ArgumentsNumberMismatchException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.InvalidReferenceException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.OperandTypeMismatchException;
import Formulas.Expressions.Nodes.*;
import Formulas.Grammar;
import Formulas.NodeType;

import java.util.Map;

public class ExpressionTreeAnalyzer {
    public void AnalyzeExpressionTree(Map<String, ExpressionNode> nodes, String nodeToStart) {
        AnalyzeNode(nodes.get(nodeToStart), nodes);
    }

    private NodeType AnalyzeNode(ExpressionNode node, Map<String, ExpressionNode> nodes) {
        NodeType nodeType = null;
        if (node instanceof UnaryOperationNode unaryOperationNode) {
            nodeType = AnalyzeUnaryOperationNode(unaryOperationNode, nodes);
        } else if (node instanceof BinaryOperationNode binaryOperationNode) {
            nodeType = AnalyzeBinaryOperationNode(binaryOperationNode, nodes);
        } else if (node instanceof FunctionNode functionNode) {
            nodeType = AnalyzeFunctionNode(functionNode, nodes);
        } else if (node instanceof RefNode refNode) {
            nodeType = AnalyzeRefNode(refNode, nodes);
        } else if (node instanceof NumberNode)
        {
            nodeType = NodeType.NUMBER;
        } else if (node instanceof BooleanNode)
        {
            nodeType = NodeType.BOOLEAN;
        }
        return nodeType;
    }

    private NodeType AnalyzeUnaryOperationNode(UnaryOperationNode node, Map<String, ExpressionNode> nodes) {
        var operator = node.getOperator();
        var operand = node.getOperand();

        AnalyzeNode(operand, nodes);

        if (Grammar.UnaryOperations.get(node.getOperator()).operandType() != node.getOperand().getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " operand type " + node.getOperand().getType());
        }

        return Grammar.UnaryOperations.get(node.getOperator()).resultType();
    }

    private NodeType AnalyzeBinaryOperationNode(BinaryOperationNode node, Map<String, ExpressionNode> nodes) {
        var operator = node.getOperator();
        var leftOperand = node.getLeftOperand();
        var rightOperand = node.getRightOperand();

        AnalyzeNode(leftOperand, nodes);
        AnalyzeNode(rightOperand, nodes);

        if (Grammar.BinaryOperations.get(operator).leftOperandType() != leftOperand.getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " left operand type " + node.getLeftOperand().getType());
        }

        if (Grammar.BinaryOperations.get(operator).rightOperandType() != rightOperand.getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " right operand type " + node.getLeftOperand().getType());
        }

        return Grammar.BinaryOperations.get(operator).resultType();
    }

    private NodeType AnalyzeFunctionNode(FunctionNode node, Map<String, ExpressionNode> nodes) {
        var functionName = node.getFunctionName();
        var arguments = node.getArguments();
        var description = Grammar.FunctionsDescription.get(functionName);
        if (description.arguments().size() != arguments.size()) {
            throw new ArgumentsNumberMismatchException("Arguments number mismatch for function " + functionName + " Awaited = " + description.arguments().size() + " but has " + arguments.size());
        }
        for (int i = 0; i < arguments.size(); ++i) {
            var allowedTypes = description.arguments().get(i);
            var argument = arguments.get(i);

            AnalyzeNode(argument, nodes);

            var currentType = arguments.get(i).getType();

            if (!allowedTypes.contains(currentType)) {
                throw new ArgumentTypeMismatchException("Argument type mismatch for function " + functionName + " Awaited = " + allowedTypes + " but has " + currentType);
            }
        }
        return Grammar.FunctionsDescription.get(functionName).resultType();
    }

    private NodeType AnalyzeRefNode(RefNode node, Map<String, ExpressionNode> nodes) {
        var nextNodeName = node.getName();
        if (!nodes.containsKey(nextNodeName)) {
            throw new InvalidReferenceException("Node " + nextNodeName + " doesn't exist");
        }
        var nextNode = nodes.get(nextNodeName);

        var nextNodeType = AnalyzeNode(nextNode, nodes);

        node.setType(nextNodeType);

        return nextNodeType;
    }
}

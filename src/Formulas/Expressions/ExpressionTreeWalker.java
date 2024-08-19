package Formulas.Expressions;

import Formulas.Expressions.ExpressionNodes.*;

import java.util.HashSet;
import java.util.Map;

public class ExpressionTreeWalker {
    public  HashSet<String> GetDependedNodes(String nodeToStart, Map<String, ExpressionNode> context) {
        var dependedNodes = new HashSet<String>();
        WalkNode(context.get(nodeToStart), context, dependedNodes);
        return dependedNodes;
    }

    private void WalkNode(ExpressionNode node, Map<String, ExpressionNode> context, HashSet<String> dependedNodes) {
        if (node instanceof UnaryOperationNode unaryOperationNode) {
            WalkUnaryOperationNode(unaryOperationNode, context, dependedNodes);
        } else if (node instanceof BinaryOperationNode binaryOperationNode) {
            WalkBinaryOperationNode(binaryOperationNode, context, dependedNodes);
        } else if (node instanceof FunctionNode functionNode) {
            WalkFunctionNode(functionNode, context, dependedNodes);
        } else if (node instanceof ReferencesNode refNode) {
            WalkRefNode(refNode, context, dependedNodes);
        }
    }


    private void WalkUnaryOperationNode(UnaryOperationNode node, Map<String, ExpressionNode> context, HashSet<String> dependedNodes) {
        var operand = node.getOperand();
        WalkNode(operand, context, dependedNodes);
    }

    private void WalkBinaryOperationNode(BinaryOperationNode node, Map<String, ExpressionNode> context, HashSet<String> dependedNodes) {
        var leftOperand = node.getLeftOperand();
        var rightOperand = node.getRightOperand();

        WalkNode(leftOperand, context, dependedNodes);
        WalkNode(rightOperand, context, dependedNodes);
    }

    private void WalkFunctionNode(FunctionNode node, Map<String, ExpressionNode> context, HashSet<String> dependedNodes) {
        var arguments = node.getArguments();
        for (int i = 0; i < arguments.size(); ++i) {
            var argument = arguments.get(i);
            WalkNode(argument, context, dependedNodes);
        }
    }

    private void WalkRefNode(ReferencesNode node, Map<String, ExpressionNode> context, HashSet<String> dependedNodes) {
        var nextNodeName = node.getReferences();
        dependedNodes.add(nextNodeName);
        if (context.containsKey(nextNodeName)) {
            var nextNode = context.get(nextNodeName);
            WalkNode(nextNode, context, dependedNodes);
        }
    }
}

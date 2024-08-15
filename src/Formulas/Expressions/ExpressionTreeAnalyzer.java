package Formulas.Expressions;

import Formulas.Exceptions.Expressions.TreeAnalyzer.ArgumentTypeMismatchException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.ArgumentsNumberMismatchException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.OperandTypeMismatchException;
import Formulas.Expressions.Nodes.BinaryOperationNode;
import Formulas.Expressions.Nodes.FunctionNode;
import Formulas.Expressions.Nodes.RefNode;
import Formulas.Expressions.Nodes.UnaryOperationNode;
import Formulas.Grammar;

public class ExpressionTreeAnalyzer {
    public void AnalyzeExpressionTree(ExpressionNode currentNode)
    {
        AnalyzeNode(currentNode);
    }

    private void AnalyzeNode(ExpressionNode node) {
        if (node instanceof UnaryOperationNode unaryOperationNode) {
            AnalyzeUnaryOperationNode(unaryOperationNode);
        } else if (node instanceof BinaryOperationNode binaryOperationNode) {
            AnalyzeBinaryOperationNode(binaryOperationNode);
        }
        else if (node instanceof FunctionNode functionNode) {
            AnalyzeFunctionNode(functionNode);
        } else if (node instanceof RefNode refNode) {

        }
    }

    private void AnalyzeUnaryOperationNode(UnaryOperationNode node) {
        var operator = node.getOperator();
        var operand = node.getOperand();

        AnalyzeExpressionTree(operand);

        if (Grammar.UnaryOperations.get(node.getOperator()).operandType() != node.getOperand().getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " operand type " + node.getOperand().getType());
        }
    }

    private void AnalyzeBinaryOperationNode(BinaryOperationNode node) {
        var operator = node.getOperator();
        var leftOperand = node.getLeftOperand();
        var rightOperand  = node.getRightOperand();

        AnalyzeNode(leftOperand);
        AnalyzeNode(rightOperand);

        if (Grammar.BinaryOperations.get(operator).leftOperandType() != leftOperand.getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " left operand type " + node.getLeftOperand().getType());
        }

        if (Grammar.BinaryOperations.get(operator).rightOperandType() != rightOperand.getType()) {
            throw new OperandTypeMismatchException("Operator = " + operator + " right operand type " + node.getLeftOperand().getType());
        }
    }

    private void AnalyzeFunctionNode(FunctionNode node) {
        var functionName = node.getFunctionName();
        var arguments = node.getArguments();
        var description = Grammar.FunctionsDescription.get(functionName);
        if (description.arguments().size() != arguments.size()) {
            throw new ArgumentsNumberMismatchException("Arguments number mismatch for function " + functionName + " Awaited = " + description.arguments().size() + " but has " + arguments.size());
        }
        for (int i = 0; i < arguments.size(); ++i) {
            var allowedTypes = description.arguments().get(i);
            var argument = arguments.get(i);

            AnalyzeNode(argument);

            var currentType = arguments.get(i).getType();

            if (!allowedTypes.contains(currentType)) {
                throw new ArgumentTypeMismatchException("Argument type mismatch for function " + functionName +" Awaited = " + allowedTypes + " but has " + currentType);
            }
        }
    }

    private void AnalyzeRefNode(RefNode node) {
//        if (otherCells.containsKey(token.value))
//        {
//            return new RefNode(token.value, otherCells.get(token.value).getType());
//        }
    }
}

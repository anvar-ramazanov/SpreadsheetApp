package Formulas.Expressions;

import Formulas.Expressions.Nodes.FunctionNode;
import Formulas.Expressions.Nodes.RefNode;
import Formulas.Expressions.Nodes.UnaryOperationNode;

import java.util.Map;

public class ExpressionTreeAnalyzer {
    public boolean AnalyzeExpressionTree(ExpressionNode currentNode)
    {


        return true;
    }

    public void AnalyzeUnaryOperationNode(UnaryOperationNode node) {
//        if (Grammar.UnaryOperations.get(token.value).operandType() != operand.getType()) {
//            throw new RuntimeException("Operand type mistmatch");
//        }
//        if (operand instanceof FunctionNode functionNode) {
//            if (Grammar.FunctionsDescription.get(functionNode.getFunctionName()).resultType() != Grammar.UnaryOperations.get(token.value).resultType()) {
//                throw new RuntimeException("Operand type mistmatch");
//            }
//        }
//        if (operand instanceof RefNode variableNode) {
//            if (!otherCells.containsKey(variableNode.getName()) || otherCells.get(variableNode.getName()).getType() !=  Grammar.UnaryOperations.get(token.value).resultType()) {
//                throw new RuntimeException("Operand type mistmatch");
//            }
//        }
    }

    public void AnalyzeRefNode(RefNode node) {
//        if (otherCells.containsKey(token.value))
//        {
//            return new RefNode(token.value, otherCells.get(token.value).getType());
//        }
    }

    public void AnalyzeFunctionNode(FunctionNode node) {
//        var description = Grammar.FunctionsDescription.get(functionName);
//        if (description.arguments().size() != arguments.size()) {
//            throw new RuntimeException("Arguments number mistmach");
//        }
//        for (int i = 0; i < arguments.size(); ++i) {
//            var allowedTypes = description.arguments().get(i);
//            var currentType = arguments.get(i).getType();
//            if (!allowedTypes.contains(currentType)) {
//                throw new RuntimeException("Argument type mistmatch");
//            }
//        }
    }
}

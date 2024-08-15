package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.OperandTypeMismatchException;
import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.Nodes.*;
import Formulas.NodeType;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class UnaryOperationTests {

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_ValidOperator() {
        var node = new UnaryOperationNode("-", new NumberNode(2));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }


    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidOperandType() {
        var node = new UnaryOperationNode("-", new BooleanNode(false));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_NestedFunctionOperand() {
        var node = new UnaryOperationNode("-", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidNestedFunctionOperand() {
        var node = new UnaryOperationNode("!", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidNestedBinaryOperation() {
        var node = new UnaryOperationNode("!",  new BinaryOperationNode("+", new NumberNode(2), new NumberNode(3), NodeType.NUMBER));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_ValidNestedBinaryOperation() {
        var node = new UnaryOperationNode("!",  new BinaryOperationNode(">", new NumberNode(2), new NumberNode(3), NodeType.BOOLEAN));
        var nodes = Map.of("A1", (ExpressionNode)node);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }


    //    @Test
//    public void ASTParser_ParseUnaryOperator_VariableOperand() {
//        var tokens = new ArrayList<Token>();
//        tokens.add(new Token(TokenType.OPERATOR, "-"));
//        tokens.add(new Token(TokenType.VARIABLE, "A2"));
//
//        var parser = new ExpressionTreeParser(tokens, Map.of("A2", new NumberNode(2)));
//
//        var result = parser.parse();
//
//        assertNotEquals(null, result);
//        assertTrue(result instanceof UnaryOperationNode);
//
//        var unaryOperationNode = (UnaryOperationNode)result;
//        var operand = unaryOperationNode.getOperand();
//
//        assertNotEquals(null, operand);
//    }
}

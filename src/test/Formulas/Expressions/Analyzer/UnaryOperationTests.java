package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.OperandTypeMismatchException;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.Nodes.BooleanNode;
import Formulas.Expressions.Nodes.FunctionNode;
import Formulas.Expressions.Nodes.NumberNode;
import Formulas.Expressions.Nodes.UnaryOperationNode;
import org.junit.Test;

import java.util.List;

public class UnaryOperationTests {

    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_ValidOperator() {
        var node = new UnaryOperationNode("-", new NumberNode(2));

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(node);
    }


    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidOperandType() {
        var node = new UnaryOperationNode("-", new BooleanNode(false));

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(node);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_NestedFunctionOperand() {
        var node = new UnaryOperationNode("-", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(node);
    }

    @Test(expected = OperandTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_UnaryOperation_InvalidNestedFunctionOperand() {
        var node = new UnaryOperationNode("!", new FunctionNode("MIN", List.of(new NumberNode(1), new NumberNode(2))));

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(node);
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

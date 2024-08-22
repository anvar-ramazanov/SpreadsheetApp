package test.Formulas.Expressions.Parser;

import Formulas.Expressions.ExpressionTreeParserImpl;

import Formulas.Expressions.ExpressionNodes.BinaryOperationNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionNodes.UnaryOperationNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class OperationsOrderTest {
    @Test
    public void ExpressionParser_Parse_CorrectOrderWithBinaryOperatorAndParenthesis() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "*"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var multiplyNode = (BinaryOperationNode)result;
        assertNotEquals(null, multiplyNode.getLeftOperand());
        assertTrue( multiplyNode.getLeftOperand() instanceof NumberNode);
        assertNotEquals(null, multiplyNode.getRightOperand());
        assertTrue( multiplyNode.getRightOperand() instanceof BinaryOperationNode);

        var addNode = (BinaryOperationNode) multiplyNode.getRightOperand();
        assertNotEquals(null, addNode.getLeftOperand());
        assertTrue( addNode.getLeftOperand() instanceof NumberNode);
        assertNotEquals(null, addNode.getRightOperand());
        assertTrue( addNode.getRightOperand() instanceof NumberNode);
    }

    @Test
    public void ExpressionParser_Parse_CorrectOrderWithBinaryAndUnaryOperator() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "*"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var multiplyNode = (BinaryOperationNode)result;
        assertNotEquals(null, multiplyNode.getLeftOperand());
        assertTrue( multiplyNode.getLeftOperand() instanceof UnaryOperationNode);
        assertNotEquals(null, multiplyNode.getRightOperand());
        assertTrue( multiplyNode.getRightOperand() instanceof NumberNode);
    }

    @Test
    public void ExpressionParser_Parse_CorrectOrderWithBinaryAndUnaryOperatorSameLevel() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var subtractNode = (BinaryOperationNode)result;
        assertNotEquals(null, subtractNode.getLeftOperand());
        assertTrue( subtractNode.getLeftOperand() instanceof UnaryOperationNode);
        assertNotEquals(null, subtractNode.getRightOperand());
        assertTrue( subtractNode.getRightOperand() instanceof NumberNode);
    }


}
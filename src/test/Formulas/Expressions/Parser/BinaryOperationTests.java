package test.Formulas.Expressions.Parser;

import Formulas.Exceptions.Expressions.TreeParser.UnexpectedTokenException;
import Formulas.Expressions.ExpressionTreeParserImpl;
import Formulas.Expressions.ExpressionNodes.BinaryOperationNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BinaryOperationTests {
    @Test
    public void ExpressionParser_ParseBinaryOperator_Plus() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var binaryOperationNode = (BinaryOperationNode)result;
        var left = binaryOperationNode.getLeftOperand();
        var right = binaryOperationNode.getRightOperand();

        assertNotEquals(null, left);
        assertNotEquals(null, right);
    }

    @Test
    public void ExpressionParser_ParseBinaryOperator_Minus() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var binaryOperationNode = (BinaryOperationNode)result;
        var left = binaryOperationNode.getLeftOperand();
        var right = binaryOperationNode.getRightOperand();

        assertNotEquals(null, left);
        assertNotEquals(null, right);
    }

    @Test
    public void ExpressionParser_ParseBinaryOperator_Divide() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "/"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var binaryOperationNode = (BinaryOperationNode)result;
        var left = binaryOperationNode.getLeftOperand();
        var right = binaryOperationNode.getRightOperand();

        assertNotEquals(null, left);
        assertNotEquals(null, right);
    }

    @Test
    public void ExpressionParser_ParseBinaryOperator_Multiply() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "*"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var binaryOperationNode = (BinaryOperationNode)result;
        var left = binaryOperationNode.getLeftOperand();
        var right = binaryOperationNode.getRightOperand();

        assertNotEquals(null, left);
        assertNotEquals(null, right);
    }

    @Test
    public void ExpressionParser_ParseBinaryOperator_More() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, ">"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var binaryOperationNode = (BinaryOperationNode)result;
        var left = binaryOperationNode.getLeftOperand();
        var right = binaryOperationNode.getRightOperand();

        assertNotEquals(null, left);
        assertNotEquals(null, right);
    }

    @Test
    public void ExpressionParser_ParseBinaryOperator_Less() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, ">"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var binaryOperationNode = (BinaryOperationNode)result;
        var left = binaryOperationNode.getLeftOperand();
        var right = binaryOperationNode.getRightOperand();

        assertNotEquals(null, left);
        assertNotEquals(null, right);
    }

    @Test(expected = UnexpectedTokenException.class)
    public void ExpressionParser_ParseBinaryOperator_TwoOperators() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));

        var parser = new ExpressionTreeParserImpl();

        parser.parse(tokens);
    }

}

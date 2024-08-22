package test.Formulas.Expressions.Parser;

import Formulas.Exceptions.Expressions.TreeParser.TokenExpectedException;
import Formulas.Exceptions.Expressions.TreeParser.UnexpectedTokenException;
import Formulas.Expressions.ExpressionTreeParserImpl;
import Formulas.Expressions.ExpressionNodes.BinaryOperationNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionNodes.UnaryOperationNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ParenthesisTests {
    @Test
    public void ExpressionParser_ParseParenthesis_WithBinaryOperator() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);;
    }

    @Test
    public void ExpressionParser_ParseParenthesis_WithUnaryOperator() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof UnaryOperationNode);;
    }

    @Test
    public void ExpressionParser_ParseParenthesis_WithNumber() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof NumberNode);;
    }

    @Test(expected = TokenExpectedException.class)
    public void ExpressionParser_ParseParenthesis_MissingClosingParenthesis() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        parser.parse(tokens);
    }

    @Test(expected = UnexpectedTokenException.class)
    public void ExpressionParser_ParseParenthesis_MissingOpenedParenthesis() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));

        var parser = new ExpressionTreeParserImpl();

        parser.parse(tokens);
    }

    @Test(expected = RuntimeException.class)
    public void ExpressionParser_ParseParenthesis_Empty() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ExpressionTreeParserImpl();

        parser.parse(tokens);
    }
}

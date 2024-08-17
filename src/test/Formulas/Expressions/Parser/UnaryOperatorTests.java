package test.Formulas.Expressions.Parser;

import Formulas.Expressions.ExpressionTreeParser;
import Formulas.Expressions.ExpressionNodes.UnaryOperationNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UnaryOperatorTests {
    @Test
    public void ExpressionParser_ParseUnaryOperator_NumberOperand() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParser();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof UnaryOperationNode);

        var unaryOperationNode = (UnaryOperationNode)result;
        var operand = unaryOperationNode.getOperand();

        assertNotEquals(null, operand);
    }

    @Test
    public void ExpressionParser_ParseUnaryOperator_FunctionOperand() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.COMMA, ","));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ExpressionTreeParser();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertTrue(result instanceof UnaryOperationNode);

        var unaryOperationNode = (UnaryOperationNode)result;
        var operand = unaryOperationNode.getOperand();

        assertNotEquals(null, operand);
    }


    @Test(expected = RuntimeException.class)
    public void ExpressionParser_ParseUnaryOperator_InvalidOperator() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParser();

        parser.parse(tokens);
    }
}

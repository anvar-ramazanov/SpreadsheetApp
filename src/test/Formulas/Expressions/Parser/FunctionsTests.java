package test.Formulas.Expressions.Parser;

import Formulas.Exceptions.Expressions.TreeParser.TokenExpectedException;
import Formulas.Exceptions.Expressions.TreeParser.UnexpectedTokenException;
import Formulas.Expressions.ExpressionTreeParser;
import Formulas.Expressions.ExpressionNodes.FunctionNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FunctionsTests {
    @Test
    public void ExpressionParser_ParseFunctions_ParseMinFunction() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.COMMA, ","));
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ExpressionTreeParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof FunctionNode);

        var functionNode = (FunctionNode)result;

        assertEquals("MIN", functionNode.getFunctionName());
        assertEquals(2, functionNode.getArguments().size());
        assertTrue(functionNode.getArguments().get(0) instanceof NumberNode);
        assertTrue(functionNode.getArguments().get(1) instanceof NumberNode);
    }

    @Test(expected = TokenExpectedException.class)
    public void ExpressionParser_ParseFunctions_ForgottenParenthesis() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.COMMA, ","));
        tokens.add(new Token(TokenType.NUMBER, "1"));

        var parser = new ExpressionTreeParser(tokens);

        parser.parse();
    }

    @Test(expected = UnexpectedTokenException.class)
    public void ExpressionParser_ParseFunctions_ForgottenComma() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ExpressionTreeParser(tokens);

        parser.parse();
    }


}

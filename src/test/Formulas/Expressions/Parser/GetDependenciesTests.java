package test.Formulas.Expressions.Parser;

import Formulas.Expressions.ExpressionTreeParserImpl;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GetDependenciesTests {
    @Test
    public void ExpressionParser_GetDependencies_HasNoDependencies() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertEquals(0, result.getParentCells().size());
    }

    @Test
    public void ExpressionParser_GetDependencies_HasTwoDependencies() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.REFERENCE, "A1"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.REFERENCE, "A2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertEquals(2, result.getParentCells().size());
        assertTrue( result.getParentCells().contains("A1"));
        assertTrue( result.getParentCells().contains("A2"));
    }

    @Test
    public void ExpressionParser_GetDependencies_HasOneDependency() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.REFERENCE, "A2"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.REFERENCE, "A2"));

        var parser = new ExpressionTreeParserImpl();

        var result = parser.parse(tokens);

        assertNotEquals(null, result);
        assertEquals(1, result.getParentCells().size());
        assertTrue( result.getParentCells().contains("A2"));
    }
}

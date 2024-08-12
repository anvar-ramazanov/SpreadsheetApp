package test.Formulas.AST.Parser;

import Formulas.AST.ASTParser;
import Formulas.AST.Nodes.BinaryOperationNode;
import Formulas.AST.Nodes.NumberNode;
import Formulas.AST.Nodes.UnaryOperationNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ParenthesisTests {
    @Test
    public void ASTParser_ParseParenthesis_WithBinaryOperator() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);;
    }

    @Test
    public void ASTParser_ParseParenthesis_WithUnaryOperator() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof UnaryOperationNode);;
    }

    @Test
    public void ASTParser_ParseParenthesis_WithNumber() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof NumberNode);;
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseParenthesis_MissingClosingParenthesis() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseParenthesis_MissingOpenedParenthesis() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));

        var parser = new ASTParser(tokens);

        parser.parse();
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseParenthesis_Empty() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }
}

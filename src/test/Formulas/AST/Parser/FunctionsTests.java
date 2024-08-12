package test.Formulas.AST.Parser;

import Formulas.AST.ASTParser;
import Formulas.AST.Nodes.FunctionNode;
import Formulas.AST.Nodes.NumberNode;
import Formulas.AST.Nodes.VariableNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FunctionsTests {
    @Test
    public void ASTParser_ParseFunctions_ParseMinFunction() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.COMMA, ","));
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof FunctionNode);

        var functionNode = (FunctionNode)result;

        assertEquals("MIN", functionNode.getFunctionName());
        assertEquals(2, functionNode.getArguments().size());
        assertTrue(functionNode.getArguments().get(0) instanceof NumberNode);
        assertTrue(functionNode.getArguments().get(1) instanceof NumberNode);
    }

    @Test
    public void ASTParser_ParseFunctions_MixArgumentsType() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.COMMA, ","));
        tokens.add(new Token(TokenType.VARIABLE, "A2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof FunctionNode);

        var functionNode = (FunctionNode)result;

        assertEquals("MIN", functionNode.getFunctionName());
        assertEquals(2, functionNode.getArguments().size());
        assertTrue(functionNode.getArguments().get(0) instanceof NumberNode);
        assertTrue(functionNode.getArguments().get(1) instanceof VariableNode);
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseFunctions_ForgottenComma() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.BOOL, "2"));
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseFunctions_ArgumentsNumberMismatch() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseFunctions_ArgumentsTypeMismatch() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.BOOL, "TRUE"));
        tokens.add(new Token(TokenType.COMMA, ","));
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }
}

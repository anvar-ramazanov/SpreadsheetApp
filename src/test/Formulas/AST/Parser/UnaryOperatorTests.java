package test.Formulas.AST.Parser;

import Formulas.AST.ASTParser;
import Formulas.AST.Nodes.NumberNode;
import Formulas.AST.Nodes.UnaryOperationNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UnaryOperatorTests {
    @Test
    public void ASTParser_ParseUnaryOperator_NumberOperand() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof UnaryOperationNode);

        var unaryOperationNode = (UnaryOperationNode)result;
        var operand = unaryOperationNode.getOperand();

        assertNotEquals(null, operand);
    }

    @Test
    public void ASTParser_ParseUnaryOperator_VariableOperand() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.VARIABLE, "A2"));

        var parser = new ASTParser(tokens, Map.of("A2", new NumberNode(2)));

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof UnaryOperationNode);

        var unaryOperationNode = (UnaryOperationNode)result;
        var operand = unaryOperationNode.getOperand();

        assertNotEquals(null, operand);
    }

    @Test
    public void ASTParser_ParseUnaryOperator_FunctionOperand() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.COMMA, ","));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof UnaryOperationNode);

        var unaryOperationNode = (UnaryOperationNode)result;
        var operand = unaryOperationNode.getOperand();

        assertNotEquals(null, operand);
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseUnaryOperator_FunctionOperandTypeMismatch() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "!"));
        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.COMMA, ","));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseUnaryOperator_InvalidOperator() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseUnaryOperator_InvalidOperandType() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.BOOL, "FALSE"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }
}

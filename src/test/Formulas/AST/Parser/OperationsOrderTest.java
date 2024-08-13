package test.Formulas.AST.Parser;

import Formulas.AST.ASTParser;

import Formulas.AST.Nodes.BinaryOperationNode;
import Formulas.AST.Nodes.NumberNode;
import Formulas.AST.Nodes.UnaryOperationNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class OperationsOrderTest {
    @Test
    public void ASTParser_Parse_CorrectOrderWithBinaryOperatorAndParenthesis() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "*"));
        tokens.add(new Token(TokenType.PARENTHESIS, "("));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.PARENTHESIS, ")"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var multiplyNode = (BinaryOperationNode)result;
        assertNotEquals(null, multiplyNode.getLeft());
        assertTrue( multiplyNode.getLeft() instanceof NumberNode);
        assertNotEquals(null, multiplyNode.getRight());
        assertTrue( multiplyNode.getRight() instanceof BinaryOperationNode);

        var addNode = (BinaryOperationNode) multiplyNode.getRight();
        assertNotEquals(null, addNode.getLeft());
        assertTrue( addNode.getLeft() instanceof NumberNode);
        assertNotEquals(null, addNode.getRight());
        assertTrue( addNode.getRight() instanceof NumberNode);
    }

    @Test
    public void ASTParser_Parse_CorrectOrderWithBinaryAndUnaryOperator() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "*"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var multiplyNode = (BinaryOperationNode)result;
        assertNotEquals(null, multiplyNode.getLeft());
        assertTrue( multiplyNode.getLeft() instanceof UnaryOperationNode);
        assertNotEquals(null, multiplyNode.getRight());
        assertTrue( multiplyNode.getRight() instanceof NumberNode);
    }

    @Test
    public void ASTParser_Parse_CorrectOrderWithBinaryAndUnaryOperatorSameLevel() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));
        tokens.add(new Token(TokenType.OPERATOR, "-"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var subtractNode = (BinaryOperationNode)result;
        assertNotEquals(null, subtractNode.getLeft());
        assertTrue( subtractNode.getLeft() instanceof UnaryOperationNode);
        assertNotEquals(null, subtractNode.getRight());
        assertTrue( subtractNode.getRight() instanceof NumberNode);
    }


}
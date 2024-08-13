package test.Formulas.AST;

import Formulas.AST.ASTParser;

import Formulas.AST.Nodes.BinaryOperationNode;
import Formulas.AST.Nodes.NumberNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class AstParserTest {
    @Test
    public void ASTParser_Parse_CorrectOrder() {
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


}
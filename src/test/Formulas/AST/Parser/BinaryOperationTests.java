package test.Formulas.AST.Parser;

import Formulas.AST.ASTParser;
import Formulas.AST.Nodes.BinaryOperationNode;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BinaryOperationTests {
    @Test
    public void ASTParser_ParseBinaryOperator() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "2"));

        var parser = new ASTParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var binaryOperationNode = (BinaryOperationNode)result;
        var left = binaryOperationNode.getLeft();
        var right = binaryOperationNode.getRight();

        assertNotEquals(null, left);
        assertNotEquals(null, right);
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseBinaryOperator_RightOperandTypeMistmatch() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.BOOL, "FALSE"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseBinaryOperator_LeftOperandTypeMistmatch() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.BOOL, "FALSE"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.NUMBER, "1"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }

    @Test(expected = RuntimeException.class)
    public void ASTParser_ParseBinaryOperator_TwoOperators() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));

        var parser = new ASTParser(tokens);

        parser.parse();
    }

}

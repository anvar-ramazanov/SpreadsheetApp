package test.Formulas.Expressions.Parser;

import Formulas.Exceptions.Expressions.UnexpectedTokenException;
import Formulas.Expressions.ExpressionTreeParser;
import Formulas.Expressions.Nodes.BinaryOperationNode;
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

        var parser = new ExpressionTreeParser(tokens);

        var result = parser.parse();

        assertNotEquals(null, result);
        assertTrue(result instanceof BinaryOperationNode);

        var binaryOperationNode = (BinaryOperationNode)result;
        var left = binaryOperationNode.getLeft();
        var right = binaryOperationNode.getRight();

        assertNotEquals(null, left);
        assertNotEquals(null, right);
    }

//    @Test(expected = RuntimeException.class)
//    public void ASTParser_ParseBinaryOperator_RightOperandTypeMismatch() {
//        var tokens = new ArrayList<Token>();
//        tokens.add(new Token(TokenType.NUMBER, "1"));
//        tokens.add(new Token(TokenType.OPERATOR, "+"));
//        tokens.add(new Token(TokenType.BOOL, "FALSE"));
//
//        var parser = new ExpressionTreeParser(tokens);
//
//        parser.parse();
//    }

//    @Test(expected = RuntimeException.class)
//    public void ASTParser_ParseBinaryOperator_LeftOperandTypeMismatch() {
//        var tokens = new ArrayList<Token>();
//        tokens.add(new Token(TokenType.BOOL, "FALSE"));
//        tokens.add(new Token(TokenType.OPERATOR, "+"));
//        tokens.add(new Token(TokenType.NUMBER, "1"));
//
//        var parser = new ExpressionTreeParser(tokens);
//
//        parser.parse();
//    }

    @Test(expected = UnexpectedTokenException.class)
    public void ASTParser_ParseBinaryOperator_TwoOperators() {
        var tokens = new ArrayList<Token>();
        tokens.add(new Token(TokenType.NUMBER, "1"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));
        tokens.add(new Token(TokenType.OPERATOR, "+"));

        var parser = new ExpressionTreeParser(tokens);

        parser.parse();
    }

}

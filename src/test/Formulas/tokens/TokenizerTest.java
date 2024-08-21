package test.Formulas.tokens;

import Formulas.Exceptions.Tokenizer.UnexpectedCharacterException;
import Formulas.Tokens.TokenType;
import Formulas.Tokens.Tokenizer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {
    @Test
    public void test_parse_int_number()
    {
        var num = "6";
        Tokenizer tokenizer = new Tokenizer();
        var tokens = tokenizer.tokenize(num);

        assertEquals(1, tokens.size());

        var token = tokens.getFirst();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals(num, token.value);
    }

    @Test
    public void test_parse_float_number()
    {
        var num = "6.6";
        Tokenizer tokenizer = new Tokenizer();
        var tokens = tokenizer.tokenize("6.6");

        assertEquals(1, tokens.size());

        var token = tokens.getFirst();
        assertEquals(TokenType.NUMBER, token.type);
        assertEquals(num, token.value);
    }

    @Test(expected = UnexpectedCharacterException.class)
    public void test_unexpected_character() {
        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize("5;6");
    }

    @Test
    public void test_parse_variable_valid_lowercase() {
        var cellName = "a6";
        Tokenizer tokenizer = new Tokenizer();
        var tokens =  tokenizer.tokenize(cellName);

        assertEquals(1, tokens.size());

        var token = tokens.getFirst();
        assertEquals(TokenType.VARIABLE, token.type);
        assertEquals(cellName.toUpperCase(), token.value);
    }

    @Test
    public void test_parse_variable_valid_uppercase() {
        var cellName = "A6";
        Tokenizer tokenizer = new Tokenizer();
        var tokens =  tokenizer.tokenize(cellName);

        assertEquals(1, tokens.size());

        var token = tokens.getFirst();
        assertEquals(TokenType.VARIABLE, token.type);
        assertEquals(cellName, token.value);
    }

    @Test(expected = UnexpectedCharacterException.class)
    public void test_parse_variable_invalid() {
        var cellName = "6a";

        Tokenizer tokenizer = new Tokenizer();

        tokenizer.tokenize(cellName);
    }

    @Test
    public void test_bool_false() {
        Tokenizer tokenizer = new Tokenizer();
        var result = tokenizer.tokenize("FALSE");
        assertEquals(1, result.size());

        var token = result.getFirst();
        assertEquals(TokenType.BOOL, token.type);
        assertEquals("FALSE", token.value);
    }

    @Test
    public void test_bool_true() {
        Tokenizer tokenizer = new Tokenizer();
        var result = tokenizer.tokenize("TRUE");
        assertEquals(1, result.size());

        var token = result.getFirst();
        assertEquals(TokenType.BOOL, token.type);
        assertEquals("TRUE", token.value);
    }

    @Test
    public void test_function() {
        Tokenizer tokenizer = new Tokenizer();
        var result = tokenizer.tokenize("MIN(6, 5)");
        assertEquals(6, result.size());

        var token = result.getFirst();
        assertEquals(TokenType.FUNCTION, token.type);
        assertEquals("MIN", token.value);
    }

    @Test
    public void test_math_operations() {
        Tokenizer tokenizer = new Tokenizer();

        var tokens = tokenizer.tokenize("+-*/><=!");

        assertEquals(8, tokens.size());

        var plus = tokens.get(0);
        assertEquals(TokenType.OPERATOR, plus.type);
        assertEquals("+", plus.value);

        var minus = tokens.get(1);
        assertEquals(TokenType.OPERATOR, minus.type);
        assertEquals("-", minus.value);

        var multiply = tokens.get(2);
        assertEquals(TokenType.OPERATOR, multiply.type);
        assertEquals("*", multiply.value);

        var divide = tokens.get(3);
        assertEquals(TokenType.OPERATOR, divide.type);
        assertEquals("/", divide.value);

        var gt = tokens.get(4);
        assertEquals(TokenType.OPERATOR, gt.type);
        assertEquals(">", gt.value);

        var ls = tokens.get(5);
        assertEquals(TokenType.OPERATOR, ls.type);
        assertEquals("<", ls.value);

        var eq = tokens.get(6);
        assertEquals(TokenType.OPERATOR, eq.type);
        assertEquals("=", eq.value);

        var excp = tokens.get(7);
        assertEquals(TokenType.OPERATOR, excp.type);
        assertEquals("!", excp.value);
    }

}

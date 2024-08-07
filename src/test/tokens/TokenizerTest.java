package test.tokens;

import Formulas.Tokens.Tokenizer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {
    @Test
    public void testBool() {
        Tokenizer tokenizer = new Tokenizer();
        var result = tokenizer.tokenize("a > 5 + FALSE");
        assertEquals(5, result.size());
    }
}

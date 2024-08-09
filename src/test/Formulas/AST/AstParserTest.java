package test.Formulas.AST;

import Formulas.Tokens.Token;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;


public class AstParserTest {
    @Test
    public void test() {
        var tokens = new ArrayList<Token>();
        assertEquals(0, tokens.size());
    }
}
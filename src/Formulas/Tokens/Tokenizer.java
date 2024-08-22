package Formulas.Tokens;

import java.util.List;

public interface Tokenizer {
    public List<Token> tokenize(String expression);
}

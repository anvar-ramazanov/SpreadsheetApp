package Formulas.Tokens;

import java.util.List;

public interface Tokenizer {
    /**
     * Tokenizes the given input string.
     *
     * @param expression the string to tokenize
     * @return a list of tokens
     */
    List<Token> tokenize(String expression);
}

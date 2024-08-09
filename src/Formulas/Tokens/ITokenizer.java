package Formulas.Tokens;

import java.util.List;

public interface ITokenizer {
    public List<Token> tokenize(String expression);
}

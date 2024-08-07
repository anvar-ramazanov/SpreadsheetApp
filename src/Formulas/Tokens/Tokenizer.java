package Formulas.Tokens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Tokenizer {

    private static final HashSet<String> functions = new HashSet<String>(Arrays.asList("SUM", "POW", "SQRT"));

    private static final HashSet<String> booleans = new HashSet<String>(Arrays.asList("TRUE", "FALSE"));

    public List<Token> tokenize(String expression) {
        List<Token> tokens = new ArrayList<>();
        int length = expression.length();

        List<Character> buf = new ArrayList<>();
        for (int i = 0; i < length; ) {
            char current = expression.charAt(i);
            if (Character.isWhitespace(current)) {
                i++;
                continue;
            }
            if (Character.isDigit(current)) {
                StringBuilder number = new StringBuilder();
                while (i < length && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                tokens.add(new Token(TokenType.NUMBER, number.toString()));
            } else if ("+-*/><=!".indexOf(current) != -1) {
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(current)));
                i++;
            }
            else if (",".indexOf(current) != -1) {
                tokens.add(new Token(TokenType.COMMA, String.valueOf(current)));
                i++;
            } else if ("()".indexOf(current) != -1) {
                tokens.add(new Token(TokenType.PARENTHESIS, String.valueOf(current)));
                i++;
            } else if (Character.isAlphabetic(current)) {
                StringBuilder word = new StringBuilder();
                while (i < length && (Character.isAlphabetic(expression.charAt(i)) || Character.isDigit(expression.charAt(i)))) {
                    word.append(expression.charAt(i));
                    i++;
                }
                String value = word.toString();
                if (functions.contains(value))
                {
                    tokens.add(new Token(TokenType.FUNCTION, value));
                } else if (booleans.contains(value)) {
                    tokens.add(new Token(TokenType.BOOL, value));
                }else {
                    tokens.add(new Token(TokenType.VARIABLE, value));
                }
            } else {
                throw new IllegalArgumentException("Unexpected character: " + current);
            }
        }
        return tokens;
    }
}

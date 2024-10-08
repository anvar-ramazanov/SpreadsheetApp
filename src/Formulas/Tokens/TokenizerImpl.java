package Formulas.Tokens;

import Formulas.Exceptions.Tokenizer.UnexpectedCharacterException;
import Formulas.Language.ExpressionLanguage;
import Helpers.StringHelpers;

import java.util.ArrayList;
import java.util.List;

import static Formulas.Tokens.TokenizerHelpers.isValidCellName;

public class TokenizerImpl implements Tokenizer {
    public List<Token> tokenize(String expression) {
        List<Token> tokens = new ArrayList<>();
        int length = expression.length();

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
                var str = number.toString();
                if (StringHelpers.isNumeric(str)) {
                    tokens.add(new Token(TokenType.NUMBER, str));
                }
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
                String value = word.toString().toUpperCase(); // storing everything in upper case
                if (ExpressionLanguage.FunctionsDescription.containsKey(value))
                {
                    tokens.add(new Token(TokenType.FUNCTION, value));
                } else if (value.equals("FALSE") || value.equals("TRUE")) {
                    tokens.add(new Token(TokenType.BOOL, value));
                } else if (isValidCellName(value)) {
                    tokens.add(new Token(TokenType.REFERENCE, value));
                } else {
                    throw new UnexpectedCharacterException("Unexpected character: " + current);
                }
            } else {
                throw new UnexpectedCharacterException("Unexpected character: " + current);
            }
        }
        return tokens;
    }
}

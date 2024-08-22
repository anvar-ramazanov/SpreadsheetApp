package Formulas.Expressions;

import Formulas.Tokens.Token;

import java.util.List;

public interface ExpressionTreeParser {
    /**
     * Parse expression tree
     *
     * @param tokens list of tokens
     * @return root node of expression tree
     */
    ExpressionNode parse(List<Token> tokens);
}

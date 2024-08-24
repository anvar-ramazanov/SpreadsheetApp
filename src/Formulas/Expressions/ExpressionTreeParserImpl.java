package Formulas.Expressions;

import Formulas.Exceptions.Expressions.TreeParser.TokenExpectedException;
import Formulas.Exceptions.Expressions.TreeParser.UnexpectedTokenException;
import Formulas.Expressions.ExpressionNodes.*;
import Formulas.Language.ExpressionLanguage;
import Formulas.Language.OperatorPrecedence;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ExpressionTreeParserImpl implements ExpressionTreeParser {
    private List<Token> tokens;
    private int position;
    private HashSet<String> parentCells;

    public ExpressionNode parse(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
        this.parentCells = new HashSet<>();
        var node = parseExpression();
        if (currentToken() != null) {
            throw new UnexpectedTokenException("Unexpected token");
        }
        node.setParentCells(parentCells);
        return node;
    }

    private Token currentToken() {
        if (position < tokens.size()) {
            return tokens.get(position);
        }
        return null;
    }

    private Token consumeToken() {
        Token token = currentToken();
        position++;
        return token;
    }

    private ExpressionNode parseExpression() {
        ExpressionNode node = parseTerm();
        Token currentToken = currentToken();
        while (currentToken != null && (currentToken.type == TokenType.OPERATOR) && (
                ExpressionLanguage.BinaryOperations.containsKey(currentToken.value) &&
                ExpressionLanguage.BinaryOperations.get(currentToken.value).precedence() == OperatorPrecedence.Low
        )) {
            String operator = consumeToken().value;
            ExpressionNode right = parseTerm();
            node = new BinaryOperationNode(operator, node, right);
            currentToken = currentToken();
        }
        return node;
    }

    private ExpressionNode parseTerm() {
        ExpressionNode node = parseFactor();
        Token currentToken = currentToken();
        while (currentToken != null && (currentToken.type == TokenType.OPERATOR) && (
                ExpressionLanguage.BinaryOperations.containsKey(currentToken.value) &&
                ExpressionLanguage.BinaryOperations.get(currentToken.value).precedence() == OperatorPrecedence.High)) {
            String operator = consumeToken().value;
            ExpressionNode right = parseFactor();
            node = new BinaryOperationNode(operator, node, right);
            currentToken = currentToken();
        }
        return node;
    }

    private ExpressionNode parseFactor() {
        Token token = currentToken();

        if (token == null) {
            throw new TokenExpectedException("Expected token, but get null");
        }

        if (token.type == TokenType.OPERATOR && (ExpressionLanguage.UnaryOperations.containsKey(token.value))) {
            String operator = consumeToken().value;
            ExpressionNode operand = parseFactor();
            return new UnaryOperationNode(operator, operand);
        } else if (token.type == TokenType.NUMBER) {
            consumeToken();
            return new NumberNode(Double.parseDouble(token.value));
        } else if (token.type == TokenType.REFERENCE) {
            consumeToken();
            this.parentCells.add(token.value);
            return new ReferencesNode(token.value);
        } else if (token.type == TokenType.PARENTHESIS && token.value.equals("(")) {
            consumeToken();
            ExpressionNode node = parseExpression();
            if (currentToken() != null && currentToken().type == TokenType.PARENTHESIS && currentToken().value.equals(")")) {
                consumeToken();
            } else {
                throw new TokenExpectedException("Expected closing parenthesis");
            }
            return node;
        } else if (token.type == TokenType.FUNCTION) {
            consumeToken();
            String functionName = token.value;
            if (currentToken().type == TokenType.PARENTHESIS && currentToken().value.equals("(")) {
                consumeToken();
                List<ExpressionNode> arguments = new ArrayList<>();
                while (currentToken() != null && !currentToken().value.equals(")")) {
                    arguments.add(parseExpression());
                    if (currentToken() != null && currentToken().type == TokenType.COMMA) {
                        consumeToken();
                    } else if (currentToken() != null && currentToken().type != TokenType.PARENTHESIS){
                        throw new UnexpectedTokenException("Expected comma");
                    }
                }
                if (currentToken() != null && currentToken().value.equals(")")) {
                    consumeToken();
                } else {
                    throw new TokenExpectedException("Expected closing parenthesis after function arguments");
                }
                return new FunctionNode(functionName, arguments);
            }
        } else if (token.type == TokenType.BOOL) {
            consumeToken();
            return new BooleanNode(Boolean.parseBoolean(token.value));
        }
        throw new UnexpectedTokenException("Unexpected token: " + token.value);
    }
}
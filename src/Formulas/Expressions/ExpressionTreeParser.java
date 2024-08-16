package Formulas.Expressions;

import Formulas.Exceptions.Expressions.TreeParser.TokenExpectedException;
import Formulas.Exceptions.Expressions.TreeParser.UnexpectedTokenException;
import Formulas.Expressions.Nodes.*;
import Formulas.Grammar;
import Formulas.NodeType;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;

import java.util.ArrayList;
import java.util.List;

public class ExpressionTreeParser {
    private final List<Token> tokens;
    private int position;

    public ExpressionTreeParser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
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

    public ExpressionNode parse()  {
        var node = parseExpression();
        if (currentToken() != null) {
            throw new UnexpectedTokenException("Unexpected token");
        }
        return node;
    }

    private ExpressionNode parseExpression() {
        ExpressionNode node = parseTerm();
        Token currentToken = currentToken();
        while (currentToken != null && (currentToken.type == TokenType.OPERATOR) && (currentToken.value.equals("+") || currentToken.value.equals("-")  || currentToken.value.equals(">") || currentToken.value.equals("<"))) { // FIXME
            String operator = consumeToken().value;
            ExpressionNode right = parseTerm();
            NodeType resultType = Grammar.BinaryOperations.get(operator).resultType();
            node = new BinaryOperationNode(operator, node, right);
            currentToken = currentToken();
        }
        return node;
    }

    private ExpressionNode parseTerm() {
        ExpressionNode node = parseFactor();
        Token currentToken = currentToken();
        while (currentToken != null && (currentToken.type == TokenType.OPERATOR) && (currentToken.value.equals("*") || currentToken.value.equals("/"))) {
            String operator = consumeToken().value;
            ExpressionNode right = parseFactor();
            NodeType resultType = Grammar.BinaryOperations.get(operator).resultType();
            node = new BinaryOperationNode(operator, node, right);
            currentToken = currentToken();
        }
        return node;
    }

    private ExpressionNode parseFactor() {
        Token token = currentToken();

        if (token == null) {
            throw new TokenExpectedException();
        }

        if (token.type == TokenType.OPERATOR && (Grammar.UnaryOperations.containsKey(token.value))) {
            String operator = consumeToken().value;
            ExpressionNode operand = parseFactor();
            return new UnaryOperationNode(operator, operand);
        } else if (token.type == TokenType.NUMBER) {
            consumeToken();
            return new NumberNode(Double.parseDouble(token.value));
        } else if (token.type == TokenType.VARIABLE) {
            consumeToken();
            return new RefNode(token.value);
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
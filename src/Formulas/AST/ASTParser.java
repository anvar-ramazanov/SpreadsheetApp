package Formulas.AST;

import Formulas.AST.Nodes.*;
import Formulas.Tokens.Token;
import Formulas.Tokens.TokenType;

import java.util.ArrayList;
import java.util.List;

public class ASTParser {
    private final List<Token> tokens;
    private int position;

    public ASTParser(List<Token> tokens) {
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

    public ASTNode parse() {
        return parseExpression();
    }

    private ASTNode parseExpression() {
        ASTNode node = parseTerm();
        while (currentToken() != null && (currentToken().type == TokenType.OPERATOR) && (currentToken().value.equals("+") || currentToken().value.equals("-"))) {
            String operator = consumeToken().value;
            ASTNode right = parseTerm();
            node = new BinaryOperationNode(node, operator, right);
        }
        return node;
    }

    private ASTNode parseTerm() {
        ASTNode node = parseFactor();
        while (currentToken() != null && (currentToken().type == TokenType.OPERATOR) && (currentToken().value.equals("*") || currentToken().value.equals("/"))) {
            String operator = consumeToken().value;
            ASTNode right = parseFactor();
            node = new BinaryOperationNode(node, operator, right);
        }
        return node;
    }

    private ASTNode parseFactor() {
        Token token = currentToken();

        // Handle unary operators
        if (token != null && token.type == TokenType.OPERATOR && (token.value.equals("-") || token.value.equals("!"))) {
            String operator = consumeToken().value;
            ASTNode operand = parseFactor();
            return new UnaryOperationNode(operator, operand);
        }

        if (token.type == TokenType.NUMBER) {
            consumeToken();
            return new NumberNode(Double.parseDouble(token.value));
        } else if (token.type == TokenType.VARIABLE) {
            consumeToken();
            return new VariableNode(token.value);
        } else if (token.type == TokenType.PARENTHESIS && token.value.equals("(")) {
            consumeToken();
            ASTNode node = parseExpression();
            if (currentToken().type == TokenType.PARENTHESIS && currentToken().value.equals(")")) {
                consumeToken();
            } else {
                throw new RuntimeException("Expected closing parenthesis");
            }
            return node;
        } else if (token.type == TokenType.FUNCTION) {
            consumeToken();
            String functionName = token.value;
            if (currentToken().type == TokenType.PARENTHESIS && currentToken().value.equals("(")) {
                consumeToken();
                List<ASTNode> arguments = new ArrayList<>();
                while (currentToken() != null && !currentToken().value.equals(")")) {
                    arguments.add(parseExpression());
                    if (currentToken().type == TokenType.COMMA) {
                        consumeToken();
                    }
                }
                if (currentToken().value.equals(")")) {
                    consumeToken();
                } else {
                    throw new RuntimeException("Expected closing parenthesis after function arguments");
                }
                return new FunctionNode(functionName, arguments);
            }
        } else if (token.type == TokenType.BOOL) {
            consumeToken();
            return new BooleanNode(Boolean.parseBoolean(token.value));
        }
        throw new RuntimeException("Unexpected token: " + token.value);
    }
}
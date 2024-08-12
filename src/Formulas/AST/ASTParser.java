package Formulas.AST;

import Formulas.AST.Nodes.*;
import Formulas.Functions;
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
        var node = parseExpression();
        if (currentToken() != null) {
            throw new RuntimeException("Unexpected token"); // FIXME
        }
        return node;
    }

    private ASTNode parseExpression() {
        ASTNode node = parseTerm();
        Token currentToken = currentToken();
        while (currentToken != null && (currentToken.type == TokenType.OPERATOR) && (currentToken.value.equals("+") || currentToken.value.equals("-"))) {
            String operator = consumeToken().value;
            ASTNode right = parseTerm();
            node = new BinaryOperationNode(node, operator, right);
            currentToken = currentToken();
        }
        return node;
    }

    private ASTNode parseTerm() {
        ASTNode node = parseFactor();
        Token currentToken = currentToken();
        while (currentToken != null && (currentToken.type == TokenType.OPERATOR) && (currentToken.value.equals("*") || currentToken.value.equals("/"))) {
            String operator = consumeToken().value;
            ASTNode right = parseFactor();
            node = new BinaryOperationNode(node, operator, right);
            currentToken = currentToken();
        }
        return node;
    }

    private ASTNode parseFactor() {
        Token token = currentToken();

        if (token == null) {
            throw new RuntimeException("Run out of tokens"); // FIXME
        }

        if (token.type == TokenType.OPERATOR && (token.value.equals("-") || token.value.equals("!"))) {
            String operator = consumeToken().value;
            ASTNode operand = parseFactor();
            return new UnaryOperationNode(operator, operand);
        } else if (token.type == TokenType.NUMBER) {
            consumeToken();
            return new NumberNode(Double.parseDouble(token.value));
        } else if (token.type == TokenType.VARIABLE) {
            consumeToken();
            return new VariableNode(token.value);
        } else if (token.type == TokenType.PARENTHESIS && token.value.equals("(")) {
            consumeToken();
            ASTNode node = parseExpression();
            if (currentToken() != null && currentToken().type == TokenType.PARENTHESIS && currentToken().value.equals(")")) {
                consumeToken();
            } else {
                throw new RuntimeException("Expected closing parenthesis"); // FIXME
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
                    throw new RuntimeException("Expected closing parenthesis after function arguments"); // FIXME
                }
                var description = Functions.FunctionsDescription.get(functionName);
                if (description.arguments().size() != arguments.size()) {
                    throw new RuntimeException("Arguments number mistmach");
                }
                for (int i = 0; i < arguments.size(); ++i) {
                    var allowedTypes = description.arguments().get(i);
                    var currentType = arguments.get(i).getType();
                    if (!allowedTypes.contains(currentType)) {
                        throw new RuntimeException("Argument type mistmatch");
                    }
                }

                return new FunctionNode(functionName, arguments);
            }
        } else if (token.type == TokenType.BOOL) {
            consumeToken();
            return new BooleanNode(Boolean.parseBoolean(token.value));
        }
        throw new RuntimeException("Unexpected token: " + token.value); // FIXME
    }
}
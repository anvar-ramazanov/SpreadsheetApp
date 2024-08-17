package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.ArgumentTypeMismatchException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.ArgumentsNumberMismatchException;
import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.FunctionNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;

import Formulas.Expressions.ExpressionNodes.UnaryOperationNode;
import org.junit.Test;
import java.util.*;


public class FunctionNodeTests {

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ValidMinFunction() {
        var functionNode =  new FunctionNode("MIN", List.of(new NumberNode(2), new NumberNode(1)));
        var nodes = Map.of("A1", (ExpressionNode)functionNode);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ValidMinFunction_WithNestedUnaryOperation() {
        var functionNode =  new FunctionNode("MIN", List.of(new NumberNode(2), new UnaryOperationNode("-", new NumberNode(2))));
        var nodes = Map.of("A1", (ExpressionNode)functionNode);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }

    @Test(expected = ArgumentsNumberMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ArgumentsNumberMismatch() {
        var functionNode =  new FunctionNode("MIN", List.of(new NumberNode(2)));
        var nodes = Map.of("A1", (ExpressionNode)functionNode);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ArgumentsTypeMismatch() {
        var functionNode =  new FunctionNode("MIN", List.of(new NumberNode(2), new BooleanNode(false)));
        var nodes = Map.of("A1", (ExpressionNode)functionNode);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ValidMinFunction_WithInvalidNestedUnaryOperation() {
        var functionNode =  new FunctionNode("MIN", List.of(new NumberNode(2), new UnaryOperationNode("!", new BooleanNode(false))));
        var nodes = Map.of("A1", (ExpressionNode)functionNode);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }


//    @Test(expected = RuntimeException.class)
//    public void ASTParser_ParseFunctions_ArgumentsTypeMismatch() {
//        var tokens = new ArrayList<Token>();
//        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
//        tokens.add(new Token(TokenType.PARENTHESIS, "("));
//        tokens.add(new Token(TokenType.BOOL, "TRUE"));
//        tokens.add(new Token(TokenType.COMMA, ","));
//        tokens.add(new Token(TokenType.NUMBER, "1"));
//        tokens.add(new Token(TokenType.PARENTHESIS, ")"));
//
//        var parser = new ExpressionTreeParser(tokens);
//
//        parser.parse();
//    }

    //    @Test
//    public void ASTParser_ParseFunctions_MixArgumentsType() {
//        var tokens = new ArrayList<Token>();
//        tokens.add(new Token(TokenType.FUNCTION, "MIN"));
//        tokens.add(new Token(TokenType.PARENTHESIS, "("));
//        tokens.add(new Token(TokenType.NUMBER, "2"));
//        tokens.add(new Token(TokenType.COMMA, ","));
//        tokens.add(new Token(TokenType.VARIABLE, "A2"));
//        tokens.add(new Token(TokenType.PARENTHESIS, ")"));
//
//        var parser = new ExpressionTreeParser(tokens, Map.of("A2", new NumberNode(2)));
//
//        var result = parser.parse();
//
//        assertNotEquals(null, result);
//        assertTrue(result instanceof FunctionNode);
//
//        var functionNode = (FunctionNode)result;
//
//        assertEquals("MIN", functionNode.getFunctionName());
//        assertEquals(2, functionNode.getArguments().size());
//        assertTrue(functionNode.getArguments().get(0) instanceof NumberNode);
//        assertTrue(functionNode.getArguments().get(1) instanceof RefNode);
//    }
}

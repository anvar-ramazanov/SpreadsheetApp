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
        var a1 =  new FunctionNode("MIN", List.of(new NumberNode(2), new NumberNode(1)));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ValidMinFunction_WithNestedUnaryOperation() {
        var a1 =  new FunctionNode("MIN", List.of(new NumberNode(2), new UnaryOperationNode("-", new NumberNode(2))));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);
    }

    @Test(expected = ArgumentsNumberMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ArgumentsNumberMismatch() {
        var a1 =  new FunctionNode("MIN", List.of(new NumberNode(2)));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ArgumentsTypeMismatch() {
        var a1 =  new FunctionNode("MIN", List.of(new NumberNode(2), new BooleanNode(false)));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ValidMinFunction_WithInvalidNestedUnaryOperation() {
        var a1 =  new FunctionNode("MIN", List.of(new NumberNode(2), new UnaryOperationNode("!", new BooleanNode(false))));
        var nodes = Map.of("A1", (ExpressionNode)a1);

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);
    }
}

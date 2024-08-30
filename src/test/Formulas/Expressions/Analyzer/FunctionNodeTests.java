package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.ArgumentTypeMismatchException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.ArgumentsNumberMismatchException;
import Formulas.Expressions.ExpressionTreeAnalyzerImpl;
import Formulas.Expressions.ExpressionNodes.BooleanNode;
import Formulas.Expressions.ExpressionNodes.FunctionNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;

import Formulas.Expressions.ExpressionNodes.UnaryOperationNode;
import Models.Cell.ExpressionCell;
import org.junit.Test;
import java.util.*;


public class FunctionNodeTests {

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ValidMinFunction() {
        var expression =  new FunctionNode("MIN", List.of(new NumberNode(2), new NumberNode(1)));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context, false);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ValidMinFunction_WithNestedUnaryOperation() {
        var expression =  new FunctionNode("MIN", List.of(new NumberNode(2), new UnaryOperationNode("-", new NumberNode(2))));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context, false);
    }

    @Test(expected = ArgumentsNumberMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ArgumentsNumberMismatch() {
        var expression =  new FunctionNode("MIN", List.of(new NumberNode(2)));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context, false);
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ArgumentsTypeMismatch() {
        var expression =  new FunctionNode("MIN", List.of(new NumberNode(2), new BooleanNode(false)));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context, false);
    }

    @Test(expected = ArgumentTypeMismatchException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Functions_ValidMinFunction_WithInvalidNestedUnaryOperation() {
        var expression =  new FunctionNode("MIN", List.of(new NumberNode(2), new UnaryOperationNode("!", new BooleanNode(false))));
        Map<String, ExpressionCell> context = Map.of();

        var analyzer = new ExpressionTreeAnalyzerImpl();

        analyzer.AnalyzeExpressionTree(expression,"A1", context, false);
    }
}

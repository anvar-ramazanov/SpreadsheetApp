package test.Formulas;

import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionTreeAnalyzerImpl;
import Formulas.Expressions.ExpressionTreeEvaluatorImpl;
import Formulas.Expressions.ExpressionTreeParserImpl;
import Formulas.Tokens.TokenizerImpl;
import Models.Cell.ExpressionCell;
import org.junit.Test;
import test.Formulas.Expressions.TestExpressionCell;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class E2EFormulasTests {
    @Test
    public void E2ETest_HappyPath(){
        var expression = "pow(-2, A1 - 3) * (42 + B2)";

        var a1 = new TestExpressionCell(new NumberNode(6));
        var b2 = new TestExpressionCell(new NumberNode(3));

        Map<String, ExpressionCell> context = Map.of(
                "A1",  a1,
                "B2", b2);

        var tokenizer = new TokenizerImpl();
        var expressionTreeParser = new ExpressionTreeParserImpl();
        var expressionTreeAnalyzer = new ExpressionTreeAnalyzerImpl();
        var expressionEvaluator = new ExpressionTreeEvaluatorImpl();

        var tokens = tokenizer.tokenize(expression);

        var expressionTree = expressionTreeParser.parse(tokens);

        expressionTreeAnalyzer.AnalyzeExpressionTree(expressionTree, "A2", context, false);

        var result = expressionEvaluator.EvaluateExpressionTree(expressionTree, context);

        assertEquals(-360.0, result);

    }
}

package test.Formulas;

import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionTreeAnalyzerImpl;
import Formulas.Expressions.ExpressionTreeEvaluatorImpl;
import Formulas.Expressions.ExpressionTreeParserImpl;
import Formulas.Tokens.TokenizerImpl;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class E2EFormulasTests {
    @Test
    public void E2ETest_HappyPath(){
        var expression = "pow(-2, A1 - 3) * (42 + B2)";

        var a1 = new NumberNode(6);
        var b2 = new NumberNode(3);

        var context = new java.util.HashMap<>(Map.of(
                "A1", (ExpressionNode) a1,
                "B2", b2));

        var tokenizer = new TokenizerImpl();
        var expressionTreeParser = new ExpressionTreeParserImpl();
        var expressionTreeAnalyzer = new ExpressionTreeAnalyzerImpl();
        var expressionEvaluator = new ExpressionTreeEvaluatorImpl();

        var tokens = tokenizer.tokenize(expression);

        var expressionTree = expressionTreeParser.parse(tokens);

        context.put("A2", expressionTree);

        expressionTreeAnalyzer.AnalyzeExpressionTree(expressionTree, "A2", context);

        var result = expressionEvaluator.EvaluateExpressionTree(expressionTree, context);

        assertEquals(-360.0, result);

    }
}

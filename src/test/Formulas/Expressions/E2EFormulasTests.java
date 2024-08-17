package test.Formulas.Expressions;

import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionNodes.NumberNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionTreeEvaluator;
import Formulas.Expressions.ExpressionTreeParser;
import Formulas.Tokens.Tokenizer;
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

        var tokenizer = new Tokenizer();

        var tokens = tokenizer.tokenize(expression);
        var expressionTreeParser = new ExpressionTreeParser(tokens);

        var expressionTree = expressionTreeParser.parse();

        context.put("A2", expressionTree);

        var expressionTreeAnalyzer = new ExpressionTreeAnalyzer();

        expressionTreeAnalyzer.AnalyzeExpressionTree("A2", context);

        var expressionEvaluator = new ExpressionTreeEvaluator();
        var result = expressionEvaluator.EvaluateExpressionTree("A2", context);

        assertEquals(-360.0, result);

    }
}

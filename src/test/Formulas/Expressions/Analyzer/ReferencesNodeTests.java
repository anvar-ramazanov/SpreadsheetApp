package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.CircularDependencyException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.InvalidReferenceException;
import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionNodes.*;
import Formulas.Language.DataType;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertSame;

public class ReferencesNodeTests {
    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_ValidJump() {
        var a1 = new ReferencesNode("A2");
        var a2 = new BooleanNode(false);
        var nodes = Map.of(
                "A1", a1,
                "A2", a2
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);

        assertSame(DataType.BOOLEAN, a1.getType());
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_ValidTwoJumps() {
        var a1 = new ReferencesNode("A2");
        var a2 = new ReferencesNode("B3");
        var b3 = new BooleanNode(false);
        var nodes = Map.of(
                "A1", a1,
                "A2", a2,
                "B3", b3
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);

        assertSame(DataType.BOOLEAN, a1.getType());
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_ValidTwoJumpsWithFormula() {
        var a1 = new ReferencesNode("A2");
        var a2 = new FunctionNode("MIN", List.of(new NumberNode(2), new NumberNode(4)));
        var nodes = Map.of(
                "A1", a1,
                "A2", a2
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);

        assertSame(DataType.NUMBER, a1.getType());
    }


    @Test(expected = InvalidReferenceException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_InvalidJump() {
        var a1 = new ReferencesNode("A2");
        var nodes = Map.of(
                "A1", (ExpressionNode)a1
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);
    }

    @Test(expected = CircularDependencyException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_CircularDependency() {
        var a1 = new ReferencesNode("A2");
        var a2 = new ReferencesNode("A3");
        var a3 = new ReferencesNode("A1");
        var nodes = Map.of(
                "A1", (ExpressionNode)a1,
                "A2", a2,
                "A3", a3
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);
    }

    @Test(expected = CircularDependencyException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_CircularDependencyLongChain() {
        var b1 = new BinaryOperationNode("/", new ReferencesNode("C1"), new ReferencesNode("A1"));
        var c1 = new BinaryOperationNode("+", new ReferencesNode("E1"), new ReferencesNode("G1"));
        var d1 = new NumberNode(1);
        var e1 = new BinaryOperationNode("*", new ReferencesNode("D1"), new ReferencesNode("B1"));
        var f1 = new NumberNode(2);
        var g1 = new BinaryOperationNode("*", new ReferencesNode("F1"), new ReferencesNode("B1"));

        var nodes = Map.of(
                "B1", b1,
                "C1", c1,
                "D1", d1,
                "E1", e1,
                "F1", f1,
                "G1", g1
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(b1, nodes);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_TwoLinksOnLevel() {
        var a1 = new ReferencesNode("A2");
        var a2 = new BinaryOperationNode("+", new ReferencesNode("A3"), new ReferencesNode("A3"));
        var a3 = new NumberNode(5);
        var nodes = Map.of(
                "A1", a1,
                "A2", a2,
                "A3", a3
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, nodes);
    }

    @Test(expected = InvalidReferenceException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_SelfLink() {
        var a1 = new ReferencesNode("A1");

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(a1, new HashMap<>());
    }
}

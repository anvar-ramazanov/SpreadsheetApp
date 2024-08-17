package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.CircularDependencyException;
import Formulas.Exceptions.Expressions.TreeAnalyzer.InvalidReferenceException;
import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.ExpressionNodes.*;
import Formulas.Language.DataType;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertSame;

public class ReferencesNodeTests {
    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_ValidJump() {
        var node1 = new ReferencesNode("A2");
        var node2 = new BooleanNode(false);
        var nodes = Map.of(
                "A1", node1,
                "A2", node2
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);

        assertSame(DataType.BOOLEAN, node1.getType());
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_ValidTwoJumps() {
        var node1 = new ReferencesNode("A2");
        var node2 = new ReferencesNode("B3");
        var node3 = new BooleanNode(false);
        var nodes = Map.of(
                "A1", node1,
                "A2", node2,
                "B3", node3
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);

        assertSame(DataType.BOOLEAN, node1.getType());
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_ValidTwoJumpsWithFormula() {
        var node1 = new ReferencesNode("A2");
        var node2 = new FunctionNode("MIN", List.of(new NumberNode(2), new NumberNode(4)));
        var nodes = Map.of(
                "A1", node1,
                "A2", node2
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);

        assertSame(DataType.NUMBER, node1.getType());
    }


    @Test(expected = InvalidReferenceException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_InvalidJump() {
        var node1 = new ReferencesNode("A2");
        var nodes = Map.of(
                "A1", (ExpressionNode)node1
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }

    @Test(expected = CircularDependencyException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_CircularDependency() {
        var node1 = new ReferencesNode("A2");
        var node2 = new ReferencesNode("A3");
        var node3 = new ReferencesNode("A1");
        var nodes = Map.of(
                "A1", (ExpressionNode)node1,
                "A2", node2,
                "A3", node3
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }

    @Test(expected = CircularDependencyException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_CircularDependencyLongChain() {
        var node_b1 = new BinaryOperationNode("/", new ReferencesNode("C1"), new ReferencesNode("A1"));
        var node_c1 = new BinaryOperationNode("+", new ReferencesNode("E1"), new ReferencesNode("G1"));
        var node_d1 = new NumberNode(1);
        var node_e1 = new BinaryOperationNode("*", new ReferencesNode("D1"), new ReferencesNode("B1"));
        var node_f1 = new NumberNode(2);
        var node_g1 = new BinaryOperationNode("*", new ReferencesNode("F1"), new ReferencesNode("B1"));

        var nodes = Map.of(
                "B1", node_b1,
                "C1", node_c1,
                "D1", node_d1,
                "E1", node_e1,
                "F1", node_f1,
                "G1", node_g1
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("B1", nodes);
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_TwoLinksOnLevel() {
        var node1 = new ReferencesNode("A2");
        var node2 = new BinaryOperationNode("+", new ReferencesNode("A3"), new ReferencesNode("A3"));
        var node3 = new NumberNode(5);
        var nodes = Map.of(
                "A1", node1,
                "A2", node2,
                "A3", node3
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree("A1", nodes);
    }
}

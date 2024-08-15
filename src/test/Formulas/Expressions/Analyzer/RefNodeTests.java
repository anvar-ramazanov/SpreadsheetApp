package test.Formulas.Expressions.Analyzer;

import Formulas.Exceptions.Expressions.TreeAnalyzer.InvalidReferenceException;
import Formulas.Expressions.ExpressionNode;
import Formulas.Expressions.ExpressionTreeAnalyzer;
import Formulas.Expressions.Nodes.BooleanNode;
import Formulas.Expressions.Nodes.FunctionNode;
import Formulas.Expressions.Nodes.NumberNode;
import Formulas.Expressions.Nodes.RefNode;
import Formulas.NodeType;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertSame;

public class RefNodeTests {
    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_ValidJump() {
        var node1 = new RefNode("A2");
        var node2 = new BooleanNode(false);
        var nodes = Map.of(
                "A1", node1,
                "A2", node2
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");

        assertSame(NodeType.BOOLEAN, node1.getType());
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_ValidTwoJumps() {
        var node1 = new RefNode("A2");
        var node2 = new RefNode("B3");
        var node3 = new BooleanNode(false);
        var nodes = Map.of(
                "A1", node1,
                "A2", node2,
                "B3", node3
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");

        assertSame(NodeType.BOOLEAN, node1.getType());
    }

    @Test
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_ValidTwoJumpsWithFormula() {
        var node1 = new RefNode("A2");
        var node2 = new FunctionNode("MIN", List.of(new NumberNode(2), new NumberNode(4)));
        var nodes = Map.of(
                "A1", node1,
                "A2", node2
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");

        assertSame(NodeType.NUMBER, node1.getType());
    }


    @Test(expected = InvalidReferenceException.class)
    public void ExpressionAnalyzer_AnalyzeExpressionTree_Refs_InvalidJump() {
        var node1 = new RefNode("A2");
        var nodes = Map.of(
                "A1", (ExpressionNode)node1
        );

        var analyzer = new ExpressionTreeAnalyzer();

        analyzer.AnalyzeExpressionTree(nodes, "A1");
    }
}

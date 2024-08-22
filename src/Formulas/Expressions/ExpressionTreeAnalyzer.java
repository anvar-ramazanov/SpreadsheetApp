package Formulas.Expressions;

import java.util.Map;

public interface ExpressionTreeAnalyzer {
    /**
     * Check correctness of expression tree.
     * Throw exception if find some problems
     *
     * @param expressionNode root node of expression tree
     * @param nodeName name of root node
     * @param context map of others expression trees on the list
     */
    void AnalyzeExpressionTree(ExpressionNode expressionNode, String nodeName, Map<String, ExpressionNode> context);
}

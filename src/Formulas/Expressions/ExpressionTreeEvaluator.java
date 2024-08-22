package Formulas.Expressions;

import java.util.Map;

public interface ExpressionTreeEvaluator {
    /**
     * Evaluate given expression tree.
     *
     * @param node rootNode of expression tree
     * @param context map of others expression trees on the list
     * @return Result of evaluation expression tree
     */
    Object EvaluateExpressionTree(ExpressionNode node, Map<String, ExpressionNode> context);
}

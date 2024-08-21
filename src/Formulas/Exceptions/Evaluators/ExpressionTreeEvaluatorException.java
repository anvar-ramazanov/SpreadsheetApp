package Formulas.Exceptions.Evaluators;

public abstract class ExpressionTreeEvaluatorException extends RuntimeException  {
    public ExpressionTreeEvaluatorException() {
        super();
    }

    public ExpressionTreeEvaluatorException(String message) {
        super(message);
    }

    public ExpressionTreeEvaluatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpressionTreeEvaluatorException(Throwable cause) {
        super(cause);
    }
}

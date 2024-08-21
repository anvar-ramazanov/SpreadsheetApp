package Formulas.Exceptions.Expressions.TreeAnalyzer;

public abstract class ExpressionTreeAnalyzerException extends RuntimeException {
    public ExpressionTreeAnalyzerException() {
        super();
    }

    public ExpressionTreeAnalyzerException(String message) {
        super(message);
    }

    public ExpressionTreeAnalyzerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpressionTreeAnalyzerException(Throwable cause) {
        super(cause);
    }
}

package Formulas.Exceptions.Expressions.TreeParser;

public abstract class ExpressionTreeParserException extends RuntimeException {
    public ExpressionTreeParserException() {
        super();
    }

    public ExpressionTreeParserException(String message) {
        super(message);
    }

    public ExpressionTreeParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpressionTreeParserException(Throwable cause) {
        super(cause);
    }
}

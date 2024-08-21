package Formulas.Exceptions.Expressions.TreeParser;

public class UnexpectedTokenException extends ExpressionTreeParserException {

    public UnexpectedTokenException() {
        super();
    }

    public UnexpectedTokenException(String message) {
        super(message);
    }

    public UnexpectedTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedTokenException(Throwable cause) {
        super(cause);
    }
}

package Formulas.Exceptions.Expressions.TreeParser;

public class TokenExpectedException extends ExpressionTreeParserException {

    public TokenExpectedException() {
        super();
    }

    public TokenExpectedException(String message) {
        super(message);
    }

    public TokenExpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenExpectedException(Throwable cause) {
        super(cause);
    }
}

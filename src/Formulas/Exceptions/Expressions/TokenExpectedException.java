package Formulas.Exceptions.Expressions;

public class TokenExpectedException extends RuntimeException {

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

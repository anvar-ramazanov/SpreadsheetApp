package Formulas.Exceptions.Expressions.TreeAnalyzer;

public class InvalidReferenceException extends RuntimeException {

    public InvalidReferenceException() {
        super();
    }

    public InvalidReferenceException(String message) {
        super(message);
    }

    public InvalidReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidReferenceException(Throwable cause) {
        super(cause);
    }
}

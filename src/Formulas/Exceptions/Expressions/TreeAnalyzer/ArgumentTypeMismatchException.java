package Formulas.Exceptions.Expressions.TreeAnalyzer;

public class ArgumentTypeMismatchException extends RuntimeException {

    public ArgumentTypeMismatchException() {
        super();
    }

    public ArgumentTypeMismatchException(String message) {
        super(message);
    }

    public ArgumentTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentTypeMismatchException(Throwable cause) {
        super(cause);
    }
}

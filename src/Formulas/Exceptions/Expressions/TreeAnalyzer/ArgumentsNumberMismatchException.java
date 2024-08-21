package Formulas.Exceptions.Expressions.TreeAnalyzer;

public class ArgumentsNumberMismatchException extends ExpressionTreeAnalyzerException {

    public ArgumentsNumberMismatchException() {
        super();
    }

    public ArgumentsNumberMismatchException(String message) {
        super(message);
    }

    public ArgumentsNumberMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentsNumberMismatchException(Throwable cause) {
        super(cause);
    }
}
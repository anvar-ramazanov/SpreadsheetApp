package Formulas.Exceptions.Expressions.TreeAnalyzer;

public class OperandTypeMismatchException extends ExpressionTreeAnalyzerException {

    public OperandTypeMismatchException() {
        super();
    }

    public OperandTypeMismatchException(String message) {
        super(message);
    }

    public OperandTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperandTypeMismatchException(Throwable cause) {
        super(cause);
    }
}

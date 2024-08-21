package Formulas.Exceptions.Evaluators;

public class DivideByZeroException extends EvaluatorException {

    public DivideByZeroException() {
        super();
    }

    public DivideByZeroException(String message) {
        super(message);
    }

    public DivideByZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public DivideByZeroException(Throwable cause) {
        super(cause);
    }
}

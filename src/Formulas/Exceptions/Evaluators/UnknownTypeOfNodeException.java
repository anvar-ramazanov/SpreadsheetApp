package Formulas.Exceptions.Evaluators;

public class UnknownTypeOfNodeException extends RuntimeException {

    public UnknownTypeOfNodeException() {
        super();
    }

    public UnknownTypeOfNodeException(String message) {
        super(message);
    }

    public UnknownTypeOfNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTypeOfNodeException(Throwable cause) {
        super(cause);
    }
}
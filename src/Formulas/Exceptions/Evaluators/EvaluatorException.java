package Formulas.Exceptions.Evaluators;

public abstract class EvaluatorException extends RuntimeException  {
    public EvaluatorException() {
        super();
    }

    public EvaluatorException(String message) {
        super(message);
    }

    public EvaluatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvaluatorException(Throwable cause) {
        super(cause);
    }
}

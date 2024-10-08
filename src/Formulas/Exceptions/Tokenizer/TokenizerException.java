package Formulas.Exceptions.Tokenizer;

public abstract class TokenizerException extends RuntimeException {
    public TokenizerException() {
        super();
    }

    public TokenizerException(String message) {
        super(message);
    }

    public TokenizerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenizerException(Throwable cause) {
        super(cause);
    }
}

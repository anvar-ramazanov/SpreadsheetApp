package Formulas.Exceptions.Tokenizer;

public class UnexpectedCharacterException extends TokenizerException {
      public UnexpectedCharacterException() {
        super();
    }

    public UnexpectedCharacterException(String message) {
        super(message);
    }

    public UnexpectedCharacterException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedCharacterException(Throwable cause) {
        super(cause);
    }
}

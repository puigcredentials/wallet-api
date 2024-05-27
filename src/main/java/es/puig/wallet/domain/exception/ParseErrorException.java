package es.puig.wallet.domain.exception;

public class ParseErrorException extends RuntimeException {
    public ParseErrorException(String message) {
        super(message);
    }
}


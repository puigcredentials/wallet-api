package es.puig.wallet.domain.exception;

public class NoSuchTransactionException extends Exception {

    public NoSuchTransactionException(String message) {
        super(message);
    }
}

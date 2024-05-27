package es.puig.wallet.domain.exception;

public class NoSuchVerifiableCredentialException extends Exception {

    public NoSuchVerifiableCredentialException(String message) {
        super(message);
    }
}

package es.puig.wallet.domain.exception;

public class KeyPairGenerationError extends RuntimeException {
    public KeyPairGenerationError(String message) {
        super(message);
    }
}

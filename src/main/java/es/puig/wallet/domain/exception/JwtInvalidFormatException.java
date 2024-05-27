package es.puig.wallet.domain.exception;

public class JwtInvalidFormatException extends  Exception{
    public JwtInvalidFormatException(String message) {
        super(message);
    }

}

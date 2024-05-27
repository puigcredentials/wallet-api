package es.puig.wallet.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record DeferredCredentialRequest(
        @JsonProperty("transaction_id") String transactionId
) {
}

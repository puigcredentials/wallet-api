package es.puig.wallet.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

public interface ProofJWTService {
    Mono<JsonNode> buildCredentialRequest(String nonce, String issuer, String did);
}

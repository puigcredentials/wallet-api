package es.puig.wallet.domain.service;

import es.puig.wallet.domain.model.AuthorisationServerMetadata;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface EbsiIdTokenService {
    Mono<Map<String, String>> getIdTokenResponse(String processId, String did, AuthorisationServerMetadata authorisationServerMetadata, String jwt);
}

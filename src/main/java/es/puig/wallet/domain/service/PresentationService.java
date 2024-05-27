package es.puig.wallet.domain.service;

import es.puig.wallet.domain.model.CredentialsBasicInfo;
import es.puig.wallet.domain.model.VcSelectorResponse;
import reactor.core.publisher.Mono;

public interface PresentationService {
    Mono<String> createSignedVerifiablePresentation(String processId, String authorizationToken, VcSelectorResponse vcSelectorResponse,String nonce, String audience);
    Mono<String> createSignedVerifiablePresentation(String processId, String authorizationToken, CredentialsBasicInfo credentialsBasicInfo, String nonce, String audience);
    Mono<String> createEncodedVerifiablePresentationForDome(String processId, String authorizationToken, VcSelectorResponse vcSelectorResponse);
}

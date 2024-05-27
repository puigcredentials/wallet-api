package es.puig.wallet.domain.service;

import es.puig.wallet.domain.model.AuthorisationServerMetadata;
import es.puig.wallet.domain.model.CredentialIssuerMetadata;
import es.puig.wallet.domain.model.CredentialOffer;
import es.puig.wallet.domain.model.TokenResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Map;

public interface EbsiAuthorisationService {
    Mono<Tuple2<String, String>> getRequestWithOurGeneratedCodeVerifier(String processId, CredentialOffer credentialOffer, AuthorisationServerMetadata authorisationServerMetadata, CredentialIssuerMetadata credentialIssuerMetadata, String did);

    Mono<TokenResponse> sendTokenRequest(String codeVerifier, String did, AuthorisationServerMetadata authorisationServerMetadata, Map<String, String> params);
}

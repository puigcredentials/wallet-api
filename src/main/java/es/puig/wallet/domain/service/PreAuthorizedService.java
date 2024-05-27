package es.puig.wallet.domain.service;

import es.puig.wallet.domain.model.AuthorisationServerMetadata;
import es.puig.wallet.domain.model.CredentialOffer;
import es.puig.wallet.domain.model.TokenResponse;
import reactor.core.publisher.Mono;

public interface PreAuthorizedService {
    Mono<TokenResponse> getPreAuthorizedToken(String processId, CredentialOffer credentialOffer, AuthorisationServerMetadata authorisationServerMetadata, String authorizationToken);
}

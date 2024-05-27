package es.puig.wallet.domain.service;

import es.puig.wallet.domain.model.AuthorisationServerMetadata;
import es.puig.wallet.domain.model.CredentialIssuerMetadata;
import reactor.core.publisher.Mono;

public interface AuthorisationServerMetadataService {
    Mono<AuthorisationServerMetadata> getAuthorizationServerMetadataFromCredentialIssuerMetadata(String processId, CredentialIssuerMetadata credentialIssuerMetadata);
}

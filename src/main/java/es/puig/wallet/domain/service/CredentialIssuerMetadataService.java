package es.puig.wallet.domain.service;

import es.puig.wallet.domain.model.CredentialIssuerMetadata;
import es.puig.wallet.domain.model.CredentialOffer;
import reactor.core.publisher.Mono;

public interface CredentialIssuerMetadataService {
    Mono<CredentialIssuerMetadata> getCredentialIssuerMetadataFromCredentialOffer(String processId, CredentialOffer credentialOffer);
}

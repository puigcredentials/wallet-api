package es.puig.wallet.domain.service;

import es.puig.wallet.domain.model.CredentialOffer;
import reactor.core.publisher.Mono;

public interface CredentialOfferService {
    Mono<CredentialOffer> getCredentialOfferFromCredentialOfferUri(String processId, String credentialOfferUri);
}

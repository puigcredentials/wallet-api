package es.puig.wallet.application.workflow.presentation;

import es.puig.wallet.domain.model.CredentialsBasicInfo;
import reactor.core.publisher.Mono;

public interface AttestationExchangeTurnstileWorkflow {
    Mono<String> createVerifiablePresentationForTurnstile(String processId, String authorizationToken, CredentialsBasicInfo credentialsBasicInfo);
}

package es.puig.wallet.application.workflow.issuance;

import reactor.core.publisher.Mono;

public interface DeferredCredentialDomeProfileWorkflow {
    Mono<Void> requestDeferredCredential(String processId, String userId, String credentialId);
}

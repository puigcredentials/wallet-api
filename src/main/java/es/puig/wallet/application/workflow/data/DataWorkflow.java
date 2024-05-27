package es.puig.wallet.application.workflow.data;

import es.puig.wallet.domain.model.CredentialsBasicInfo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DataWorkflow {
    Mono<List<CredentialsBasicInfo>> getAllCredentialsByUserId(String processId, String userId);
    Mono<Void> deleteCredentialByIdAndUserId(String processId, String credentialId, String userId);
}

package es.puig.wallet.application.workflow.presentation;

import es.puig.wallet.domain.model.CredentialsBasicInfo;
import es.puig.wallet.domain.model.VcSelectorRequest;
import es.puig.wallet.domain.model.VcSelectorResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AttestationExchangeCommonWorkflow {
    Mono<VcSelectorRequest> processAuthorizationRequest(String processId, String authorizationToken, String qrContent);
    Mono<List<CredentialsBasicInfo>> getSelectableCredentialsRequiredToBuildThePresentation(String processId, String authorizationToken, List<String> scope);
    Mono<Void> buildVerifiablePresentationWithSelectedVCs(String processId, String authorizationToken, VcSelectorResponse vcSelectorResponse);
}

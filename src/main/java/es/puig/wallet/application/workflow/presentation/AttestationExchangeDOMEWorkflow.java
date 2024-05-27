package es.puig.wallet.application.workflow.presentation;

import es.puig.wallet.domain.model.VcSelectorRequest;
import es.puig.wallet.domain.model.VcSelectorResponse;
import reactor.core.publisher.Mono;


public interface AttestationExchangeDOMEWorkflow {
    Mono<VcSelectorRequest> getSelectableCredentialsRequiredToBuildThePresentation(String processId, String authorizationToken, String qrContent);
    Mono<Void> publishAuthorisationResponseWithSelectedVCs(String processId, String authorizationToken, VcSelectorResponse vcSelectorResponse);
}

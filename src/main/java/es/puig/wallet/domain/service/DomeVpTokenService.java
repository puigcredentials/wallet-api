package es.puig.wallet.domain.service;

import es.puig.wallet.domain.model.AuthorizationRequest;
import es.puig.wallet.domain.model.VcSelectorRequest;
import reactor.core.publisher.Mono;

public interface DomeVpTokenService {
    Mono<VcSelectorRequest> getVpRequest(String processId, String authorizationToken, AuthorizationRequest authorizationRequest);

}

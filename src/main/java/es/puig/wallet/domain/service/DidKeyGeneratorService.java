package es.puig.wallet.domain.service;

import reactor.core.publisher.Mono;

public interface DidKeyGeneratorService {
    Mono<String> generateDidKeyJwkJcsPub();
    Mono<String> generateDidKey();
}

package es.puig.wallet.application.port;

import es.puig.wallet.infrastructure.vault.model.KeyVaultSecret;
import reactor.core.publisher.Mono;

public interface VaultService {

    Mono<Void> saveSecret(String key, KeyVaultSecret secret);
    Mono<KeyVaultSecret> getSecretByKey(String key);
    Mono<Void> deleteSecretByKey(String key);

}

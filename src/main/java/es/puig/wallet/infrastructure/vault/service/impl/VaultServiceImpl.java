package es.puig.wallet.infrastructure.vault.service.impl;

import es.puig.wallet.application.port.VaultService;
import es.puig.wallet.infrastructure.vault.model.KeyVaultSecret;
import es.puig.wallet.infrastructure.vault.service.GenericVaultService;
import es.puig.wallet.infrastructure.vault.util.VaultFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class VaultServiceImpl implements VaultService {
    private final GenericVaultService vaultService;

    public VaultServiceImpl(VaultFactory vaultFactory) {
        this.vaultService = vaultFactory.getVaultService();
    }

    @Override
    public Mono<Void> saveSecret(String key, KeyVaultSecret secret) {
        return vaultService.saveSecret(key, secret);
    }

    @Override
    public Mono<KeyVaultSecret> getSecretByKey(String key) {
        return vaultService.getSecret(key);
    }

    @Override
    public Mono<Void> deleteSecretByKey(String key) {
        return vaultService.deleteSecret(key);
    }
}

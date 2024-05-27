package es.puig.wallet.infrastructure.vault.util;

import es.puig.wallet.infrastructure.vault.exception.VaultFactoryException;
import es.puig.wallet.infrastructure.vault.service.GenericVaultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class VaultFactory {

    private final List<GenericVaultService> vaultServices;

    public GenericVaultService getVaultService() {
        if (vaultServices.size() != 1) {
            throw new VaultFactoryException(vaultServices.size());
        }

        return vaultServices.get(0);
    }
}

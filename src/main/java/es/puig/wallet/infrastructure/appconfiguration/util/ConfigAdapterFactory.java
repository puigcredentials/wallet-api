package es.puig.wallet.infrastructure.appconfiguration.util;

import es.puig.wallet.infrastructure.appconfiguration.exception.ConfigAdapterFactoryException;
import es.puig.wallet.infrastructure.appconfiguration.service.GenericConfigAdapter;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ConfigAdapterFactory {
    private final List<GenericConfigAdapter> configAdapters;

    public ConfigAdapterFactory(List<GenericConfigAdapter> configServices) {
        this.configAdapters = configServices;
    }

    public GenericConfigAdapter getAdapter() {

        if (configAdapters.size() != 1) {
            throw new ConfigAdapterFactoryException(configAdapters.size());
        }

        return configAdapters.get(0);
    }
}

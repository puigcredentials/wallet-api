package es.puig.wallet.infrastructure.appconfiguration.adapter;

import com.azure.data.appconfiguration.ConfigurationClient;
import es.puig.wallet.infrastructure.appconfiguration.config.AzureAppConfigurationProperties;
import es.puig.wallet.infrastructure.appconfiguration.model.ConfigProviderName;
import es.puig.wallet.infrastructure.appconfiguration.service.GenericConfigAdapter;
import es.puig.wallet.infrastructure.appconfiguration.util.ConfigSourceNameAnnotation;
import org.springframework.stereotype.Component;

@Component
@ConfigSourceNameAnnotation(name = ConfigProviderName.AZURE)
public class AzureAppConfigurationAdapter implements GenericConfigAdapter {

    private final ConfigurationClient configurationClient;
    private final AzureAppConfigurationProperties azureAppConfigurationProperties;

    public AzureAppConfigurationAdapter(ConfigurationClient configurationClient, AzureAppConfigurationProperties azureAppConfigurationProperties) {
        this.configurationClient = configurationClient;
        this.azureAppConfigurationProperties = azureAppConfigurationProperties;
    }

    @Override
    public String getConfiguration(String key) {
        return configurationClient.getConfigurationSetting(key,
                azureAppConfigurationProperties.label().global()).getValue();
    }

}

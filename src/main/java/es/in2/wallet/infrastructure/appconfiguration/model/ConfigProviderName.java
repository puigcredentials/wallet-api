package es.in2.wallet.infrastructure.appconfiguration.model;

public enum ConfigProviderName {
    AZURE("azure"),
    YAML("yaml");

    private final String providerName;

    ConfigProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public String toString() {
        return providerName;
    }

}

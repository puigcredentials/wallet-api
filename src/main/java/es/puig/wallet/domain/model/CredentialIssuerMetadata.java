package es.puig.wallet.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record CredentialIssuerMetadata(
        @JsonProperty("credential_issuer") String credentialIssuer,
        @JsonProperty("authorization_server") String authorizationServer,
        @JsonProperty("credential_endpoint") String credentialEndpoint,
        @JsonProperty("deferred_credential_endpoint") String deferredCredentialEndpoint,
        @JsonProperty("credentials_supported") List<CredentialsSupported> credentialsSupported,

        //From this object, we only serialize the format property because, for the moment, it is the only object we need to interpret and obtain to perform the credential Request for the DOME PROFILE
        @JsonProperty("credential_configurations_supported") Map<String,CredentialsConfigurationsSupported> credentialsConfigurationsSupported
) {
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CredentialsConfigurationsSupported(
            @JsonProperty("format") String format
    ){

    }
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record CredentialsSupported(
            @JsonProperty("format") String format,
            @JsonProperty("types") List<String> types,
            @JsonProperty("trust_framework") TrustFramework trustFramework,
            @JsonProperty("display") List<Display> display
    ) {
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record TrustFramework(
                @JsonProperty("name") String name,
                @JsonProperty("type") String type,
                @JsonProperty("uri") String uri
        ) {
        }
        @Builder
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Display(
                @JsonProperty("name") String name,
                @JsonProperty("locale") String locale
        ) {
        }
    }
}

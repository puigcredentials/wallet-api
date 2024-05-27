package es.puig.wallet.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import es.puig.wallet.domain.util.ApplicationConstants;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;


@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record CredentialsBasicInfo(
        @JsonProperty("id") String id,
        @JsonProperty("type") List<String> vcType,
        @JsonProperty("status") CredentialStatus credentialStatus,
        @JsonProperty(ApplicationConstants.AVAILABLE_FORMATS) List<String> availableFormats,
        @JsonProperty(ApplicationConstants.CREDENTIAL_SUBJECT) JsonNode credentialSubject,
        @JsonProperty(ApplicationConstants.EXPIRATION_DATE)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ApplicationConstants.ISO_8601_DATE_PATTERN)
        ZonedDateTime expirationDate
) {
}

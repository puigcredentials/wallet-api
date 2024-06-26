package es.puig.wallet.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record QrContent(@JsonProperty("qr_content") String content) {
}

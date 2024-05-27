package es.puig.wallet.domain.model;

import lombok.Builder;

@Builder
public record GlobalErrorMessage(String title, String message, String path) {

}

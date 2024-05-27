package es.in2.wallet.api.controller;

import es.puig.wallet.application.workflow.presentation.AttestationExchangeCommonWorkflow;
import es.puig.wallet.application.workflow.presentation.AttestationExchangeDOMEWorkflow;
import es.puig.wallet.application.workflow.presentation.AttestationExchangeTurnstileWorkflow;
import es.puig.wallet.domain.model.CredentialsBasicInfo;
import es.puig.wallet.domain.model.VcSelectorResponse;
import es.puig.wallet.infrastructure.core.controller.VerifiablePresentationController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.shaded.com.google.common.net.HttpHeaders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VerifiablePresentationControllerTest {
    @Mock
    private AttestationExchangeTurnstileWorkflow attestationExchangeTurnstileWorkflow;
    @Mock
    private AttestationExchangeCommonWorkflow attestationExchangeCommonWorkflow;
    @Mock
    private AttestationExchangeDOMEWorkflow attestationExchangeDOMEWorkflow;
    @InjectMocks
    private VerifiablePresentationController verifiablePresentationController;

    @Test
    void testCreateVerifiablePresentation() {
        // Arrange
        String authorizationToken = "authToken";
        VcSelectorResponse vcSelectorResponse = VcSelectorResponse.builder().redirectUri("https://redirect.uri.com").build();

        when(attestationExchangeCommonWorkflow.buildVerifiablePresentationWithSelectedVCs(anyString(), eq(authorizationToken), eq(vcSelectorResponse)))
                .thenReturn(Mono.empty());

        WebTestClient
                .bindToController(verifiablePresentationController)
                .build()
                .post()
                .uri("/api/v1/vp")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorizationToken)
                .bodyValue(vcSelectorResponse)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void testCreateVerifiablePresentationDomeCase() {
        // Arrange
        String authorizationToken = "authToken";
        VcSelectorResponse vcSelectorResponse = VcSelectorResponse.builder().redirectUri("https://dome-marketplace.org").build();

        when(attestationExchangeDOMEWorkflow.publishAuthorisationResponseWithSelectedVCs(anyString(), eq(authorizationToken), eq(vcSelectorResponse)))
                .thenReturn(Mono.empty());

        WebTestClient
                .bindToController(verifiablePresentationController)
                .build()
                .post()
                .uri("/api/v1/vp")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorizationToken)
                .bodyValue(vcSelectorResponse)
                .exchange()
                .expectStatus().isCreated();
    }
    @Test
    void testCreateVerifiablePresentationInCborFormat() {
        String authorizationToken = "authToken";
        CredentialsBasicInfo credentialsBasicInfo = CredentialsBasicInfo.builder().build();
        String expectedResponse = "cbor";

        when(attestationExchangeTurnstileWorkflow.createVerifiablePresentationForTurnstile(anyString(), eq(authorizationToken), any()))
                .thenReturn(Mono.just("cbor"));

        WebTestClient
                .bindToController(verifiablePresentationController)
                .build()
                .post()
                .uri("/api/v1/vp/cbor")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authorizationToken)
                .bodyValue(credentialsBasicInfo)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo(expectedResponse);
    }

}

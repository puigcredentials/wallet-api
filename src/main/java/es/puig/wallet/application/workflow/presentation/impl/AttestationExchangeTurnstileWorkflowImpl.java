package es.puig.wallet.application.workflow.presentation.impl;

import com.fasterxml.jackson.core.JsonParseException;
import es.puig.wallet.application.workflow.presentation.AttestationExchangeTurnstileWorkflow;
import es.puig.wallet.domain.model.CredentialsBasicInfo;
import es.puig.wallet.domain.service.CborGenerationService;
import es.puig.wallet.domain.service.PresentationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.ParseException;



@Slf4j
@Service
@RequiredArgsConstructor
public class AttestationExchangeTurnstileWorkflowImpl implements AttestationExchangeTurnstileWorkflow {
    private final PresentationService presentationService;
    private final CborGenerationService cborGenerationService;

    @Override
    public Mono<String> createVerifiablePresentationForTurnstile(String processId, String authorizationToken, CredentialsBasicInfo credentialsBasicInfo) {
        return generateAudience()
                .flatMap(audience -> presentationService.createSignedVerifiablePresentation(processId, authorizationToken, credentialsBasicInfo, credentialsBasicInfo.id(), audience)
                )
                .flatMap(vp -> {
                    try {
                        return cborGenerationService.generateCbor(processId, vp);
                    } catch (ParseException e) {
                        return Mono.error(new JsonParseException("Error parsing the Verifiable Presentation"));
                    }
                });
    }

    private static Mono<String> generateAudience() {
        return Mono.just("vpTurnstile");
    }
}

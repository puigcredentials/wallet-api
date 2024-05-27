package es.puig.wallet.domain.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import es.puig.wallet.application.port.AppConfig;
import es.puig.wallet.application.port.BrokerService;
import es.puig.wallet.domain.exception.ParseErrorException;
import es.puig.wallet.domain.model.CredentialsBasicInfo;
import es.puig.wallet.domain.model.DomeVerifiablePresentation;
import es.puig.wallet.domain.model.VcSelectorResponse;
import es.puig.wallet.domain.model.VerifiablePresentation;
import es.puig.wallet.domain.service.DataService;
import es.puig.wallet.domain.service.PresentationService;
import es.puig.wallet.domain.service.SignerService;
import es.puig.wallet.domain.util.ApplicationConstants;
import es.puig.wallet.domain.util.ApplicationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PresentationServiceImpl implements PresentationService {

    private final ObjectMapper objectMapper;
    private final DataService dataService;
    private final BrokerService brokerService;
    private final SignerService signerService;

    private final AppConfig appConfig;

    /**
     * Creates and signs a Verifiable Presentation (VP) using the selected Verifiable Credentials (VCs).
     * This method retrieves the subject DID from the first VC, constructs an unsigned VP, and signs it.
     *
     * @param authorizationToken   The authorization token to identify the user.
     * @param vcSelectorResponse   The response containing the selected VCs for the VP.
     * @param nonce                A unique nonce for the VP.
     * @param audience             The intended audience of the VP.
     */
    @Override
    public Mono<String> createSignedVerifiablePresentation(String processId, String authorizationToken, VcSelectorResponse vcSelectorResponse,String nonce, String audience) {
        return createSignedVerifiablePresentation(processId, authorizationToken, nonce, audience, vcSelectorResponse.selectedVcList(), ApplicationConstants.VC_JWT);
    }

    /**
     * Creates and signs a Verifiable Presentation (VP) using the selected Verifiable Credential (VC).
     * This method retrieves the subject DID from the first VC, constructs an unsigned VP, and signs it.
     *
     * @param authorizationToken   The authorization token to identify the user.
     * @param credentialsBasicInfo The selected VC for the VP.
     * @param nonce                A unique nonce for the VP.
     * @param audience             The intended audience of the VP.
     */
    @Override
    public Mono<String> createSignedVerifiablePresentation(String processId, String authorizationToken, CredentialsBasicInfo credentialsBasicInfo, String nonce, String audience) {
        return createSignedVerifiablePresentation(processId, authorizationToken, nonce, audience, List.of(credentialsBasicInfo), ApplicationConstants.VC_CWT);
    }

    @Override
    public Mono<String> createEncodedVerifiablePresentationForDome(String processId, String authorizationToken, VcSelectorResponse vcSelectorResponse) {
        return createVerifiablePresentationForDome(processId, authorizationToken,vcSelectorResponse.selectedVcList());
    }

    private Mono<String> createSignedVerifiablePresentation(String processId, String authorizationToken,String nonce, String audience, List<CredentialsBasicInfo> selectedVcList, String format) {
        return  ApplicationUtils.getUserIdFromToken(authorizationToken)
                        .flatMap(userId -> getVerifiableCredentials(processId,userId,selectedVcList, ApplicationConstants.VC_JWT)
                            .flatMap(verifiableCredentialsListJWT -> getSubjectDidFromTheFirstVcOfTheList(verifiableCredentialsListJWT)
                                    .flatMap(did -> getVerifiableCredentials(processId,userId,selectedVcList, format)
                                            .flatMap(verifiableCredentialsList -> // Create the unsigned verifiable presentation
                                                    createUnsignedPresentation(verifiableCredentialsList, did,nonce,audience)
                                                            .flatMap(document -> signerService.buildJWTSFromJsonNode(document,did,"vp")))
                                    )
                            )
                            // Log success
                            .doOnSuccess(verifiablePresentation -> log.info("ProcessID: {} - Verifiable Presentation created successfully: {}", processId, verifiablePresentation))
                            // Handle errors
                            .onErrorResume(e -> {
                                log.error("Error in creating Verifiable Presentation: ", e);
                                return Mono.error(e);
                            })
                        );
    }

    private Mono<String> createVerifiablePresentationForDome(String processId, String authorizationToken,List<CredentialsBasicInfo> selectedVcList) {
        return  ApplicationUtils.getUserIdFromToken(authorizationToken)
                .flatMap(userId ->getVerifiableCredentials(processId,userId,selectedVcList, ApplicationConstants.VC_JSON)
                                .flatMap(this::createEncodedPresentation)
                                // Log success
                                .doOnSuccess(verifiablePresentation -> log.info("ProcessID: {} - DOME Verifiable Presentation created successfully: {}", processId, verifiablePresentation))
                                // Handle errors
                                .onErrorResume(e -> {
                                    log.error("Error in creating Verifiable Presentation: ", e);
                                    return Mono.error(e);
                                })
                );
    }
    /**
     * Retrieves a list of Verifiable Credential JWTs based on the VCs selected in the VcSelectorResponse.
     *
     * @param selectedVcList       The selected VCs.
     * @param format               The format of the VCs
     */
    private Mono<List<String>> getVerifiableCredentials(String processId, String userId, List<CredentialsBasicInfo> selectedVcList, String format) {
        return Flux.fromIterable(selectedVcList)
                .flatMap(credential -> brokerService.getCredentialByIdAndUserId(processId,credential.id(),userId))
                .flatMap(credentialEntity -> dataService.getVerifiableCredentialOnRequestedFormat(credentialEntity,format))
                .collectList();
    }

    /**
     * Extracts the subject DID from the first Verifiable Credential in the list.
     *
     * @param verifiableCredentialsList The list of VC JWTs.
     */
    private Mono<String> getSubjectDidFromTheFirstVcOfTheList(List<String> verifiableCredentialsList) {
        return Mono.fromCallable(() -> {
            // Check if the list is not empty
            try {
                if (!verifiableCredentialsList.isEmpty()) {
                    // Get the first verifiable credential's JWT and parse it
                    String verifiableCredential = verifiableCredentialsList.get(0);
                    SignedJWT parsedVerifiableCredential = SignedJWT.parse(verifiableCredential);
                    // Extract the subject DID from the JWT claims
                    return (String) parsedVerifiableCredential.getJWTClaimsSet().getClaim("sub");
                } else {
                    // Throw an exception if the credential list is empty
                    throw new NoSuchElementException("Verifiable credentials list is empty");
                }
            } catch (Exception e) {
                throw new IllegalStateException("Error obtaining the subject DID from the verifiable credential" + e);
            }
        });
    }

    /**
     * Creates an unsigned Verifiable Presentation containing the selected VCs.
     *
     * @param vcs       The list of VC JWTs to include in the VP.
     * @param holderDid The DID of the holder of the VPs.
     * @param nonce     A unique nonce for the VP.
     * @param audience  The intended audience of the VP.
     */
    private Mono<JsonNode> createUnsignedPresentation(
            List<String> vcs,
            String holderDid,
            String nonce,
            String audience) {
        return Mono.fromCallable(() -> {
            String id = "urn:uuid:" + UUID.randomUUID();

            VerifiablePresentation vpBuilder = VerifiablePresentation
                    .builder()
                    .id(id)
                    .holder(holderDid)
                    .context(List.of(ApplicationConstants.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1))
                    .type(List.of(ApplicationConstants.VERIFIABLE_PRESENTATION))
                    .verifiableCredential(vcs)
                    .build();

            Instant issueTime = Instant.now();
            Instant expirationTime = issueTime.plus(appConfig.getCredentialPresentationExpirationTime(), ChronoUnit.valueOf(appConfig.getCredentialPresentationExpirationUnit().toUpperCase()));
            Map<String, Object> vpParsed = JWTClaimsSet.parse(objectMapper.writeValueAsString(vpBuilder)).getClaims();
            JWTClaimsSet payload = new JWTClaimsSet.Builder()
                    .issuer(holderDid)
                    .subject(holderDid)
                    .audience(audience)
                    .notBeforeTime(java.util.Date.from(issueTime))
                    .expirationTime(java.util.Date.from(expirationTime))
                    .issueTime(java.util.Date.from(issueTime))
                    .jwtID(id)
                    .claim("vp", vpParsed)
                    .claim("nonce", nonce)
                    .build();
            log.debug(payload.toString());
            return objectMapper.readTree(payload.toString());
        });
    }

    /**
     * Creates an unsigned Verifiable Presentation containing the selected VCs.
     *
     * @param vcs       The list of VC JWTs to include in the VP.
     */
    private Mono<String> createEncodedPresentation(
            List<String> vcs) {
        return Mono.fromCallable(() -> {
            List<JsonNode> vcsJsonList = vcs.stream()
                    .map(vc -> {
                        try {
                            return objectMapper.readTree(vc);
                        } catch (Exception e) {
                            throw new ParseErrorException("Error parsing VC string to JsonNode");
                        }
                    })
                    .toList();

            DomeVerifiablePresentation vp = DomeVerifiablePresentation
                    .builder()
                    .holder("did:my:wallet")
                    .context(List.of(ApplicationConstants.JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1))
                    .type(List.of(ApplicationConstants.VERIFIABLE_PRESENTATION))
                    .verifiableCredential(vcsJsonList)
                    .build();

            String vpJson = objectMapper.writeValueAsString(vp);

            return Base64.getUrlEncoder().withoutPadding().encodeToString(vpJson.getBytes());

        });
    }
}

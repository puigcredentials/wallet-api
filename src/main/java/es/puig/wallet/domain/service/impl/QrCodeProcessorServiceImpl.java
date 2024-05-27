package es.puig.wallet.domain.service.impl;

import es.puig.wallet.application.workflow.issuance.CredentialIssuanceCommonWorkflow;
import es.puig.wallet.application.workflow.issuance.CredentialIssuanceEbsiWorkflow;
import es.puig.wallet.application.workflow.presentation.AttestationExchangeCommonWorkflow;
import es.puig.wallet.application.workflow.presentation.AttestationExchangeDOMEWorkflow;
import es.puig.wallet.domain.exception.NoSuchQrContentException;
import es.puig.wallet.domain.model.QrType;
import es.puig.wallet.domain.service.QrCodeProcessorService;
import es.puig.wallet.domain.util.ApplicationRegexPattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static es.puig.wallet.domain.model.QrType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class QrCodeProcessorServiceImpl implements QrCodeProcessorService {

    private final CredentialIssuanceCommonWorkflow credentialIssuanceCommonWorkflow;
    private final CredentialIssuanceEbsiWorkflow credentialIssuanceEbsiWorkflow;
    private final AttestationExchangeCommonWorkflow attestationExchangeCommonWorkflow;
    private final AttestationExchangeDOMEWorkflow attestationExchangeDOMEWorkflow;

    @Override
    public Mono<Object> processQrContent(String processId, String authorizationToken, String qrContent) {
        log.debug("ProcessID: {} - Processing QR content: {}", processId, qrContent);
        return identifyQrContentType(qrContent)
                .flatMap(qrType -> {
                    switch (qrType) {
                        case CREDENTIAL_OFFER_URI, OPENID_CREDENTIAL_OFFER: {
                            log.info("ProcessID: {} - Processing a Verifiable Credential Offer URI", processId);
                            return credentialIssuanceCommonWorkflow.identifyAuthMethod(processId, authorizationToken, qrContent)
                                    .doOnSuccess(credential -> log.info("ProcessID: {} - Credential Issued: {}", processId, credential))
                                    .doOnError(e -> log.error("ProcessID: {} - Error while issuing credential: {}", processId, e.getMessage()));
                        }
                        case EBSI_CREDENTIAL_OFFER: {
                            log.info("ProcessID: {} - Processing a Verifiable Credential Offer URI in EBSI Format", processId);
                            return credentialIssuanceEbsiWorkflow.identifyAuthMethod(processId, authorizationToken, qrContent)
                                    .doOnSuccess(credential -> log.info("ProcessID: {} - Credential Issued: {}", processId, credential))
                                    .doOnError(e -> log.error("ProcessID: {} - Error while issuing credential: {}", processId, e.getMessage()));
                        }
                        case VC_LOGIN_REQUEST: {
                            log.info("ProcessID: {} - Processing a Verifiable Credential Login Request", processId);
                            return attestationExchangeCommonWorkflow.processAuthorizationRequest(processId, authorizationToken, qrContent)
                                    .doOnSuccess(credential -> log.info("ProcessID: {} - Attestation Exchange", processId))
                                    .doOnError(e -> log.error("ProcessID: {} - Error while processing Attestation Exchange: {}", processId, e.getMessage()));
                        }
                        case OPENID_AUTHENTICATION_REQUEST: {
                            log.info("ProcessID: {} - Processing an Authentication Request", processId);
                            return Mono.error(new NoSuchQrContentException("OpenID Authentication Request not implemented yet"));
                        }
                        case DOME_VC_LOGIN_REQUEST: {
                            log.info("ProcessID: {} - Processing an Authentication Request from DOME", processId);
                            return attestationExchangeDOMEWorkflow.getSelectableCredentialsRequiredToBuildThePresentation(processId,authorizationToken,qrContent);
                        }
                        case UNKNOWN: {
                            String errorMessage = "The received QR content cannot be processed";
                            log.warn(errorMessage);
                            return Mono.error(new NoSuchQrContentException(errorMessage));
                        }
                        default: {
                            return Mono.empty();
                        }
                    }
                });
    }

    private Mono<QrType> identifyQrContentType(String qrContent) {
        return Mono.fromSupplier(() -> {
            if(ApplicationRegexPattern.DOME_LOGIN_REQUEST_PATTERN.matcher(qrContent).matches()){
                return DOME_VC_LOGIN_REQUEST;
            }
            else if (ApplicationRegexPattern.LOGIN_REQUEST_PATTERN.matcher(qrContent).matches()) {
                return VC_LOGIN_REQUEST;
            } else if (ApplicationRegexPattern.CREDENTIAL_OFFER_PATTERN.matcher(qrContent).matches()) {
                return QrType.CREDENTIAL_OFFER_URI;
            } else if (ApplicationRegexPattern.EBSI_CREDENTIAL_OFFER_PATTERN.matcher(qrContent).matches()){
                return EBSI_CREDENTIAL_OFFER;
            } else if (ApplicationRegexPattern.OPENID_CREDENTIAL_OFFER_PATTERN.matcher(qrContent).matches()) {
                return OPENID_CREDENTIAL_OFFER;
            } else if (ApplicationRegexPattern.OPENID_AUTHENTICATION_REQUEST_PATTERN.matcher(qrContent).matches()) {
                return OPENID_AUTHENTICATION_REQUEST;
            } else {
                log.warn("Unknown QR content type: {}", qrContent);
                return UNKNOWN;
            }
        });
    }


}

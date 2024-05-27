package es.in2.wallet.domain.util;

import java.util.List;

public class ApplicationConstants {
    private ApplicationConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String USER_ENTITY_PREFIX = "urn:entities:walletUser:";
    public static final String WALLET_USER_TYPE = "WalletUser";
    public static final String CREDENTIAL_TYPE = "Credential";
    public static final String TRANSACTION_TYPE = "Transaction";
    public static final String PROPERTY_TYPE = "Property";
    public static final String RELATIONSHIP_TYPE = "Relationship";
    public static final String CREDENTIAL_ENTITY_PREFIX = "urn:entities:credential:";
    public static final String TRANSACTION_ENTITY_PREFIX = "urn:entities:transaction:";
    public static final String ATTRIBUTES = "/attrs";
    public static final String PROCESS_ID = "ProcessId";
    public static final String DID = "did";
    public static final long MSB = 0x80L;
    public static final long LSB = 0x7FL;
    public static final long MSBALL = 0xFFFFFF80L;
    public static final String PRE_AUTH_CODE_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:pre-authorized_code";
    public static final String BEARER = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CREDENTIALS = "credentials";
    public static final String JWT_PROOF_CLAIM = "openid4vci-proof+jwt";
    public static final String JSONLD_CONTEXT_W3C_2018_CREDENTIALS_V1 = "https://www.w3.org/2018/credentials/v1";
    public static final String VERIFIABLE_PRESENTATION = "VerifiablePresentation";
    public static final String DID_KEY_PREFIX = "did:key:z";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
    public static final String CONTENT_TYPE_URL_ENCODED_FORM = "application/x-www-form-urlencoded";
    public static final String ISSUER_TOKEN_PROPERTY_NAME = "iss";
    public static final String ISSUER_SUB = "sub";
    public static final String VC_JWT = "jwt_vc";
    public static final String VC_JSON = "json_vc";
    public static final String VC_CWT = "cwt_vc";
    public static final String CREDENTIAL_SUBJECT = "credentialSubject";
    public static final String EXPIRATION_DATE = "expirationDate";
    public static final String GLOBAL_STATE = "MTo3NzcwMjoyNDU1NTkwMjMzOjE3MDU5MTE3NDA=";
    public static final String AUTH_CODE_GRANT_TYPE = "authorization_code";
    public static final String CODE_VERIFIER_ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";
    public static final String CUSTOMER_PRESENTATION_DEFINITION = "CustomerPresentationDefinition";
    public static final String CUSTOMER_PRESENTATION_SUBMISSION = "CustomerPresentationSubmission";
    public static final String JWT_VC = "jwt_vc";
    public static final String JWT_VC_JSON = "jwt_vc_json";
    public static final String JWT_VP = "jwt_vp";
    public static final String AVAILABLE_FORMATS = "available_formats";
    public static final String ALLOWED_METHODS = "*";
    public static final String GLOBAL_ENDPOINTS_API = "/api/v1/**";
    public static final String ISO_8601_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssX";
    public static final List<String> DEFAULT_VC_TYPES_FOR_DOME_VERIFIER = List.of("LegalPersonCredential","LEARCredentialEmployee");
    public static final List<String> DEFAULT_SCOPE_FOR_DOME_VERIFIER = List.of("didRead", "defaultScope");
}

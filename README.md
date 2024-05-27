
## Introduction

Wallet Server is a service that allows to manage digital credentials. It is designed to be used in a decentralized identity ecosystem, where users can store their credentials in a secure and private way.

Wallet Server includes the requested features described in the [EUDI Wallet Arquitecture Reference](https://github.com/eu-digital-identity-wallet/eudi-doc-architecture-and-reference-framework/blob/main/docs/arf.md), and it is EBSI compliance ([EBSI test v3.4](https://hub.ebsi.eu/wallet-conformance)).

## Features
- Create key pair from secp256r1 algorithm.
- Create did:key identifier from ES256 key algorithm.
- Create did:key:jwk_jcs-pub identifier from ES256 key algorithm.
- Complete flows compliant with EBSI for issuing and presenting digital credentials.
- Context Broker Integration: Facilitates advanced management of credentials and DIDs through standardized interfaces.
- Vault Integration: Provides secure cryptographic storage for private keys using state-of-the-art vault technology.
- Cross-Device and Same-Device support.


## Installation
### Prerequisites
- [Docker Desktop](https://www.docker.com/)
- [Git](https://git-scm.com/)

## Supported Platforms

### Context Brokers
Wallet Server supports integration with various context brokers, offering flexibility in deployment and operation:

* Scorpio //todo version
* Orion-LD //todo version

### Vault Solutions
For secure storage of private keys, Wallet Server integrates with leading vault technologies:

* HashiCorp Vault //todo version
* Azure Key Vault //todo version

This integration ensures high-level security for cryptographic operations within the Wallet Server ecosystem.

## Configuration

Ensure to adjust the environment variables to match your configurations.

* Wallet-Server Configuration
```yaml
wallet-server:
  image: puigcredentials/wallet-api:v1.0.0
  environment:
    # Logging Configuration
    LOGGING_LEVEL_ES_PUIG_WALLET_API: "DEBUG" # Set logging level (e.g., DEBUG, INFO, WARN, ERROR)
    # OpenAPI Configuration
    OPENAPI_SERVER_URL: "https://yourdomain.com" # The URL where your Wallet Server is hosted
    OPENAPI_SERVER_DESCRIPTION: "Wallet Server API" # Brief description of your server
    OPENAPI_INFO_TITLE: "Your Wallet Server Title" # The title of your API
    OPENAPI_INFO_DESCRIPTION: "Description of your Wallet Server functionalities"
    OPENAPI_INFO_VERSION: "1.0.0" # API version
    OPENAPI_INFO_TERMS_OF_SERVICE: "https://yourdomain.com/terms" # URL to the terms of service
    OPENAPI_INFO_LICENSE_NAME: "Apache 2.0" # License name
    OPENAPI_INFO_LICENSE_URL: "https://www.apache.org/licenses/LICENSE-2.0.html" # Link to license
    OPENAPI_INFO_CONTACT_NAME: "Your Organization Name"
    OPENAPI_INFO_CONTACT_URL: "https://yourdomain.com"
    OPENAPI_INFO_CONTACT_EMAIL: "contact@yourdomain.com"
    # CORS Configuration
    WALLET_WDA_URL: "https://yourfrontenddomain.com" # Frontend domain for CORS policy
    # Broker Configuration
    BROKER_PROVIDER: "YourBrokerProvider" # e.g., scorpio, orion-ld
    BROKER_EXTERNALDOMAIN: "https://yourbrokerdomain:port"
    BROKER_INTERNALDOMAIN: "https://yourinternalbrokerdomain:port"
    BROKER_PATHS_ENTITIES: "/ngsi-ld/v1/entities"
    # Vault Configuration
    VAULT_PROVIDER_NAME: "YourVaultProvider" # e.g., hashicorp
    SPRING_CLOUD_VAULT_AUTHENTICATION: "token"
    SPRING_CLOUD_VAULT_TOKEN: "YourVaultToken"
    SPRING_CLOUD_VAULT_HOST: "vault"
    SPRING_CLOUD_VAULT_SCHEME: "http"
    SPRING_CLOUD_VAULT_PORT: "YourVaultPort"
    SPRING_CLOUD_VAULT_KV_ENABLED: "true"
  ports:
    - "8080:8081"

```

## Resources
* [Google Java Style](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml)

## Project Status
The project is currently at version **1.0.0** and is in a stable state.

## Creation Date and Update Dates
* **Creation Date:** February 19, 2024

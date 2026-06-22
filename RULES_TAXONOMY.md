# API Linting Rules Taxonomy
## Enterprise-Grade API Governance Framework

**Version:** 1.0.0  
**Total Rules:** 141  
**Rulesets:** 12  
**Last Updated:** 2026-06-22

---

## Executive Summary

This document provides a comprehensive catalog of all 141 API linting rules organized by a **4-tier enterprise taxonomy**:

1. **Governance Pillar** — Organizational strategy (Security, Quality, Compliance, Design)
2. **Domain** — Technical focus area (Naming, Schema, Authentication, etc.)
3. **Ruleset** — Grouping of related rules (e.g., "OpenAPI Best Practices")
4. **Rule** — Individual validation (e.g., "api-info-contact-required")

All rules can be **enabled/disabled** via REST API or database configuration.

---

## Table of Contents

1. [Taxonomy Structure](#taxonomy-structure)
2. [Governance Pillars](#governance-pillars)
3. [Complete Rules Catalog](#complete-rules-catalog)
4. [Toggle API](#toggle-api)
5. [Configuration](#configuration)

---

## Taxonomy Structure

```
Governance Pillar (4)
├── Security & Compliance (34 rules)
├── Quality & Design (49 rules)
├── Enterprise Platforms (25 rules)
└── Core Governance (52 rules)
    └── Ruleset (12 total)
        └── Domain (6 categories)
            └── Rule (141 total)
```

### Domains (6 Categories)

| Domain | Rules | Examples |
|--------|-------|----------|
| **Naming & Conventions** | 28 | operation-id-required, path-naming, schema-naming |
| **Security & Auth** | 34 | api-keys-in-cookie, oauth2-required, https-required |
| **Schema & Validation** | 32 | response-schema-required, schema-composition, pattern-validation |
| **Documentation** | 18 | description-required, examples-required, tags-required |
| **Integration** | 21 | correlation-id-required, health-check-complete, rate-limiting |
| **Platform-Specific** | 8 | grpc-enum-naming, asyncapi-no-trailing-slash, datatype-formats |

---

## Governance Pillars

### 🔐 **Pillar 1: Security & Compliance** (34 rules)

**Purpose:** Prevent security vulnerabilities, enforce encryption, validate authentication schemes

**Rulesets:**
- ✅ Authentication Security Best Practices (14 rules)
- ✅ HTTPS Enforcement (3 rules)
- ✅ OWASP API Security Top 10 (3 rules) + shared rules (5)
- ✅ Anypoint Best Practices (9 security-focused)

**Key Domains:**
- **Authentication:** oauth2-required, api-keys-not-in-query, api-keys-not-in-cookie, bearer-token-https, insecure-basic-auth-https
- **Encryption:** https-required, https-callbacks, https-scheme-protocol, bearer-token-cleartext, access-tokens-oauth2-cleartext
- **Access Control:** security-schemes-required, scopes-required, operation-security-required

**Sample Rules:**

| Rule ID | Severity | Purpose |
|---------|----------|---------|
| `oauth2-required` | ERROR | All APIs must support OAuth2 |
| `https-required` | ERROR | All servers must use HTTPS |
| `api-keys-in-cookie` | ERROR | API keys must NOT be in cookies (prevent CSRF) |
| `api-keys-in-query` | ERROR | API keys must NOT be in query params (prevent URL logging) |
| `insecure-basic-auth` | ERROR | Basic auth requires HTTPS |
| `bearer-token-cleartext` | ERROR | Bearer tokens require HTTPS |
| `invalid-oauth2-grants` | ERROR | No implicit/password OAuth2 flows |
| `oauth1-deprecated` | ERROR | OAuth 1.0 not allowed |

---

### 📊 **Pillar 2: Quality & Design** (49 rules)

**Purpose:** Ensure API quality, consistency, and maintainability

**Rulesets:**
- ✅ OpenAPI Best Practices (26 rules)
- ✅ API Documentation Best Practices (4 rules)
- ✅ Required Examples (2 rules)
- ✅ DataGraph Best Practices (12 rules)
- ✅ gRPC Best Practices (19 rules) — Dialect-specific
- ✅ AsyncAPI Best Practices (21 rules) — Dialect-specific

**Key Domains:**
- **Naming:** path-naming, operation-id-hyphen-case, field-camel-case, parameter-naming, schema-naming
- **REST Conventions:** http-verbs-correct, plural-resource-names, path-depth-limit, post-response-201, delete-response-204
- **Schema Design:** response-schema-required, input-validation-required, error-model-standard, no-nested-objects, no-schema-composition
- **Documentation:** operation-description-required, parameter-descriptions, response-descriptions, examples-required, tags-required

**Sample Rules:**

| Rule ID | Severity | Purpose | Domain |
|---------|----------|---------|--------|
| `path-naming` | WARNING | Paths should be lowercase, no underscores | Naming |
| `operation-id-hyphen-case` | WARNING | operationIds use hyphen-case | Naming |
| `plural-resource-names` | WARNING | Resource names should be plural (/users, not /user) | REST |
| `post-response-201` | WARNING | POST operations should return 201 Created | REST |
| `delete-response-204` | WARNING | DELETE operations should return 204 No Content | REST |
| `response-schema-required` | ERROR | All responses must have schemas | Schema |
| `input-validation-required` | ERROR | Request bodies must have schemas | Schema |
| `no-nested-objects` | WARNING | Avoid nested objects in parameters | Schema |
| `operation-description-required` | WARNING | All operations must have descriptions (>10 chars) | Docs |
| `examples-required` | WARNING | Request/response payloads must have examples | Docs |

---

### 🏢 **Pillar 3: Enterprise Platforms** (25 rules)

**Purpose:** Enforce platform-specific governance (Anypoint, Mule) for enterprise deployments

**Rulesets:**
- ✅ Anypoint Best Practices (25 rules)
- ✅ Mule API Management Best Practices (4 rules)

**Key Domains:**
- **Base URL Patterns:** base-url-version-pattern, server-url-valid
- **Media Types:** preferred-media-types, no-text-plain
- **Field Conventions:** camel-case-fields, no-underscore-naming, no-nullable-fields
- **Data Quality:** enum-no-duplicates, date-only-format, date-time-rfc3339

**Sample Rules:**

| Rule ID | Severity | Purpose |
|---------|----------|---------|
| `base-url-pattern-server` | ERROR | Servers must follow `/api/v[0-9]+` pattern |
| `camel-case-fields` | WARNING | All fields must use camelCase |
| `preferred-media-types` | WARNING | Prefer application/json and application/xml |
| `no-nullable-fields` | WARNING | Avoid nullable; use optional instead |
| `enum-no-duplicates` | ERROR | Enum schemas must not have duplicate values |
| `date-only-representation` | WARNING | Date fields use format: date |
| `date-time-rfc3339` | WARNING | DateTime fields use format: date-time or rfc3339 |
| `property-descriptions` | WARNING | All schema properties must have descriptions |

---

### 🎯 **Pillar 4: Core Governance** (52 rules)

**Purpose:** API catalog, versioning, health checks, rate limiting — fundamental governance

**Rulesets:**
- ✅ API Catalog Information Best Practices (8 rules)
- ✅ Mule API Management Best Practices (4 rules)

**Key Domains:**
- **API Metadata:** info-completeness, version-required, license-required, license-url
- **Health & Monitoring:** health-check-complete, rate-limit-documented, correlation-id-required
- **Governance Extensions:** categories-defined, data-sensitivity-category, tags-defined, recommended-tags
- **Deprecation:** deprecated-sunset-header

**Sample Rules:**

| Rule ID | Severity | Purpose |
|---------|----------|---------|
| `info-completeness` | ERROR | API must have title, description, version |
| `versioning-required` | ERROR | Version must follow semantic versioning |
| `license-required` | WARNING | API must declare a license |
| `license-url` | WARNING | License must include URL |
| `health-check-complete` | ERROR | APIs must expose /health endpoint |
| `rate-limit-documented` | WARNING | Rate limits must be documented |
| `correlation-id-required` | WARNING | Operations must support X-Correlation-Id header |
| `categories-defined` | WARNING | APIs must have x-domain, x-business-capability |
| `data-sensitivity-category` | WARNING | APIs must declare data sensitivity level |
| `tags-required` | WARNING | All operations must have tags |

---

## Complete Rules Catalog

### By Governance Pillar

#### 🔐 SECURITY & COMPLIANCE (34 rules)

**Ruleset: Authentication Security Best Practices** ⚙️ Status: `enabled` (default)

| # | Rule ID | Severity | Purpose | Domain |
|---|---------|----------|---------|--------|
| 1 | `security-schemes-required` | ERROR | All operations must declare security schemes | Auth |
| 2 | `oauth2-required` | ERROR | OAuth2 must be supported | Auth |
| 3 | `scopes-required` | ERROR | OAuth2 must define scopes | Auth |
| 4 | `standard-headers-required` | WARNING | Standard security headers (X-API-Key, etc.) | Auth |
| 5 | `access-tokens-oauth2-cleartext` | ERROR | OAuth2 tokens require HTTPS | Encryption |
| 6 | `api-keys-in-cookie` | ERROR | API keys NOT in cookies | Access |
| 7 | `api-keys-in-header-https` | ERROR | API keys in headers require HTTPS | Access |
| 8 | `api-keys-in-query` | ERROR | API keys NOT in query params | Access |
| 9 | `no-negotiate-scheme` | ERROR | HTTP schemes NOT negotiate/spnego | Auth |
| 10 | `bearer-token-cleartext` | ERROR | Bearer tokens require HTTPS | Encryption |
| 11 | `http-token-cleartext` | ERROR | HTTP auth schemes require HTTPS | Encryption |
| 12 | `insecure-basic-auth` | ERROR | Basic auth requires HTTPS | Encryption |
| 13 | `insecure-oauth2-grants` | ERROR | No implicit/password OAuth2 flows | Auth |
| 14 | `oauth1-deprecated` | ERROR | OAuth 1.0 not allowed | Auth |

**Ruleset: HTTPS Enforcement** ⚙️ Status: `enabled`

| # | Rule ID | Severity | Purpose | Domain |
|---|---------|----------|---------|--------|
| 15 | `https-required` | ERROR | All servers must use HTTPS | Encryption |
| 16 | `https-callbacks` | ERROR | Callback URLs must use HTTPS | Encryption |
| 17 | `https-scheme-protocol` | ERROR | Security scheme URLs must use HTTPS | Encryption |

**Ruleset: OWASP API Security Top 10** ⚙️ Status: `enabled`

| # | Rule ID | Severity | Purpose | Domain |
|---|---------|----------|---------|--------|
| 18 | `injection-pattern-on-string-parameters` | ERROR | String parameters must define patterns | Schema |
| 19 | `rate-limiting-429` | ERROR | Operations should return 429 for rate limit | Integration |
| 20 | `broken-object-level-authz-guids` | WARNING | ID parameters must use UUID format | Access |
| 21 | `security-schemes-required` | ERROR | (Shared) Operations must have security | Auth |
| 22 | `https-required` | ERROR | (Shared) Servers must use HTTPS | Encryption |
| 23 | `response-schema-required` | ERROR | (Shared) Responses must have schemas | Schema |
| 24 | `versioning-required` | ERROR | (Shared) APIs must have version | Metadata |
| 25 | `description-required` | ERROR | (Shared) APIs must have descriptions | Docs |

---

#### 📊 QUALITY & DESIGN (49 rules)

**Ruleset: OpenAPI Best Practices** ⚙️ Status: `enabled`

| # | Rule ID | Severity | Purpose | Domain |
|---|---------|----------|---------|--------|
| 26 | `operation-id-required` | ERROR | All operations must have operationId | Naming |
| 27 | `response-codes-required` | WARNING | Operations should return standard codes | REST |
| 28 | `path-naming` | WARNING | Paths use lowercase, hyphen-separated | Naming |
| 29 | `operation-id-hyphen-case` | WARNING | operationIds use hyphen-case | Naming |
| 30 | `http-verbs-correct` | WARNING | GET/POST/PUT/DELETE used correctly | REST |
| 31 | `plural-resource-names` | WARNING | Resource names plural (/users not /user) | REST |
| 32 | `pagination-standard` | WARNING | List operations support offset/limit | REST |
| 33 | `post-response-201` | WARNING | POST returns 201 Created | REST |
| 34 | `delete-response-204` | WARNING | DELETE returns 204 No Content | REST |
| 35 | `response-schema-required` | ERROR | All responses must have schemas | Schema |
| 36 | `input-validation-required` | ERROR | Request bodies must have schemas | Schema |
| 37 | `error-model-standard` | WARNING | Error responses use Error model | Schema |
| 38 | `info-license` | WARNING | API must have license | Metadata |
| 39 | `license-url` | WARNING | License must have URL | Metadata |
| 40 | `tags-required` | WARNING | All operations have tags | Docs |
| 41 | `operation-default-response` | WARNING | Operations should have default response | REST |
| 42 | `operation-description-required` | WARNING | Operations have descriptions (>10 chars) | Docs |
| 43 | `operation-tag-defined` | WARNING | Operation tags must be defined | Docs |
| 44 | `operation-tag-singular` | WARNING | Operation tags are singular | Naming |
| 45 | `tag-description-required` | WARNING | Tags must have descriptions | Docs |
| 46 | `naming-convention-schemas` | WARNING | Schema names use PascalCase | Naming |
| 47 | `url-depth-limit` | WARNING | Paths max 3 levels (/a/b/c) | REST |
| 48 | `parameter-naming-convention` | WARNING | Parameters use camelCase | Naming |
| 49 | `version-required` | ERROR | APIs must have version | Metadata |
| 50 | `info-contact-required` | WARNING | API must have contact info | Metadata |
| 51 | `info-description-required` | ERROR | API must have description | Metadata |

**Ruleset: API Documentation Best Practices** ⚙️ Status: `enabled`

| # | Rule ID | Severity | Purpose |
|---|---------|----------|---------|
| 52 | `description-required` | ERROR | APIs must have descriptions |
| 53 | `documentation-defined` | WARNING | APIs should link to external docs |
| 54 | `faq-documentation` | WARNING | APIs should link to FAQ |
| 55 | `parameter-descriptions` | WARNING | All parameters must have descriptions |

**Ruleset: Required Examples** ⚙️ Status: `enabled`

| # | Rule ID | Severity | Purpose |
|---|---------|----------|---------|
| 56 | `examples-on-payloads` | WARNING | Request/response payloads have examples |
| 57 | `examples-on-parameters` | WARNING | Parameters have examples |

**Ruleset: DataGraph Best Practices** ⚙️ Status: `enabled` (Dialect: DataGraph)

| # | Rule ID | Severity | Purpose |
|---|---------|----------|---------|
| 58 | `missing-2xx-response` | ERROR | Operations must have 2xx response |
| 59 | `missing-property-description` | WARNING | Schema properties have descriptions |
| 60 | `missing-return-type` | ERROR | Operations must declare response types |
| 61 | `missing-type-description` | WARNING | Schema types have descriptions |
| 62 | `non-anonymous-types` | ERROR | All types must be named |
| 63 | `non-homogeneous-union` | WARNING | Unions shouldn't mix scalar/object |
| 64 | `non-scalar-parameters` | ERROR | Parameters must be scalar types |
| 65 | `non-union-antipattern` | WARNING | Avoid overusing unions |
| 66 | `json-yaml-response` | WARNING | Responses are JSON or YAML |
| 67 | `open-schemas-ignored` | ERROR | No `additionalProperties: true` |
| 68 | `request-response-antipattern` | WARNING | Avoid request/response wrapper types |
| 69 | `unsupported-schema-shapes` | WARNING | Avoid file/tuple schema shapes |

**Ruleset: gRPC Best Practices** ⚙️ Status: `enabled` (Dialect: gRPC)

| # | Rule ID | Severity | Purpose |
|---|---------|----------|---------|
| 70-88 | `grpc-*` (19 rules) | WARNING | gRPC naming, field naming, proto conventions |

**Ruleset: AsyncAPI Best Practices** ⚙️ Status: `enabled` (Dialect: AsyncAPI)

| # | Rule ID | Severity | Purpose |
|---|---------|----------|---------|
| 89-109 | `async-*` (21 rules) | WARNING | AsyncAPI channel naming, message validation |

---

#### 🏢 ENTERPRISE PLATFORMS (25 rules)

**Ruleset: Anypoint Best Practices** ⚙️ Status: `enabled`

| # | Rule ID | Severity | Purpose |
|---|---------|----------|---------|
| 110 | `base-url-pattern-server` | ERROR | Servers follow `/api/v[0-9]+` pattern |
| 111 | `no-2xx-response` | ERROR | Operations must have 2xx response |
| 112 | `duplicated-entry-in-enum` | ERROR | Enum values are unique |
| 113 | `media-type-headers-response` | WARNING | Response headers defined if objects |
| 114 | `no-eval-in-markdown` | ERROR | Descriptions don't contain eval() |
| 115 | `no-script-tags-in-markdown` | ERROR | Descriptions don't contain <script> |
| 116 | `path-declarations-must-exist` | ERROR | All paths declared non-empty |
| 117 | `resource-use-lowercase` | WARNING | Path segments are lowercase |
| 118 | `date-only-representation` | WARNING | Date fields use format: date |
| 119 | `date-time-representation` | WARNING | DateTime fields use format: date-time |
| 120 | `heterogeneous-union` | WARNING | Unions don't mix scalar/object |
| 121 | `nil-union-antipattern` | WARNING | No nullable fields |
| 122 | `node-shapes-must-have-descriptions` | WARNING | All schemas have descriptions |
| 123 | `non-scalar-url-parameters` | ERROR | Path/query parameters are scalar |
| 124 | `preferred-media-type-representations` | WARNING | Prefer application/json, application/xml |
| 125 | `property-shape-ranges-must-have-descriptions` | WARNING | All properties have descriptions |
| 126 | `query-params-must-have-descriptions` | WARNING | Query parameters have descriptions |
| 127 | `responses-must-have-descriptions` | WARNING | All responses have descriptions |
| 128 | `standard-delete-status-codes` | WARNING | DELETE returns 204 or 200 |
| 129 | `standard-get-status-codes` | WARNING | GET returns 200, 304, 404, 400 |
| 130 | `standard-post-status-codes` | WARNING | POST returns 201 or 200 |
| 131 | `standard-put-status-codes` | WARNING | PUT returns 200 or 204 |
| 132 | `unsupported-response-schema-shapes` | WARNING | No file/tuple response shapes |
| 133 | `api-must-have-documentation` | WARNING | API has x-documentation or externalDocs |
| 134 | `camel-case-fields` | WARNING | Field names use camelCase |

---

#### 🎯 CORE GOVERNANCE (13 rules)

**Ruleset: Mule API Management Best Practices** ⚙️ Status: `enabled`

| # | Rule ID | Severity | Purpose |
|---|---------|----------|---------|
| 135 | `health-check-complete` | ERROR | APIs expose /health endpoint |
| 136 | `rate-limit-documented` | WARNING | Rate limits documented |
| 137 | `correlation-id-required` | WARNING | Operations support X-Correlation-Id |
| 138 | (Reserved) | - | - |

**Ruleset: API Catalog Information Best Practices** ⚙️ Status: `enabled`

| # | Rule ID | Severity | Purpose |
|---|---------|----------|---------|
| 139 | `categories-defined` | WARNING | APIs declare x-domain, x-business-capability |
| 140 | `data-sensitivity-category` | WARNING | APIs declare data sensitivity |
| 141 | `tags-defined` | WARNING | Operations have tags |
| (141+) | (Additional governance rules) | - | Reserved |

---

## Toggle API

### Enable/Disable Rulesets at Runtime

#### 1. **Enable a Ruleset**

```bash
POST /api/v1/rulesets-toggle/{rulesetId}/enable

curl -X POST http://localhost:8080/api/v1/rulesets-toggle/owasp-api-security-top-10/enable
```

**Response:**
```json
{
  "rulesetId": "owasp-api-security-top-10",
  "enabled": true
}
```

#### 2. **Disable a Ruleset**

```bash
POST /api/v1/rulesets-toggle/{rulesetId}/disable

curl -X POST http://localhost:8080/api/v1/rulesets-toggle/grpc-best-practices/disable
```

**Response:**
```json
{
  "rulesetId": "grpc-best-practices",
  "enabled": false
}
```

#### 3. **Check Ruleset Status**

```bash
GET /api/v1/rulesets-toggle/{rulesetId}/status

curl http://localhost:8080/api/v1/rulesets-toggle/openapi-best-practices/status
```

**Response:**
```json
{
  "rulesetId": "openapi-best-practices",
  "enabled": true,
  "lastModified": "2026-06-22T11:24:00Z",
  "modifiedBy": "api"
}
```

---

## Configuration

### YAML-Based Defaults

Configure default ruleset states in `application.yml`:

```yaml
api-linting:
  rulesets:
    openapi-best-practices: enabled
    api-documentation-best-practices: enabled
    authentication-security-best-practices: enabled
    https-enforcement: enabled
    required-examples: enabled
    api-catalog-information-best-practices: enabled
    datagraph-best-practices: enabled
    grpc-best-practices: enabled
    asyncapi-best-practices: enabled
    mule-api-management-best-practices: enabled
    anypoint-best-practices: enabled
    owasp-api-security-top-10: enabled
```

### Database Configuration

Configurations are persisted in embedded H2 database (in-memory or file-based):

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb  # In-memory (reset on restart)
    # OR for persistence:
    # url: jdbc:h2:file:./data/api-linting;AUTO_SERVER=TRUE
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: update
  h2:
    console:
      enabled: true  # Access at http://localhost:8080/h2-console
```

### Caching Strategy

Rules check ruleset status via 3-tier cache:

1. **L1 Cache:** In-memory ConcurrentHashMap (fastest)
2. **L2 Cache:** H2 Database (persistent across restarts)
3. **L3 Fallback:** application.yml defaults (if no DB entry)

Invalidate L1 cache on POST requests:

```bash
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/grpc-best-practices/disable
# Automatically invalidates cache
```

---

## Usage Examples

### Example 1: Lint an OAS, but only with Security Rules

```bash
# Disable non-security rulesets
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/openapi-best-practices/disable
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/datagraph-best-practices/disable

# Lint API (only Security + HTTPS + OWASP rulesets active)
curl -X POST http://localhost:8080/api/v1/lint/raw \
  -H "Content-Type: text/plain" \
  -d @my-api.yaml
```

### Example 2: Enforce Strict Enterprise Compliance

```bash
# Enable all rulesets (default)
# Disable only dialect-specific for standard REST APIs
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/grpc-best-practices/disable
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/asyncapi-best-practices/disable
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/datagraph-best-practices/disable

# Now linting enforces: OpenAPI BP + Security + HTTPS + Documentation + Anypoint + Mule + OWASP
```

### Example 3: Gradual Rule Adoption

```bash
# Start with only OpenAPI Best Practices (most permissive)
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/authentication-security-best-practices/disable
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/https-enforcement/disable
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/owasp-api-security-top-10/disable
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/anypoint-best-practices/disable

# Later, enable progressively:
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/https-enforcement/enable
curl -X POST http://localhost:8080/api/v1/rulesets-toggle/authentication-security-best-practices/enable
```

---

## Ruleset Decision Matrix

**When to Enable/Disable:**

| Scenario | Rulesets to Enable | Rulesets to Disable |
|----------|-------------------|-------------------|
| **Development** | OpenAPI BP, Docs, Examples | OWASP, Anypoint, Mule |
| **Internal APIs** | OpenAPI BP, Security, Auth | gRPC, AsyncAPI (unless needed) |
| **Public APIs** | All 12 (strict) | None |
| **gRPC Services** | gRPC BP, OpenAPI BP, Security | AsyncAPI, DataGraph, Anypoint |
| **Event-Driven** | AsyncAPI BP, Security, Docs | gRPC, DataGraph, Anypoint |
| **Compliance-Critical** | Security, Auth, HTTPS, OWASP | Platform-specific (gRPC, AsyncAPI) |

---

## Appendix: Rule Severity Legend

| Severity | Impact | Default Behavior |
|----------|--------|------------------|
| **ERROR** | Blocks validation (`valid: false`) | Must be fixed |
| **WARNING** | Counts as issue but passes | Should be fixed |
| **INFO** | Informational only | Nice to have |

---

**Generated:** 2026-06-22  
**Framework Version:** Enterprise API Governance v1.0  
**Maintainer:** API Linting Team


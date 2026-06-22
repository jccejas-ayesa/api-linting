# Test Case: tc-010 - Payment Gateway
## Complexity: Medium

### Specification
- **Title:** Payment Gateway API
- **Version:** 3.0.0
- **Paths:** 4
- **Operations:** 5

### Linting Results
- **Valid:** false
- **Total Issues:** 50
- **Errors:** 1 | **Warnings:** 27 | **Info:** 22

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | asyncapi-channel-parameters | WARNING | Channel path parameter 'paymentId' is not defined in the path item parameters list (Path: /payments/{paymentId}) |
| 3 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 4 | insecure-oauth2-grants | ERROR | OAuth2 security scheme 'oauth2' uses the implicit grant flow (Path: /components/securitySchemes/oauth2/flows/implicit) |
| 5 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 6 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 7 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /payments/{paymentId} [GET]/responses/404) |
| 8 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /payments/{paymentId} [GET]/responses/404) |
| 9 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /payments/{paymentId} [GET]/responses/404) |
| 10 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /payments/{paymentId} [GET]/responses/404) |
| 11 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 12 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 13 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 14 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 15 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 16 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 17 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 18 | input-validation-required | INFO | String property 'currency' has no validation constraints (Path: /payments [POST]/requestBody/properties/currency) |
| 19 | input-validation-required | INFO | String property 'sourceToken' has no validation constraints (Path: /payments [POST]/requestBody/properties/sourceToken) |
| 20 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /payments [POST]/parameters/X-Correlation-Id) |
| 21 | input-validation-required | INFO | String property 'paymentId' has no validation constraints (Path: /refunds [POST]/requestBody/properties/paymentId) |
| 22 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /refunds [POST]/parameters/X-Correlation-Id) |
| 23 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /payments [GET]/responses/200/content/application/json) |
| 24 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 25 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 26 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'paymentId' is not modeled as a UUID/GUID (Path: /payments/{paymentId} [GET]/parameters/paymentId) |
| 27 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /payments [GET]/responses) |
| 28 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /payments [POST]/responses) |
| 29 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /payments/{paymentId} [GET]/responses) |
| 30 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /refunds [POST]/responses) |
| 31 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 32 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /payments [GET]/parameters/X-Correlation-Id) |
| 33 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /payments [POST]/parameters/X-Correlation-Id) |
| 34 | injection-pattern-on-string-parameters | WARNING | String parameter 'paymentId' does not define a restrictive pattern (Path: /payments/{paymentId} [GET]/parameters/paymentId) |
| 35 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /payments/{paymentId} [GET]/parameters/X-Correlation-Id) |
| 36 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /refunds [POST]/parameters/X-Correlation-Id) |
| 37 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 38 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /payments [GET]) |
| 39 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 40 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 41 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 42 | response-codes-validation | WARNING | No error responses defined (Path: /payments [GET]) |
| 43 | response-codes-validation | WARNING | No error responses defined (Path: /payments [POST]) |
| 44 | response-codes-validation | WARNING | No error responses defined (Path: /refunds [POST]) |
| 45 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 46 | standard-headers-required | INFO | No Client-Id header documented (Path: /payments [GET]) |
| 47 | standard-headers-required | INFO | No Client-Id header documented (Path: /payments [POST]) |
| 48 | standard-headers-required | INFO | No Client-Id header documented (Path: /payments/{paymentId} [GET]) |
| 49 | standard-headers-required | INFO | No Client-Id header documented (Path: /refunds [POST]) |
| 50 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

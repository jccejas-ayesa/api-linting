# Test Case: tc-014 - Audit API
## Complexity: Medium

### Specification
- **Title:** Audit API
- **Version:** 1.3.0
- **Paths:** 3
- **Operations:** 3

### Linting Results
- **Valid:** false
- **Total Issues:** 42
- **Errors:** 2 | **Warnings:** 25 | **Info:** 15

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | standard-post-status-codes | WARNING | POST operation uses non-standard success status codes (Path: /reports/compliance [POST]) |
| 3 | asyncapi-channel-parameters | WARNING | Channel path parameter 'eventId' is not defined in the path item parameters list (Path: /audit-events/{eventId}) |
| 4 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 5 | api-keys-in-query | ERROR | API key security scheme 'queryKey' uses query parameters (Path: /components/securitySchemes/queryKey) |
| 6 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 7 | correlation-id-required | WARNING | No correlation ID header defined (Path: /audit-events [GET]) |
| 8 | correlation-id-required | WARNING | No correlation ID header defined (Path: /audit-events/{eventId} [GET]) |
| 9 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 10 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 11 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 12 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 13 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 14 | health-check-complete | ERROR | Missing liveness health endpoint (/health or /live) (Path: /paths) |
| 15 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 16 | input-validation-required | INFO | String property 'scope' has no validation constraints (Path: /reports/compliance [POST]/requestBody/properties/scope) |
| 17 | input-validation-required | INFO | String property 'from' has no validation constraints (Path: /reports/compliance [POST]/requestBody/properties/from) |
| 18 | input-validation-required | INFO | String property 'to' has no validation constraints (Path: /reports/compliance [POST]/requestBody/properties/to) |
| 19 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /reports/compliance [POST]/parameters/X-Correlation-Id) |
| 20 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /audit-events [GET]/responses/200/content/application/json) |
| 21 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 22 | oauth2-required | WARNING | Security scheme 'queryKey' uses type 'apiKey' instead of OAuth2 or OpenID Connect (Path: /components/securitySchemes/queryKey) |
| 23 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'eventId' is not modeled as a UUID/GUID (Path: /audit-events/{eventId} [GET]/parameters/eventId) |
| 24 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /audit-events [GET]/responses) |
| 25 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /audit-events/{eventId} [GET]/responses) |
| 26 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /reports/compliance [POST]/responses) |
| 27 | injection-pattern-on-string-parameters | WARNING | String parameter 'from' does not define a restrictive pattern (Path: /audit-events [GET]/parameters/from) |
| 28 | injection-pattern-on-string-parameters | WARNING | String parameter 'to' does not define a restrictive pattern (Path: /audit-events [GET]/parameters/to) |
| 29 | injection-pattern-on-string-parameters | WARNING | String parameter 'api_key' does not define a restrictive pattern (Path: /audit-events [GET]/parameters/api_key) |
| 30 | injection-pattern-on-string-parameters | WARNING | String parameter 'eventId' does not define a restrictive pattern (Path: /audit-events/{eventId} [GET]/parameters/eventId) |
| 31 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /reports/compliance [POST]/parameters/X-Correlation-Id) |
| 32 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /audit-events [GET]) |
| 33 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 34 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 35 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 36 | response-codes-validation | WARNING | No error responses defined (Path: /audit-events [GET]) |
| 37 | response-codes-validation | WARNING | No error responses defined (Path: /audit-events/{eventId} [GET]) |
| 38 | response-codes-validation | WARNING | No error responses defined (Path: /reports/compliance [POST]) |
| 39 | standard-headers-required | INFO | No Client-Id header documented (Path: /audit-events [GET]) |
| 40 | standard-headers-required | INFO | No Client-Id header documented (Path: /audit-events/{eventId} [GET]) |
| 41 | standard-headers-required | INFO | No Client-Id header documented (Path: /reports/compliance [POST]) |
| 42 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

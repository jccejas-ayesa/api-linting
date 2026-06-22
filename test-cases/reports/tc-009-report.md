# Test Case: tc-009 - Event System
## Complexity: Medium

### Specification
- **Title:** Event System API
- **Version:** 1.4.0
- **Paths:** 4
- **Operations:** 5

### Linting Results
- **Valid:** true
- **Total Issues:** 55
- **Errors:** 0 | **Warnings:** 34 | **Info:** 21

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | standard-post-status-codes | WARNING | POST operation uses non-standard success status codes (Path: /events [POST]) |
| 3 | asyncapi-channel-parameters | WARNING | Channel path parameter 'eventId' is not defined in the path item parameters list (Path: /events/{eventId}/subscriptions) |
| 4 | asyncapi-channel-parameters | WARNING | Channel path parameter 'subscriptionId' is not defined in the path item parameters list (Path: /subscriptions/{subscriptionId}) |
| 5 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 6 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 7 | delete-response-standard | INFO | DELETE has no 404 response for non-existent resources (Path: /subscriptions/{subscriptionId} [DELETE]) |
| 8 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 9 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 10 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 11 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 12 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 13 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 14 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 15 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 16 | http-verbs-usage | WARNING | POST should not be used on existing resources (use PUT or PATCH) (Path: /events/{eventId}/subscriptions [POST]) |
| 17 | input-validation-required | INFO | String property 'topic' has no validation constraints (Path: /events [POST]/requestBody/properties/topic) |
| 18 | input-validation-required | INFO | String property 'callbackUrl' has no validation constraints (Path: /events [POST]/requestBody/properties/callbackUrl) |
| 19 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /events [POST]/parameters/X-Correlation-Id) |
| 20 | input-validation-required | INFO | String property 'callbackUrl' has no validation constraints (Path: /events/{eventId}/subscriptions [POST]/requestBody/properties/callbackUrl) |
| 21 | input-validation-required | INFO | String property 'secret' has no validation constraints (Path: /events/{eventId}/subscriptions [POST]/requestBody/properties/secret) |
| 22 | input-validation-required | INFO | Parameter 'eventId' has no validation constraints (Path: /events/{eventId}/subscriptions [POST]/parameters/eventId) |
| 23 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /events/{eventId}/subscriptions [POST]/parameters/X-Correlation-Id) |
| 24 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /events [GET]/responses/200/content/application/json) |
| 25 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 26 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 27 | oauth2-required | WARNING | Security scheme 'bearerAuth' uses type 'http' instead of OAuth2 or OpenID Connect (Path: /components/securitySchemes/bearerAuth) |
| 28 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'eventId' is not modeled as a UUID/GUID (Path: /events/{eventId}/subscriptions [POST]/parameters/eventId) |
| 29 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'subscriptionId' is not modeled as a UUID/GUID (Path: /subscriptions/{subscriptionId} [DELETE]/parameters/subscriptionId) |
| 30 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /events [GET]/responses) |
| 31 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /events [POST]/responses) |
| 32 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /events/{eventId}/subscriptions [POST]/responses) |
| 33 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /subscriptions/{subscriptionId} [DELETE]/responses) |
| 34 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 35 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /events [GET]/parameters/X-Correlation-Id) |
| 36 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /events [POST]/parameters/X-Correlation-Id) |
| 37 | injection-pattern-on-string-parameters | WARNING | String parameter 'eventId' does not define a restrictive pattern (Path: /events/{eventId}/subscriptions [POST]/parameters/eventId) |
| 38 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /events/{eventId}/subscriptions [POST]/parameters/X-Correlation-Id) |
| 39 | injection-pattern-on-string-parameters | WARNING | String parameter 'subscriptionId' does not define a restrictive pattern (Path: /subscriptions/{subscriptionId} [DELETE]/parameters/subscriptionId) |
| 40 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /subscriptions/{subscriptionId} [DELETE]/parameters/X-Correlation-Id) |
| 41 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 42 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /events [GET]) |
| 43 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 44 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 45 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 46 | response-codes-validation | WARNING | No error responses defined (Path: /events [GET]) |
| 47 | response-codes-validation | WARNING | No error responses defined (Path: /events [POST]) |
| 48 | response-codes-validation | WARNING | No error responses defined (Path: /events/{eventId}/subscriptions [POST]) |
| 49 | response-codes-validation | WARNING | No error responses defined (Path: /subscriptions/{subscriptionId} [DELETE]) |
| 50 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 51 | standard-headers-required | INFO | No Client-Id header documented (Path: /events [GET]) |
| 52 | standard-headers-required | INFO | No Client-Id header documented (Path: /events [POST]) |
| 53 | standard-headers-required | INFO | No Client-Id header documented (Path: /events/{eventId}/subscriptions [POST]) |
| 54 | standard-headers-required | INFO | No Client-Id header documented (Path: /subscriptions/{subscriptionId} [DELETE]) |
| 55 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; warnings were intentionally preserved to exercise lint coverage.

### Final Status
⚠️ PASS WITH WARNINGS

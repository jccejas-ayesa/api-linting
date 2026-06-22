# Test Case: tc-019 - Microservices Mesh
## Complexity: High

### Specification
- **Title:** Microservices Mesh API
- **Version:** 2.6.0
- **Paths:** 6
- **Operations:** 6

### Linting Results
- **Valid:** false
- **Total Issues:** 66
- **Errors:** 4 | **Warnings:** 40 | **Info:** 22

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | asyncapi-channel-parameters | WARNING | Channel path parameter 'serviceId' is not defined in the path item parameters list (Path: /services/{serviceId}/instances) |
| 3 | asyncapi-channel-parameters | WARNING | Channel path parameter 'subscriptionId' is not defined in the path item parameters list (Path: /events/subscriptions/{subscriptionId}/acknowledge) |
| 4 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 5 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/1) |
| 6 | access-tokens-oauth2-cleartext | ERROR | OAuth2 security scheme 'oauth2' requires HTTPS, but server URL is 'http://mesh-admin.example.com/api/v2' (Path: /servers/1) |
| 7 | api-keys-in-header-https | ERROR | Header API key security scheme 'headerKey' requires HTTPS, but server URL is 'http://mesh-admin.example.com/api/v2' (Path: /servers/1) |
| 8 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 9 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 10 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /policies/retries [PUT]/responses/200) |
| 11 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 12 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 13 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 14 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 15 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 16 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 17 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 18 | https-required | ERROR | Server URL does not use HTTPS: http://mesh-admin.example.com/api/v2 (Path: /servers/1) |
| 19 | use-https-for-scheme-protocol | ERROR | Insecure protocol scheme 'http' detected in server URL: http://mesh-admin.example.com/api/v2 (Path: /servers/1) |
| 20 | http-verbs-usage | WARNING | POST should not be used on existing resources (use PUT or PATCH) (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]) |
| 21 | input-validation-required | INFO | String property 'sourceService' has no validation constraints (Path: /routes [POST]/requestBody/properties/sourceService) |
| 22 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /routes [POST]/parameters/X-Correlation-Id) |
| 23 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /policies/retries [PUT]/parameters/X-Correlation-Id) |
| 24 | input-validation-required | INFO | String property 'callbackUrl' has no validation constraints (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]/requestBody/properties/callbackUrl) |
| 25 | input-validation-required | INFO | String property 'status' has no validation constraints (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]/requestBody/properties/status) |
| 26 | input-validation-required | INFO | Parameter 'subscriptionId' has no validation constraints (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]/parameters/subscriptionId) |
| 27 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]/parameters/X-Correlation-Id) |
| 28 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /services [GET]/responses/200/content/application/json) |
| 29 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /services/{serviceId}/instances [GET]/responses/200/content/application/json) |
| 30 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 31 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 32 | oauth2-required | WARNING | Security scheme 'headerKey' uses type 'apiKey' instead of OAuth2 or OpenID Connect (Path: /components/securitySchemes/headerKey) |
| 33 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'serviceId' is not modeled as a UUID/GUID (Path: /services/{serviceId}/instances [GET]/parameters/serviceId) |
| 34 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'subscriptionId' is not modeled as a UUID/GUID (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]/parameters/subscriptionId) |
| 35 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /services [GET]/responses) |
| 36 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /services/{serviceId}/instances [GET]/responses) |
| 37 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /routes [POST]/responses) |
| 38 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /policies/retries [PUT]/responses) |
| 39 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]/responses) |
| 40 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 41 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /services [GET]/parameters/X-Correlation-Id) |
| 42 | injection-pattern-on-string-parameters | WARNING | String parameter 'serviceId' does not define a restrictive pattern (Path: /services/{serviceId}/instances [GET]/parameters/serviceId) |
| 43 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /services/{serviceId}/instances [GET]/parameters/X-Correlation-Id) |
| 44 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /routes [POST]/parameters/X-Correlation-Id) |
| 45 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /policies/retries [PUT]/parameters/X-Correlation-Id) |
| 46 | injection-pattern-on-string-parameters | WARNING | String parameter 'subscriptionId' does not define a restrictive pattern (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]/parameters/subscriptionId) |
| 47 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]/parameters/X-Correlation-Id) |
| 48 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 49 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /services [GET]) |
| 50 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /services/{serviceId}/instances [GET]) |
| 51 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]) |
| 52 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 53 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 54 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 55 | response-codes-validation | WARNING | No error responses defined (Path: /services [GET]) |
| 56 | response-codes-validation | WARNING | No error responses defined (Path: /services/{serviceId}/instances [GET]) |
| 57 | response-codes-validation | WARNING | No error responses defined (Path: /routes [POST]) |
| 58 | response-codes-validation | WARNING | No error responses defined (Path: /policies/retries [PUT]) |
| 59 | response-codes-validation | WARNING | No error responses defined (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]) |
| 60 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 61 | standard-headers-required | INFO | No Client-Id header documented (Path: /services [GET]) |
| 62 | standard-headers-required | INFO | No Client-Id header documented (Path: /services/{serviceId}/instances [GET]) |
| 63 | standard-headers-required | INFO | No Client-Id header documented (Path: /routes [POST]) |
| 64 | standard-headers-required | INFO | No Client-Id header documented (Path: /policies/retries [PUT]) |
| 65 | standard-headers-required | INFO | No Client-Id header documented (Path: /events/subscriptions/{subscriptionId}/acknowledge [POST]) |
| 66 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

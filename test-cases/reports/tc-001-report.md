# Test Case: tc-001 - Simple User API
## Complexity: Low

### Specification
- **Title:** Simple User API
- **Version:** 1.0.0
- **Paths:** 3
- **Operations:** 6

### Linting Results
- **Valid:** true
- **Total Issues:** 70
- **Errors:** 0 | **Warnings:** 31 | **Info:** 39

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | asyncapi-channel-parameters | WARNING | Channel path parameter 'userId' is not defined in the path item parameters list (Path: /users/{userId}) |
| 3 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 4 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 5 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 6 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /users [GET]/responses/400) |
| 7 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /users [GET]/responses/400) |
| 8 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /users [GET]/responses/400) |
| 9 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /users [GET]/responses/400) |
| 10 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /users [POST]/responses/400) |
| 11 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /users [POST]/responses/400) |
| 12 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /users [POST]/responses/400) |
| 13 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /users [POST]/responses/400) |
| 14 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /users/{userId} [GET]/responses/404) |
| 15 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /users/{userId} [GET]/responses/404) |
| 16 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /users/{userId} [GET]/responses/404) |
| 17 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /users/{userId} [GET]/responses/404) |
| 18 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /users/{userId} [DELETE]/responses/404) |
| 19 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /users/{userId} [DELETE]/responses/404) |
| 20 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /users/{userId} [DELETE]/responses/404) |
| 21 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /users/{userId} [DELETE]/responses/404) |
| 22 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /users/{userId} [PATCH]/responses/400) |
| 23 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /users/{userId} [PATCH]/responses/400) |
| 24 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /users/{userId} [PATCH]/responses/400) |
| 25 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /users/{userId} [PATCH]/responses/400) |
| 26 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 27 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 28 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 29 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 30 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 31 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 32 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 33 | input-validation-required | INFO | String property 'displayName' has no validation constraints (Path: /users [POST]/requestBody/properties/displayName) |
| 34 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /users [POST]/parameters/X-Correlation-Id) |
| 35 | input-validation-required | INFO | String property 'displayName' has no validation constraints (Path: /users/{userId} [PATCH]/requestBody/properties/displayName) |
| 36 | input-validation-required | INFO | Parameter 'userId' has no validation constraints (Path: /users/{userId} [PATCH]/parameters/userId) |
| 37 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /users/{userId} [PATCH]/parameters/X-Correlation-Id) |
| 38 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /users [GET]/responses/200/content/application/json) |
| 39 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 40 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 41 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'userId' is not modeled as a UUID/GUID (Path: /users/{userId} [GET]/parameters/userId) |
| 42 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'userId' is not modeled as a UUID/GUID (Path: /users/{userId} [PATCH]/parameters/userId) |
| 43 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'userId' is not modeled as a UUID/GUID (Path: /users/{userId} [DELETE]/parameters/userId) |
| 44 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /users [GET]/responses) |
| 45 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /users [POST]/responses) |
| 46 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /users/{userId} [GET]/responses) |
| 47 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /users/{userId} [PATCH]/responses) |
| 48 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /users/{userId} [DELETE]/responses) |
| 49 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 50 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /users [GET]/parameters/X-Correlation-Id) |
| 51 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /users [POST]/parameters/X-Correlation-Id) |
| 52 | injection-pattern-on-string-parameters | WARNING | String parameter 'userId' does not define a restrictive pattern (Path: /users/{userId} [GET]/parameters/userId) |
| 53 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /users/{userId} [GET]/parameters/X-Correlation-Id) |
| 54 | injection-pattern-on-string-parameters | WARNING | String parameter 'userId' does not define a restrictive pattern (Path: /users/{userId} [PATCH]/parameters/userId) |
| 55 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /users/{userId} [PATCH]/parameters/X-Correlation-Id) |
| 56 | injection-pattern-on-string-parameters | WARNING | String parameter 'userId' does not define a restrictive pattern (Path: /users/{userId} [DELETE]/parameters/userId) |
| 57 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /users/{userId} [DELETE]/parameters/X-Correlation-Id) |
| 58 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 59 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /users [GET]) |
| 60 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 61 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 62 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 63 | request-response-antipattern | WARNING | Schema name 'UserCreateRequest' contains Request or Response (Path: /components/schemas/UserCreateRequest) |
| 64 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 65 | standard-headers-required | INFO | No Client-Id header documented (Path: /users [GET]) |
| 66 | standard-headers-required | INFO | No Client-Id header documented (Path: /users [POST]) |
| 67 | standard-headers-required | INFO | No Client-Id header documented (Path: /users/{userId} [GET]) |
| 68 | standard-headers-required | INFO | No Client-Id header documented (Path: /users/{userId} [DELETE]) |
| 69 | standard-headers-required | INFO | No Client-Id header documented (Path: /users/{userId} [PATCH]) |
| 70 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; warnings were intentionally preserved to exercise lint coverage.

### Final Status
⚠️ PASS WITH WARNINGS

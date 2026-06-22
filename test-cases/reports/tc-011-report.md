# Test Case: tc-011 - Notification Service
## Complexity: Medium

### Specification
- **Title:** Notification Service API
- **Version:** 1.7.0
- **Paths:** 4
- **Operations:** 6

### Linting Results
- **Valid:** false
- **Total Issues:** 67
- **Errors:** 3 | **Warnings:** 40 | **Info:** 24

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | no-2xx-response | ERROR | Operation is missing a 2xx response (Path: /templates/{templateId} [DELETE]) |
| 3 | preferred-media-type-representations | WARNING | Media type 'text/plain' is not a preferred representation (Path: /channels/email/test [POST]/responses/200/content/text/plain) |
| 4 | standard-delete-status-codes | WARNING | DELETE operation uses non-standard success status codes (Path: /templates/{templateId} [DELETE]) |
| 5 | asyncapi-channel-parameters | WARNING | Channel path parameter 'templateId' is not defined in the path item parameters list (Path: /templates/{templateId}) |
| 6 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 7 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 8 | delete-response-standard | WARNING | DELETE has no success response defined (Path: /templates/{templateId} [DELETE]) |
| 9 | delete-response-standard | INFO | DELETE has no 404 response for non-existent resources (Path: /templates/{templateId} [DELETE]) |
| 10 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 11 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /templates/{templateId} [DELETE]/responses/400) |
| 12 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /templates/{templateId} [DELETE]/responses/400) |
| 13 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /templates/{templateId} [DELETE]/responses/400) |
| 14 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /templates/{templateId} [DELETE]/responses/400) |
| 15 | provide-examples-on-payloads | INFO | No examples provided for content type 'text/plain' (Path: /channels/email/test [POST]/responses/200) |
| 16 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 17 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 18 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 19 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 20 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 21 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 22 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 23 | input-validation-required | INFO | String property 'recipient' has no validation constraints (Path: /notifications [POST]/requestBody/properties/recipient) |
| 24 | input-validation-required | INFO | String property 'templateId' has no validation constraints (Path: /notifications [POST]/requestBody/properties/templateId) |
| 25 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /notifications [POST]/parameters/X-Correlation-Id) |
| 26 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /channels/email/test [POST]/parameters/X-Correlation-Id) |
| 27 | not-json-or-yaml-response | WARNING | Response uses unsupported media type 'text/plain' (Path: /channels/email/test [POST]/responses/200/content/text/plain) |
| 28 | missing-2xx-response | WARNING | Operation is missing a 2xx success response (Path: /templates/{templateId} [DELETE]) |
| 29 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /notifications [GET]/responses/200/content/application/json) |
| 30 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 31 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 32 | oauth2-required | WARNING | Security scheme 'bearerAuth' uses type 'http' instead of OAuth2 or OpenID Connect (Path: /components/securitySchemes/bearerAuth) |
| 33 | operation-default-response | ERROR | Operation uses a default response (Path: /templates/{templateId} [DELETE]) |
| 34 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'templateId' is not modeled as a UUID/GUID (Path: /templates/{templateId} [GET]/parameters/templateId) |
| 35 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'templateId' is not modeled as a UUID/GUID (Path: /templates/{templateId} [DELETE]/parameters/templateId) |
| 36 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /notifications [GET]/responses) |
| 37 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /notifications [POST]/responses) |
| 38 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /channels/email/test [POST]/responses) |
| 39 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /templates/{templateId} [GET]/responses) |
| 40 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /templates/{templateId} [DELETE]/responses) |
| 41 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 42 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /notifications [GET]/parameters/X-Correlation-Id) |
| 43 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /notifications [POST]/parameters/X-Correlation-Id) |
| 44 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /channels/email/test [POST]/parameters/X-Correlation-Id) |
| 45 | injection-pattern-on-string-parameters | WARNING | String parameter 'templateId' does not define a restrictive pattern (Path: /templates/{templateId} [GET]/parameters/templateId) |
| 46 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /templates/{templateId} [GET]/parameters/X-Correlation-Id) |
| 47 | injection-pattern-on-string-parameters | WARNING | String parameter 'templateId' does not define a restrictive pattern (Path: /templates/{templateId} [DELETE]/parameters/templateId) |
| 48 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /templates/{templateId} [DELETE]/parameters/X-Correlation-Id) |
| 49 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 50 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /notifications [GET]) |
| 51 | plural-resource-names | WARNING | Path segment 'email' appears to be singular — use plural nouns for collections (Path: /channels/email/test) |
| 52 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /channels/email/test [POST]) |
| 53 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 54 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 55 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 56 | response-codes-validation | WARNING | No error responses defined (Path: /notifications [GET]) |
| 57 | response-codes-validation | WARNING | No error responses defined (Path: /notifications [POST]) |
| 58 | response-codes-validation | WARNING | No error responses defined (Path: /channels/email/test [POST]) |
| 59 | response-codes-validation | WARNING | No error responses defined (Path: /templates/{templateId} [GET]) |
| 60 | response-codes-validation | ERROR | No success response (2xx) defined (Path: /templates/{templateId} [DELETE]) |
| 61 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 62 | standard-headers-required | INFO | No Client-Id header documented (Path: /notifications [GET]) |
| 63 | standard-headers-required | INFO | No Client-Id header documented (Path: /notifications [POST]) |
| 64 | standard-headers-required | INFO | No Client-Id header documented (Path: /channels/email/test [POST]) |
| 65 | standard-headers-required | INFO | No Client-Id header documented (Path: /templates/{templateId} [GET]) |
| 66 | standard-headers-required | INFO | No Client-Id header documented (Path: /templates/{templateId} [DELETE]) |
| 67 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

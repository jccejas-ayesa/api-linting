# Test Case: tc-003 - Order API
## Complexity: Low

### Specification
- **Title:** Order API
- **Version:** 2.0.0
- **Paths:** 3
- **Operations:** 6

### Linting Results
- **Valid:** true
- **Total Issues:** 73
- **Errors:** 0 | **Warnings:** 33 | **Info:** 40

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | asyncapi-channel-parameters | WARNING | Channel path parameter 'orderId' is not defined in the path item parameters list (Path: /orders/{orderId}) |
| 3 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 4 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 5 | delete-response-standard | WARNING | DELETE returns 200 instead of 204 No Content (Path: /orders/{orderId} [DELETE]) |
| 6 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 7 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /orders [GET]/responses/400) |
| 8 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /orders [GET]/responses/400) |
| 9 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /orders [GET]/responses/400) |
| 10 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /orders [GET]/responses/400) |
| 11 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /orders [POST]/responses/400) |
| 12 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /orders [POST]/responses/400) |
| 13 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /orders [POST]/responses/400) |
| 14 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /orders [POST]/responses/400) |
| 15 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /orders/{orderId} [GET]/responses/404) |
| 16 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /orders/{orderId} [GET]/responses/404) |
| 17 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /orders/{orderId} [GET]/responses/404) |
| 18 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /orders/{orderId} [GET]/responses/404) |
| 19 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /orders/{orderId} [DELETE]/responses/404) |
| 20 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /orders/{orderId} [DELETE]/responses/404) |
| 21 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /orders/{orderId} [DELETE]/responses/404) |
| 22 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /orders/{orderId} [DELETE]/responses/404) |
| 23 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /orders/{orderId} [PATCH]/responses/400) |
| 24 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /orders/{orderId} [PATCH]/responses/400) |
| 25 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /orders/{orderId} [PATCH]/responses/400) |
| 26 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /orders/{orderId} [PATCH]/responses/400) |
| 27 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /orders/{orderId} [PATCH]/responses/200) |
| 28 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 29 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 30 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 31 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 32 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 33 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 34 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 35 | input-validation-required | INFO | String property 'customerId' has no validation constraints (Path: /orders [POST]/requestBody/properties/customerId) |
| 36 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /orders [POST]/parameters/X-Correlation-Id) |
| 37 | input-validation-required | INFO | String property 'customerId' has no validation constraints (Path: /orders/{orderId} [PATCH]/requestBody/properties/customerId) |
| 38 | input-validation-required | INFO | Parameter 'orderId' has no validation constraints (Path: /orders/{orderId} [PATCH]/parameters/orderId) |
| 39 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /orders/{orderId} [PATCH]/parameters/X-Correlation-Id) |
| 40 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /orders [GET]/responses/200/content/application/json) |
| 41 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 42 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 43 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'orderId' is not modeled as a UUID/GUID (Path: /orders/{orderId} [GET]/parameters/orderId) |
| 44 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'orderId' is not modeled as a UUID/GUID (Path: /orders/{orderId} [PATCH]/parameters/orderId) |
| 45 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'orderId' is not modeled as a UUID/GUID (Path: /orders/{orderId} [DELETE]/parameters/orderId) |
| 46 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /orders [GET]/responses) |
| 47 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /orders [POST]/responses) |
| 48 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /orders/{orderId} [GET]/responses) |
| 49 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /orders/{orderId} [PATCH]/responses) |
| 50 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /orders/{orderId} [DELETE]/responses) |
| 51 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 52 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /orders [GET]/parameters/X-Correlation-Id) |
| 53 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /orders [POST]/parameters/X-Correlation-Id) |
| 54 | injection-pattern-on-string-parameters | WARNING | String parameter 'orderId' does not define a restrictive pattern (Path: /orders/{orderId} [GET]/parameters/orderId) |
| 55 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /orders/{orderId} [GET]/parameters/X-Correlation-Id) |
| 56 | injection-pattern-on-string-parameters | WARNING | String parameter 'orderId' does not define a restrictive pattern (Path: /orders/{orderId} [PATCH]/parameters/orderId) |
| 57 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /orders/{orderId} [PATCH]/parameters/X-Correlation-Id) |
| 58 | injection-pattern-on-string-parameters | WARNING | String parameter 'orderId' does not define a restrictive pattern (Path: /orders/{orderId} [DELETE]/parameters/orderId) |
| 59 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /orders/{orderId} [DELETE]/parameters/X-Correlation-Id) |
| 60 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 61 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /orders [GET]) |
| 62 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /orders [POST]) |
| 63 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 64 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 65 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 66 | request-response-antipattern | WARNING | Schema name 'OrderCreateRequest' contains Request or Response (Path: /components/schemas/OrderCreateRequest) |
| 67 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 68 | standard-headers-required | INFO | No Client-Id header documented (Path: /orders [GET]) |
| 69 | standard-headers-required | INFO | No Client-Id header documented (Path: /orders [POST]) |
| 70 | standard-headers-required | INFO | No Client-Id header documented (Path: /orders/{orderId} [GET]) |
| 71 | standard-headers-required | INFO | No Client-Id header documented (Path: /orders/{orderId} [DELETE]) |
| 72 | standard-headers-required | INFO | No Client-Id header documented (Path: /orders/{orderId} [PATCH]) |
| 73 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; warnings were intentionally preserved to exercise lint coverage.

### Final Status
⚠️ PASS WITH WARNINGS

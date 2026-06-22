# Test Case: tc-020 - Compliance-Critical Finance API
## Complexity: High

### Specification
- **Title:** Compliance-Critical Finance API
- **Version:** 2.5.0
- **Paths:** 6
- **Operations:** 6

### Linting Results
- **Valid:** true
- **Total Issues:** 89
- **Errors:** 0 | **Warnings:** 30 | **Info:** 59

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | asyncapi-channel-parameters | WARNING | Channel path parameter 'accountId' is not defined in the path item parameters list (Path: /accounts/{accountId}) |
| 3 | asyncapi-channel-parameters | WARNING | Channel path parameter 'paymentId' is not defined in the path item parameters list (Path: /payments/{paymentId}) |
| 4 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 5 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 6 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 7 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /accounts [GET]/responses/429) |
| 8 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /accounts [GET]/responses/429) |
| 9 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /accounts [GET]/responses/429) |
| 10 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /accounts [GET]/responses/429) |
| 11 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /accounts/{accountId} [GET]/responses/404) |
| 12 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /accounts/{accountId} [GET]/responses/404) |
| 13 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /accounts/{accountId} [GET]/responses/404) |
| 14 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /accounts/{accountId} [GET]/responses/404) |
| 15 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /payments [POST]/responses/400) |
| 16 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /payments [POST]/responses/400) |
| 17 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /payments [POST]/responses/400) |
| 18 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /payments [POST]/responses/400) |
| 19 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /payments [POST]/responses/429) |
| 20 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /payments [POST]/responses/429) |
| 21 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /payments [POST]/responses/429) |
| 22 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /payments [POST]/responses/429) |
| 23 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /payments/{paymentId} [GET]/responses/404) |
| 24 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /payments/{paymentId} [GET]/responses/404) |
| 25 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /payments/{paymentId} [GET]/responses/404) |
| 26 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /payments/{paymentId} [GET]/responses/404) |
| 27 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Limit' does not include an example (Path: /accounts/get/responses/200/headers/X-Rate-Limit-Limit) |
| 28 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Remaining' does not include an example (Path: /accounts/get/responses/200/headers/X-Rate-Limit-Remaining) |
| 29 | provide-examples-on-parameters | INFO | Header 'Retry-After' does not include an example (Path: /accounts/get/responses/200/headers/Retry-After) |
| 30 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Limit' does not include an example (Path: /accounts/get/responses/429/headers/X-Rate-Limit-Limit) |
| 31 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Remaining' does not include an example (Path: /accounts/get/responses/429/headers/X-Rate-Limit-Remaining) |
| 32 | provide-examples-on-parameters | INFO | Header 'Retry-After' does not include an example (Path: /accounts/get/responses/429/headers/Retry-After) |
| 33 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Limit' does not include an example (Path: /accounts/{accountId}/get/responses/200/headers/X-Rate-Limit-Limit) |
| 34 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Remaining' does not include an example (Path: /accounts/{accountId}/get/responses/200/headers/X-Rate-Limit-Remaining) |
| 35 | provide-examples-on-parameters | INFO | Header 'Retry-After' does not include an example (Path: /accounts/{accountId}/get/responses/200/headers/Retry-After) |
| 36 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Limit' does not include an example (Path: /payments/post/responses/201/headers/X-Rate-Limit-Limit) |
| 37 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Remaining' does not include an example (Path: /payments/post/responses/201/headers/X-Rate-Limit-Remaining) |
| 38 | provide-examples-on-parameters | INFO | Header 'Retry-After' does not include an example (Path: /payments/post/responses/201/headers/Retry-After) |
| 39 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Limit' does not include an example (Path: /payments/post/responses/429/headers/X-Rate-Limit-Limit) |
| 40 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Remaining' does not include an example (Path: /payments/post/responses/429/headers/X-Rate-Limit-Remaining) |
| 41 | provide-examples-on-parameters | INFO | Header 'Retry-After' does not include an example (Path: /payments/post/responses/429/headers/Retry-After) |
| 42 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Limit' does not include an example (Path: /payments/{paymentId}/get/responses/200/headers/X-Rate-Limit-Limit) |
| 43 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Remaining' does not include an example (Path: /payments/{paymentId}/get/responses/200/headers/X-Rate-Limit-Remaining) |
| 44 | provide-examples-on-parameters | INFO | Header 'Retry-After' does not include an example (Path: /payments/{paymentId}/get/responses/200/headers/Retry-After) |
| 45 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Limit' does not include an example (Path: /transactions/get/responses/200/headers/X-Rate-Limit-Limit) |
| 46 | provide-examples-on-parameters | INFO | Header 'X-Rate-Limit-Remaining' does not include an example (Path: /transactions/get/responses/200/headers/X-Rate-Limit-Remaining) |
| 47 | provide-examples-on-parameters | INFO | Header 'Retry-After' does not include an example (Path: /transactions/get/responses/200/headers/Retry-After) |
| 48 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 49 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 50 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 51 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 52 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 53 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 54 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 55 | input-validation-required | INFO | String property 'debtorAccountId' has no validation constraints (Path: /payments [POST]/requestBody/properties/debtorAccountId) |
| 56 | input-validation-required | INFO | String property 'creditorAccountId' has no validation constraints (Path: /payments [POST]/requestBody/properties/creditorAccountId) |
| 57 | input-validation-required | INFO | String property 'currency' has no validation constraints (Path: /payments [POST]/requestBody/properties/currency) |
| 58 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /payments [POST]/parameters/X-Correlation-Id) |
| 59 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /accounts [GET]/responses/200/content/application/json) |
| 60 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /transactions [GET]/responses/200/content/application/json) |
| 61 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 62 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 63 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'accountId' is not modeled as a UUID/GUID (Path: /accounts/{accountId} [GET]/parameters/accountId) |
| 64 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'paymentId' is not modeled as a UUID/GUID (Path: /payments/{paymentId} [GET]/parameters/paymentId) |
| 65 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /accounts/{accountId} [GET]/responses) |
| 66 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /payments/{paymentId} [GET]/responses) |
| 67 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /transactions [GET]/responses) |
| 68 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 69 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /accounts [GET]/parameters/X-Correlation-Id) |
| 70 | injection-pattern-on-string-parameters | WARNING | String parameter 'accountId' does not define a restrictive pattern (Path: /accounts/{accountId} [GET]/parameters/accountId) |
| 71 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /accounts/{accountId} [GET]/parameters/X-Correlation-Id) |
| 72 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /payments [POST]/parameters/X-Correlation-Id) |
| 73 | injection-pattern-on-string-parameters | WARNING | String parameter 'paymentId' does not define a restrictive pattern (Path: /payments/{paymentId} [GET]/parameters/paymentId) |
| 74 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /payments/{paymentId} [GET]/parameters/X-Correlation-Id) |
| 75 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /transactions [GET]/parameters/X-Correlation-Id) |
| 76 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 77 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /accounts [GET]) |
| 78 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /transactions [GET]) |
| 79 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 80 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 81 | response-codes-validation | WARNING | No error responses defined (Path: /accounts [GET]) |
| 82 | response-codes-validation | WARNING | No error responses defined (Path: /transactions [GET]) |
| 83 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 84 | standard-headers-required | INFO | No Client-Id header documented (Path: /accounts [GET]) |
| 85 | standard-headers-required | INFO | No Client-Id header documented (Path: /accounts/{accountId} [GET]) |
| 86 | standard-headers-required | INFO | No Client-Id header documented (Path: /payments [POST]) |
| 87 | standard-headers-required | INFO | No Client-Id header documented (Path: /payments/{paymentId} [GET]) |
| 88 | standard-headers-required | INFO | No Client-Id header documented (Path: /transactions [GET]) |
| 89 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; warnings were intentionally preserved to exercise lint coverage.

### Final Status
⚠️ PASS WITH WARNINGS

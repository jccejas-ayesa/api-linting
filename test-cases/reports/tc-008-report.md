# Test Case: tc-008 - E-commerce Platform
## Complexity: Medium

### Specification
- **Title:** E-commerce Platform API
- **Version:** 2.3.1
- **Paths:** 5
- **Operations:** 8

### Linting Results
- **Valid:** true
- **Total Issues:** 71
- **Errors:** 0 | **Warnings:** 48 | **Info:** 23

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | standard-post-status-codes | WARNING | POST operation uses non-standard success status codes (Path: /orders [POST]) |
| 3 | asyncapi-channel-parameters | WARNING | Channel path parameter 'customerId' is not defined in the path item parameters list (Path: /customers/{customerId}/orders/{orderId}/items) |
| 4 | asyncapi-channel-parameters | WARNING | Channel path parameter 'orderId' is not defined in the path item parameters list (Path: /customers/{customerId}/orders/{orderId}/items) |
| 5 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 6 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 7 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 8 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 9 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 10 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 11 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 12 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 13 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 14 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 15 | input-validation-required | INFO | String property 'name' has no validation constraints (Path: /products [POST]/requestBody/properties/name) |
| 16 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /products [POST]/parameters/X-Correlation-Id) |
| 17 | input-validation-required | INFO | String property 'customerId' has no validation constraints (Path: /carts [POST]/requestBody/properties/customerId) |
| 18 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /carts [POST]/parameters/X-Correlation-Id) |
| 19 | input-validation-required | INFO | String property 'customerId' has no validation constraints (Path: /orders [POST]/requestBody/properties/customerId) |
| 20 | input-validation-required | INFO | String property 'cartId' has no validation constraints (Path: /orders [POST]/requestBody/properties/cartId) |
| 21 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /orders [POST]/parameters/X-Correlation-Id) |
| 22 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /products [GET]/responses/200/content/application/json) |
| 23 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /carts [GET]/responses/200/content/application/json) |
| 24 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /orders [GET]/responses/200/content/application/json) |
| 25 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /customers/{customerId}/orders/{orderId}/items [GET]/responses/200/content/application/json) |
| 26 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 27 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 28 | operation-singular-tag | WARNING | Operation defines more than one tag (Path: /customers/{customerId}/orders/{orderId}/items [GET]) |
| 29 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'customerId' is not modeled as a UUID/GUID (Path: /customers/{customerId}/orders/{orderId}/items [GET]/parameters/customerId) |
| 30 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'orderId' is not modeled as a UUID/GUID (Path: /customers/{customerId}/orders/{orderId}/items [GET]/parameters/orderId) |
| 31 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /products [GET]/responses) |
| 32 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /products [POST]/responses) |
| 33 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /carts [GET]/responses) |
| 34 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /carts [POST]/responses) |
| 35 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /orders [GET]/responses) |
| 36 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /orders [POST]/responses) |
| 37 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /customers/{customerId}/orders/{orderId}/items [GET]/responses) |
| 38 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 39 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /products [GET]/parameters/X-Correlation-Id) |
| 40 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /products [POST]/parameters/X-Correlation-Id) |
| 41 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /carts [GET]/parameters/X-Correlation-Id) |
| 42 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /carts [POST]/parameters/X-Correlation-Id) |
| 43 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /orders [GET]/parameters/X-Correlation-Id) |
| 44 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /orders [POST]/parameters/X-Correlation-Id) |
| 45 | injection-pattern-on-string-parameters | WARNING | String parameter 'customerId' does not define a restrictive pattern (Path: /customers/{customerId}/orders/{orderId}/items [GET]/parameters/customerId) |
| 46 | injection-pattern-on-string-parameters | WARNING | String parameter 'orderId' does not define a restrictive pattern (Path: /customers/{customerId}/orders/{orderId}/items [GET]/parameters/orderId) |
| 47 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /customers/{customerId}/orders/{orderId}/items [GET]/parameters/X-Correlation-Id) |
| 48 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 49 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /products [GET]) |
| 50 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /carts [GET]) |
| 51 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /orders [GET]) |
| 52 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /customers/{customerId}/orders/{orderId}/items [GET]) |
| 53 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 54 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 55 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 56 | response-codes-validation | WARNING | No error responses defined (Path: /products [GET]) |
| 57 | response-codes-validation | WARNING | No error responses defined (Path: /products [POST]) |
| 58 | response-codes-validation | WARNING | No error responses defined (Path: /carts [GET]) |
| 59 | response-codes-validation | WARNING | No error responses defined (Path: /carts [POST]) |
| 60 | response-codes-validation | WARNING | No error responses defined (Path: /orders [GET]) |
| 61 | response-codes-validation | WARNING | No error responses defined (Path: /orders [POST]) |
| 62 | response-codes-validation | WARNING | No error responses defined (Path: /customers/{customerId}/orders/{orderId}/items [GET]) |
| 63 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 64 | standard-headers-required | INFO | No Client-Id header documented (Path: /products [GET]) |
| 65 | standard-headers-required | INFO | No Client-Id header documented (Path: /products [POST]) |
| 66 | standard-headers-required | INFO | No Client-Id header documented (Path: /carts [GET]) |
| 67 | standard-headers-required | INFO | No Client-Id header documented (Path: /carts [POST]) |
| 68 | standard-headers-required | INFO | No Client-Id header documented (Path: /orders [GET]) |
| 69 | standard-headers-required | INFO | No Client-Id header documented (Path: /orders [POST]) |
| 70 | standard-headers-required | INFO | No Client-Id header documented (Path: /customers/{customerId}/orders/{orderId}/items [GET]) |
| 71 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; warnings were intentionally preserved to exercise lint coverage.

### Final Status
⚠️ PASS WITH WARNINGS

# Test Case: tc-004 - Inventory API
## Complexity: Low

### Specification
- **Title:** Inventory API
- **Version:** 1.0.2
- **Paths:** 3
- **Operations:** 6

### Linting Results
- **Valid:** false
- **Total Issues:** 79
- **Errors:** 4 | **Warnings:** 34 | **Info:** 41

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | asyncapi-channel-parameters | WARNING | Channel path parameter 'inventoryItemId' is not defined in the path item parameters list (Path: /inventory-items/{inventoryItemId}) |
| 3 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 4 | api-keys-in-cookie | ERROR | API key security scheme 'sessionCookie' uses cookies (Path: /components/securitySchemes/sessionCookie) |
| 5 | insecure-basic-auth | ERROR | Basic authentication scheme 'basicAuth' requires HTTPS, but server URL is 'http://inventory.example.com/api/v1' (Path: /servers/0) |
| 6 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 7 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 8 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /inventory-items [GET]/responses/400) |
| 9 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /inventory-items [GET]/responses/400) |
| 10 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /inventory-items [GET]/responses/400) |
| 11 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /inventory-items [GET]/responses/400) |
| 12 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /inventory-items [POST]/responses/400) |
| 13 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /inventory-items [POST]/responses/400) |
| 14 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /inventory-items [POST]/responses/400) |
| 15 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /inventory-items [POST]/responses/400) |
| 16 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /inventory-items/{inventoryItemId} [GET]/responses/404) |
| 17 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /inventory-items/{inventoryItemId} [GET]/responses/404) |
| 18 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /inventory-items/{inventoryItemId} [GET]/responses/404) |
| 19 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /inventory-items/{inventoryItemId} [GET]/responses/404) |
| 20 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /inventory-items/{inventoryItemId} [DELETE]/responses/404) |
| 21 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /inventory-items/{inventoryItemId} [DELETE]/responses/404) |
| 22 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /inventory-items/{inventoryItemId} [DELETE]/responses/404) |
| 23 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /inventory-items/{inventoryItemId} [DELETE]/responses/404) |
| 24 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /inventory-items/{inventoryItemId} [PATCH]/responses/400) |
| 25 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /inventory-items/{inventoryItemId} [PATCH]/responses/400) |
| 26 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /inventory-items/{inventoryItemId} [PATCH]/responses/400) |
| 27 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /inventory-items/{inventoryItemId} [PATCH]/responses/400) |
| 28 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 29 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 30 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 31 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 32 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 33 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 34 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 35 | https-required | ERROR | Server URL does not use HTTPS: http://inventory.example.com/api/v1 (Path: /servers/0) |
| 36 | use-https-for-scheme-protocol | ERROR | Insecure protocol scheme 'http' detected in server URL: http://inventory.example.com/api/v1 (Path: /servers/0) |
| 37 | input-validation-required | INFO | String property 'sku' has no validation constraints (Path: /inventory-items [POST]/requestBody/properties/sku) |
| 38 | input-validation-required | INFO | String property 'warehouseCode' has no validation constraints (Path: /inventory-items [POST]/requestBody/properties/warehouseCode) |
| 39 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /inventory-items [POST]/parameters/X-Correlation-Id) |
| 40 | input-validation-required | INFO | String property 'sku' has no validation constraints (Path: /inventory-items/{inventoryItemId} [PATCH]/requestBody/properties/sku) |
| 41 | input-validation-required | INFO | String property 'warehouseCode' has no validation constraints (Path: /inventory-items/{inventoryItemId} [PATCH]/requestBody/properties/warehouseCode) |
| 42 | input-validation-required | INFO | Parameter 'inventoryItemId' has no validation constraints (Path: /inventory-items/{inventoryItemId} [PATCH]/parameters/inventoryItemId) |
| 43 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /inventory-items/{inventoryItemId} [PATCH]/parameters/X-Correlation-Id) |
| 44 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /inventory-items [GET]/responses/200/content/application/json) |
| 45 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 46 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 47 | oauth2-required | WARNING | Security scheme 'sessionCookie' uses type 'apiKey' instead of OAuth2 or OpenID Connect (Path: /components/securitySchemes/sessionCookie) |
| 48 | oauth2-required | WARNING | Security scheme 'basicAuth' uses type 'http' instead of OAuth2 or OpenID Connect (Path: /components/securitySchemes/basicAuth) |
| 49 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'inventoryItemId' is not modeled as a UUID/GUID (Path: /inventory-items/{inventoryItemId} [GET]/parameters/inventoryItemId) |
| 50 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'inventoryItemId' is not modeled as a UUID/GUID (Path: /inventory-items/{inventoryItemId} [PATCH]/parameters/inventoryItemId) |
| 51 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'inventoryItemId' is not modeled as a UUID/GUID (Path: /inventory-items/{inventoryItemId} [DELETE]/parameters/inventoryItemId) |
| 52 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /inventory-items [GET]/responses) |
| 53 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /inventory-items [POST]/responses) |
| 54 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /inventory-items/{inventoryItemId} [GET]/responses) |
| 55 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /inventory-items/{inventoryItemId} [PATCH]/responses) |
| 56 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /inventory-items/{inventoryItemId} [DELETE]/responses) |
| 57 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 58 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /inventory-items [GET]/parameters/X-Correlation-Id) |
| 59 | injection-pattern-on-string-parameters | WARNING | String parameter 'api_key' does not define a restrictive pattern (Path: /inventory-items [GET]/parameters/api_key) |
| 60 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /inventory-items [POST]/parameters/X-Correlation-Id) |
| 61 | injection-pattern-on-string-parameters | WARNING | String parameter 'inventoryItemId' does not define a restrictive pattern (Path: /inventory-items/{inventoryItemId} [GET]/parameters/inventoryItemId) |
| 62 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /inventory-items/{inventoryItemId} [GET]/parameters/X-Correlation-Id) |
| 63 | injection-pattern-on-string-parameters | WARNING | String parameter 'inventoryItemId' does not define a restrictive pattern (Path: /inventory-items/{inventoryItemId} [PATCH]/parameters/inventoryItemId) |
| 64 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /inventory-items/{inventoryItemId} [PATCH]/parameters/X-Correlation-Id) |
| 65 | injection-pattern-on-string-parameters | WARNING | String parameter 'inventoryItemId' does not define a restrictive pattern (Path: /inventory-items/{inventoryItemId} [DELETE]/parameters/inventoryItemId) |
| 66 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /inventory-items/{inventoryItemId} [DELETE]/parameters/X-Correlation-Id) |
| 67 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 68 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /inventory-items [GET]) |
| 69 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 70 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 71 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 72 | request-response-antipattern | WARNING | Schema name 'InventoryItemCreateRequest' contains Request or Response (Path: /components/schemas/InventoryItemCreateRequest) |
| 73 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 74 | standard-headers-required | INFO | No Client-Id header documented (Path: /inventory-items [GET]) |
| 75 | standard-headers-required | INFO | No Client-Id header documented (Path: /inventory-items [POST]) |
| 76 | standard-headers-required | INFO | No Client-Id header documented (Path: /inventory-items/{inventoryItemId} [GET]) |
| 77 | standard-headers-required | INFO | No Client-Id header documented (Path: /inventory-items/{inventoryItemId} [DELETE]) |
| 78 | standard-headers-required | INFO | No Client-Id header documented (Path: /inventory-items/{inventoryItemId} [PATCH]) |
| 79 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

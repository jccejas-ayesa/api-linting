# Test Case: tc-018 - Legacy API Modernization
## Complexity: High

### Specification
- **Title:** Legacy API Modernization
- **Version:** v1
- **Paths:** 4
- **Operations:** 4

### Linting Results
- **Valid:** false
- **Total Issues:** 87
- **Errors:** 9 | **Warnings:** 58 | **Info:** 20

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | base-url-pattern-server | ERROR | Server URL does not follow the required /api/version pattern (Path: /servers/0/url) |
| 3 | camel-case-fields | WARNING | Property name 'user_id' is not camelCase (Path: /components/schemas/LegacyUser/properties/user_id) |
| 4 | camel-case-fields | WARNING | Property name 'full_name' is not camelCase (Path: /components/schemas/LegacyUser/properties/full_name) |
| 5 | camel-case-fields | WARNING | Property name 'order_id' is not camelCase (Path: /components/schemas/LegacyOrder/properties/order_id) |
| 6 | preferred-media-type-representations | WARNING | Media type 'text/plain' is not a preferred representation (Path: /createOrder [POST]/responses/200/content/text/plain) |
| 7 | preferred-media-type-representations | WARNING | Media type 'text/plain' is not a preferred representation (Path: /HealthCheck [GET]/responses/200/content/text/plain) |
| 8 | resource-use-lowercase | WARNING | Path segment 'getUser' is not lowercase (Path: /getUser) |
| 9 | resource-use-lowercase | WARNING | Path segment 'createOrder' is not lowercase (Path: /createOrder) |
| 10 | resource-use-lowercase | WARNING | Path segment 'deleteOrder' is not lowercase (Path: /deleteOrder) |
| 11 | resource-use-lowercase | WARNING | Path segment 'HealthCheck' is not lowercase (Path: /HealthCheck) |
| 12 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 13 | insecure-basic-auth | ERROR | Basic authentication scheme 'basicAuth' requires HTTPS, but server URL is 'http://legacy.example.com/v1' (Path: /servers/0) |
| 14 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 15 | correlation-id-required | WARNING | No correlation ID header defined (Path: /getUser [POST]) |
| 16 | correlation-id-required | WARNING | No correlation ID header defined (Path: /createOrder [POST]) |
| 17 | correlation-id-required | WARNING | No correlation ID header defined (Path: /deleteOrder [POST]) |
| 18 | correlation-id-required | WARNING | No correlation ID header defined (Path: /HealthCheck [GET]) |
| 19 | data-sensitivity-category-should-be-declared | WARNING | Data sensitivity category is not declared (Path: /) |
| 20 | description-required | INFO | Missing operation description (Path: /HealthCheck [GET]) |
| 21 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 22 | provide-examples-on-payloads | INFO | No examples provided for content type 'text/plain' (Path: /HealthCheck [GET]/responses/200) |
| 23 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 24 | x-extensions-governance | WARNING | Missing required governance extension: 'x-business-capability' (Path: /info) |
| 25 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 26 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 27 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 28 | health-check-complete | ERROR | Missing liveness health endpoint (/health or /live) (Path: /paths) |
| 29 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 30 | https-required | ERROR | Server URL does not use HTTPS: http://legacy.example.com/v1 (Path: /servers/0) |
| 31 | use-https-for-scheme-protocol | ERROR | Insecure protocol scheme 'http' detected in server URL: http://legacy.example.com/v1 (Path: /servers/0) |
| 32 | info-license | WARNING | Missing API license information (Path: /info/license) |
| 33 | info-completeness | WARNING | Missing contact information (Path: /info/contact) |
| 34 | input-validation-required | INFO | String property 'user_id' has no validation constraints (Path: /getUser [POST]/requestBody/properties/user_id) |
| 35 | input-validation-required | INFO | Parameter 'api_key' has no validation constraints (Path: /getUser [POST]/parameters/api_key) |
| 36 | input-validation-required | INFO | String property 'customer_id' has no validation constraints (Path: /createOrder [POST]/requestBody/properties/customer_id) |
| 37 | input-validation-required | INFO | Parameter 'api_key' has no validation constraints (Path: /createOrder [POST]/parameters/api_key) |
| 38 | input-validation-required | INFO | String property 'order_id' has no validation constraints (Path: /deleteOrder [POST]/requestBody/properties/order_id) |
| 39 | input-validation-required | INFO | Parameter 'api_key' has no validation constraints (Path: /deleteOrder [POST]/parameters/api_key) |
| 40 | not-json-or-yaml-response | WARNING | Response uses unsupported media type 'application/xml' (Path: /getUser [POST]/responses/200/content/application/xml) |
| 41 | not-json-or-yaml-response | WARNING | Response uses unsupported media type 'text/plain' (Path: /createOrder [POST]/responses/200/content/text/plain) |
| 42 | not-json-or-yaml-response | WARNING | Response uses unsupported media type 'text/plain' (Path: /HealthCheck [GET]/responses/200/content/text/plain) |
| 43 | naming-convention-schemas | WARNING | Property 'user_id' does not follow camelCase convention (Path: /components/schemas/LegacyUser/properties/user_id) |
| 44 | naming-convention-schemas | WARNING | Property 'full_name' does not follow camelCase convention (Path: /components/schemas/LegacyUser/properties/full_name) |
| 45 | naming-convention-schemas | WARNING | Property 'order_id' does not follow camelCase convention (Path: /components/schemas/LegacyOrder/properties/order_id) |
| 46 | oas-commons-usage | WARNING | No standard error schema found (expected one of: Error, Problem, ErrorResponse, ProblemDetail, ApiError) (Path: /components/schemas) |
| 47 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 48 | oauth2-required | WARNING | Security scheme 'basicAuth' uses type 'http' instead of OAuth2 or OpenID Connect (Path: /components/securitySchemes/basicAuth) |
| 49 | openapi-tags | WARNING | No global tags defined (Path: /tags) |
| 50 | operation-description | WARNING | Operation description is missing (Path: /HealthCheck [GET]) |
| 51 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: GetUser (Path: /getUser [POST]) |
| 52 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: CreateOrder (Path: /createOrder [POST]) |
| 53 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: DeleteOrder (Path: /deleteOrder [POST]) |
| 54 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: HealthCheck (Path: /HealthCheck [GET]) |
| 55 | operation-tag-defined | WARNING | Operation tag is not defined globally: Legacy (Path: /getUser [POST]) |
| 56 | operation-tag-defined | WARNING | Operation tag is not defined globally: Legacy (Path: /createOrder [POST]) |
| 57 | operation-tag-defined | WARNING | Operation tag is not defined globally: Legacy (Path: /deleteOrder [POST]) |
| 58 | operation-tag-defined | WARNING | Operation tag is not defined globally: Legacy (Path: /HealthCheck [GET]) |
| 59 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /getUser [POST]/responses) |
| 60 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /createOrder [POST]/responses) |
| 61 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /deleteOrder [POST]/responses) |
| 62 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /HealthCheck [GET]/responses) |
| 63 | injection-pattern-on-string-parameters | WARNING | String parameter 'api_key' does not define a restrictive pattern (Path: /getUser [POST]/parameters/api_key) |
| 64 | injection-pattern-on-string-parameters | WARNING | String parameter 'api_key' does not define a restrictive pattern (Path: /createOrder [POST]/parameters/api_key) |
| 65 | injection-pattern-on-string-parameters | WARNING | String parameter 'api_key' does not define a restrictive pattern (Path: /deleteOrder [POST]/parameters/api_key) |
| 66 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /HealthCheck [GET]) |
| 67 | path-naming-convention | WARNING | Path does not follow kebab-case convention (Path: /getUser) |
| 68 | path-naming-convention | WARNING | Path does not follow kebab-case convention (Path: /createOrder) |
| 69 | path-naming-convention | WARNING | Path does not follow kebab-case convention (Path: /deleteOrder) |
| 70 | path-naming-convention | WARNING | Path does not follow kebab-case convention (Path: /HealthCheck) |
| 71 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /getUser [POST]) |
| 72 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /createOrder [POST]) |
| 73 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /deleteOrder [POST]) |
| 74 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 75 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 76 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 77 | response-codes-validation | WARNING | No error responses defined (Path: /getUser [POST]) |
| 78 | response-codes-validation | WARNING | No error responses defined (Path: /createOrder [POST]) |
| 79 | response-codes-validation | WARNING | No error responses defined (Path: /deleteOrder [POST]) |
| 80 | response-codes-validation | WARNING | No error responses defined (Path: /HealthCheck [GET]) |
| 81 | standard-headers-required | INFO | No Client-Id header documented (Path: /getUser [POST]) |
| 82 | standard-headers-required | INFO | No Client-Id header documented (Path: /createOrder [POST]) |
| 83 | standard-headers-required | INFO | No Client-Id header documented (Path: /deleteOrder [POST]) |
| 84 | standard-headers-required | INFO | No Client-Id header documented (Path: /HealthCheck [GET]) |
| 85 | tags-should-be-defined | WARNING | No tags defined for this API (Path: /tags) |
| 86 | versioning-required | WARNING | Version 'v1' does not follow semantic versioning (Path: /info/version) |
| 87 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

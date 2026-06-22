# Test Case: tc-016 - GraphQL-REST Bridge
## Complexity: High

### Specification
- **Title:** GraphQL-REST Bridge API
- **Version:** 1.0.0
- **Paths:** 5
- **Operations:** 5

### Linting Results
- **Valid:** false
- **Total Issues:** 59
- **Errors:** 1 | **Warnings:** 38 | **Info:** 20

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | asyncapi-channel-parameters | WARNING | Channel path parameter 'queryName' is not defined in the path item parameters list (Path: /queries/{queryName}) |
| 3 | asyncapi-channel-parameters | WARNING | Channel path parameter 'mutationName' is not defined in the path item parameters list (Path: /mutations/{mutationName}) |
| 4 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 5 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 6 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 7 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /schema [GET]/responses/200) |
| 8 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 9 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 10 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 11 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 12 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 13 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 14 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 15 | http-verbs-usage | WARNING | POST should not be used on existing resources (use PUT or PATCH) (Path: /queries/{queryName} [POST]) |
| 16 | http-verbs-usage | WARNING | POST should not be used on existing resources (use PUT or PATCH) (Path: /mutations/{mutationName} [POST]) |
| 17 | input-validation-required | INFO | String property 'query' has no validation constraints (Path: /graphql [POST]/requestBody/properties/query) |
| 18 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /graphql [POST]/parameters/X-Correlation-Id) |
| 19 | input-validation-required | WARNING | Request body schema has no required properties defined (Path: /queries/{queryName} [POST]/requestBody) |
| 20 | input-validation-required | INFO | Parameter 'queryName' has no validation constraints (Path: /queries/{queryName} [POST]/parameters/queryName) |
| 21 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /queries/{queryName} [POST]/parameters/X-Correlation-Id) |
| 22 | input-validation-required | INFO | Parameter 'mutationName' has no validation constraints (Path: /mutations/{mutationName} [POST]/parameters/mutationName) |
| 23 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /mutations/{mutationName} [POST]/parameters/X-Correlation-Id) |
| 24 | missing-return-type | WARNING | Response media type 'application/json' is missing a schema (Path: /schema [GET]/responses/200/content/application/json) |
| 25 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 26 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 27 | oauth2-required | WARNING | Security scheme 'bearerAuth' uses type 'http' instead of OAuth2 or OpenID Connect (Path: /components/securitySchemes/bearerAuth) |
| 28 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: execute_named_mutation (Path: /mutations/{mutationName} [POST]) |
| 29 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /graphql [POST]/responses) |
| 30 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /queries/{queryName} [POST]/responses) |
| 31 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /mutations/{mutationName} [POST]/responses) |
| 32 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /schema [GET]/responses) |
| 33 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 34 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /graphql [POST]/parameters/X-Correlation-Id) |
| 35 | injection-pattern-on-string-parameters | WARNING | String parameter 'queryName' does not define a restrictive pattern (Path: /queries/{queryName} [POST]/parameters/queryName) |
| 36 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /queries/{queryName} [POST]/parameters/X-Correlation-Id) |
| 37 | injection-pattern-on-string-parameters | WARNING | String parameter 'mutationName' does not define a restrictive pattern (Path: /mutations/{mutationName} [POST]/parameters/mutationName) |
| 38 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /mutations/{mutationName} [POST]/parameters/X-Correlation-Id) |
| 39 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /schema [GET]/parameters/X-Correlation-Id) |
| 40 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 41 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /schema [GET]) |
| 42 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /graphql [POST]) |
| 43 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /queries/{queryName} [POST]) |
| 44 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /mutations/{mutationName} [POST]) |
| 45 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 46 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 47 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 48 | request-response-antipattern | WARNING | Schema name 'GraphQLRequest' contains Request or Response (Path: /components/schemas/GraphQLRequest) |
| 49 | request-response-antipattern | WARNING | Schema name 'GraphQLResponse' contains Request or Response (Path: /components/schemas/GraphQLResponse) |
| 50 | response-codes-validation | WARNING | No error responses defined (Path: /graphql [POST]) |
| 51 | response-codes-validation | WARNING | No error responses defined (Path: /queries/{queryName} [POST]) |
| 52 | response-codes-validation | WARNING | No error responses defined (Path: /mutations/{mutationName} [POST]) |
| 53 | response-codes-validation | WARNING | No error responses defined (Path: /schema [GET]) |
| 54 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 55 | standard-headers-required | INFO | No Client-Id header documented (Path: /graphql [POST]) |
| 56 | standard-headers-required | INFO | No Client-Id header documented (Path: /queries/{queryName} [POST]) |
| 57 | standard-headers-required | INFO | No Client-Id header documented (Path: /mutations/{mutationName} [POST]) |
| 58 | standard-headers-required | INFO | No Client-Id header documented (Path: /schema [GET]) |
| 59 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

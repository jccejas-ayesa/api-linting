# Test Case: tc-017 - Multi-tenant Platform
## Complexity: High

### Specification
- **Title:** Multi-tenant Platform API
- **Version:** 3.2.0
- **Paths:** 4
- **Operations:** 6

### Linting Results
- **Valid:** false
- **Total Issues:** 70
- **Errors:** 4 | **Warnings:** 45 | **Info:** 21

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | asyncapi-channel-parameters | WARNING | Channel path parameter 'tenantId' is not defined in the path item parameters list (Path: /tenants/{tenantId}/users) |
| 3 | asyncapi-channel-parameters | WARNING | Channel path parameter 'tenantId' is not defined in the path item parameters list (Path: /tenants/{tenantId}/billing/accounts) |
| 4 | asyncapi-channel-parameters | WARNING | Channel path parameter 'tenantId' is not defined in the path item parameters list (Path: /tenants/{tenantId}/settings) |
| 5 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 6 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/1) |
| 7 | access-tokens-oauth2-cleartext | ERROR | OAuth2 security scheme 'oauth2' requires HTTPS, but server URL is 'http://platform-legacy.example.com/api/v3' (Path: /servers/1) |
| 8 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 9 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 10 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 11 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 12 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 13 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 14 | health-check-complete | ERROR | Missing liveness health endpoint (/health or /live) (Path: /paths) |
| 15 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 16 | https-required | ERROR | Server URL does not use HTTPS: http://platform-legacy.example.com/api/v3 (Path: /servers/1) |
| 17 | use-https-for-scheme-protocol | ERROR | Insecure protocol scheme 'http' detected in server URL: http://platform-legacy.example.com/api/v3 (Path: /servers/1) |
| 18 | http-verbs-usage | WARNING | POST should not be used on existing resources (use PUT or PATCH) (Path: /tenants/{tenantId}/users [POST]) |
| 19 | input-validation-required | INFO | String property 'name' has no validation constraints (Path: /tenants [POST]/requestBody/properties/name) |
| 20 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /tenants [POST]/parameters/X-Correlation-Id) |
| 21 | input-validation-required | INFO | Parameter 'tenantId' has no validation constraints (Path: /tenants/{tenantId}/users [POST]/parameters/tenantId) |
| 22 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /tenants/{tenantId}/users [POST]/parameters/X-Correlation-Id) |
| 23 | input-validation-required | INFO | Parameter 'tenantId' has no validation constraints (Path: /tenants/{tenantId}/settings [PUT]/parameters/tenantId) |
| 24 | input-validation-required | INFO | Parameter 'X-Tenant-Id' has no validation constraints (Path: /tenants/{tenantId}/settings [PUT]/parameters/X-Tenant-Id) |
| 25 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /tenants/{tenantId}/settings [PUT]/parameters/X-Correlation-Id) |
| 26 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /tenants [GET]/responses/200/content/application/json) |
| 27 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /tenants/{tenantId}/users [GET]/responses/200/content/application/json) |
| 28 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /tenants/{tenantId}/billing/accounts [GET]/responses/200/content/application/json) |
| 29 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 30 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'tenantId' is not modeled as a UUID/GUID (Path: /tenants/{tenantId}/users [GET]/parameters/tenantId) |
| 31 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'tenantId' is not modeled as a UUID/GUID (Path: /tenants/{tenantId}/users [POST]/parameters/tenantId) |
| 32 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'tenantId' is not modeled as a UUID/GUID (Path: /tenants/{tenantId}/billing/accounts [GET]/parameters/tenantId) |
| 33 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'tenantId' is not modeled as a UUID/GUID (Path: /tenants/{tenantId}/settings [PUT]/parameters/tenantId) |
| 34 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tenants [GET]/responses) |
| 35 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tenants [POST]/responses) |
| 36 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tenants/{tenantId}/users [GET]/responses) |
| 37 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tenants/{tenantId}/users [POST]/responses) |
| 38 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tenants/{tenantId}/billing/accounts [GET]/responses) |
| 39 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tenants/{tenantId}/settings [PUT]/responses) |
| 40 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tenants [GET]/parameters/X-Correlation-Id) |
| 41 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tenants [POST]/parameters/X-Correlation-Id) |
| 42 | injection-pattern-on-string-parameters | WARNING | String parameter 'tenantId' does not define a restrictive pattern (Path: /tenants/{tenantId}/users [GET]/parameters/tenantId) |
| 43 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Tenant-Id' does not define a restrictive pattern (Path: /tenants/{tenantId}/users [GET]/parameters/X-Tenant-Id) |
| 44 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tenants/{tenantId}/users [GET]/parameters/X-Correlation-Id) |
| 45 | injection-pattern-on-string-parameters | WARNING | String parameter 'tenantId' does not define a restrictive pattern (Path: /tenants/{tenantId}/users [POST]/parameters/tenantId) |
| 46 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tenants/{tenantId}/users [POST]/parameters/X-Correlation-Id) |
| 47 | injection-pattern-on-string-parameters | WARNING | String parameter 'tenantId' does not define a restrictive pattern (Path: /tenants/{tenantId}/billing/accounts [GET]/parameters/tenantId) |
| 48 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tenants/{tenantId}/billing/accounts [GET]/parameters/X-Correlation-Id) |
| 49 | injection-pattern-on-string-parameters | WARNING | String parameter 'tenantId' does not define a restrictive pattern (Path: /tenants/{tenantId}/settings [PUT]/parameters/tenantId) |
| 50 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Tenant-Id' does not define a restrictive pattern (Path: /tenants/{tenantId}/settings [PUT]/parameters/X-Tenant-Id) |
| 51 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tenants/{tenantId}/settings [PUT]/parameters/X-Correlation-Id) |
| 52 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /tenants [GET]) |
| 53 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /tenants/{tenantId}/users [GET]) |
| 54 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /tenants/{tenantId}/billing/accounts [GET]) |
| 55 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 56 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 57 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 58 | response-codes-validation | WARNING | No error responses defined (Path: /tenants [GET]) |
| 59 | response-codes-validation | WARNING | No error responses defined (Path: /tenants [POST]) |
| 60 | response-codes-validation | WARNING | No error responses defined (Path: /tenants/{tenantId}/users [GET]) |
| 61 | response-codes-validation | WARNING | No error responses defined (Path: /tenants/{tenantId}/users [POST]) |
| 62 | response-codes-validation | WARNING | No error responses defined (Path: /tenants/{tenantId}/billing/accounts [GET]) |
| 63 | response-codes-validation | WARNING | No error responses defined (Path: /tenants/{tenantId}/settings [PUT]) |
| 64 | standard-headers-required | INFO | No Client-Id header documented (Path: /tenants [GET]) |
| 65 | standard-headers-required | INFO | No Client-Id header documented (Path: /tenants [POST]) |
| 66 | standard-headers-required | INFO | No Client-Id header documented (Path: /tenants/{tenantId}/users [GET]) |
| 67 | standard-headers-required | INFO | No Client-Id header documented (Path: /tenants/{tenantId}/users [POST]) |
| 68 | standard-headers-required | INFO | No Client-Id header documented (Path: /tenants/{tenantId}/billing/accounts [GET]) |
| 69 | standard-headers-required | INFO | No Client-Id header documented (Path: /tenants/{tenantId}/settings [PUT]) |
| 70 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

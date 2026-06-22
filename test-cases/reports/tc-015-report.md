# Test Case: tc-015 - Enterprise Data Hub
## Complexity: High

### Specification
- **Title:** Enterprise Data Hub API
- **Version:** 4.0.0
- **Paths:** 6
- **Operations:** 8

### Linting Results
- **Valid:** false
- **Total Issues:** 78
- **Errors:** 6 | **Warnings:** 47 | **Info:** 25

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | standard-post-status-codes | WARNING | POST operation uses non-standard success status codes (Path: /pipelines [POST]) |
| 3 | asyncapi-channel-parameters | WARNING | Channel path parameter 'datasetId' is not defined in the path item parameters list (Path: /datasets/{datasetId}/partitions) |
| 4 | asyncapi-channel-parameters | WARNING | Channel path parameter 'entityId' is not defined in the path item parameters list (Path: /lineage/{entityId}) |
| 5 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 6 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/1) |
| 7 | access-tokens-oauth2-cleartext | ERROR | OAuth2 security scheme 'oauth2' requires HTTPS, but server URL is 'http://datahub-dr.example.com/api/v4' (Path: /servers/1) |
| 8 | api-keys-in-query | ERROR | API key security scheme 'queryKey' uses query parameters (Path: /components/securitySchemes/queryKey) |
| 9 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 10 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 11 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 12 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 13 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 14 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 15 | repeated-field-names-pluralized | ERROR | Array property 'upstream' does not appear pluralized (Path: /components/schemas/LineageGraph/properties/upstream) |
| 16 | repeated-field-names-pluralized | ERROR | Array property 'downstream' does not appear pluralized (Path: /components/schemas/LineageGraph/properties/downstream) |
| 17 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 18 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 19 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 20 | https-required | ERROR | Server URL does not use HTTPS: http://datahub-dr.example.com/api/v4 (Path: /servers/1) |
| 21 | use-https-for-scheme-protocol | ERROR | Insecure protocol scheme 'http' detected in server URL: http://datahub-dr.example.com/api/v4 (Path: /servers/1) |
| 22 | input-validation-required | INFO | String property 'name' has no validation constraints (Path: /datasets [POST]/requestBody/properties/name) |
| 23 | input-validation-required | INFO | String property 'ownerTeam' has no validation constraints (Path: /datasets [POST]/requestBody/properties/ownerTeam) |
| 24 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /datasets [POST]/parameters/X-Correlation-Id) |
| 25 | input-validation-required | INFO | String property 'name' has no validation constraints (Path: /pipelines [POST]/requestBody/properties/name) |
| 26 | input-validation-required | INFO | String property 'schedule' has no validation constraints (Path: /pipelines [POST]/requestBody/properties/schedule) |
| 27 | input-validation-required | INFO | String property 'sourceDatasetId' has no validation constraints (Path: /pipelines [POST]/requestBody/properties/sourceDatasetId) |
| 28 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /pipelines [POST]/parameters/X-Correlation-Id) |
| 29 | input-validation-required | INFO | String property 'datasetId' has no validation constraints (Path: /exports [POST]/requestBody/properties/datasetId) |
| 30 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /exports [POST]/parameters/X-Correlation-Id) |
| 31 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /datasets [GET]/responses/200/content/application/json) |
| 32 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /datasets/{datasetId}/partitions [GET]/responses/200/content/application/json) |
| 33 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /pipelines [GET]/responses/200/content/application/json) |
| 34 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 35 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 36 | oauth2-required | WARNING | Security scheme 'queryKey' uses type 'apiKey' instead of OAuth2 or OpenID Connect (Path: /components/securitySchemes/queryKey) |
| 37 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'datasetId' is not modeled as a UUID/GUID (Path: /datasets/{datasetId}/partitions [GET]/parameters/datasetId) |
| 38 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'entityId' is not modeled as a UUID/GUID (Path: /lineage/{entityId} [GET]/parameters/entityId) |
| 39 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /datasets [GET]/responses) |
| 40 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /datasets [POST]/responses) |
| 41 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /datasets/{datasetId}/partitions [GET]/responses) |
| 42 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /pipelines [GET]/responses) |
| 43 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /pipelines [POST]/responses) |
| 44 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /exports [POST]/responses) |
| 45 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /lineage/{entityId} [GET]/responses) |
| 46 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 47 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /datasets [GET]/parameters/X-Correlation-Id) |
| 48 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /datasets [POST]/parameters/X-Correlation-Id) |
| 49 | injection-pattern-on-string-parameters | WARNING | String parameter 'datasetId' does not define a restrictive pattern (Path: /datasets/{datasetId}/partitions [GET]/parameters/datasetId) |
| 50 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /datasets/{datasetId}/partitions [GET]/parameters/X-Correlation-Id) |
| 51 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /pipelines [GET]/parameters/X-Correlation-Id) |
| 52 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /pipelines [POST]/parameters/X-Correlation-Id) |
| 53 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /exports [POST]/parameters/X-Correlation-Id) |
| 54 | injection-pattern-on-string-parameters | WARNING | String parameter 'entityId' does not define a restrictive pattern (Path: /lineage/{entityId} [GET]/parameters/entityId) |
| 55 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /lineage/{entityId} [GET]/parameters/X-Correlation-Id) |
| 56 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 57 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /datasets [GET]) |
| 58 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /datasets/{datasetId}/partitions [GET]) |
| 59 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /pipelines [GET]) |
| 60 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 61 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 62 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 63 | response-codes-validation | WARNING | No error responses defined (Path: /datasets [GET]) |
| 64 | response-codes-validation | WARNING | No error responses defined (Path: /datasets [POST]) |
| 65 | response-codes-validation | WARNING | No error responses defined (Path: /datasets/{datasetId}/partitions [GET]) |
| 66 | response-codes-validation | WARNING | No error responses defined (Path: /pipelines [GET]) |
| 67 | response-codes-validation | WARNING | No error responses defined (Path: /pipelines [POST]) |
| 68 | response-codes-validation | WARNING | No error responses defined (Path: /exports [POST]) |
| 69 | response-codes-validation | WARNING | No error responses defined (Path: /lineage/{entityId} [GET]) |
| 70 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 71 | standard-headers-required | INFO | No Client-Id header documented (Path: /datasets [GET]) |
| 72 | standard-headers-required | INFO | No Client-Id header documented (Path: /datasets [POST]) |
| 73 | standard-headers-required | INFO | No Client-Id header documented (Path: /datasets/{datasetId}/partitions [GET]) |
| 74 | standard-headers-required | INFO | No Client-Id header documented (Path: /pipelines [GET]) |
| 75 | standard-headers-required | INFO | No Client-Id header documented (Path: /pipelines [POST]) |
| 76 | standard-headers-required | INFO | No Client-Id header documented (Path: /exports [POST]) |
| 77 | standard-headers-required | INFO | No Client-Id header documented (Path: /lineage/{entityId} [GET]) |
| 78 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

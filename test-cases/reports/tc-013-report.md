# Test Case: tc-013 - Configuration Service
## Complexity: Medium

### Specification
- **Title:** Configuration Service API
- **Version:** 2.1.0
- **Paths:** 4
- **Operations:** 6

### Linting Results
- **Valid:** true
- **Total Issues:** 66
- **Errors:** 0 | **Warnings:** 47 | **Info:** 19

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | nil-union-antipattern | WARNING | Property 'description' is nullable (Path: /components/schemas/ConfigBase/properties/description) |
| 3 | node-shapes-must-have-descriptions | WARNING | Schema 'ConfigInput' is missing a description (Path: /components/schemas/ConfigInput) |
| 4 | node-shapes-must-have-descriptions | WARNING | Schema 'Config' is missing a description (Path: /components/schemas/Config) |
| 5 | asyncapi-channel-parameters | WARNING | Channel path parameter 'configId' is not defined in the path item parameters list (Path: /configs/{configId}) |
| 6 | asyncapi-channel-parameters | WARNING | Channel path parameter 'configId' is not defined in the path item parameters list (Path: /configs/{configId}/versions/{versionId}) |
| 7 | asyncapi-channel-parameters | WARNING | Channel path parameter 'versionId' is not defined in the path item parameters list (Path: /configs/{configId}/versions/{versionId}) |
| 8 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 9 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 10 | description-required | INFO | Schema 'ConfigInput' has no description (Path: /components/schemas/ConfigInput) |
| 11 | description-required | INFO | Schema 'Config' has no description (Path: /components/schemas/Config) |
| 12 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 13 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 14 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 15 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 16 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 17 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 18 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 19 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 20 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /configs [POST]/parameters/X-Correlation-Id) |
| 21 | input-validation-required | INFO | Parameter 'configId' has no validation constraints (Path: /configs/{configId} [PUT]/parameters/configId) |
| 22 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /configs/{configId} [PUT]/parameters/X-Correlation-Id) |
| 23 | missing-type-description | WARNING | Schema 'ConfigInput' is missing a description (Path: /components/schemas/ConfigInput) |
| 24 | missing-type-description | WARNING | Schema 'Config' is missing a description (Path: /components/schemas/Config) |
| 25 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /configs [GET]/responses/200/content/application/json) |
| 26 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 27 | non-union-antipattern | WARNING | Property 'description' uses nullable=true (Path: /components/schemas/ConfigBase/properties/description) |
| 28 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 29 | open-schemas-ignored | WARNING | Schema 'ConfigInput' does not explicitly set additionalProperties to false (Path: /components/schemas/ConfigInput) |
| 30 | open-schemas-ignored | WARNING | Schema 'Config' does not explicitly set additionalProperties to false (Path: /components/schemas/Config) |
| 31 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'configId' is not modeled as a UUID/GUID (Path: /configs/{configId} [GET]/parameters/configId) |
| 32 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'configId' is not modeled as a UUID/GUID (Path: /configs/{configId} [PUT]/parameters/configId) |
| 33 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'configId' is not modeled as a UUID/GUID (Path: /configs/{configId}/versions/{versionId} [GET]/parameters/configId) |
| 34 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'versionId' is not modeled as a UUID/GUID (Path: /configs/{configId}/versions/{versionId} [GET]/parameters/versionId) |
| 35 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /configs [GET]/responses) |
| 36 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /configs [POST]/responses) |
| 37 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /configs/{configId} [GET]/responses) |
| 38 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /configs/{configId} [PUT]/responses) |
| 39 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /configs/{configId}/versions/{versionId} [GET]/responses) |
| 40 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 41 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /configs [GET]/parameters/X-Correlation-Id) |
| 42 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /configs [POST]/parameters/X-Correlation-Id) |
| 43 | injection-pattern-on-string-parameters | WARNING | String parameter 'configId' does not define a restrictive pattern (Path: /configs/{configId} [GET]/parameters/configId) |
| 44 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /configs/{configId} [GET]/parameters/X-Correlation-Id) |
| 45 | injection-pattern-on-string-parameters | WARNING | String parameter 'configId' does not define a restrictive pattern (Path: /configs/{configId} [PUT]/parameters/configId) |
| 46 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /configs/{configId} [PUT]/parameters/X-Correlation-Id) |
| 47 | injection-pattern-on-string-parameters | WARNING | String parameter 'configId' does not define a restrictive pattern (Path: /configs/{configId}/versions/{versionId} [GET]/parameters/configId) |
| 48 | injection-pattern-on-string-parameters | WARNING | String parameter 'versionId' does not define a restrictive pattern (Path: /configs/{configId}/versions/{versionId} [GET]/parameters/versionId) |
| 49 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /configs/{configId}/versions/{versionId} [GET]/parameters/X-Correlation-Id) |
| 50 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 51 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /configs [GET]) |
| 52 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 53 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 54 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 55 | response-codes-validation | WARNING | No error responses defined (Path: /configs [GET]) |
| 56 | response-codes-validation | WARNING | No error responses defined (Path: /configs [POST]) |
| 57 | response-codes-validation | WARNING | No error responses defined (Path: /configs/{configId} [GET]) |
| 58 | response-codes-validation | WARNING | No error responses defined (Path: /configs/{configId} [PUT]) |
| 59 | response-codes-validation | WARNING | No error responses defined (Path: /configs/{configId}/versions/{versionId} [GET]) |
| 60 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 61 | standard-headers-required | INFO | No Client-Id header documented (Path: /configs [GET]) |
| 62 | standard-headers-required | INFO | No Client-Id header documented (Path: /configs [POST]) |
| 63 | standard-headers-required | INFO | No Client-Id header documented (Path: /configs/{configId} [GET]) |
| 64 | standard-headers-required | INFO | No Client-Id header documented (Path: /configs/{configId} [PUT]) |
| 65 | standard-headers-required | INFO | No Client-Id header documented (Path: /configs/{configId}/versions/{versionId} [GET]) |
| 66 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; warnings were intentionally preserved to exercise lint coverage.

### Final Status
⚠️ PASS WITH WARNINGS

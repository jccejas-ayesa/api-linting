# Test Case: tc-006 - Tag API
## Complexity: Low

### Specification
- **Title:** Tag API
- **Version:** 1.2.0
- **Paths:** 3
- **Operations:** 6

### Linting Results
- **Valid:** false
- **Total Issues:** 80
- **Errors:** 2 | **Warnings:** 37 | **Info:** 41

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | duplicated-entry-in-enum | ERROR | Enum contains duplicated entries (Path: /components/schemas/Tag/properties/tagType) |
| 3 | duplicated-entry-in-enum | ERROR | Enum contains duplicated entries (Path: /components/schemas/TagCreateRequest/properties/tagType) |
| 4 | property-shape-ranges-must-have-descriptions | WARNING | Property 'metadata' is missing a description (Path: /components/schemas/Tag/properties/metadata) |
| 5 | property-shape-ranges-must-have-descriptions | WARNING | Property 'metadata' is missing a description (Path: /components/schemas/TagCreateRequest/properties/metadata) |
| 6 | asyncapi-channel-parameters | WARNING | Channel path parameter 'tagId' is not defined in the path item parameters list (Path: /tags/{tagId}) |
| 7 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 8 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 9 | description-required | INFO | Property 'metadata' has no description (Path: /components/schemas/Tag/properties/metadata) |
| 10 | description-required | INFO | Property 'metadata' has no description (Path: /components/schemas/TagCreateRequest/properties/metadata) |
| 11 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 12 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /tags [GET]/responses/400) |
| 13 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /tags [GET]/responses/400) |
| 14 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /tags [GET]/responses/400) |
| 15 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /tags [GET]/responses/400) |
| 16 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /tags [POST]/responses/400) |
| 17 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /tags [POST]/responses/400) |
| 18 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /tags [POST]/responses/400) |
| 19 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /tags [POST]/responses/400) |
| 20 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /tags/{tagId} [GET]/responses/404) |
| 21 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /tags/{tagId} [GET]/responses/404) |
| 22 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /tags/{tagId} [GET]/responses/404) |
| 23 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /tags/{tagId} [GET]/responses/404) |
| 24 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /tags/{tagId} [DELETE]/responses/404) |
| 25 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /tags/{tagId} [DELETE]/responses/404) |
| 26 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /tags/{tagId} [DELETE]/responses/404) |
| 27 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /tags/{tagId} [DELETE]/responses/404) |
| 28 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /tags/{tagId} [PATCH]/responses/400) |
| 29 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /tags/{tagId} [PATCH]/responses/400) |
| 30 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /tags/{tagId} [PATCH]/responses/400) |
| 31 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /tags/{tagId} [PATCH]/responses/400) |
| 32 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 33 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 34 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 35 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 36 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 37 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 38 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 39 | input-validation-required | INFO | String property 'name' has no validation constraints (Path: /tags [POST]/requestBody/properties/name) |
| 40 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /tags [POST]/parameters/X-Correlation-Id) |
| 41 | input-validation-required | INFO | String property 'name' has no validation constraints (Path: /tags/{tagId} [PATCH]/requestBody/properties/name) |
| 42 | input-validation-required | INFO | Parameter 'tagId' has no validation constraints (Path: /tags/{tagId} [PATCH]/parameters/tagId) |
| 43 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /tags/{tagId} [PATCH]/parameters/X-Correlation-Id) |
| 44 | missing-property-description | WARNING | Property 'metadata' is missing a description (Path: /components/schemas/Tag/properties/metadata) |
| 45 | missing-property-description | WARNING | Property 'metadata' is missing a description (Path: /components/schemas/TagCreateRequest/properties/metadata) |
| 46 | missing-return-type | WARNING | Response media type 'application/json' is missing a schema (Path: /tags [POST]/responses/201/content/application/json) |
| 47 | missing-return-type | WARNING | Response media type 'application/json' is missing a schema (Path: /tags/{tagId} [GET]/responses/200/content/application/json) |
| 48 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /tags [GET]/responses/200/content/application/json) |
| 49 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 50 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 51 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'tagId' is not modeled as a UUID/GUID (Path: /tags/{tagId} [GET]/parameters/tagId) |
| 52 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'tagId' is not modeled as a UUID/GUID (Path: /tags/{tagId} [PATCH]/parameters/tagId) |
| 53 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'tagId' is not modeled as a UUID/GUID (Path: /tags/{tagId} [DELETE]/parameters/tagId) |
| 54 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tags [GET]/responses) |
| 55 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tags [POST]/responses) |
| 56 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tags/{tagId} [GET]/responses) |
| 57 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tags/{tagId} [PATCH]/responses) |
| 58 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /tags/{tagId} [DELETE]/responses) |
| 59 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 60 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tags [GET]/parameters/X-Correlation-Id) |
| 61 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tags [POST]/parameters/X-Correlation-Id) |
| 62 | injection-pattern-on-string-parameters | WARNING | String parameter 'tagId' does not define a restrictive pattern (Path: /tags/{tagId} [GET]/parameters/tagId) |
| 63 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tags/{tagId} [GET]/parameters/X-Correlation-Id) |
| 64 | injection-pattern-on-string-parameters | WARNING | String parameter 'tagId' does not define a restrictive pattern (Path: /tags/{tagId} [PATCH]/parameters/tagId) |
| 65 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tags/{tagId} [PATCH]/parameters/X-Correlation-Id) |
| 66 | injection-pattern-on-string-parameters | WARNING | String parameter 'tagId' does not define a restrictive pattern (Path: /tags/{tagId} [DELETE]/parameters/tagId) |
| 67 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /tags/{tagId} [DELETE]/parameters/X-Correlation-Id) |
| 68 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 69 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /tags [GET]) |
| 70 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 71 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 72 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 73 | request-response-antipattern | WARNING | Schema name 'TagCreateRequest' contains Request or Response (Path: /components/schemas/TagCreateRequest) |
| 74 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 75 | standard-headers-required | INFO | No Client-Id header documented (Path: /tags [GET]) |
| 76 | standard-headers-required | INFO | No Client-Id header documented (Path: /tags [POST]) |
| 77 | standard-headers-required | INFO | No Client-Id header documented (Path: /tags/{tagId} [GET]) |
| 78 | standard-headers-required | INFO | No Client-Id header documented (Path: /tags/{tagId} [DELETE]) |
| 79 | standard-headers-required | INFO | No Client-Id header documented (Path: /tags/{tagId} [PATCH]) |
| 80 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

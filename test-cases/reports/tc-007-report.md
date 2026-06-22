# Test Case: tc-007 - Status API
## Complexity: Low

### Specification
- **Title:** Status API
- **Version:** 0.9.0
- **Paths:** 2
- **Operations:** 5

### Linting Results
- **Valid:** false
- **Total Issues:** 97
- **Errors:** 1 | **Warnings:** 40 | **Info:** 56

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | asyncapi-channel-parameters | WARNING | Channel path parameter 'statusRecordId' is not defined in the path item parameters list (Path: /statuses/{statusRecordId}) |
| 3 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 4 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 5 | description-required | INFO | Missing operation description (Path: /statuses [GET]) |
| 6 | description-required | INFO | Missing operation description (Path: /statuses [POST]) |
| 7 | description-required | INFO | Missing operation description (Path: /statuses/{statusRecordId} [GET]) |
| 8 | description-required | INFO | Missing operation description (Path: /statuses/{statusRecordId} [DELETE]) |
| 9 | description-required | INFO | Missing operation description (Path: /statuses/{statusRecordId} [PATCH]) |
| 10 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 11 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /statuses [GET]/responses/400) |
| 12 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /statuses [GET]/responses/400) |
| 13 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /statuses [GET]/responses/400) |
| 14 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /statuses [GET]/responses/400) |
| 15 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /statuses [POST]/responses/400) |
| 16 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /statuses [POST]/responses/400) |
| 17 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /statuses [POST]/responses/400) |
| 18 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /statuses [POST]/responses/400) |
| 19 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /statuses/{statusRecordId} [GET]/responses/404) |
| 20 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /statuses/{statusRecordId} [GET]/responses/404) |
| 21 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /statuses/{statusRecordId} [GET]/responses/404) |
| 22 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /statuses/{statusRecordId} [GET]/responses/404) |
| 23 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /statuses/{statusRecordId} [DELETE]/responses/404) |
| 24 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /statuses/{statusRecordId} [DELETE]/responses/404) |
| 25 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /statuses/{statusRecordId} [DELETE]/responses/404) |
| 26 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /statuses/{statusRecordId} [DELETE]/responses/404) |
| 27 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /statuses/{statusRecordId} [PATCH]/responses/400) |
| 28 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /statuses/{statusRecordId} [PATCH]/responses/400) |
| 29 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /statuses/{statusRecordId} [PATCH]/responses/400) |
| 30 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /statuses/{statusRecordId} [PATCH]/responses/400) |
| 31 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses [GET]/responses/200) |
| 32 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses [GET]/responses/400) |
| 33 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses [POST]/requestBody) |
| 34 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses [POST]/responses/201) |
| 35 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses [POST]/responses/400) |
| 36 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses/{statusRecordId} [GET]/responses/200) |
| 37 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses/{statusRecordId} [GET]/responses/404) |
| 38 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses/{statusRecordId} [DELETE]/responses/404) |
| 39 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses/{statusRecordId} [PATCH]/requestBody) |
| 40 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses/{statusRecordId} [PATCH]/responses/200) |
| 41 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /statuses/{statusRecordId} [PATCH]/responses/400) |
| 42 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 43 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 44 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 45 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 46 | health-check-complete | ERROR | Missing liveness health endpoint (/health or /live) (Path: /paths) |
| 47 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 48 | info-license | WARNING | Missing API license information (Path: /info/license) |
| 49 | info-completeness | WARNING | Missing contact information (Path: /info/contact) |
| 50 | input-validation-required | INFO | String property 'code' has no validation constraints (Path: /statuses [POST]/requestBody/properties/code) |
| 51 | input-validation-required | INFO | String property 'label' has no validation constraints (Path: /statuses [POST]/requestBody/properties/label) |
| 52 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /statuses [POST]/parameters/X-Correlation-Id) |
| 53 | input-validation-required | INFO | String property 'code' has no validation constraints (Path: /statuses/{statusRecordId} [PATCH]/requestBody/properties/code) |
| 54 | input-validation-required | INFO | String property 'label' has no validation constraints (Path: /statuses/{statusRecordId} [PATCH]/requestBody/properties/label) |
| 55 | input-validation-required | INFO | Parameter 'statusRecordId' has no validation constraints (Path: /statuses/{statusRecordId} [PATCH]/parameters/statusRecordId) |
| 56 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /statuses/{statusRecordId} [PATCH]/parameters/X-Correlation-Id) |
| 57 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /statuses [GET]/responses/200/content/application/json) |
| 58 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 59 | openapi-tags | WARNING | No global tags defined (Path: /tags) |
| 60 | operation-description | WARNING | Operation description is missing (Path: /statuses [GET]) |
| 61 | operation-description | WARNING | Operation description is missing (Path: /statuses [POST]) |
| 62 | operation-description | WARNING | Operation description is missing (Path: /statuses/{statusRecordId} [GET]) |
| 63 | operation-description | WARNING | Operation description is missing (Path: /statuses/{statusRecordId} [DELETE]) |
| 64 | operation-description | WARNING | Operation description is missing (Path: /statuses/{statusRecordId} [PATCH]) |
| 65 | operation-tags | WARNING | Operation has no tags (Path: /statuses [GET]) |
| 66 | operation-tags | WARNING | Operation has no tags (Path: /statuses [POST]) |
| 67 | operation-tags | WARNING | Operation has no tags (Path: /statuses/{statusRecordId} [GET]) |
| 68 | operation-tags | WARNING | Operation has no tags (Path: /statuses/{statusRecordId} [DELETE]) |
| 69 | operation-tags | WARNING | Operation has no tags (Path: /statuses/{statusRecordId} [PATCH]) |
| 70 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'statusRecordId' is not modeled as a UUID/GUID (Path: /statuses/{statusRecordId} [GET]/parameters/statusRecordId) |
| 71 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'statusRecordId' is not modeled as a UUID/GUID (Path: /statuses/{statusRecordId} [PATCH]/parameters/statusRecordId) |
| 72 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'statusRecordId' is not modeled as a UUID/GUID (Path: /statuses/{statusRecordId} [DELETE]/parameters/statusRecordId) |
| 73 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /statuses [GET]/responses) |
| 74 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /statuses [POST]/responses) |
| 75 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /statuses/{statusRecordId} [GET]/responses) |
| 76 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /statuses/{statusRecordId} [PATCH]/responses) |
| 77 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /statuses/{statusRecordId} [DELETE]/responses) |
| 78 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /statuses [GET]/parameters/X-Correlation-Id) |
| 79 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /statuses [POST]/parameters/X-Correlation-Id) |
| 80 | injection-pattern-on-string-parameters | WARNING | String parameter 'statusRecordId' does not define a restrictive pattern (Path: /statuses/{statusRecordId} [GET]/parameters/statusRecordId) |
| 81 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /statuses/{statusRecordId} [GET]/parameters/X-Correlation-Id) |
| 82 | injection-pattern-on-string-parameters | WARNING | String parameter 'statusRecordId' does not define a restrictive pattern (Path: /statuses/{statusRecordId} [PATCH]/parameters/statusRecordId) |
| 83 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /statuses/{statusRecordId} [PATCH]/parameters/X-Correlation-Id) |
| 84 | injection-pattern-on-string-parameters | WARNING | String parameter 'statusRecordId' does not define a restrictive pattern (Path: /statuses/{statusRecordId} [DELETE]/parameters/statusRecordId) |
| 85 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /statuses/{statusRecordId} [DELETE]/parameters/X-Correlation-Id) |
| 86 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /statuses [GET]) |
| 87 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 88 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 89 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 90 | request-response-antipattern | WARNING | Schema name 'StatusRecordCreateRequest' contains Request or Response (Path: /components/schemas/StatusRecordCreateRequest) |
| 91 | standard-headers-required | INFO | No Client-Id header documented (Path: /statuses [GET]) |
| 92 | standard-headers-required | INFO | No Client-Id header documented (Path: /statuses [POST]) |
| 93 | standard-headers-required | INFO | No Client-Id header documented (Path: /statuses/{statusRecordId} [GET]) |
| 94 | standard-headers-required | INFO | No Client-Id header documented (Path: /statuses/{statusRecordId} [DELETE]) |
| 95 | standard-headers-required | INFO | No Client-Id header documented (Path: /statuses/{statusRecordId} [PATCH]) |
| 96 | tags-should-be-defined | WARNING | No tags defined for this API (Path: /tags) |
| 97 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

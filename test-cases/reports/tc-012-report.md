# Test Case: tc-012 - Analytics API
## Complexity: Medium

### Specification
- **Title:** Analytics API
- **Version:** 0.8.0
- **Paths:** 3
- **Operations:** 3

### Linting Results
- **Valid:** false
- **Total Issues:** 62
- **Errors:** 4 | **Warnings:** 42 | **Info:** 16

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | camel-case-fields | WARNING | Property name 'tenant_id' is not camelCase (Path: /components/schemas/RunQueryRequest/properties/tenant_id) |
| 3 | camel-case-fields | WARNING | Property name 'query_name' is not camelCase (Path: /components/schemas/RunQueryRequest/properties/query_name) |
| 4 | camel-case-fields | WARNING | Property name 'output_format' is not camelCase (Path: /components/schemas/RunQueryRequest/properties/output_format) |
| 5 | camel-case-fields | WARNING | Property name 'metric_name' is not camelCase (Path: /components/schemas/QueryResult/properties/rows/items/properties/metric_name) |
| 6 | camel-case-fields | WARNING | Property name 'metric_value' is not camelCase (Path: /components/schemas/QueryResult/properties/rows/items/properties/metric_value) |
| 7 | camel-case-fields | WARNING | Property name 'report_id' is not camelCase (Path: /components/schemas/Report/properties/report_id) |
| 8 | camel-case-fields | WARNING | Property name 'download_url' is not camelCase (Path: /components/schemas/Report/properties/download_url) |
| 9 | resource-use-lowercase | WARNING | Path segment 'AnalyticsService' is not lowercase (Path: /AnalyticsService/RunQuery) |
| 10 | resource-use-lowercase | WARNING | Path segment 'RunQuery' is not lowercase (Path: /AnalyticsService/RunQuery) |
| 11 | resource-use-lowercase | WARNING | Path segment 'AnalyticsService' is not lowercase (Path: /AnalyticsService/GetReport) |
| 12 | resource-use-lowercase | WARNING | Path segment 'GetReport' is not lowercase (Path: /AnalyticsService/GetReport) |
| 13 | asyncapi-channel-parameters | WARNING | Channel path parameter 'reportId' is not defined in the path item parameters list (Path: /reports/{reportId}/export) |
| 14 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 15 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 16 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 17 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 18 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 19 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 20 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 21 | health-check-complete | ERROR | Missing liveness health endpoint (/health or /live) (Path: /paths) |
| 22 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 23 | http-verbs-usage | WARNING | POST should not be used on existing resources (use PUT or PATCH) (Path: /reports/{reportId}/export [POST]) |
| 24 | input-validation-required | INFO | String property 'tenant_id' has no validation constraints (Path: /AnalyticsService/RunQuery [POST]/requestBody/properties/tenant_id) |
| 25 | input-validation-required | INFO | String property 'query_name' has no validation constraints (Path: /AnalyticsService/RunQuery [POST]/requestBody/properties/query_name) |
| 26 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /AnalyticsService/RunQuery [POST]/parameters/X-Correlation-Id) |
| 27 | input-validation-required | INFO | Parameter 'reportId' has no validation constraints (Path: /reports/{reportId}/export [POST]/parameters/reportId) |
| 28 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /reports/{reportId}/export [POST]/parameters/X-Correlation-Id) |
| 29 | naming-convention-schemas | WARNING | Property 'tenant_id' does not follow camelCase convention (Path: /components/schemas/RunQueryRequest/properties/tenant_id) |
| 30 | naming-convention-schemas | WARNING | Property 'query_name' does not follow camelCase convention (Path: /components/schemas/RunQueryRequest/properties/query_name) |
| 31 | naming-convention-schemas | WARNING | Property 'output_format' does not follow camelCase convention (Path: /components/schemas/RunQueryRequest/properties/output_format) |
| 32 | naming-convention-schemas | WARNING | Property 'report_id' does not follow camelCase convention (Path: /components/schemas/Report/properties/report_id) |
| 33 | naming-convention-schemas | WARNING | Property 'download_url' does not follow camelCase convention (Path: /components/schemas/Report/properties/download_url) |
| 34 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 35 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: AnalyticsService_RunQuery (Path: /AnalyticsService/RunQuery [POST]) |
| 36 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: AnalyticsService_GetReport (Path: /AnalyticsService/GetReport [GET]) |
| 37 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'reportId' is not modeled as a UUID/GUID (Path: /reports/{reportId}/export [POST]/parameters/reportId) |
| 38 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /AnalyticsService/RunQuery [POST]/responses) |
| 39 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /AnalyticsService/GetReport [GET]/responses) |
| 40 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /reports/{reportId}/export [POST]/responses) |
| 41 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /AnalyticsService/RunQuery [POST]/parameters/X-Correlation-Id) |
| 42 | injection-pattern-on-string-parameters | WARNING | String parameter 'report_id' does not define a restrictive pattern (Path: /AnalyticsService/GetReport [GET]/parameters/report_id) |
| 43 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /AnalyticsService/GetReport [GET]/parameters/X-Correlation-Id) |
| 44 | injection-pattern-on-string-parameters | WARNING | String parameter 'reportId' does not define a restrictive pattern (Path: /reports/{reportId}/export [POST]/parameters/reportId) |
| 45 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /reports/{reportId}/export [POST]/parameters/X-Correlation-Id) |
| 46 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /AnalyticsService/GetReport [GET]) |
| 47 | path-naming-convention | WARNING | Path does not follow kebab-case convention (Path: /AnalyticsService/RunQuery) |
| 48 | path-naming-convention | WARNING | Path does not follow kebab-case convention (Path: /AnalyticsService/GetReport) |
| 49 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /AnalyticsService/RunQuery [POST]) |
| 50 | post-response-standard | WARNING | POST creation endpoint returns 200 instead of 201 Created (Path: /reports/{reportId}/export [POST]) |
| 51 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 52 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 53 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 54 | request-response-antipattern | WARNING | Schema name 'RunQueryRequest' contains Request or Response (Path: /components/schemas/RunQueryRequest) |
| 55 | response-codes-validation | WARNING | No error responses defined (Path: /AnalyticsService/RunQuery [POST]) |
| 56 | response-codes-validation | WARNING | No error responses defined (Path: /AnalyticsService/GetReport [GET]) |
| 57 | response-codes-validation | WARNING | No error responses defined (Path: /reports/{reportId}/export [POST]) |
| 58 | security-schemes-required | ERROR | No security schemes defined (Path: /components/securitySchemes) |
| 59 | standard-headers-required | INFO | No Client-Id header documented (Path: /AnalyticsService/RunQuery [POST]) |
| 60 | standard-headers-required | INFO | No Client-Id header documented (Path: /AnalyticsService/GetReport [GET]) |
| 61 | standard-headers-required | INFO | No Client-Id header documented (Path: /reports/{reportId}/export [POST]) |
| 62 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

# Test Case: tc-005 - Category API
## Complexity: Low

### Specification
- **Title:** Category API
- **Version:** 1.0.0
- **Paths:** 3
- **Operations:** 6

### Linting Results
- **Valid:** false
- **Total Issues:** 97
- **Errors:** 7 | **Warnings:** 51 | **Info:** 39

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | parser | ERROR | Parse error: attribute paths.'/Category_Items'(get).responses.200.content.'application/json'.schema.#/components/schemas/CategoryItem is missing (Path: /) |
| 2 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 3 | camel-case-fields | WARNING | Property name 'category_name' is not camelCase (Path: /components/schemas/CategoryItemCreateRequest/properties/category_name) |
| 4 | camel-case-fields | WARNING | Property name 'display_order' is not camelCase (Path: /components/schemas/CategoryItemCreateRequest/properties/display_order) |
| 5 | camel-case-fields | WARNING | Property name 'is_visible' is not camelCase (Path: /components/schemas/CategoryItemCreateRequest/properties/is_visible) |
| 6 | camel-case-fields | WARNING | Property name 'category_name' is not camelCase (Path: /components/schemas/category_item/properties/category_name) |
| 7 | camel-case-fields | WARNING | Property name 'display_order' is not camelCase (Path: /components/schemas/category_item/properties/display_order) |
| 8 | camel-case-fields | WARNING | Property name 'is_visible' is not camelCase (Path: /components/schemas/category_item/properties/is_visible) |
| 9 | resource-use-lowercase | WARNING | Path segment 'Category_Items' is not lowercase (Path: /Category_Items) |
| 10 | resource-use-lowercase | WARNING | Path segment 'Category_Items' is not lowercase (Path: /Category_Items/{CategoryId}) |
| 11 | asyncapi-channel-parameters | WARNING | Channel path parameter 'CategoryId' is not defined in the path item parameters list (Path: /Category_Items/{CategoryId}) |
| 12 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 13 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 14 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 15 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /Category_Items [GET]/responses/400) |
| 16 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /Category_Items [GET]/responses/400) |
| 17 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /Category_Items [GET]/responses/400) |
| 18 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /Category_Items [GET]/responses/400) |
| 19 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /Category_Items [POST]/responses/400) |
| 20 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /Category_Items [POST]/responses/400) |
| 21 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /Category_Items [POST]/responses/400) |
| 22 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /Category_Items [POST]/responses/400) |
| 23 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /Category_Items/{CategoryId} [GET]/responses/404) |
| 24 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /Category_Items/{CategoryId} [GET]/responses/404) |
| 25 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /Category_Items/{CategoryId} [GET]/responses/404) |
| 26 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /Category_Items/{CategoryId} [GET]/responses/404) |
| 27 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /Category_Items/{CategoryId} [DELETE]/responses/404) |
| 28 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /Category_Items/{CategoryId} [DELETE]/responses/404) |
| 29 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /Category_Items/{CategoryId} [DELETE]/responses/404) |
| 30 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /Category_Items/{CategoryId} [DELETE]/responses/404) |
| 31 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /Category_Items/{CategoryId} [PATCH]/responses/400) |
| 32 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /Category_Items/{CategoryId} [PATCH]/responses/400) |
| 33 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /Category_Items/{CategoryId} [PATCH]/responses/400) |
| 34 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /Category_Items/{CategoryId} [PATCH]/responses/400) |
| 35 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 36 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 37 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 38 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 39 | message-names-upper-camel-case | ERROR | Schema name 'category_item' is not UpperCamelCase (Path: /components/schemas/category_item) |
| 40 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 41 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 42 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 43 | input-validation-required | INFO | String property 'category_name' has no validation constraints (Path: /Category_Items [POST]/requestBody/properties/category_name) |
| 44 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /Category_Items [POST]/parameters/X-Correlation-Id) |
| 45 | input-validation-required | INFO | String property 'category_name' has no validation constraints (Path: /Category_Items/{CategoryId} [PATCH]/requestBody/properties/category_name) |
| 46 | input-validation-required | INFO | Parameter 'CategoryId' has no validation constraints (Path: /Category_Items/{CategoryId} [PATCH]/parameters/CategoryId) |
| 47 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /Category_Items/{CategoryId} [PATCH]/parameters/X-Correlation-Id) |
| 48 | naming-convention-schemas | WARNING | Property 'category_name' does not follow camelCase convention (Path: /components/schemas/CategoryItemCreateRequest/properties/category_name) |
| 49 | naming-convention-schemas | WARNING | Property 'display_order' does not follow camelCase convention (Path: /components/schemas/CategoryItemCreateRequest/properties/display_order) |
| 50 | naming-convention-schemas | WARNING | Property 'is_visible' does not follow camelCase convention (Path: /components/schemas/CategoryItemCreateRequest/properties/is_visible) |
| 51 | naming-convention-schemas | WARNING | Schema name 'category_item' does not follow PascalCase convention (Path: /components/schemas/category_item) |
| 52 | naming-convention-schemas | WARNING | Property 'category_name' does not follow camelCase convention (Path: /components/schemas/category_item/properties/category_name) |
| 53 | naming-convention-schemas | WARNING | Property 'display_order' does not follow camelCase convention (Path: /components/schemas/category_item/properties/display_order) |
| 54 | naming-convention-schemas | WARNING | Property 'is_visible' does not follow camelCase convention (Path: /components/schemas/category_item/properties/is_visible) |
| 55 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /Category_Items [GET]/responses/200/content/application/json) |
| 56 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 57 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 58 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: CategoryItemsGet (Path: /Category_Items [GET]) |
| 59 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: CategoryItemsPost (Path: /Category_Items [POST]) |
| 60 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: GetCategoryItemById (Path: /Category_Items/{CategoryId} [GET]) |
| 61 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: DeleteCategoryItem (Path: /Category_Items/{CategoryId} [DELETE]) |
| 62 | operation-operationId-hyphen-case | ERROR | operationId is not lower-hyphen-case: Update_Category_Item (Path: /Category_Items/{CategoryId} [PATCH]) |
| 63 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'CategoryId' is not modeled as a UUID/GUID (Path: /Category_Items/{CategoryId} [GET]/parameters/CategoryId) |
| 64 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'CategoryId' is not modeled as a UUID/GUID (Path: /Category_Items/{CategoryId} [PATCH]/parameters/CategoryId) |
| 65 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'CategoryId' is not modeled as a UUID/GUID (Path: /Category_Items/{CategoryId} [DELETE]/parameters/CategoryId) |
| 66 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /Category_Items [GET]/responses) |
| 67 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /Category_Items [POST]/responses) |
| 68 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /Category_Items/{CategoryId} [GET]/responses) |
| 69 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /Category_Items/{CategoryId} [PATCH]/responses) |
| 70 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /Category_Items/{CategoryId} [DELETE]/responses) |
| 71 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 72 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /Category_Items [GET]/parameters/X-Correlation-Id) |
| 73 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /Category_Items [POST]/parameters/X-Correlation-Id) |
| 74 | injection-pattern-on-string-parameters | WARNING | String parameter 'CategoryId' does not define a restrictive pattern (Path: /Category_Items/{CategoryId} [GET]/parameters/CategoryId) |
| 75 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /Category_Items/{CategoryId} [GET]/parameters/X-Correlation-Id) |
| 76 | injection-pattern-on-string-parameters | WARNING | String parameter 'CategoryId' does not define a restrictive pattern (Path: /Category_Items/{CategoryId} [PATCH]/parameters/CategoryId) |
| 77 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /Category_Items/{CategoryId} [PATCH]/parameters/X-Correlation-Id) |
| 78 | injection-pattern-on-string-parameters | WARNING | String parameter 'CategoryId' does not define a restrictive pattern (Path: /Category_Items/{CategoryId} [DELETE]/parameters/CategoryId) |
| 79 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /Category_Items/{CategoryId} [DELETE]/parameters/X-Correlation-Id) |
| 80 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 81 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /Category_Items [GET]) |
| 82 | parameter-naming-convention | WARNING | Path parameter 'CategoryId' does not follow camelCase (Path: /Category_Items/{CategoryId}/parameters/CategoryId) |
| 83 | parameter-naming-convention | WARNING | Path parameter 'CategoryId' does not follow camelCase (Path: /Category_Items/{CategoryId}/parameters/CategoryId) |
| 84 | parameter-naming-convention | WARNING | Path parameter 'CategoryId' does not follow camelCase (Path: /Category_Items/{CategoryId}/parameters/CategoryId) |
| 85 | path-naming-convention | WARNING | Path does not follow kebab-case convention (Path: /Category_Items) |
| 86 | path-naming-convention | WARNING | Path does not follow kebab-case convention (Path: /Category_Items/{CategoryId}) |
| 87 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 88 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 89 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 90 | request-response-antipattern | WARNING | Schema name 'CategoryItemCreateRequest' contains Request or Response (Path: /components/schemas/CategoryItemCreateRequest) |
| 91 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 92 | standard-headers-required | INFO | No Client-Id header documented (Path: /Category_Items [GET]) |
| 93 | standard-headers-required | INFO | No Client-Id header documented (Path: /Category_Items [POST]) |
| 94 | standard-headers-required | INFO | No Client-Id header documented (Path: /Category_Items/{CategoryId} [GET]) |
| 95 | standard-headers-required | INFO | No Client-Id header documented (Path: /Category_Items/{CategoryId} [DELETE]) |
| 96 | standard-headers-required | INFO | No Client-Id header documented (Path: /Category_Items/{CategoryId} [PATCH]) |
| 97 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; rule violations were intentionally preserved to keep this negative test case reusable.

### Final Status
❌ FAIL

# Test Case: tc-002 - Product API
## Complexity: Low

### Specification
- **Title:** Product API
- **Version:** 1.1.0
- **Paths:** 3
- **Operations:** 6

### Linting Results
- **Valid:** true
- **Total Issues:** 135
- **Errors:** 0 | **Warnings:** 65 | **Info:** 70

### Issues Found
| # | Rule ID | Severity | Message |
|---|---------|----------|---------|
| 1 | api-must-have-documentation | WARNING | API documentation is missing (Path: /) |
| 2 | node-shapes-must-have-descriptions | WARNING | Schema 'Product' is missing a description (Path: /components/schemas/Product) |
| 3 | node-shapes-must-have-descriptions | WARNING | Schema 'ProductCreateRequest' is missing a description (Path: /components/schemas/ProductCreateRequest) |
| 4 | property-shape-ranges-must-have-descriptions | WARNING | Property 'id' is missing a description (Path: /components/schemas/Product/properties/id) |
| 5 | property-shape-ranges-must-have-descriptions | WARNING | Property 'name' is missing a description (Path: /components/schemas/Product/properties/name) |
| 6 | property-shape-ranges-must-have-descriptions | WARNING | Property 'sku' is missing a description (Path: /components/schemas/Product/properties/sku) |
| 7 | property-shape-ranges-must-have-descriptions | WARNING | Property 'price' is missing a description (Path: /components/schemas/Product/properties/price) |
| 8 | property-shape-ranges-must-have-descriptions | WARNING | Property 'availability' is missing a description (Path: /components/schemas/Product/properties/availability) |
| 9 | property-shape-ranges-must-have-descriptions | WARNING | Property 'name' is missing a description (Path: /components/schemas/ProductCreateRequest/properties/name) |
| 10 | property-shape-ranges-must-have-descriptions | WARNING | Property 'sku' is missing a description (Path: /components/schemas/ProductCreateRequest/properties/sku) |
| 11 | property-shape-ranges-must-have-descriptions | WARNING | Property 'price' is missing a description (Path: /components/schemas/ProductCreateRequest/properties/price) |
| 12 | property-shape-ranges-must-have-descriptions | WARNING | Property 'availability' is missing a description (Path: /components/schemas/ProductCreateRequest/properties/availability) |
| 13 | asyncapi-channel-parameters | WARNING | Channel path parameter 'productId' is not defined in the path item parameters list (Path: /products/{productId}) |
| 14 | asyncapi-server-not-example-com | WARNING | Server URL points to example.com (Path: /servers/0) |
| 15 | categories-should-be-defined | WARNING | No categories defined for this API (Path: /) |
| 16 | description-required | INFO | Missing operation description (Path: /products [GET]) |
| 17 | description-required | INFO | Missing operation description (Path: /products [POST]) |
| 18 | description-required | INFO | Missing operation description (Path: /products/{productId} [GET]) |
| 19 | description-required | INFO | Missing operation description (Path: /products/{productId} [DELETE]) |
| 20 | description-required | INFO | Missing operation description (Path: /products/{productId} [PATCH]) |
| 21 | description-required | INFO | Missing operation description (Path: /health [GET]) |
| 22 | description-required | INFO | Schema 'Product' has no description (Path: /components/schemas/Product) |
| 23 | description-required | INFO | Property 'id' has no description (Path: /components/schemas/Product/properties/id) |
| 24 | description-required | INFO | Property 'name' has no description (Path: /components/schemas/Product/properties/name) |
| 25 | description-required | INFO | Property 'sku' has no description (Path: /components/schemas/Product/properties/sku) |
| 26 | description-required | INFO | Property 'price' has no description (Path: /components/schemas/Product/properties/price) |
| 27 | description-required | INFO | Property 'availability' has no description (Path: /components/schemas/Product/properties/availability) |
| 28 | description-required | INFO | Schema 'ProductCreateRequest' has no description (Path: /components/schemas/ProductCreateRequest) |
| 29 | description-required | INFO | Property 'name' has no description (Path: /components/schemas/ProductCreateRequest/properties/name) |
| 30 | description-required | INFO | Property 'sku' has no description (Path: /components/schemas/ProductCreateRequest/properties/sku) |
| 31 | description-required | INFO | Property 'price' has no description (Path: /components/schemas/ProductCreateRequest/properties/price) |
| 32 | description-required | INFO | Property 'availability' has no description (Path: /components/schemas/ProductCreateRequest/properties/availability) |
| 33 | documentation-should-be-defined | WARNING | The API should have documentation — info.description is missing (Path: /info/description) |
| 34 | documentation-should-be-defined | INFO | No external documentation link provided (Path: /externalDocs) |
| 35 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /products [GET]/responses/400) |
| 36 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /products [GET]/responses/400) |
| 37 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /products [GET]/responses/400) |
| 38 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /products [GET]/responses/400) |
| 39 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /products [POST]/responses/400) |
| 40 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /products [POST]/responses/400) |
| 41 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /products [POST]/responses/400) |
| 42 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /products [POST]/responses/400) |
| 43 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /products/{productId} [GET]/responses/404) |
| 44 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /products/{productId} [GET]/responses/404) |
| 45 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /products/{productId} [GET]/responses/404) |
| 46 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /products/{productId} [GET]/responses/404) |
| 47 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /products/{productId} [DELETE]/responses/404) |
| 48 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /products/{productId} [DELETE]/responses/404) |
| 49 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /products/{productId} [DELETE]/responses/404) |
| 50 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /products/{productId} [DELETE]/responses/404) |
| 51 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'type' (Path: /products/{productId} [PATCH]/responses/400) |
| 52 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'detail' (Path: /products/{productId} [PATCH]/responses/400) |
| 53 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'title' (Path: /products/{productId} [PATCH]/responses/400) |
| 54 | error-model-standard | INFO | Error schema missing RFC 7807 field: 'status' (Path: /products/{productId} [PATCH]/responses/400) |
| 55 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products [GET]/responses/200) |
| 56 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products [GET]/responses/400) |
| 57 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products [POST]/requestBody) |
| 58 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products [POST]/responses/201) |
| 59 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products [POST]/responses/400) |
| 60 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products/{productId} [GET]/responses/200) |
| 61 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products/{productId} [GET]/responses/404) |
| 62 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products/{productId} [DELETE]/responses/404) |
| 63 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products/{productId} [PATCH]/requestBody) |
| 64 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products/{productId} [PATCH]/responses/200) |
| 65 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /products/{productId} [PATCH]/responses/400) |
| 66 | provide-examples-on-payloads | INFO | No examples provided for content type 'application/json' (Path: /health [GET]/responses/200) |
| 67 | faq | WARNING | The API documentation should include an FAQ page (Path: /) |
| 68 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-api-layer' (Path: /info) |
| 69 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-lifecycle-status' (Path: /info) |
| 70 | x-extensions-governance | INFO | Missing recommended governance extension: 'x-technical-owner' (Path: /info) |
| 71 | health-check-complete | WARNING | Missing readiness endpoint (/ready or /readiness) (Path: /paths) |
| 72 | health-check-complete | WARNING | Health endpoint does not report dependency status (Path: /paths) |
| 73 | health-check-complete | INFO | Health endpoint missing 503 response for unhealthy state (Path: /health) |
| 74 | info-license | WARNING | Missing API license information (Path: /info/license) |
| 75 | info-completeness | WARNING | Missing API description (Path: /info/description) |
| 76 | info-completeness | WARNING | Missing contact information (Path: /info/contact) |
| 77 | input-validation-required | INFO | String property 'name' has no validation constraints (Path: /products [POST]/requestBody/properties/name) |
| 78 | input-validation-required | INFO | String property 'sku' has no validation constraints (Path: /products [POST]/requestBody/properties/sku) |
| 79 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /products [POST]/parameters/X-Correlation-Id) |
| 80 | input-validation-required | INFO | String property 'name' has no validation constraints (Path: /products/{productId} [PATCH]/requestBody/properties/name) |
| 81 | input-validation-required | INFO | String property 'sku' has no validation constraints (Path: /products/{productId} [PATCH]/requestBody/properties/sku) |
| 82 | input-validation-required | INFO | Parameter 'productId' has no validation constraints (Path: /products/{productId} [PATCH]/parameters/productId) |
| 83 | input-validation-required | INFO | Parameter 'X-Correlation-Id' has no validation constraints (Path: /products/{productId} [PATCH]/parameters/X-Correlation-Id) |
| 84 | missing-property-description | WARNING | Property 'id' is missing a description (Path: /components/schemas/Product/properties/id) |
| 85 | missing-property-description | WARNING | Property 'name' is missing a description (Path: /components/schemas/Product/properties/name) |
| 86 | missing-property-description | WARNING | Property 'sku' is missing a description (Path: /components/schemas/Product/properties/sku) |
| 87 | missing-property-description | WARNING | Property 'price' is missing a description (Path: /components/schemas/Product/properties/price) |
| 88 | missing-property-description | WARNING | Property 'availability' is missing a description (Path: /components/schemas/Product/properties/availability) |
| 89 | missing-property-description | WARNING | Property 'name' is missing a description (Path: /components/schemas/ProductCreateRequest/properties/name) |
| 90 | missing-property-description | WARNING | Property 'sku' is missing a description (Path: /components/schemas/ProductCreateRequest/properties/sku) |
| 91 | missing-property-description | WARNING | Property 'price' is missing a description (Path: /components/schemas/ProductCreateRequest/properties/price) |
| 92 | missing-property-description | WARNING | Property 'availability' is missing a description (Path: /components/schemas/ProductCreateRequest/properties/availability) |
| 93 | missing-type-description | WARNING | Schema 'Product' is missing a description (Path: /components/schemas/Product) |
| 94 | missing-type-description | WARNING | Schema 'ProductCreateRequest' is missing a description (Path: /components/schemas/ProductCreateRequest) |
| 95 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /products [GET]/responses/200/content/application/json) |
| 96 | non-anonymous-types | WARNING | Response media type 'application/json' uses an inline schema (Path: /health [GET]/responses/200/content/application/json) |
| 97 | oas-commons-usage | INFO | API has list endpoints but no pagination schema found (Path: /components/schemas) |
| 98 | operation-description | WARNING | Operation description is missing (Path: /products [GET]) |
| 99 | operation-description | WARNING | Operation description is missing (Path: /products [POST]) |
| 100 | operation-description | WARNING | Operation description is missing (Path: /products/{productId} [GET]) |
| 101 | operation-description | WARNING | Operation description is missing (Path: /products/{productId} [DELETE]) |
| 102 | operation-description | WARNING | Operation description is missing (Path: /products/{productId} [PATCH]) |
| 103 | operation-description | WARNING | Operation description is missing (Path: /health [GET]) |
| 104 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'productId' is not modeled as a UUID/GUID (Path: /products/{productId} [GET]/parameters/productId) |
| 105 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'productId' is not modeled as a UUID/GUID (Path: /products/{productId} [PATCH]/parameters/productId) |
| 106 | broken-object-level-authorization-use-guids | WARNING | Path identifier 'productId' is not modeled as a UUID/GUID (Path: /products/{productId} [DELETE]/parameters/productId) |
| 107 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /products [GET]/responses) |
| 108 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /products [POST]/responses) |
| 109 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /products/{productId} [GET]/responses) |
| 110 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /products/{productId} [PATCH]/responses) |
| 111 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /products/{productId} [DELETE]/responses) |
| 112 | lack-of-resources-and-rate-limiting-too-many-requests | WARNING | Operation does not document a 429 Too Many Requests response (Path: /health [GET]/responses) |
| 113 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /products [GET]/parameters/X-Correlation-Id) |
| 114 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /products [POST]/parameters/X-Correlation-Id) |
| 115 | injection-pattern-on-string-parameters | WARNING | String parameter 'productId' does not define a restrictive pattern (Path: /products/{productId} [GET]/parameters/productId) |
| 116 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /products/{productId} [GET]/parameters/X-Correlation-Id) |
| 117 | injection-pattern-on-string-parameters | WARNING | String parameter 'productId' does not define a restrictive pattern (Path: /products/{productId} [PATCH]/parameters/productId) |
| 118 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /products/{productId} [PATCH]/parameters/X-Correlation-Id) |
| 119 | injection-pattern-on-string-parameters | WARNING | String parameter 'productId' does not define a restrictive pattern (Path: /products/{productId} [DELETE]/parameters/productId) |
| 120 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /products/{productId} [DELETE]/parameters/X-Correlation-Id) |
| 121 | injection-pattern-on-string-parameters | WARNING | String parameter 'X-Correlation-Id' does not define a restrictive pattern (Path: /health [GET]/parameters/X-Correlation-Id) |
| 122 | pagination-standard | WARNING | Collection endpoint has no pagination parameters (page/pageSize) (Path: /products [GET]) |
| 123 | rate-limit-documented | WARNING | No rate limiting documentation found in the API specification (Path: /) |
| 124 | recommended-categories | INFO | None of the recommended categories (Business Unit, Domain, Sub-Domain, API Layer) are present (Path: /) |
| 125 | recommended-tags | INFO | None of the recommended tags (system, process, experience) are present (Path: /tags) |
| 126 | request-response-antipattern | WARNING | Schema name 'ProductCreateRequest' contains Request or Response (Path: /components/schemas/ProductCreateRequest) |
| 127 | response-codes-validation | WARNING | No error responses defined (Path: /health [GET]) |
| 128 | standard-headers-required | INFO | No Client-Id header documented (Path: /products [GET]) |
| 129 | standard-headers-required | INFO | No Client-Id header documented (Path: /products [POST]) |
| 130 | standard-headers-required | INFO | No Client-Id header documented (Path: /products/{productId} [GET]) |
| 131 | standard-headers-required | INFO | No Client-Id header documented (Path: /products/{productId} [DELETE]) |
| 132 | standard-headers-required | INFO | No Client-Id header documented (Path: /products/{productId} [PATCH]) |
| 133 | tag-description | WARNING | Global tag is missing a description: Product (Path: /tags/Product) |
| 134 | tag-description | WARNING | Global tag is missing a description: Health (Path: /tags/Health) |
| 135 | versioning-required | INFO | No versioned path prefixes found (e.g., /v1/...) (Path: /paths) |

### Corrections Applied (if any)
- No syntax corrections were required; warnings were intentionally preserved to exercise lint coverage.

### Final Status
⚠️ PASS WITH WARNINGS

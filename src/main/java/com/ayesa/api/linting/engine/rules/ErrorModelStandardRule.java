package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that error responses follow the RFC 7807 Problem Details standard.
 * Covers directive 4.5: Manejo global obligatorio de errores.
 */
@Component
public class ErrorModelStandardRule implements LintingRule {

    private static final Set<String> ERROR_CODES = Set.of("400", "401", "403", "404", "405", "409", "422", "429", "500", "502", "503");
    private static final Set<String> PROBLEM_DETAIL_FIELDS = Set.of("type", "title", "status", "detail");

    @Override
    public String getRuleId() {
        return "error-model-standard";
    }

    @Override
    public String getDescription() {
        return "Validates that error responses follow a standard error model (RFC 7807 Problem Details)";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            PathItem pathItem = pathEntry.getValue();

            checkOperation(issues, openAPI, path, "GET", pathItem.getGet());
            checkOperation(issues, openAPI, path, "POST", pathItem.getPost());
            checkOperation(issues, openAPI, path, "PUT", pathItem.getPut());
            checkOperation(issues, openAPI, path, "DELETE", pathItem.getDelete());
            checkOperation(issues, openAPI, path, "PATCH", pathItem.getPatch());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, OpenAPI openAPI, String path, String method, Operation operation) {
        if (operation == null || operation.getResponses() == null) {
            return;
        }

        String location = path + " [" + method + "]";

        for (Map.Entry<String, ApiResponse> respEntry : operation.getResponses().entrySet()) {
            String code = respEntry.getKey();
            if (!ERROR_CODES.contains(code)) {
                continue;
            }

            ApiResponse response = respEntry.getValue();
            Content content = response.getContent();

            if (content == null || content.isEmpty()) {
                issues.add(LintingIssue.warning(getRuleId(), location + "/responses/" + code,
                        "Error response " + code + " has no content schema",
                        "Define a response body using the standard error model (RFC 7807)"));
                continue;
            }

            MediaType mediaType = content.get("application/json");
            if (mediaType == null) {
                mediaType = content.get("application/problem+json");
            }

            if (mediaType == null || mediaType.getSchema() == null) {
                issues.add(LintingIssue.warning(getRuleId(), location + "/responses/" + code,
                        "Error response " + code + " has no JSON schema",
                        "Use application/json or application/problem+json with the standard error model"));
                continue;
            }

            Schema<?> schema = resolveSchema(openAPI, mediaType.getSchema());
            if (schema != null && schema.getProperties() != null) {
                for (String required : PROBLEM_DETAIL_FIELDS) {
                    if (!schema.getProperties().containsKey(required)) {
                        issues.add(LintingIssue.info(getRuleId(), location + "/responses/" + code,
                                "Error schema missing RFC 7807 field: '" + required + "'",
                                "Include 'type', 'title', 'status', and 'detail' fields per RFC 7807"));
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Schema<?> resolveSchema(OpenAPI openAPI, Schema<?> schema) {
        if (schema.get$ref() != null && openAPI.getComponents() != null
                && openAPI.getComponents().getSchemas() != null) {
            String ref = schema.get$ref();
            String schemaName = ref.substring(ref.lastIndexOf('/') + 1);
            return openAPI.getComponents().getSchemas().get(schemaName);
        }
        return schema;
    }
}

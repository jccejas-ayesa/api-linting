package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates usage of corporate OAS commons components.
 * Checks for standard error schemas, pagination, and audit patterns.
 * Covers directive 2.4: Uso obligatorio de OAS Commons corporativo.
 */
@Component
public class OasCommonsUsageRule implements LintingRule {

    private static final Set<String> COMMON_ERROR_SCHEMA_NAMES = Set.of(
            "Error", "ErrorResponse", "ProblemDetail", "ApiError", "Problem"
    );

    private static final Set<String> PAGINATION_SCHEMA_NAMES = Set.of(
            "Pagination", "PageInfo", "PaginatedResponse", "PageMetadata"
    );

    @Override
    public String getRuleId() {
        return "oas-commons-usage";
    }

    @Override
    public String getDescription() {
        return "Validates usage of corporate OAS commons (standard error schemas, pagination, common components)";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        checkErrorSchemaUsage(openAPI, issues);
        checkPaginationUsage(openAPI, issues);
        checkRefUsage(openAPI, issues);

        return issues;
    }

    private void checkErrorSchemaUsage(OpenAPI openAPI, List<LintingIssue> issues) {
        if (openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            issues.add(LintingIssue.warning(getRuleId(), "/components/schemas",
                    "No schemas defined — cannot verify use of common error model",
                    "Define schemas and reference corporate OAS commons for errors"));
            return;
        }

        boolean hasErrorSchema = openAPI.getComponents().getSchemas().keySet().stream()
                .anyMatch(name -> COMMON_ERROR_SCHEMA_NAMES.stream()
                        .anyMatch(common -> name.toLowerCase().contains(common.toLowerCase())));

        if (!hasErrorSchema) {
            issues.add(LintingIssue.warning(getRuleId(), "/components/schemas",
                    "No standard error schema found (expected one of: " + String.join(", ", COMMON_ERROR_SCHEMA_NAMES) + ")",
                    "Define or reference a corporate standard error schema"));
        }
    }

    private void checkPaginationUsage(OpenAPI openAPI, List<LintingIssue> issues) {
        if (openAPI.getPaths() == null) {
            return;
        }

        boolean hasListEndpoints = openAPI.getPaths().entrySet().stream()
                .anyMatch(e -> e.getValue().getGet() != null && !e.getKey().contains("{"));

        if (!hasListEndpoints) {
            return;
        }

        boolean hasPaginationSchema = openAPI.getComponents() != null
                && openAPI.getComponents().getSchemas() != null
                && openAPI.getComponents().getSchemas().keySet().stream()
                .anyMatch(name -> PAGINATION_SCHEMA_NAMES.stream()
                        .anyMatch(common -> name.toLowerCase().contains(common.toLowerCase())));

        if (!hasPaginationSchema) {
            issues.add(LintingIssue.info(getRuleId(), "/components/schemas",
                    "API has list endpoints but no pagination schema found",
                    "Consider using a standard pagination schema for collection endpoints"));
        }
    }

    private void checkRefUsage(OpenAPI openAPI, List<LintingIssue> issues) {
        if (openAPI.getPaths() == null) {
            return;
        }

        int inlineSchemas = 0;

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            PathItem pathItem = pathEntry.getValue();
            inlineSchemas += countInlineSchemas(pathItem.getGet());
            inlineSchemas += countInlineSchemas(pathItem.getPost());
            inlineSchemas += countInlineSchemas(pathItem.getPut());
            inlineSchemas += countInlineSchemas(pathItem.getDelete());
            inlineSchemas += countInlineSchemas(pathItem.getPatch());
        }

        if (inlineSchemas > 5) {
            issues.add(LintingIssue.warning(getRuleId(), "/paths",
                    inlineSchemas + " inline schemas found — consider using $ref to shared components",
                    "Extract repeated schemas to /components/schemas and reference them with $ref"));
        }
    }

    private int countInlineSchemas(Operation operation) {
        if (operation == null || operation.getResponses() == null) {
            return 0;
        }

        int count = 0;
        for (ApiResponse response : operation.getResponses().values()) {
            if (response.getContent() != null) {
                count += (int) response.getContent().values().stream()
                        .filter(mt -> mt.getSchema() != null && mt.getSchema().get$ref() == null
                                && mt.getSchema().getProperties() != null)
                        .count();
            }
        }
        return count;
    }
}

package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that GET collection endpoints support standard pagination.
 * Covers API-BP-COMMON-018, API-BP-GET-002, API-BP-GET-003.
 */
@Component
public class PaginationStandardRule implements LintingRule {

    private static final Set<String> PAGINATION_PARAMS = Set.of("page", "pagesize", "page_size", "limit", "offset");

    @Override
    public String getRuleId() {
        return "pagination-standard";
    }

    @Override
    public String getDescription() {
        return "Validates that GET collection endpoints support standard pagination (page/pageSize) (API-BP-GET-002/003)";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
            String path = entry.getKey();
            PathItem pathItem = entry.getValue();

            // Only check GET endpoints that return collections (no path params at the end)
            if (pathItem.getGet() == null || isResourceEndpoint(path)) {
                continue;
            }

            // Skip health/utility endpoints
            if (path.contains("/health") || path.contains("/ready") || path.contains("/live")
                    || path.contains("/search")) {
                continue;
            }

            Operation getOp = pathItem.getGet();
            boolean hasPagination = hasPaginationParams(getOp) || hasPaginationParams(pathItem);

            if (!hasPagination) {
                issues.add(LintingIssue.warning(getRuleId(), path + " [GET]",
                        "Collection endpoint has no pagination parameters (page/pageSize)",
                        "Add 'page' and 'pageSize' query parameters for paginated responses (default: 20, max: 100)"));
            }
        }

        return issues;
    }

    private boolean isResourceEndpoint(String path) {
        // Endpoints ending with a path parameter are single-resource endpoints
        return path.endsWith("}");
    }

    private boolean hasPaginationParams(Operation operation) {
        if (operation.getParameters() == null) {
            return false;
        }
        return operation.getParameters().stream()
                .filter(p -> "query".equals(p.getIn()))
                .anyMatch(p -> PAGINATION_PARAMS.contains(p.getName().toLowerCase()));
    }

    private boolean hasPaginationParams(PathItem pathItem) {
        if (pathItem.getParameters() == null) {
            return false;
        }
        return pathItem.getParameters().stream()
                .filter(p -> "query".equals(p.getIn()))
                .anyMatch(p -> PAGINATION_PARAMS.contains(p.getName().toLowerCase()));
    }
}

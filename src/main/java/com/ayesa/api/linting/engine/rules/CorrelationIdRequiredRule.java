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
 * Validates that operations include correlation ID headers.
 * Covers directive 5.2: Uso obligatorio de identificadores de correlación.
 */
@Component
public class CorrelationIdRequiredRule implements LintingRule {

    private static final Set<String> CORRELATION_HEADERS = Set.of(
            "x-correlation-id", "x-request-id", "x-trace-id", "traceparent"
    );

    @Override
    public String getRuleId() {
        return "correlation-id-required";
    }

    @Override
    public String getDescription() {
        return "Validates that operations include correlation/tracing ID headers (X-Correlation-Id, X-Request-Id)";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            if (isHealthEndpoint(path)) {
                continue;
            }

            PathItem pathItem = pathEntry.getValue();
            List<Parameter> pathLevelParams = pathItem.getParameters();

            checkOperation(issues, path, "GET", pathItem.getGet(), pathLevelParams);
            checkOperation(issues, path, "POST", pathItem.getPost(), pathLevelParams);
            checkOperation(issues, path, "PUT", pathItem.getPut(), pathLevelParams);
            checkOperation(issues, path, "DELETE", pathItem.getDelete(), pathLevelParams);
            checkOperation(issues, path, "PATCH", pathItem.getPatch(), pathLevelParams);
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, String path, String method,
                                 Operation operation, List<Parameter> pathParams) {
        if (operation == null) {
            return;
        }

        boolean hasCorrelation = hasCorrelationParam(pathParams) || hasCorrelationParam(operation.getParameters());

        if (!hasCorrelation) {
            issues.add(LintingIssue.warning(getRuleId(), path + " [" + method + "]",
                    "No correlation ID header defined",
                    "Add a header parameter like 'X-Correlation-Id' or 'X-Request-Id' for traceability"));
        }
    }

    private boolean hasCorrelationParam(List<Parameter> params) {
        if (params == null) {
            return false;
        }
        return params.stream()
                .filter(p -> "header".equals(p.getIn()))
                .anyMatch(p -> p.getName() != null
                        && CORRELATION_HEADERS.contains(p.getName().toLowerCase()));
    }

    private boolean isHealthEndpoint(String path) {
        return path.contains("/health") || path.contains("/ready") || path.contains("/live");
    }
}

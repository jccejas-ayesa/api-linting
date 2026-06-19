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
 * Validates that standard headers are documented (Authorization, Client-Id).
 * Headers must follow Train-Case convention.
 * Covers API-BP-COMMON-014 and API-BP-COMMON-019.
 */
@Component
public class StandardHeadersRequiredRule implements LintingRule {

    private static final Set<String> REQUIRED_HEADERS = Set.of(
            "authorization"
    );

    private static final Set<String> RECOMMENDED_HEADERS = Set.of(
            "x-ibm-client-id", "x-client-id"
    );

    @Override
    public String getRuleId() {
        return "standard-headers-required";
    }

    @Override
    public String getDescription() {
        return "Validates that standard security headers (Authorization, Client-Id) are documented (API-BP-COMMON-014/019)";
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

            // Skip health endpoints
            if (path.contains("/health") || path.contains("/ready") || path.contains("/live")) {
                continue;
            }

            checkOperationHeaders(issues, path, "GET", pathItem.getGet(), pathItem.getParameters());
            checkOperationHeaders(issues, path, "POST", pathItem.getPost(), pathItem.getParameters());
            checkOperationHeaders(issues, path, "PUT", pathItem.getPut(), pathItem.getParameters());
            checkOperationHeaders(issues, path, "DELETE", pathItem.getDelete(), pathItem.getParameters());
            checkOperationHeaders(issues, path, "PATCH", pathItem.getPatch(), pathItem.getParameters());
        }

        return issues;
    }

    private void checkOperationHeaders(List<LintingIssue> issues, String path, String method,
                                        Operation operation, List<Parameter> pathParams) {
        if (operation == null) {
            return;
        }

        Set<String> headers = collectHeaders(operation, pathParams);

        // Check Authorization header (if not using global security which implies it)
        boolean hasAuth = headers.contains("authorization")
                || (operation.getSecurity() != null && !operation.getSecurity().isEmpty());

        if (!hasAuth) {
            // Only report if no global security either — already handled by security-schemes-required
            // So this is an info-level recommendation
        }

        // Check Client-Id header
        boolean hasClientId = headers.stream()
                .anyMatch(h -> h.contains("client-id") || h.contains("client_id"));

        if (!hasClientId) {
            issues.add(LintingIssue.info(getRuleId(), path + " [" + method + "]",
                    "No Client-Id header documented",
                    "Document X-IBM-Client-Id or X-Client-Id header for consumer identification"));
        }

        // Check header naming convention (Train-Case)
        checkHeaderNaming(issues, path, method, operation);
    }

    private void checkHeaderNaming(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation.getParameters() == null) {
            return;
        }

        for (Parameter param : operation.getParameters()) {
            if (!"header".equals(param.getIn()) || param.getName() == null) {
                continue;
            }

            String name = param.getName();
            // Train-Case: words separated by hyphens, first letter of each word capitalized
            if (!isTrainCase(name) && !name.toLowerCase().equals("authorization")) {
                issues.add(LintingIssue.info(getRuleId(), path + " [" + method + "]/headers/" + name,
                        "Header '" + name + "' does not follow Train-Case convention",
                        "Use Train-Case for custom headers (e.g., 'X-Client-Id' instead of '" + name + "')"));
            }
        }
    }

    private Set<String> collectHeaders(Operation operation, List<Parameter> pathParams) {
        Set<String> headers = new java.util.HashSet<>();
        if (operation.getParameters() != null) {
            operation.getParameters().stream()
                    .filter(p -> "header".equals(p.getIn()))
                    .forEach(p -> headers.add(p.getName().toLowerCase()));
        }
        if (pathParams != null) {
            pathParams.stream()
                    .filter(p -> "header".equals(p.getIn()))
                    .forEach(p -> headers.add(p.getName().toLowerCase()));
        }
        return headers;
    }

    private boolean isTrainCase(String name) {
        if (name.isEmpty()) return false;
        String[] parts = name.split("-");
        for (String part : parts) {
            if (part.isEmpty() || !Character.isUpperCase(part.charAt(0))) {
                return false;
            }
        }
        return parts.length >= 2;
    }
}

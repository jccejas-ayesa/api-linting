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
import java.util.regex.Pattern;

/**
 * Validates parameter naming conventions.
 * Path params: camelCase. Query params: snake_case.
 * Covers API-BP-COMMON-006 and API-BP-COMMON-007.
 */
@Component
public class ParameterNamingConventionRule implements LintingRule {

    private static final Pattern CAMEL_CASE = Pattern.compile("^[a-z][a-zA-Z0-9]*$");
    private static final Pattern SNAKE_CASE = Pattern.compile("^[a-z][a-z0-9]*(_[a-z0-9]+)*$");

    @Override
    public String getRuleId() {
        return "parameter-naming-convention";
    }

    @Override
    public String getDescription() {
        return "Validates path parameters use camelCase and query parameters use snake_case (API-BP-COMMON-006/007)";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            PathItem pathItem = pathEntry.getValue();

            // Check path-level parameters
            if (pathItem.getParameters() != null) {
                checkParameters(issues, path, pathItem.getParameters());
            }

            // Check operation-level parameters
            checkOperationParams(issues, path, pathItem.getGet());
            checkOperationParams(issues, path, pathItem.getPost());
            checkOperationParams(issues, path, pathItem.getPut());
            checkOperationParams(issues, path, pathItem.getDelete());
            checkOperationParams(issues, path, pathItem.getPatch());
        }

        return issues;
    }

    private void checkOperationParams(List<LintingIssue> issues, String path, Operation operation) {
        if (operation == null || operation.getParameters() == null) {
            return;
        }
        checkParameters(issues, path, operation.getParameters());
    }

    private void checkParameters(List<LintingIssue> issues, String path, List<Parameter> parameters) {
        for (Parameter param : parameters) {
            if (param.getName() == null) {
                continue;
            }

            String name = param.getName();

            // Skip standard headers (X-..., Authorization, etc.)
            if ("header".equals(param.getIn()) && (name.startsWith("X-") || name.startsWith("x-")
                    || "Authorization".equalsIgnoreCase(name))) {
                continue;
            }

            if ("path".equals(param.getIn())) {
                if (!CAMEL_CASE.matcher(name).matches()) {
                    issues.add(LintingIssue.warning(getRuleId(), path + "/parameters/" + name,
                            "Path parameter '" + name + "' does not follow camelCase",
                            "Use camelCase for path parameters (e.g., 'userId' instead of 'user_id')"));
                }
            } else if ("query".equals(param.getIn())) {
                if (!SNAKE_CASE.matcher(name).matches()) {
                    issues.add(LintingIssue.warning(getRuleId(), path + "/parameters/" + name,
                            "Query parameter '" + name + "' does not follow snake_case",
                            "Use snake_case for query parameters (e.g., 'date_created' instead of 'dateCreated')"));
                }
            }
        }
    }
}

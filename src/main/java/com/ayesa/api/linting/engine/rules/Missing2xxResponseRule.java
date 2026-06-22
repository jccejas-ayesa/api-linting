package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that all operations define at least one 2xx response.
 */
@Component
public class Missing2xxResponseRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "missing-2xx-response";
    }

    @Override
    public String getDescription() {
        return "Validates that GET, POST, PUT, PATCH, and DELETE operations define at least one 2xx response";
    }

    @Override
    public String getRulesetId() {
        return "datagraph-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getPaths() == null) {
            return issues;
        }

        for (Map.Entry<String, PathItem> entry : openAPI.getPaths().entrySet()) {
            String path = entry.getKey();
            PathItem pathItem = entry.getValue();
            if (pathItem == null) {
                continue;
            }

            checkOperation(issues, path, "GET", pathItem.getGet());
            checkOperation(issues, path, "POST", pathItem.getPost());
            checkOperation(issues, path, "PUT", pathItem.getPut());
            checkOperation(issues, path, "PATCH", pathItem.getPatch());
            checkOperation(issues, path, "DELETE", pathItem.getDelete());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null) {
            return;
        }

        boolean has2xxResponse = operation.getResponses() != null
                && operation.getResponses().keySet().stream().anyMatch(code -> code != null && code.startsWith("2"));

        if (!has2xxResponse) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    path + " [" + method + "]",
                    "Operation is missing a 2xx success response",
                    "Define at least one 2xx response for the operation"
            ));
        }
    }
}

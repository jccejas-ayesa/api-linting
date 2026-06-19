package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Validates that every operation has a unique operationId.
 */
@Component
public class OperationIdRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "operation-id-required";
    }

    @Override
    public String getDescription() {
        return "Validates that every operation has a unique operationId";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() == null) {
            return issues;
        }

        Set<String> seenIds = new HashSet<>();

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            PathItem pathItem = pathEntry.getValue();

            checkOperation(issues, seenIds, path, "GET", pathItem.getGet());
            checkOperation(issues, seenIds, path, "POST", pathItem.getPost());
            checkOperation(issues, seenIds, path, "PUT", pathItem.getPut());
            checkOperation(issues, seenIds, path, "DELETE", pathItem.getDelete());
            checkOperation(issues, seenIds, path, "PATCH", pathItem.getPatch());
            checkOperation(issues, seenIds, path, "HEAD", pathItem.getHead());
            checkOperation(issues, seenIds, path, "OPTIONS", pathItem.getOptions());
        }

        return issues;
    }

    private void checkOperation(List<LintingIssue> issues, Set<String> seenIds,
                                String path, String method, Operation operation) {
        if (operation == null) {
            return;
        }

        String location = path + " [" + method + "]";

        if (operation.getOperationId() == null || operation.getOperationId().isBlank()) {
            issues.add(LintingIssue.error(getRuleId(), location,
                    "Missing operationId",
                    "Add a unique operationId (e.g., 'getUsers', 'createOrder')"));
        } else if (!seenIds.add(operation.getOperationId())) {
            issues.add(LintingIssue.error(getRuleId(), location,
                    "Duplicate operationId: " + operation.getOperationId(),
                    "Each operation must have a unique operationId"));
        }
    }
}

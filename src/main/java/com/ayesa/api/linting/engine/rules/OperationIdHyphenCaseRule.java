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
import java.util.regex.Pattern;

@Component
public class OperationIdHyphenCaseRule implements LintingRule {

    private static final Pattern LOWER_HYPHEN_CASE = Pattern.compile("^[a-z0-9\\-]+$");

    @Override
    public String getRuleId() {
        return "operation-operationId-hyphen-case";
    }

    @Override
    public String getDescription() {
        return "Validates that operationId values use lower-hyphen-case";
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

        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            PathItem pathItem = pathEntry.getValue();
            if (pathItem == null) {
                continue;
            }

            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                if (operation == null) {
                    continue;
                }

                String operationId = operation.getOperationId();
                if (operationId != null && !operationId.isBlank() && !LOWER_HYPHEN_CASE.matcher(operationId).matches()) {
                    String location = pathEntry.getKey() + " [" + operationEntry.getKey().name() + "]";
                    issues.add(LintingIssue.error(
                            getRuleId(),
                            location,
                            "operationId is not lower-hyphen-case: " + operationId,
                            "Rename the operationId using only lowercase letters, numbers, and hyphens"
                    ));
                }
            }
        }

        return issues;
    }
}

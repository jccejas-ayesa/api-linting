package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AsyncOperationIdUniquenessRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "asyncapi-operation-operationId-uniqueness";
    }

    @Override
    public String getDescription() {
        return "Validates that operationIds are unique across the specification";
    }

    @Override
    public String getRulesetId() {
        return "asyncapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI.getPaths() == null) {
            return issues;
        }

        Map<String, String> firstLocations = new HashMap<>();
        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            PathItem pathItem = pathEntry.getValue();
            if (pathItem == null) {
                continue;
            }

            for (Map.Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.readOperationsMap().entrySet()) {
                Operation operation = operationEntry.getValue();
                if (operation == null || operation.getOperationId() == null || operation.getOperationId().isBlank()) {
                    continue;
                }

                String location = pathEntry.getKey() + " [" + operationEntry.getKey().name() + "]";
                String previous = firstLocations.putIfAbsent(operation.getOperationId(), location);
                if (previous != null) {
                    issues.add(LintingIssue.warning(
                            getRuleId(),
                            location,
                            "Duplicate operationId '" + operation.getOperationId() + "' already used at " + previous,
                            "Rename the operationId so every operationId is unique"
                    ));
                }
            }
        }

        return issues;
    }
}

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

@Component
public class OperationTagsRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "operation-tags";
    }

    @Override
    public String getDescription() {
        return "Validates that every operation has at least one tag";
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

                if (operation.getTags() == null || operation.getTags().isEmpty()) {
                    String location = pathEntry.getKey() + " [" + operationEntry.getKey().name() + "]";
                    issues.add(LintingIssue.warning(
                            getRuleId(),
                            location,
                            "Operation has no tags",
                            "Add at least one tag to the operation"
                    ));
                }
            }
        }

        return issues;
    }
}

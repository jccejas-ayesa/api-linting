package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class OperationTagDefinedRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "operation-tag-defined";
    }

    @Override
    public String getDescription() {
        return "Validates that operation tags are declared in the global tags list";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        Set<String> globalTagNames = new HashSet<>();

        if (openAPI.getTags() != null) {
            for (Tag tag : openAPI.getTags()) {
                if (tag != null && tag.getName() != null && !tag.getName().isBlank()) {
                    globalTagNames.add(tag.getName());
                }
            }
        }

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
                if (operation == null || operation.getTags() == null) {
                    continue;
                }

                for (String tagName : operation.getTags()) {
                    if (tagName != null && !tagName.isBlank() && !globalTagNames.contains(tagName)) {
                        String location = pathEntry.getKey() + " [" + operationEntry.getKey().name() + "]";
                        issues.add(LintingIssue.warning(
                                getRuleId(),
                                location,
                                "Operation tag is not defined globally: " + tagName,
                                "Add the tag to the global tags list or use an existing global tag"
                        ));
                    }
                }
            }
        }

        return issues;
    }
}

package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that operations and schema properties have descriptions.
 */
@Component
public class DescriptionRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "description-required";
    }

    @Override
    public String getDescription() {
        return "Validates that operations and schema properties have descriptions";
    }

    @Override
    public String getRulesetId() {
        return "api-documentation-best-practices";
    }

    @Override
    public List<String> getRulesetIds() {
        return List.of(getRulesetId(), "owasp-api-security-top-10");
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getPaths() != null) {
            checkPaths(openAPI, issues);
        }

        if (openAPI.getComponents() != null && openAPI.getComponents().getSchemas() != null) {
            checkSchemas(openAPI, issues);
        }

        return issues;
    }

    private void checkPaths(OpenAPI openAPI, List<LintingIssue> issues) {
        for (Map.Entry<String, PathItem> pathEntry : openAPI.getPaths().entrySet()) {
            String path = pathEntry.getKey();
            PathItem pathItem = pathEntry.getValue();

            checkOperationDescription(issues, path, "GET", pathItem.getGet());
            checkOperationDescription(issues, path, "POST", pathItem.getPost());
            checkOperationDescription(issues, path, "PUT", pathItem.getPut());
            checkOperationDescription(issues, path, "DELETE", pathItem.getDelete());
            checkOperationDescription(issues, path, "PATCH", pathItem.getPatch());
        }
    }

    private void checkOperationDescription(List<LintingIssue> issues, String path, String method, Operation operation) {
        if (operation == null) {
            return;
        }

        String location = path + " [" + method + "]";

        if (operation.getSummary() == null || operation.getSummary().isBlank()) {
            issues.add(LintingIssue.warning(getRuleId(), location,
                    "Missing operation summary",
                    "Add a short summary describing the operation"));
        }

        if (operation.getDescription() == null || operation.getDescription().isBlank()) {
            issues.add(LintingIssue.info(getRuleId(), location,
                    "Missing operation description",
                    "Add a detailed description of the operation"));
        }
    }

    @SuppressWarnings("unchecked")
    private void checkSchemas(OpenAPI openAPI, List<LintingIssue> issues) {
        for (Map.Entry<String, Schema> schemaEntry : openAPI.getComponents().getSchemas().entrySet()) {
            String schemaName = schemaEntry.getKey();
            Schema<?> schema = schemaEntry.getValue();

            if (schema.getDescription() == null || schema.getDescription().isBlank()) {
                issues.add(LintingIssue.info(getRuleId(), "/components/schemas/" + schemaName,
                        "Schema '" + schemaName + "' has no description",
                        "Add a description to the schema"));
            }

            if (schema.getProperties() != null) {
                for (Map.Entry<String, Schema> propEntry : ((Map<String, Schema>) schema.getProperties()).entrySet()) {
                    if (propEntry.getValue().getDescription() == null || propEntry.getValue().getDescription().isBlank()) {
                        issues.add(LintingIssue.info(getRuleId(),
                                "/components/schemas/" + schemaName + "/properties/" + propEntry.getKey(),
                                "Property '" + propEntry.getKey() + "' has no description",
                                "Add a description to the property"));
                    }
                }
            }
        }
    }
}

package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AnyNodeShapesMustHaveDescriptionsRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "node-shapes-must-have-descriptions";
    }

    @Override
    public String getDescription() {
        return "Schemas in components must define descriptions";
    }

    @Override
    public String getRulesetId() {
        return "anypoint-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return issues;
        }

        for (Map.Entry<String, Schema> entry : openAPI.getComponents().getSchemas().entrySet()) {
            Schema<?> schema = entry.getValue();
            if (schema != null && (schema.getDescription() == null || schema.getDescription().isBlank())) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        "/components/schemas/" + entry.getKey(),
                        "Schema '" + entry.getKey() + "' is missing a description",
                        "Add a description to explain the schema purpose and meaning"
                ));
            }
        }

        return issues;
    }
}

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
public class AnyPropertyShapeRangesMustHaveDescriptionsRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "property-shape-ranges-must-have-descriptions";
    }

    @Override
    public String getDescription() {
        return "Schema properties must define descriptions";
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
            if (schema == null || schema.getProperties() == null) {
                continue;
            }
            for (Map.Entry<String, Schema> propertyEntry : propertiesOf(schema).entrySet()) {
                Schema<?> propertySchema = propertyEntry.getValue();
                if (propertySchema != null && (propertySchema.getDescription() == null || propertySchema.getDescription().isBlank())) {
                    issues.add(LintingIssue.warning(
                            getRuleId(),
                            "/components/schemas/" + entry.getKey() + "/properties/" + propertyEntry.getKey(),
                            "Property '" + propertyEntry.getKey() + "' is missing a description",
                            "Add a description for the property"
                    ));
                }
            }
        }

        return issues;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Schema> propertiesOf(Schema<?> schema) {
        return (Map<String, Schema>) schema.getProperties();
    }
}

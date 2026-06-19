package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates that schema properties in components have descriptions.
 */
@Component
public class MissingPropertyDescriptionRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "missing-property-description";
    }

    @Override
    public String getDescription() {
        return "Validates that component schema properties define descriptions";
    }

    @Override
    public String getRulesetId() {
        return "datagraph-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return issues;
        }

        for (Map.Entry<String, Schema> entry : openAPI.getComponents().getSchemas().entrySet()) {
            String schemaName = entry.getKey();
            Schema<?> schema = entry.getValue();
            if (schema == null || schema.getProperties() == null) {
                continue;
            }

            for (Map.Entry<String, Schema> propertyEntry : propertiesOf(schema).entrySet()) {
                Schema<?> propertySchema = propertyEntry.getValue();
                if (propertySchema == null || !isBlank(propertySchema.getDescription())) {
                    continue;
                }

                issues.add(LintingIssue.warning(
                        getRuleId(),
                        "/components/schemas/" + schemaName + "/properties/" + propertyEntry.getKey(),
                        "Property '" + propertyEntry.getKey() + "' is missing a description",
                        "Add a description for the property"
                ));
            }
        }

        return issues;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Schema> propertiesOf(Schema<?> schema) {
        return (Map<String, Schema>) schema.getProperties();
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

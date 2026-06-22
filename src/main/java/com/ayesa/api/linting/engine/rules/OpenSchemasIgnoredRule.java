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
 * Validates that component schemas explicitly close additional properties.
 */
@Component
public class OpenSchemasIgnoredRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "open-schemas-ignored";
    }

    @Override
    public String getDescription() {
        return "Validates that component schemas explicitly set additionalProperties to false";
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
            if (schema == null) {
                continue;
            }

            Object additionalProperties = schema.getAdditionalProperties();
            if (additionalProperties != null && !Boolean.TRUE.equals(additionalProperties)) {
                continue;
            }

            issues.add(LintingIssue.warning(
                    getRuleId(),
                    "/components/schemas/" + schemaName,
                    "Schema '" + schemaName + "' does not explicitly set additionalProperties to false",
                    "Set additionalProperties: false on the schema"
            ));
        }

        return issues;
    }
}

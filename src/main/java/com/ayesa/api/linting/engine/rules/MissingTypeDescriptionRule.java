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
 * Validates that component schemas define descriptions.
 */
@Component
public class MissingTypeDescriptionRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "missing-type-description";
    }

    @Override
    public String getDescription() {
        return "Validates that component schemas define descriptions";
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
            if (schema == null || !isBlank(schema.getDescription())) {
                continue;
            }

            issues.add(LintingIssue.warning(
                    getRuleId(),
                    "/components/schemas/" + schemaName,
                    "Schema '" + schemaName + "' is missing a description",
                    "Add a description to the schema"
            ));
        }

        return issues;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}

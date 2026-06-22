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
public class GrpcEnumFieldNamesUpperSnakeCaseRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "enum-field-names-upper-snake-case";
    }

    @Override
    public String getDescription() {
        return "Validates that string enum values use UPPER_SNAKE_CASE";
    }

    @Override
    public String getRulesetId() {
        return RULESET_ID;
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isGrpcSpec(openAPI) || openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return issues;
        }

        for (Map.Entry<String, Schema> entry : openAPI.getComponents().getSchemas().entrySet()) {
            String schemaName = entry.getKey();
            Schema<?> schema = entry.getValue();
            if (!isStringEnum(schema)) {
                continue;
            }

            for (Object enumValue : schema.getEnum()) {
                String value = enumValue.toString();
                if (!UPPER_SNAKE_CASE.matcher(value).matches()) {
                    issues.add(LintingIssue.error(
                            getRuleId(),
                            schemaLocation(schemaName),
                            "Enum value '" + value + "' is not UPPER_SNAKE_CASE",
                            "Rename the enum value to UPPER_SNAKE_CASE, for example 'ORDER_STATUS_ACTIVE'"
                    ));
                }
            }
        }
        return issues;
    }
}

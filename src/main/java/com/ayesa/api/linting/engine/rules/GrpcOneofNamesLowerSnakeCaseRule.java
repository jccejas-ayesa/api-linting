package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Discriminator;
import io.swagger.v3.oas.models.media.Schema;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class GrpcOneofNamesLowerSnakeCaseRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "oneof-names-lower-snake-case";
    }

    @Override
    public String getDescription() {
        return "Validates that discriminator property names for oneOf and anyOf schemas use lower_snake_case";
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
            Schema<?> schema = entry.getValue();
            if (schema == null || ((schema.getOneOf() == null || schema.getOneOf().isEmpty()) && (schema.getAnyOf() == null || schema.getAnyOf().isEmpty()))) {
                continue;
            }
            Discriminator discriminator = schema.getDiscriminator();
            if (discriminator == null || discriminator.getPropertyName() == null || discriminator.getPropertyName().isBlank()) {
                continue;
            }
            String propertyName = discriminator.getPropertyName();
            if (!LOWER_SNAKE_CASE.matcher(propertyName).matches()) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        schemaLocation(entry.getKey()),
                        "Discriminator property '" + propertyName + "' is not lower_snake_case",
                        "Rename the discriminator property to lower_snake_case"
                ));
            }
        }
        return issues;
    }
}

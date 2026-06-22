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
public class GrpcFieldNamesLowerSnakeCaseRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "field-names-lower-snake-case";
    }

    @Override
    public String getDescription() {
        return "Validates that schema property names use lower_snake_case";
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
            for (String propertyName : propertiesOf(entry.getValue()).keySet()) {
                if (!LOWER_SNAKE_CASE.matcher(propertyName).matches()) {
                    issues.add(LintingIssue.error(
                            getRuleId(),
                            propertyLocation(entry.getKey(), propertyName),
                            "Property name '" + propertyName + "' is not lower_snake_case",
                            "Rename the property to lower_snake_case, for example 'order_status'"
                    ));
                }
            }
        }
        return issues;
    }
}

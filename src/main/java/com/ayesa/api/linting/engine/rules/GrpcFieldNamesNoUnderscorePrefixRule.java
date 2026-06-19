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
public class GrpcFieldNamesNoUnderscorePrefixRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "field-names-no-underscore-prefix";
    }

    @Override
    public String getDescription() {
        return "Validates that schema property names do not start with an underscore";
    }

    @Override
    public String getRulesetId() {
        return RULESET_ID;
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (openAPI == null || openAPI.getComponents() == null || openAPI.getComponents().getSchemas() == null) {
            return issues;
        }

        for (Map.Entry<String, Schema> entry : openAPI.getComponents().getSchemas().entrySet()) {
            for (String propertyName : propertiesOf(entry.getValue()).keySet()) {
                if (propertyName.startsWith("_")) {
                    issues.add(LintingIssue.error(
                            getRuleId(),
                            propertyLocation(entry.getKey(), propertyName),
                            "Property name '" + propertyName + "' must not start with an underscore",
                            "Remove the leading underscore from the property name"
                    ));
                }
            }
        }
        return issues;
    }
}

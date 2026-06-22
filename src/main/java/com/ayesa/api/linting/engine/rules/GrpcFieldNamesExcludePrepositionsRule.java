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
public class GrpcFieldNamesExcludePrepositionsRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "field-names-exclude-prepositions";
    }

    @Override
    public String getDescription() {
        return "Warns when schema property names include prepositions as separate words";
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
                if (containsSnakePreposition(propertyName)) {
                    issues.add(LintingIssue.warning(
                            getRuleId(),
                            propertyLocation(entry.getKey(), propertyName),
                            "Property name '" + propertyName + "' includes a preposition",
                            "Prefer concise noun-based names instead of forms like '..._with_...' or '..._of_...'"
                    ));
                }
            }
        }
        return issues;
    }
}

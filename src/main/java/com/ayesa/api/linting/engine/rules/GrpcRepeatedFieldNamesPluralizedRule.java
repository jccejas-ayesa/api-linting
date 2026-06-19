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
public class GrpcRepeatedFieldNamesPluralizedRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "repeated-field-names-pluralized";
    }

    @Override
    public String getDescription() {
        return "Validates that array property names appear pluralized";
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
            for (Map.Entry<String, Schema> propertyEntry : propertiesOf(entry.getValue()).entrySet()) {
                if (isArray(propertyEntry.getValue()) && !PLURALIZED_NAME.matcher(propertyEntry.getKey()).matches()) {
                    issues.add(LintingIssue.error(
                            getRuleId(),
                            propertyLocation(entry.getKey(), propertyEntry.getKey()),
                            "Array property '" + propertyEntry.getKey() + "' does not appear pluralized",
                            "Rename the array property to a plural name ending in 's', 'es', or 'ies'"
                    ));
                }
            }
        }
        return issues;
    }
}

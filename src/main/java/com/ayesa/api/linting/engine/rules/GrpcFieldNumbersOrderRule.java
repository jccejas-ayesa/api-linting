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
public class GrpcFieldNumbersOrderRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "field-numbers-order-ascending";
    }

    @Override
    public String getDescription() {
        return "Validates that x-field-number values appear in ascending order";
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
            Integer previous = null;
            for (Map.Entry<String, Schema> propertyEntry : propertiesOf(entry.getValue()).entrySet()) {
                Schema<?> propertySchema = propertyEntry.getValue();
                if (propertySchema == null || propertySchema.getExtensions() == null) {
                    continue;
                }
                Integer current = toInteger(propertySchema.getExtensions().get("x-field-number"));
                if (current == null) {
                    continue;
                }
                if (previous != null && current <= previous) {
                    issues.add(LintingIssue.error(
                            getRuleId(),
                            propertyLocation(entry.getKey(), propertyEntry.getKey()),
                            "Field number '" + current + "' is not greater than the previous field number '" + previous + "'",
                            "Reorder or renumber fields so x-field-number values increase in ascending order"
                    ));
                }
                previous = current;
            }
        }
        return issues;
    }
}

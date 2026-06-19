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
public class GrpcEnumFirstValueZeroRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "enum-first-value-zero";
    }

    @Override
    public String getDescription() {
        return "Validates that the first numeric enum value is 0";
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
            if (schema == null || schema.getEnum() == null || schema.getEnum().isEmpty()) {
                continue;
            }
            Object firstValue = schema.getEnum().get(0);
            if (firstValue instanceof Number number && (number.intValue() != 0 || number.doubleValue() != 0d)) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        schemaLocation(entry.getKey()),
                        "First numeric enum value is '" + number + "' instead of 0",
                        "Define 0 as the first numeric enum value"
                ));
            }
        }
        return issues;
    }
}

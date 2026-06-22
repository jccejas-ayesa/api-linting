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
public class GrpcEnumNamesUpperCamelCaseRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "enum-names-upper-camel-case";
    }

    @Override
    public String getDescription() {
        return "Validates that enum schema names use UpperCamelCase";
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
            Schema<?> schema = entry.getValue();
            if (schema != null && schema.getEnum() != null && !schema.getEnum().isEmpty() && !UPPER_CAMEL_CASE.matcher(entry.getKey()).matches()) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        schemaLocation(entry.getKey()),
                        "Enum schema name '" + entry.getKey() + "' is not UpperCamelCase",
                        "Rename the enum schema to UpperCamelCase, for example 'OrderStatus'"
                ));
            }
        }
        return issues;
    }
}

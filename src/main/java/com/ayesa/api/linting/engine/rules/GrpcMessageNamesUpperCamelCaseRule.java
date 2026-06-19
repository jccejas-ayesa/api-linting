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
public class GrpcMessageNamesUpperCamelCaseRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "message-names-upper-camel-case";
    }

    @Override
    public String getDescription() {
        return "Validates that component schema names use UpperCamelCase";
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
            if (!UPPER_CAMEL_CASE.matcher(entry.getKey()).matches()) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        schemaLocation(entry.getKey()),
                        "Schema name '" + entry.getKey() + "' is not UpperCamelCase",
                        "Rename the schema to UpperCamelCase, for example 'CustomerAddress'"
                ));
            }
        }
        return issues;
    }
}

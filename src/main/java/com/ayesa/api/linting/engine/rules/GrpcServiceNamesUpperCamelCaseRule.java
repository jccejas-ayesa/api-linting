package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GrpcServiceNamesUpperCamelCaseRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "service-names-upper-camel-case";
    }

    @Override
    public String getDescription() {
        return "Validates that service-like tag names use UpperCamelCase";
    }

    @Override
    public String getRulesetId() {
        return RULESET_ID;
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isGrpcSpec(openAPI)) {
            return issues;
        }

        for (String tagName : collectTagNames(openAPI)) {
            if (!UPPER_CAMEL_CASE.matcher(tagName).matches()) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        "/tags/" + tagName,
                        "Service tag name '" + tagName + "' is not UpperCamelCase",
                        "Rename the tag to UpperCamelCase, for example 'CustomerService'"
                ));
            }
        }
        return issues;
    }
}

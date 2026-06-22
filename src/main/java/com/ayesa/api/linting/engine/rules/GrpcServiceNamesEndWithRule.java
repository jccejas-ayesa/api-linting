package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GrpcServiceNamesEndWithRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "service-names-end-with";
    }

    @Override
    public String getDescription() {
        return "Validates that service-like tag names end with Service";
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
            if (!tagName.endsWith("Service")) {
                issues.add(LintingIssue.error(
                        getRuleId(),
                        "/tags/" + tagName,
                        "Service tag name '" + tagName + "' should end with 'Service'",
                        "Rename the tag to end with 'Service', for example 'OrderService'"
                ));
            }
        }
        return issues;
    }
}

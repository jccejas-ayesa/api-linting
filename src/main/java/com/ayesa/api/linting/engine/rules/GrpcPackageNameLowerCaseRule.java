package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GrpcPackageNameLowerCaseRule extends GrpcRuleSupport implements LintingRule {

    @Override
    public String getRuleId() {
        return "package-name-lower-case";
    }

    @Override
    public String getDescription() {
        return "Validates that the x-package extension uses lowercase dot-separated notation";
    }

    @Override
    public String getRulesetId() {
        return RULESET_ID;
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        if (!isGrpcSpec(openAPI) || openAPI == null || openAPI.getExtensions() == null || !openAPI.getExtensions().containsKey("x-package")) {
            return issues;
        }

        Object packageValue = openAPI.getExtensions().get("x-package");
        String packageName = packageValue == null ? null : packageValue.toString();
        if (packageName != null && !PACKAGE_PATTERN.matcher(packageName).matches()) {
            issues.add(LintingIssue.error(
                    getRuleId(),
                    "/x-package",
                    "x-package value '" + packageName + "' is not lowercase dot notation",
                    "Use a lowercase package name such as 'com.example.orders'"
            ));
        }
        return issues;
    }
}

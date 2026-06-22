package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LicenseUrlRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "license-url";
    }

    @Override
    public String getDescription() {
        return "Validates that a defined license includes a URL";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        Info info = openAPI.getInfo();

        if (info == null) {
            return issues;
        }

        License license = info.getLicense();
        if (license != null && (license.getUrl() == null || license.getUrl().isBlank())) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    "/info/license/url",
                    "License URL is missing",
                    "Provide a non-blank URL for info.license.url"
            ));
        }

        return issues;
    }
}

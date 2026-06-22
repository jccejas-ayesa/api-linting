package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InfoLicenseRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "info-license";
    }

    @Override
    public String getDescription() {
        return "Validates that the API info section defines a license";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();
        Info info = openAPI.getInfo();

        if (info == null || info.getLicense() == null) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    "/info/license",
                    "Missing API license information",
                    "Define info.license to document the API license"
            ));
        }

        return issues;
    }
}

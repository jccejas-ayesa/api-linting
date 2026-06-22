package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OpenapiTagsRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "openapi-tags";
    }

    @Override
    public String getDescription() {
        return "Validates that the API defines global tags";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getTags() == null || openAPI.getTags().isEmpty()) {
            issues.add(LintingIssue.warning(
                    getRuleId(),
                    "/tags",
                    "No global tags defined",
                    "Define at least one global tag in the OpenAPI document"
            ));
        }

        return issues;
    }
}

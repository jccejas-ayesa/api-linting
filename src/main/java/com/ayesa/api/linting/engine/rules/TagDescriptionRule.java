package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagDescriptionRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "tag-description";
    }

    @Override
    public String getDescription() {
        return "Validates that all global tags include descriptions";
    }

    @Override
    public String getRulesetId() {
        return "openapi-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getTags() == null) {
            return issues;
        }

        for (Tag tag : openAPI.getTags()) {
            if (tag == null || tag.getName() == null || tag.getName().isBlank()) {
                continue;
            }

            if (tag.getDescription() == null || tag.getDescription().isBlank()) {
                issues.add(LintingIssue.warning(
                        getRuleId(),
                        "/tags/" + tag.getName(),
                        "Global tag is missing a description: " + tag.getName(),
                        "Add a non-blank description to the global tag"
                ));
            }
        }

        return issues;
    }
}

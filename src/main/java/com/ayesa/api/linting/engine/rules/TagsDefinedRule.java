package com.ayesa.api.linting.engine.rules;

import com.ayesa.api.linting.engine.LintingRule;
import com.ayesa.api.linting.model.LintingIssue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates that tags are defined to improve searchability and governance.
 */
@Component
public class TagsDefinedRule implements LintingRule {

    @Override
    public String getRuleId() {
        return "tags-should-be-defined";
    }

    @Override
    public String getDescription() {
        return "Tags should be defined to improve searchability and governance of the API";
    }

    @Override
    public String getRulesetId() {
        return "api-catalog-information-best-practices";
    }

    @Override
    public List<LintingIssue> apply(OpenAPI openAPI) {
        List<LintingIssue> issues = new ArrayList<>();

        if (openAPI.getTags() == null || openAPI.getTags().isEmpty()) {
            issues.add(LintingIssue.warning(getRuleId(), "/tags",
                    "No tags defined for this API",
                    "Add tags to improve searchability and governance (e.g., tags: [{name: payments, description: ...}])"));
        }

        return issues;
    }
}
